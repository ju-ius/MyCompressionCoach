FROM openjdk:8u181-jdk-alpine
ARG JAR_FILE=logForm-1.0-SNAPSHOT.jar

WORKDIR /opt/app

COPY ./${JAR_FILE} /opt/app/
COPY password.txt /opt/app/

ENTRYPOINT ["java", "-jar", "logForm-1.0-SNAPSHOT.jar"]
