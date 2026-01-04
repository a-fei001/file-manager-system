package com.example.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO配置类
 * @description 配置MinIO客户端连接
 */
@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 创建MinIO客户端Bean
     * @return MinIO客户端实例
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    /**
     * 获取MinIO端点地址
     * @return 端点地址
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * 获取存储桶名称
     * @return 存储桶名称
     */
    public String getBucketName() {
        return bucketName;
    }
}
