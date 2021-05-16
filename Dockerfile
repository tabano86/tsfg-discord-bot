FROM openjdk:15-jdk-alpine
COPY --from=build /target/dicord-bot-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]