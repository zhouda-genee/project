package com.genee.service.module.organization;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.genee.service.module.pojo.OrganizationEntity;

@Path("/organization")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface OrganizationService {

	@Path("/root")
	@GET
	public OrganizationEntity getRootOrganization();

	@Path("/child")
	@GET
	public List<OrganizationEntity> getChildOrganization(@QueryParam("id") int id);
}
