#!/bin/bash

# Tạo biến DATE theo định dạng ddMMyyyy
DATE=$(date '+%d%m%Y')
echo "🕒 Ngày build: $DATE"

# Kiểm tra file JAR tồn tại
JAR_FILE="target/base-invoke-service-${DATE}.jar"
if [ ! -f "$JAR_FILE" ]; then
  echo "❌ Không tìm thấy file: $JAR_FILE"
  exit 1
fi

# Truyền biến DATE vào docker-compose và build
DATE=$DATE docker-compose up -d --build