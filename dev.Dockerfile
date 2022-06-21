FROM maven:3.6.3-jdk-11 AS build

WORKDIR /workspace
COPY . .

RUN mvn -f pom.xml clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]