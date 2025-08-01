#!/bin/bash
# Load biến từ file .env
set -a
source .env
set +a

# Tạo biến DATE theo định dạng ddMMyyyy
DATE=$(date '+%d%m%Y')
echo "Ngày build: $DATE"

# Build project
mvn clean install -Djar.finalName=$JAR_NAME-$DATE

# Kiểm tra file JAR tồn tại
JAR_FILE="target/${JAR_NAME}-${DATE}.jar"
if [ ! -f "$JAR_FILE" ]; then
  echo "Không tìm thấy file: $JAR_FILE"
  exit 1
fi

# Truyền biến DATE vào docker-compose và build
DATE=$DATE docker-compose up -d --build