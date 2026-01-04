package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 用户Mapper接口
 * @description 提供用户数据访问方法
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(String username);
    
    /**
     * 查询所有普通用户（按入系统时间倒序）
     * @return 普通用户列表
     */
    @Select("SELECT * FROM sys_user WHERE role = 0 ORDER BY join_date DESC")
    List<User> findAllUsers();
    
    /**
     * 查询所有管理员用户（按入系统时间倒序）
     * @return 管理员用户列表
     */
    @Select("SELECT * FROM sys_user WHERE role = 1 ORDER BY join_date DESC")
    List<User> findAllAdmins();
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    User findById(Long id);
    
    /**
     * 根据ID删除用户
     * @param id 用户ID
     */
    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    void deleteById(Long id);
    
    /**
     * 新增用户
     * @param user 用户对象
     */
    @Insert("INSERT INTO sys_user (username, password, role, description, admin_id, admin_name) " +
            "VALUES (#{username}, #{password}, #{role}, #{description}, #{adminId}, #{adminName})")
    void insert(User user);
    
    /**
     * 更新用户信息（只更新用户名、密码、职称）
     * @param user 用户对象
     */
    @org.apache.ibatis.annotations.Update("UPDATE sys_user SET username = #{username}, password = #{password}, description = #{description} WHERE id = #{id}")
    void update(User user);
}
