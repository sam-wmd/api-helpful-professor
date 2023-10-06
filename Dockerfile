FROM maven:3-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
RUN apt-get update && apt-get install -y libstdc++6
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

VOLUME /app/src

CMD ["java","-jar","app.jar"]