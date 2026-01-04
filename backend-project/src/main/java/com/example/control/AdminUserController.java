package com.example.control;

import com.example.mapper.UserMapper;
import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 管理员用户管理控制器
 * @description 提供管理员用户管理相关接口
 */
@RestController
@RequestMapping("/admin/admin")
public class AdminUserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 查询所有管理员用户
     * @return 管理员用户列表
     */
    @GetMapping("/list")
    public Result<List<User>> findAllAdmins() {
        List<User> admins = userService.findAllAdmins();
        return Result.success("查询成功", admins);
    }
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return Result.success("查询成功", user);
    }
    
    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 新增管理员用户
     * @param user 用户对象
     * @return 操作结果
     */
    @PostMapping
    public Result<User> addAdmin(@RequestBody User user) {
        user.setRole(1);
        Long adminId = ThreadLocalUtil.getUserId();
        User adminUser = userMapper.findById(adminId);
        String adminName = adminUser != null ? adminUser.getUsername() : "unknown";
        user.setAdminId(adminId);
        user.setAdminName(adminName);
        User addedUser = userService.addUser(user);
        return Result.success("新增成功", addedUser);
    }
    
    /**
     * 修改管理员用户信息（只修改用户名、密码、职称）
     * @param user 用户对象
     * @return 操作结果
     */
    @PutMapping
    public Result<Void> updateAdmin(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success("修改成功");
    }
}