FROM openjdk:8-alpine

EXPOSE 3000

ADD build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
