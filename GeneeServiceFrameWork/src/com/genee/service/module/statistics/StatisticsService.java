package com.genee.service.module.statistics;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/statistics")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface StatisticsService {
	
}
