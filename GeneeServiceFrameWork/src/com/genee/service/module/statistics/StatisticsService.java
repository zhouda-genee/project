package com.genee.service.module.statistics;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.genee.service.framework.core.base.PageSupport;

@Path("/statistics")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface StatisticsService {
	
	@Path("/eqindex")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public PageSupport<Map<String, Object>> queryEquipmentIndex(
			@FormParam("eq_name") String eq_name, 
			@FormParam("eq_type") String eq_type,
			@FormParam("eq_org") String eq_org, 
			@FormParam("eq_contact") String eq_contact, 
			@FormParam("eq_incharge") String eq_incharge,
			@FormParam("lab_org") String lab_org, 
			@FormParam("lab") String lab, 
			@FormParam("user") String user, 
			@FormParam("dstart") long dstart,
			@FormParam("dend") long dend, 
			@FormParam("size") int size, 
			@FormParam("page") int page, 
			@FormParam("sort_name") String sort_name, 
			@FormParam("sort") String sort);
	
}
