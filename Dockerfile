FROM openjdk:17.0.1-jdk-slim

ARG JAR_FILE=build/libs/yandex-lavka-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} yandex-lavka-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","yandex-lavka-0.0.1-SNAPSHOT.jar"]