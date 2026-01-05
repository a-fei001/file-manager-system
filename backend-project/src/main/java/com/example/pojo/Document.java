package com.example.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文档实体类
 * @description 对应数据库sys_document表
 */
@Data
public class Document {
    private Long id;
    private String fileName;
    private String fileType;
    private String bucketName;
    private String objectKey;
    private Long adminId;
    private String adminName;
    private LocalDateTime createTime;
}
