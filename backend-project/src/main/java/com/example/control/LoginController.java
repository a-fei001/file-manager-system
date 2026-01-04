package com.example.control;

import com.example.pojo.Result;
import com.example.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录控制器
 * @description 处理用户登录请求
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 用户登录接口
     * @param loginData 登录数据，包含username和password
     * @return 登录结果，包含token、username和role
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        log.info("收到登录请求，用户名: {}", username);

        try {
            Map<String, Object> data = loginService.login(username, password);
            log.info("用户登录成功，用户名: {}, 角色: {}", username, data.get("role"));
            return Result.success("登录成功", data);
        } catch (Exception e) {
            log.error("用户登录失败，用户名: {}, 错误信息: {}", username, e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
