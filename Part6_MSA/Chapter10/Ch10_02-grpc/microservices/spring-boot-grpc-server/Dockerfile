FROM openjdk:8u121-jdk

RUN mkdir /app
WORKDIR /app

ADD ./build/libs/*.jar /app/app.jar
ENV SERVER_PORT="18080"
EXPOSE 18080
ENTRYPOINT ["java", "-jar", "app.jar"]