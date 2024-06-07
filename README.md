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
```bash
git clone git@github.com:amlweems/xzbot.git
```

2. Netcat Traditional installieren (vereinfacht exploit)
```bash
sudo apt-get install netcat-traditional
sudo apt remove netcat-openbsd
```

3. Existierendes liblzma entfernen
```bash
sudo rm /usr/lib/x86_64-linux-gnu/liblzma.so*
```

4. XZ liblzma library kopieren
```bash
sudo cp xzbot/assets/liblzma.so.5.6.1.patch /usr/lib/x86_64-linux-gnu/
```

5. System neu verlinken
```bash
sudo ldconfig -v | grep liblzma
```
Zeigt hier liblzma.so.5 -> liblzma.so.5.6.1.patch passt alles

## Flag 3 Creation
```bash
cat micha.gif >> neuesLogo.gif
cat flag3.zip >> neuesLogo.gif

# Flagge extrahieren:
unzip neuesLogo.gif
```

## User passwörter:
micha: ichLiebeCompressionCoach2024!!
julius: ArrIchBinEinPirat0815!
nils: ¡IchMacheKeinMasterHihi5612!
robert: ichbinDerScherenmann03!

osboxes.org: sx[E9jVu>O-`?JS7

## Flags:
1. THM{ab0bfd73daaec7912dcdca1ba0ba3d05}
2. THM{9a48ddad2656385fce58af47a0ef56cf}
3. THM{6811597e100f778019a5363fe01a24c7}
