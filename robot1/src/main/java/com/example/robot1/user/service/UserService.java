package com.example.robot1.user.service;

import com.example.robot1.user.domain.User;
import com.example.robot1.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //根据Id查找其他用户信息
    public User getUser(int userid){
        return userMapper.getUser(userid);
    }

    //添加用户
    public User insertUser(User user){
        userMapper.insetUser(user);
        return user;
    }

    //删除
    public int deleteById(int userid){
       return userMapper.deleteById(userid);
    }

    //修改
    public int updateById(User user){
        return userMapper.updateById(user);
    }

    //显示信息
    public List<User> findAll(){
        return userMapper.findAll();
    }
}
