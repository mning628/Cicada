package com.mn.mycat.controller;


import com.mn.mycat.model.Users;
import com.mn.mycat.service.UsersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class UserController
{
    @Resource
    private UsersService usersService;

    @RequestMapping("/user/add")
    public String add(String name)
    {
        Users u = new Users();
        u.setName(name).setIndate(new Date());
        usersService.add(u);
        return "插入成功";
    }

    @RequestMapping("/user/find")
    public List<Users> find()
    {
        return usersService.find();
    }

}
