package com.genee.service.module.equipment;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.genee.service.module.pojo.BaseEntity;

@Path("/equipment")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface EquipmentService {

	@Path("/equipmentname")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public List<BaseEntity> getEquipmentByName(@FormParam("name") String name);
}
