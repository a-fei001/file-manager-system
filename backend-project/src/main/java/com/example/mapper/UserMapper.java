package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;

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
}
