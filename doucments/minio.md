sudo docker run -d \
  -p 9000:9000 \
  -p 9001:9001 \
  --name minio \
  -v /root/minio-data:/data \
  -e "MINIO_ROOT_USER=minioadmin" \
  -e "MINIO_ROOT_PASSWORD=minioadmin123" \
  minio/minio server /data --console-address ":9001"

bucketname: file-manager-system