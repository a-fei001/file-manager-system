package com.example.control;

import com.example.pojo.Result;
import com.example.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文档管理控制器
 * @description 提供文档上传相关接口
 */
@RestController
@RequestMapping("/admin/doc")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    /**
     * 上传文件
     * @param file 上传的文件
     * @return 文件访问URL
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            logger.info("收到文件上传请求，文件名: {}, 文件大小: {} bytes", 
                    file.getOriginalFilename(), file.getSize());

            if (file.isEmpty()) {
                logger.warn("上传文件为空");
                return Result.error("上传文件不能为空");
            }

            String fileUrl = documentService.uploadFile(file);
            logger.info("文件上传成功，返回URL: {}", fileUrl);

            return Result.success("上传成功", fileUrl);
        } catch (Exception e) {
            logger.error("文件上传异常: {}", e.getMessage(), e);
            return Result.error("上传失败: " + e.getMessage());
        }
    }
}
