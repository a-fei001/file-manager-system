package com.example.service.impl;

import com.example.config.MinioConfig;
import com.example.mapper.DocumentMapper;
import com.example.mapper.UserDocMapper;
import com.example.pojo.Document;
import com.example.service.UserDocService;
import com.example.vo.DocumentVO;
import com.example.vo.PageResult;
import io.minio.MinioClient;
import io.minio.GetObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户文档服务实现类
 * @description 实现用户文档查询相关业务逻辑
 */
@Service
public class UserDocServiceImpl implements UserDocService {
    
    @Autowired
    private UserDocMapper userDocMapper;
    
    @Autowired
    private DocumentMapper documentMapper;
    
    @Autowired
    private MinioClient minioClient;
    
    @Override
    public PageResult<DocumentVO> findByPage(String fileName, String fileType, Integer pageNum, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", fileName);
        params.put("fileType", fileType);
        
        Long total = userDocMapper.countByCondition(params);
        
        Integer offset = (pageNum - 1) * pageSize;
        params.put("pageSize", pageSize);
        params.put("offset", offset);
        
        return PageResult.of(total, userDocMapper.findByCondition(params));
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
}
