FROM openjdk:11

COPY *.jar /app.jar

CMD ["--server.port=8081"]

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]
