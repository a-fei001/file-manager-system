package com.example.service;

import com.example.vo.DocumentVO;
import com.example.vo.PageResult;
import java.io.InputStream;

/**
 * 用户文档服务接口
 * @description 提供用户文档查询相关业务逻辑
 */
public interface UserDocService {
    
    /**
     * 分页查询文档列表
     * @param fileName 文件名（模糊查询）
     * @param fileType 文件类型
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<DocumentVO> findByPage(String fileName, String fileType, Integer pageNum, Integer pageSize);
    
    /**
     * 根据ID下载文档
     * @param id 文档ID
     * @return 文件输入流
     */
    InputStream download(Long id);
}
