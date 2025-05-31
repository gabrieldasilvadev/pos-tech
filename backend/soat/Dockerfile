FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn/ .mvn/

RUN ./mvnw dependency:go-offline --batch-mode

COPY src ./src

RUN ./mvnw package -DskipTests --batch-mode

FROM eclipse-temurin:21-jre-alpine

RUN apk add --no-cache curl

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=80.0", "-jar", "/app/app.jar"]
