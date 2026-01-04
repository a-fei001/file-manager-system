package com.example.mapper;

import com.example.pojo.User01;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {
    @Select("SELECT * FROM user_01")
    List<User01> findAll();
}