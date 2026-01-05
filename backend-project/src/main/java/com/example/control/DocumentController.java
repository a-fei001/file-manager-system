package com.example.control;

import com.example.mapper.UserMapper;
import com.example.pojo.Document;
import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.DocumentService;
import com.example.util.ThreadLocalUtil;
import com.example.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文档管理控制器
 * @description 提供文档管理相关接口
 */
@RestController
@RequestMapping("/admin/document")
public class DocumentController {
    
    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 上传文件
     * @param file 上传的文件
     * @return 操作结果
     */
    @PostMapping("/upload")
    public Result<Document> upload(@RequestParam("file") MultipartFile file) {
        Long adminId = ThreadLocalUtil.getUserId();
        User admin = userMapper.findById(adminId);
        if (admin == null) {
            return Result.error("用户不存在");
        }
        String adminName = admin.getUsername();
        Document document = documentService.upload(file, adminId, adminName);
        return Result.success("上传成功", document);
    }
    
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
        PageResult pageResult = documentService.findByPage(fileName, fileType, pageNum, pageSize);
        return Result.success("查询成功", pageResult);
    }
    
    /**
     * 根据ID查询文档
     * @param id 文档ID
     * @return 文档对象
     */
    @GetMapping("/{id}")
    public Result<Document> findById(@PathVariable Long id) {
        Document document = documentService.findById(id);
        if (document == null) {
            return Result.error("文档不存在");
        }
        return Result.success("查询成功", document);
    }
    
    /**
     * 根据ID删除文档
     * @param id 文档ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        documentService.deleteById(id);
        return Result.success("删除成功");
    }
}
