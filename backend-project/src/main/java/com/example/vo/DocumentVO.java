package com.example.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文档视图对象
 * @description 封装文档详细信息
 */
@Data
public class DocumentVO {
    private Long id;
    private String fileName;
    private String fileType;
    private String bucketName;
    private String objectKey;
    private Long adminId;
    private String adminName;
    private LocalDateTime createTime;
}
