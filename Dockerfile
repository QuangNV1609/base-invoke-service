FROM openjdk:8

# Khai báo biến truyền từ docker-compose hoặc build command
ARG DATE

WORKDIR /spring

# Sao chép đúng file JAR theo ngày
COPY target/base-invoke-service-${DATE}.jar base-invoke-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "base-invoke-service.jar"]
