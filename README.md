# Apache Docker Image

Dieses Docker-Projekt erstellt ein Docker-Image mit einem Apache-Webserver.

## Schritte
1. **git installieren:**
```bash
sudo apt update
``````
```bash
sudo apt install git
```
2. **docker installieren:**
```bash
sudo apt update
```
```bash
sudo apt install apt-transport-https ca-certificates curl software-properties-common
```
```bash
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```
```bash
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
```
```bash
sudo apt update
```
```bash
sudo apt install docker-ce
```
**überprüfen ob installation funktioniert hat:**
```bash
sudo systemctl start docker
sudo docker run hello-world
``````
3. **git clone:**
```bash
git clone https://github.com/ju-ius/MyCompressionCoach/tree/main
```

4. **backend**
```bash
cd java_backend
```
```bash
sudo docker build -t backend .
```
```bash
sudo docker run --name logger -d --restart always backend
```
5. **frontend**
```bash
sudo docker build -t frontend .
```
```bash
docker run --name frontend -d -p 80:80  --restart always frontend
```

6.
- repository löschen
- docker rechte nur mit sudo, user kein sudo geben