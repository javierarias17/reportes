FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY applications/app-service/build/libs/reportes.jar ./reportes.jar
ENTRYPOINT [ "java", "-jar", "reportes.jar" ]

