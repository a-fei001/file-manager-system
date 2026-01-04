package com.example.control;

import com.example.pojo.User01;
import com.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TestController {

    @Autowired
    private TestService testService;

    // TODO: 项目完成后记得删除~~~
    @GetMapping("/test")
    public List<User01> test() {
        return testService.getAllUsers();
    }
}