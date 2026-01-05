package com.example.service.impl;

import com.example.config.MinioConfig;
import com.example.mapper.DocumentMapper;
import com.example.pojo.Document;
import com.example.service.DocumentService;
import com.example.vo.DocumentVO;
import com.example.vo.PageResult;
import io.minio.MinioClient;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档服务实现类
 * @description 实现文档管理相关业务逻辑
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    
    @Autowired
    private DocumentMapper documentMapper;
    
    @Autowired
    private MinioClient minioClient;
    
    @Autowired
    private MinioConfig minioConfig;
    
    @Transactional
    @Override
    public Document upload(MultipartFile file, Long adminId, String adminName) {
        String fileName = file.getOriginalFilename();
        
        Document existingDoc = documentMapper.findByFileName(fileName);
        if (existingDoc != null) {
            throw new RuntimeException("文件名已存在，请修改文件名后重试");
        }
        
        String fileType = getFileExtension(fileName);
        String objectKey = generateObjectKey(fileName);
        String bucketName = minioConfig.getBucketName();
        
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectKey)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
        
        Document document = new Document();
        document.setFileName(fileName);
        document.setFileType(fileType);
        document.setBucketName(bucketName);
        document.setObjectKey(objectKey);
        document.setAdminId(adminId);
        document.setAdminName(adminName);
        document.setCreateTime(LocalDateTime.now());
        
        documentMapper.insert(document);
        
        return document;
    }
    
    @Override
    public Document findById(Long id) {
        return documentMapper.findById(id);
    }
    
    @Transactional
    @Override
    public void deleteById(Long id) {
        Document document = documentMapper.findById(id);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }
        
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(document.getBucketName())
                    .object(document.getObjectKey())
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("删除MinIO文件失败: " + e.getMessage());
        }
        
        documentMapper.deleteById(id);
    }
    
    @Override
    public PageResult<DocumentVO> findByPage(String fileName, String fileType, Integer pageNum, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", fileName);
        params.put("fileType", fileType);
        
        Long total = documentMapper.countByCondition(params);
        
        Integer offset = (pageNum - 1) * pageSize;
        params.put("pageSize", pageSize);
        params.put("offset", offset);
        
        return PageResult.of(total, documentMapper.findByCondition(params));
    }
    
    @Override
    public InputStream download(Long id) {
        Document document = documentMapper.findById(id);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }
        
        try {
            return minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(document.getBucketName())
                    .object(document.getObjectKey())
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("下载文件失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return 文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }
    
    /**
     * 生成MinIO对象键
     * @param fileName 原始文件名
     * @return 对象键
     */
    private String generateObjectKey(String fileName) {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        return dateStr + "/" + fileName;
    }
}
