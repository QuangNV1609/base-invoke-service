#!/bin/bash
# Tạo biến DATE theo định dạng ddMMyyyy
DATE=$(date '+%d%m%Y')

# Truyền biến DATE vào docker-compose và build
DATE=$DATE docker-compose up -d --build