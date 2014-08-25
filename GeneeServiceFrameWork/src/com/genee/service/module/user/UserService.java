package com.genee.service.module.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.genee.service.module.pojo.BaseEntity;

@Path("/user")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface UserService {

	@Path("/contact")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<BaseEntity> getContact(@FormParam("name") String name);

	@Path("/incharge")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<BaseEntity> getIncharge(@FormParam("name") String name);

	@Path("/recuser")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<BaseEntity> getUserInRecord(@FormParam("name") String name);
}
