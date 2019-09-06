package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.example.demo.bean.User;

public interface UserMapper {
    @Select("select id,username,password from user where username=#{username}")
    public List<User> queryAll(User user);
    
    @Insert("insert into user (username,password) values(#{username},#{password})")
	public void insert(User user);
}
