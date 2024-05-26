package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class MainController {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    public static void main(String[] args) throws IOException {
        int port = 8080; // Change this to your desired port

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new ContactFormHandler());
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
}
