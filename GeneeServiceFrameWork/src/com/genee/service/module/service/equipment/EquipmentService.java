package com.genee.service.module.service.equipment;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.genee.service.module.pojo.BaseEntity;
import com.genee.service.module.pojo.TagEntity;

@Path("/equipment")
public interface EquipmentService {

	@Path("/equipmentname")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public List<BaseEntity> getEquipmentByName(@FormParam("name") String name);

	@Path("/root")
	@GET
	public TagEntity getRootEquipment();

	@Path("/child")
	@GET
	public List<TagEntity> getChildEquipment(@QueryParam("id") long id);
}
