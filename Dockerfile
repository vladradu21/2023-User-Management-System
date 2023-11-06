FROM openjdk:17-jdk-alpine
COPY target/*.jar app.jar

RUN sleep 5
ENTRYPOINT ["java","-jar","/app.jar"]

#docker network create --driver bridge ems-network
#docker-compose up --build