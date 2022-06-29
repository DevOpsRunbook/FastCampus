FROM openjdk:8u121-jdk

RUN mkdir /app
WORKDIR /app

ADD ./build/libs/*.jar /app/app.jar

EXPOSE 18888
ENTRYPOINT ["java", "-jar", "app.jar", "--server-host=spring-boot-grpc-server", "--server-port=18888"]