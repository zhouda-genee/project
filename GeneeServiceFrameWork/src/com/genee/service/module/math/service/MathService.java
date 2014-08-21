package com.genee.service.module.math.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.genee.service.module.math.pojo.UserEntity;

@Path("/math")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface MathService {
	
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public int add(@FormParam("a") String a, @FormParam("b")String b);
	
	@Path("/response")
	@Consumes({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
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
