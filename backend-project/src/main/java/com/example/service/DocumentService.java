package com.example.service;

import com.example.pojo.Document;
import com.example.vo.DocumentVO;
import com.example.vo.PageResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文档服务接口
 * @description 提供文档管理相关业务逻辑
 */
public interface DocumentService {
    
    /**
     * 上传文件
     * @param file 上传的文件
     * @param adminId 管理员ID
     * @param adminName 管理员名称
     * @return 上传的文档对象
     */
    Document upload(MultipartFile file, Long adminId, String adminName);
    
    /**
     * 根据ID查询文档
     * @param id 文档ID
     * @return 文档对象
     */
    Document findById(Long id);
    
    /**
     * 根据ID删除文档
     * @param id 文档ID
     */
    void deleteById(Long id);
    
    /**
     * 分页查询文档列表
     * @param fileName 文件名（模糊查询）
     * @param fileType 文件类型
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<DocumentVO> findByPage(String fileName, String fileType, Integer pageNum, Integer pageSize);
}
