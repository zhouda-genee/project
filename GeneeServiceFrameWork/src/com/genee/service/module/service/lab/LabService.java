package com.genee.service.module.service.lab;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.genee.service.module.pojo.BaseEntity;

@Path("/lab")
public interface LabService {

	@Path("/labname")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public List<BaseEntity> getLabByName(@FormParam("name") String name);
}
