package com.genee.service.module.math.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.genee.service.module.math.pojo.UserEntity;

@Path("/math")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface MathService {
	
	@Path("/add")
	@GET
	public int add(@QueryParam("a") int a, @QueryParam("b")int b);
	
	@Path("/response")
	@GET
	public Response getUser();
	
	@Path("/add2")
	@GET
	public int add2();
	
	@Path("/getusers")
	@GET
	public List<UserEntity> getUserList();
	
	@Path("/getusers2")
	@GET
	public Response getUsers();

}
