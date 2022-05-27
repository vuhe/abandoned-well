FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080

ADD output.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Xmx256m","-jar","/app.jar","prod"]
