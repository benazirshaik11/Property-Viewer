# Stage 1: Build the application
FROM ubuntu:22.04 as builder
WORKDIR /app

# Install OpenJDK 21 and other necessary tools
RUN apt-get update && apt-get install -y openjdk-21-jdk curl unzip

# Install Gradle using curl
ARG GRADLE_VERSION=8.2.1
RUN curl -LO https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip \
    && mv gradle-${GRADLE_VERSION} /opt/gradle \
    && ln -s /opt/gradle/bin/gradle /usr/bin/gradle \
    && rm gradle-${GRADLE_VERSION}-bin.zip

# Set Gradle home environment variable
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${GRADLE_HOME}/bin:${PATH}

COPY . .
RUN gradle clean build -x test

# Stage 2: Create the final image
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
EXPOSE 5005

# Start the application with debug options
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]
