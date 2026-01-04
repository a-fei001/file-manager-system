package com.example.service.impl;

import com.example.config.MinioConfig;
import com.example.service.DocumentService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 文档服务实现类
 * @description 实现文档上传相关业务逻辑
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    /**
     * 上传文件到MinIO
     * @param file 上传的文件
     * @return 文件访问URL
     */
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileNameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String objectName = fileNameWithoutExt + "_" + UUID.randomUUID().toString() + extension;

            logger.info("开始上传文件到MinIO，文件名: {}, 对象名: {}", originalFilename, objectName);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String fileUrl = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + objectName;
            logger.info("文件上传成功，访问URL: {}", fileUrl);

            return fileUrl;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            logger.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }
}
