package com.genee.service.module.math.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.genee.service.module.math.pojo.UserEntity;

public class MathServiceImpl implements MathService {
	
	@Override
	public int add(int a, int b) {
		return a + b;
	}

	@Override
	public Response getUser() {
		UserEntity user=new UserEntity();  
        user.setId(1);  
        user.setName("zhang");  
		return Response.ok(user).build();
	}

	@Override
	public List<UserEntity> getUserList() {
		List<UserEntity> list=new ArrayList<UserEntity>();   
        
		UserEntity user=new UserEntity();  
        user.setId(1);  
        user.setName("chen");  
        list.add(user);  
          
        UserEntity user1=new UserEntity();  
        user1.setId(2);  
        user1.setName("chen shi");  
        list.add(user1);  
          
        UserEntity user2=new UserEntity();  
        user2.setId(3);  
        user2.setName("chen shi cheng");  
        list.add(user2);
        System.out.println("进入getusers方法");
		return list;
	}

	@Override
	public int add2() {
		return 12 + 13;
	}

}
