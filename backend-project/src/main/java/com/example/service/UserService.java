package com.example.service;

import com.example.pojo.User;
import java.util.List;

/**
 * 用户服务接口
 * @description 提供用户管理相关业务逻辑
 */
public interface UserService {
    /**
     * 查询所有普通用户
     * @return 普通用户列表
     */
    List<User> findAllUsers();
    
    /**
     * 查询所有管理员用户
     * @return 管理员用户列表
     */
    List<User> findAllAdmins();
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    User findById(Long id);
    
    /**
     * 根据ID删除用户
     * @param id 用户ID
     */
    void deleteById(Long id);
    
    /**
     * 新增用户
     * @param user 用户对象
     * @return 新增的用户对象
     */
    User addUser(User user);
    
    /**
     * 更新用户信息（只更新用户名、密码、职称）
     * @param user 用户对象
     */
    void updateUser(User user);
}