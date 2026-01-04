package com.example.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文档服务接口
 * @description 提供文档上传相关业务逻辑
 */
public interface DocumentService {

    /**
     * 上传文件到MinIO
     * @param file 上传的文件
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file);
}
