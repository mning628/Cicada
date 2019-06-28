package com.mn.mycat.mapper;


import com.mn.mycat.model.Users;

import java.util.List;

public interface UsersMapper
{


    void add(Users u);

    List<Users> find();

}
