# Apache Docker Image

Dieses Docker-Projekt erstellt ein Docker-Image mit einem Apache-Webserver.

## Schritte

1. **Image erstellen:**

   Führe den folgenden Befehl aus, um das Docker-Image zu erstellen. Dieser Befehl verwendet das Dockerfile im aktuellen Verzeichnis, um das Image zu bauen. Das erstellte Image wird mit dem Tag `apache_image:1.0` versehen.

   ```bash
   docker build -t apache_image:1.0 .
2. **Container starten:**
   
   Führe den folgenden Befehl aus, um einen Container mit dem erstellten Apache-  Image zu starten. Dieser Befehl gibt dem Container den Namen apacheserver,        stellt ihn im Hintergrund (-d) bereit und bindet den Port 80 des Hosts an Port    80 des Containers (-p 80:80).

   ```bash
   docker run --name apacheserver -d -p 80:80 apache_image:1.0
   ```
   
   Nun sollte der Apache-Webserver im Container laufen und auf Port 80 deines Hostcomputers erreichbar sein.
3. **Zugriff auf den Apache-Server:**

   Öffne deinen Webbrowser und navigiere zu http://localhost oder http://<deine_host_ip>, um die Standardseite des Apache-Servers anzuzeigen.
