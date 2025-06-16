FROM maven:3.6.3-openjdk-17-slim

RUN mkdir project

ENTRYPOINT ["/usr/bin/mvn", "-f", "/project/", "package", "-DskipTests"]