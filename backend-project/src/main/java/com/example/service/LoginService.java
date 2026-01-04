package com.example.service;

import java.util.Map;

/**
 * 登录服务接口
 * @description 定义用户登录相关的业务逻辑接口
 */
public interface LoginService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果，包含token、username和role
     */
    Map<String, Object> login(String username, String password);
}
