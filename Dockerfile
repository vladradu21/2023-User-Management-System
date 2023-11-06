FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/user-management-0.0.1-SNAPSHOT.jar user-management.jar

RUN sleep 10
ENTRYPOINT ["java","-jar","/user-management.jar"]

#docker network create --driver bridge ems-network
#docker-compose up --build