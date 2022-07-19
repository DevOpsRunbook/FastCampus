FROM openjdk:8-alpine

EXPOSE 8080

ADD build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
