date=$(date '+%d%m%Y')

docker build --platform linux/amd64 -t dsp/test-deploy-service:latest .
wait
docker save dsp/test-deploy-service:latest > test-deploy-service-${date}.jar
#wait
#scp ./test-deploy-service-${date}.jar admin@10.0.65.171:/home/admin/dsp/rb/test-deploy-service
#wait
#ssh admin@10.0.65.171 "cd /home/admin/dsp/rb/test-deploy-service; sh build.sh"
