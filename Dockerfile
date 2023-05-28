FROM openjdk:8-jdk-alpine
LABEL maintainer="turik@netmille.com"
COPY target/gagrid-app-helloworld-0.1.1-beta.jar gagrid-app-helloworld-0.1.0-beta.jar
ENTRYPOINT ["java","-jar","/gagrid-app-helloworld-0.1.1-beta.jar"]
