FROM docker.io/eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY gradlew ./
COPY gradle/wrapper/ gradle/wrapper/
RUN chmod +x gradlew
COPY build.gradle settings.gradle ./
COPY src/ src/
COPY build/libs/*.jar app.jar

RUN ./gradlew build --no-daemon -x test

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]