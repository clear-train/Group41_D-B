package com.example.robot1.user.cotroller;

import com.example.robot1.user.service.UserService;
import com.example.robot1.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //查询
    @RequestMapping("getUser/{userid}")
    public User getUser(@PathVariable int userid){
        return userService.getUser(userid);
    }

    //添加
    @RequestMapping("/insert/*")
    public User insert(User user){
        return userService.insertUser(user);
    }

    //删除
    @RequestMapping("/delete")
    public String delete(int userid){
        int result=userService.deleteById(userid);
        if(result>=1){
            return "删除成功";
        }else{
            return "删除失败";
        }
    }

    //修改
    @RequestMapping("/update/*")
    public String update(User user){
        int result=userService.updateById(user);
        if(result>=1){
            return "删除成功";
        }else{
            return "删除失败";
        }
    }

    @RequestMapping("/user")
    public String userMapper(Model m) {
        List<User> users = userService.findAll();
        m.addAttribute("user", users);
        return "user";
    }

}



