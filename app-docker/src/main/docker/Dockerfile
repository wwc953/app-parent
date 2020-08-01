FROM lwieske/java-8:latest
VOLUME /tmp
ADD app-docker.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
