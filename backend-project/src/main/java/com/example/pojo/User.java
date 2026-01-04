package com.example.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * @description 对应数据库sys_user表
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Integer role;
    private LocalDateTime joinDate;
    private String description;
    private Long adminId;
    private String adminName;
}
