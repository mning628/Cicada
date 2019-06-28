package com.mn.mycat.service.impl;

import com.mn.mycat.mapper.UsersMapper;
import com.mn.mycat.model.Users;
import com.mn.mycat.service.UsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("usersService")
public class UsersServiceImpl implements UsersService
{

	@Resource
	private UsersMapper usersMapper;
	
	@Override
	public void add(Users u) {
		usersMapper.add(u);
	}

	@Override
	public List<Users> find() {
		return usersMapper.find();
	}

	
	
	
	
}
