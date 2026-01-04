package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 用户服务实现类
 * @description 实现用户管理相关业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }
    
    @Override
    public List<User> findAllAdmins() {
        return userMapper.findAllAdmins();
    }
    
    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }
    
    @Override
    public User addUser(User user) {
        userMapper.insert(user);
        return user;
    }
    
    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }
}