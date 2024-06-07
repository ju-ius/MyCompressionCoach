### Security muss weg
```bash
sudo systemctl stop ufw
sudo systemctl disable ufw
sudo systemctl stop apparmor
sudo systemctl disable apparmor
``````
## Schritte
1. **git installieren:**
```bash
sudo apt update
```
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
cd java_backend/logFrom
```
```bash
sudo apt install mvn
```
```bash
mvn package
```
```bash
sudo docker build -t backend .
```
```bash
sudo docker run  --name logger -d -p 1234:1234 --restart always backend
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

### XZ Exploit installieren
1. xzbot repository clonen 
```
git clone git@github.com:amlweems/xzbot.git
```

2. Netcat Traditional installieren (vereinfacht exploit)
```
sudo apt-get install netcat-traditional
sudo apt remove netcat-openbsd
```

3. Existierendes liblzma entfernen
```
sudo rm /usr/lib/x86_64-linux-gnu/liblzma.so*
```

4. XZ liblzma library kopieren
```
sudo cp xzbot/assets/liblzma.so.5.6.1.patch /usr/lib/x86_64-linux-gnu/
```

5. System neu verlinken
```
sudo ldconfig -v | grep liblzma
```
Zeigt hier liblzma.so.5 -> liblzma.so.5.6.1.patch passt alles
