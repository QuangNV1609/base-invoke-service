FROM openjdk:8

# Khai báo biến truyền từ docker-compose hoặc build command
ARG DATE
ARG JAR_NAME

WORKDIR /spring

# Sao chép đúng file JAR theo ngày
COPY target/${JAR_NAME}-${DATE}.jar ${JAR_NAME}.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "base-invoke-service.jar"]
