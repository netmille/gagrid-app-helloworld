FROM openjdk:8-jdk-alpine
LABEL maintainer="turik@netmille.com"
COPY target/gagrid-app-helloworld-0.0.1-SNAPSHOT.jar gagrid-app-helloworld-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/gagrid-app-helloworld-0.0.1-SNAPSHOT.jar"]
