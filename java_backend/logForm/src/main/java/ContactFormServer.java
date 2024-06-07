import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactFormServer {

    private static final Logger logger = LogManager.getLogger(ContactFormServer.class);
    private static final AtomicInteger forbiddenCounter = new AtomicInteger(0); // Counter to track forbidden responses

    public static void main(String[] args) throws IOException {
        logger.info("5b 3a fe 03 87 8a 49 b2 82 32 d4 f1 a4 42 ae bd e1 09 f8 07 ac ef 7d fd 9a 7f 65 b9 62 fe 52 d6 54 73 12 ca ce cf f0 43 37 50 8f 9d 25 29 a8 f1 66 91 69 b2 1c 32 c4 80 00");
        logger.info("THM{ab0bfd73daaec7912dcdca1ba0ba3d05}");
        int port = 1234; // Change this to your desired port

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/contact", new ContactFormHandler());
        server.createContext("/secret", new FileCheckHandler()); // New endpoint
        server.setExecutor(null); // creates a default executor
        server.start();

        logger.info("Server started on port " + port);
    }

    static class ContactFormHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Get the request method (e.g., POST, GET)
            String requestMethod = exchange.getRequestMethod();

            // Set CORS headers
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Content-Type");

            if ("POST".equalsIgnoreCase(requestMethod)) {
                // Log the contact form
                InputStream requestBody = exchange.getRequestBody();
                String boundary = extractBoundary(exchange.getRequestHeaders().getFirst("Content-Type"));
                Map<String, String> formData = parseFormData(requestBody, boundary);

                logger.info("Received contact form:");
                formData.forEach((key, value) -> {
                    logger.info(key + ":");
                    logger.info(value);
                });

                // Send JSON response to client
                String response = "{\"message\": \"Contact form received successfully!\"}";
                headers.set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else if ("OPTIONS".equalsIgnoreCase(requestMethod)) {
                // Handle preflight request for CORS
                exchange.sendResponseHeaders(204, -1); // No Content
            } else {
                // Handle other HTTP methods if needed
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }

        private String extractBoundary(String contentType) {
            String[] parts = contentType.split(";");
            for (String part : parts) {
                part = part.trim();
                if (part.startsWith("boundary=")) {
                    return part.substring("boundary=".length());
                }
            }
            return null;
        }

        private Map<String, String> parseFormData(InputStream requestBody, String boundary) throws IOException {
            Map<String, String> formData = new HashMap<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            String line;
            String fieldName = null;
            StringBuilder fieldValue = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("--" + boundary)) {
                    if (fieldName != null) {
                        formData.put(fieldName, fieldValue.toString().trim());
                        fieldValue.setLength(0); // Reset the StringBuilder
                    }
                    fieldName = null;
                } else if (line.startsWith("Content-Disposition: form-data; name=")) {
                    fieldName = line.split("name=\"")[1].split("\"")[0];
                } else if (!line.isEmpty() && fieldName != null) {
                    if (fieldValue.length() > 0) {
                        fieldValue.append("\n");
                    }
                    fieldValue.append(line);
                }
            }
            if (fieldName != null) {
                formData.put(fieldName, fieldValue.toString().trim());
            }

            return formData;
        }
    }

    static class FileCheckHandler implements HttpHandler {
        private static final Logger logger = LogManager.getLogger(FileCheckHandler.class);
        private static final AtomicInteger forbiddenCounter = new AtomicInteger(0); // Counter to track forbidden responses

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET");
            headers.add("Access-Control-Allow-Headers", "Content-Type");

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Check if the file exists
                boolean fileExists = Files.exists(Paths.get("password.txt"));
                logger.info("File 'password.txt' exists: " + fileExists);

                // Read password from password.txt if it exists
                String passwordFromFile = fileExists ? readPasswordFromFile() : null;
                logger.info("Password from file: " + passwordFromFile);

                // Get provided password from query parameters
                String providedPassword = getQueryParam(exchange.getRequestURI().getQuery(), "password");
                logger.info("Provided password: " + providedPassword);

                if (fileExists) {
                    // Password file exists
                    if (providedPassword != null) {
                        // Password provided
                        if (providedPassword.equals(passwordFromFile)) {
                            // Correct password provided
                            logger.info("Correct password provided.");
                            forbiddenCounter.set(0); // Reset the counter
                            sendLogFile(exchange); // Send server.log file
                        } else {
                            // Incorrect password provided
                            logger.info("Incorrect password provided.");
                            handleForbiddenResponse(exchange, "forbidden");
                        }
                    } else {
                        // No password provided
                        logger.info("No password provided.");
                        handleForbiddenResponse(exchange, "forbidden");
                    }
                } else {
                    // No password file, respond with "lötfanclub"
                    logger.info("Password file does not exist. ");
                    forbiddenCounter.set(0); // Reset the counter
                    sendLogFile(exchange); // Send server.log file
                }
            } else {
                // Handle unsupported methods
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }

        private String getQueryParam(String query, String paramName) {
            if (query != null) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2 && keyValue[0].equals(paramName)) {
                        return keyValue[1];
                    }
                }
            }
            return null;
        }

        private void sendResponse(HttpExchange exchange, String response) throws IOException {
            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(response.getBytes());
            }
        }

        private void handleForbiddenResponse(HttpExchange exchange, String response) throws IOException {
            if (forbiddenCounter.incrementAndGet() >= 3) {
                sendResponse(exchange, "The provided password doesn't match the password in password.txt");
                forbiddenCounter.set(0); // Reset the counter after reaching the threshold
            } else {
                sendResponse(exchange, response);
            }
        }

        private String readPasswordFromFile() throws IOException {
            // Read all lines from password.txt
            List<String> lines = Files.readAllLines(Paths.get("password.txt"));
            // Assuming password.txt contains only the password on the first line
            return lines.get(0).trim();
        }

        private void sendLogFile(HttpExchange exchange) throws IOException {
            // Read the contents of server.log file
            List<String> logLines = Files.readAllLines(Paths.get("/opt/app/server.log"));
            StringBuilder logContent = new StringBuilder();
            for (String line : logLines) {
                logContent.append(line).append("\n");
            }
            // Send the contents of server.log file as response
            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, logContent.toString().getBytes().length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(logContent.toString().getBytes());
            }
        }
    }

}
