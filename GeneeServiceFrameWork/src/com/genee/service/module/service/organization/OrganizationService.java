package com.genee.service.module.service.organization;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.genee.service.module.pojo.TagEntity;

@Path("/organization")
public interface OrganizationService {

	@Path("/root")
	@GET
	public TagEntity getRootOrganization();

	@Path("/child")
	@GET
	public List<TagEntity> getChildOrganization(@QueryParam("id") long id);
}
