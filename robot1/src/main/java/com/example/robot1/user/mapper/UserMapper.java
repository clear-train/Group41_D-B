
package com.example.robot1.user.mapper;

import com.example.robot1.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {

    //根据Id查找其他用户信息
    User getUser(int userid);

    //插入新的用户信息
    void insetUser(User user);

    //删除某个用户信息
    int deleteById(int userid);

    //修改用户信息
    int updateById(User user);

    //显示所有用户的信息
    @Select("select * from user")
    List<User> findAll();
}


