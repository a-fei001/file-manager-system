package com.example.service.impl;

import com.example.mapper.TestMapper;
import com.example.pojo.User01;
import com.example.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<User01> getAllUsers() {
        List<User01> users = testMapper.findAll();
        for (User01 user : users) {
            System.out.println(user);
        }
        return users;
    }
}