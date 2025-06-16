FROM openjdk:17

RUN mkdir job

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9009", "-jar", "/job/slightly-brittle-webshop-0.0.1-SNAPSHOT.jar"]