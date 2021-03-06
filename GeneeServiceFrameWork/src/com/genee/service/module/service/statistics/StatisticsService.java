package com.genee.service.module.service.statistics;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.genee.service.framework.core.base.PageSupport;
import com.genee.service.module.pojo.AxisEntity;
import com.genee.service.module.pojo.EquipmentIndexEntity;

@Path("/statistics")
public interface StatisticsService {
	
	/**
	 * 
	 * @Title: queryEquipmentIndex 
	 * @Description: 分页显示指标 
	 * @return PageSupport<EquipmentIndexEntity>
	 * @throws
	 */
	@Path("/eqindex/page")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public PageSupport<EquipmentIndexEntity> queryEquipmentIndex(
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
			@FormParam("sort") String sort) throws Exception;
	
	/**
	 * 
	 * @Title: queryEquipmentIndex 
	 * @Description: 查询全部指标 
	 * @return List<EquipmentIndexEntity>
	 * @throws
	 */
	@Path("/eqindex")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public List<EquipmentIndexEntity> queryEquipmentIndex(
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
			@FormParam("sort_name") String sort_name, 
			@FormParam("sort") String sort);
	
	/**
	 * 
	 * @Title: queryEquipmentIndex 
	 * @Description: 查询统计 
	 * @return EquipmentIndexEntity
	 * @throws
	 */
	@Path("/eqindex/count")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public EquipmentIndexEntity queryEquipmentIndex(
			@FormParam("eq_name") String eq_name, 
			@FormParam("eq_type") String eq_type,
			@FormParam("eq_org") String eq_org, 
			@FormParam("eq_contact") String eq_contact, 
			@FormParam("eq_incharge") String eq_incharge,
			@FormParam("lab_org") String lab_org, 
			@FormParam("lab") String lab, 
			@FormParam("user") String user, 
			@FormParam("dstart") long dstart,
			@FormParam("dend") long dend);
	
	@Path("/eqindex/chart")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public List<AxisEntity> queryChartIndex(
			@FormParam("obj_type") String objType, 
			@FormParam("obj_value") String objValue, 
			@FormParam("index") String index, 
			@FormParam("dstart") String dstart, 
			@FormParam("dend") String dend,
			@FormParam("dtype") String dateType);
	
}
