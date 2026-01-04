package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.LoginService;
import com.example.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录服务实现类
 * @description 实现用户登录相关的业务逻辑
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果，包含token、username和role
     */
    @Override
    public Map<String, Object> login(String username, String password) {
        log.info("开始验证用户，用户名: {}", username);
        User user = userMapper.findByUsername(username);
        if (user == null) {
            log.warn("用户不存在，用户名: {}", username);
            throw new RuntimeException("用户不存在");
        }

        log.info("用户存在，开始验证密码，用户ID: {}", user.getId());
        if (!user.getPassword().equals(password)) {
            log.warn("密码错误，用户ID: {}, 用户名: {}", user.getId(), username);
            throw new RuntimeException("用户名或密码错误");
        }

        String token = JwtUtil.generateToken(user.getId());
        log.info("生成JWT令牌成功，用户ID: {}, 用户名: {}, 角色: {}", user.getId(), username, user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("username", user.getUsername());
        result.put("role", user.getRole());
        return result;
    }
}
