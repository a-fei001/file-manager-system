package com.example.control;

import com.example.mapper.DocumentMapper;
import com.example.pojo.Document;
import com.example.pojo.Result;
import com.example.service.UserDocService;
import com.example.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 用户文档控制器
 * @description 提供用户文档查询相关接口
 */
@RestController
@RequestMapping("/user/document")
public class UserDocController {
    
    @Autowired
    private UserDocService userDocService;
    
    @Autowired
    private DocumentMapper documentMapper;
    
    /**
     * 分页查询文档列表
     * @param fileName 文件名（模糊查询）
     * @param fileType 文件类型
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<PageResult> findByPage(
            @RequestParam(required = false) String fileName,
            @RequestParam(required = false) String fileType,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult pageResult = userDocService.findByPage(fileName, fileType, pageNum, pageSize);
        return Result.success("查询成功", pageResult);
    }
    
    /**
     * 根据ID下载文档
     * @param id 文档ID
     * @return 文件响应
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        Document document = documentMapper.findById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        
        try (InputStream inputStream = userDocService.download(id)) {
            byte[] bytes = inputStream.readAllBytes();
            
            String encodedFileName = URLEncoder.encode(document.getFileName(), StandardCharsets.UTF_8);
            
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
