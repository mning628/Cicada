package com.mn.mycat.service;

import com.mn.mycat.model.Users;

import java.util.List;


public interface UsersService
{

    void add(Users u);

    List<Users> find();

}
