FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw dependency:resolve
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]