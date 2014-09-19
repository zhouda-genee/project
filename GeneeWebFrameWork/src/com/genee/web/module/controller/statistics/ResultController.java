/**
 * 
 */
package com.genee.web.module.controller.statistics;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.rmi.log.LogOutputStream;

import com.genee.web.framework.utils.json.JsonUtil;
import com.genee.web.framework.core.base.controller.BaseController;
import com.genee.web.framework.utils.dateutil.DateUtil;
import com.genee.web.framework.utils.http.HttpClientUtil;
import com.genee.web.framework.utils.poi.ExcelUtil;
import com.genee.web.framework.utils.prop.PropertiesUtil;
import com.genee.web.module.constants.ContentType;
import com.genee.web.module.constants.ExcelType;
import com.genee.web.module.constants.SessionAttributeType;
import com.genee.web.module.pojo.IndexEntity;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.service.statistics.ExcelService;
import com.genee.web.module.service.statistics.IndexService;
import com.genee.web.module.service.statistics.UserService;

/**
 * @author jinzhe.hu 2014年9月1日
 */
@Controller
@RequestMapping("statistics/result/")
public class ResultController extends BaseController {
	@Autowired
	private IndexService indexService;

	@Autowired
	private UserService userService;

	@Autowired
	private ExcelService excelService;
	
	@RequestMapping(value = "layout", method = RequestMethod.GET)
	public String layout(){
		return "statistics/common/layout";
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> sessionMap = new HashMap<String, String>();
		sessionMap.put(SessionAttributeType.USER_ID, "1");
		sessionMap.put(SessionAttributeType.USER_NAME, "技术支持");
		sessionMap.put(SessionAttributeType.ROLE, "1");
		sessionMap.put(SessionAttributeType.ROLE_NAME, "校级管理员");
//		sessionMap.put(SessionAttributeType.LAB_ID, "2");
		request.getSession().setAttribute(SessionAttributeType.PARAM_USER, sessionMap);
		return "statistics/indexlist/resultlist";
	}

	/**
	 * 通过Ajax请求该方法，获取某一角色的所有指标类型
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "roleindextype", method = RequestMethod.GET)
	public void getRoleIndexType(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> sessionMap = (Map<String, Object>)
		request.getSession().getAttribute(SessionAttributeType.PARAM_USER);
		String roleId = sessionMap.get(SessionAttributeType.ROLE).toString();
		List<IndexTypeEntity> entities = userService.queryIndexTypeEntityByRole(roleId);
		result.put("result", entities);
		result.put("request-status", "success");
		outJson(response, result, null);
	}

	/**
	 * 通过Ajax请求该方法，获取某一角色的所有指标
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "roleindex", method = RequestMethod.GET)
	public void getRoleIndex(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> sessionMap = (Map<String, Object>)
		request.getSession().getAttribute(SessionAttributeType.PARAM_USER);
		String roleId = sessionMap.get(SessionAttributeType.ROLE).toString();
		List<IndexEntity> entities = userService.queryIndexEntityByRole(roleId);
		result.put("result", entities);
		result.put("request-status", "success");
		outJson(response, result, null);
	}

	/**
	 * 导出EXCEL并下载的方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "excel", method = RequestMethod.POST)
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		Map<String, Object> indexMap = this.queryIndex(request);
		List<Map<String, Object>> topHeaders = (List<Map<String, Object>>) indexMap.get("topHeaders");
		List<Map<String, Object>> midHeaders = (List<Map<String, Object>>) indexMap.get("midHeaders");
		List<Map<String, Object>> contents = (List<Map<String, Object>>) indexMap.get("contents");
		Map<String, Object> total = (Map<String, Object>) indexMap.get("total");

		Workbook workbook = ExcelUtil.buildExcel(ExcelType.XLS, topHeaders,
				midHeaders, contents, total);

		// 设置response的编码方式
		response.setContentType(ContentType.EXCEL_2003);

		// 解决中文乱码
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((DateUtil.date2String(DateUtil.sysTimestamp(), "yyMMddHHmmss") + ".xls").getBytes("UTF-8")));

		OutputStream out = response.getOutputStream();
		out.flush();
		workbook.write(out);
		out.close();
	}
	
	/**
	 * 跳转到打印页面的方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping(value = "print", method = RequestMethod.POST)
	public String printIndex(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		return "statistics/indexlist/search-print";
	}
	
	/**
	 * 获取需要打印的数据的方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping(value = "print/data", method = RequestMethod.POST)
	public void printData(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		Map<String, Object> indexMap = this.queryIndex(request);
		String result = JSONObject.toJSONString(indexMap);
		outJson(response, result, null);
	}
	
	/**
	 * 通过Ajax请求该方法，获取组织结构的根节点
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "rootOrganization", method = RequestMethod.GET)
	public void getRootOrganization(HttpServletRequest request,
			HttpServletResponse response) {
		String servletUrl = PropertiesUtil.getPropertiesValue("application-config.properties", "servlet_url");
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonrpc", "2.0");
		params.put("id", String.valueOf(System.currentTimeMillis()));
		params.put("method", "org_root");
		
		Map<String,Object> param = new HashMap<String, Object>();

		params.put("params", JsonUtil.getJsonString4JavaPOJO(param));

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		
		String result = HttpClientUtil.post(servletUrl, params, headers);
		outJson(response, result, null);
	}
	
	/**
	 * 通过Ajax请求该方法，获取组织结构的子节点
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "childOrganization", method = RequestMethod.GET)
	public void getChildOrganization(HttpServletRequest request,
			HttpServletResponse response) {
		String servletUrl = PropertiesUtil.getPropertiesValue("application-config.properties", "servlet_url");
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonrpc", "2.0");
		params.put("id", String.valueOf(System.currentTimeMillis()));
		params.put("method", "equipment_child");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("id", request.getParameter("id"));

		params.put("params", JsonUtil.getJsonString4JavaPOJO(param));
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		
		String result = HttpClientUtil.post(servletUrl, params, headers);
		outJson(response, result, null);
	}
	
	/**
	 * 通过Ajax请求该方法，获取仪器分类的根节点
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "rootEquipment", method = RequestMethod.GET)
	public void getRootEquipment(HttpServletRequest request,
			HttpServletResponse response) {
		String servletUrl = PropertiesUtil.getPropertiesValue("application-config.properties", "servlet_url");
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonrpc", "2.0");
		params.put("id", String.valueOf(System.currentTimeMillis()));
		params.put("method", "equipment_root");
		
		Map<String,Object> param = new HashMap<String, Object>();

		params.put("params", JsonUtil.getJsonString4JavaPOJO(param));

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		
		String result = HttpClientUtil.post(servletUrl, params, headers);
		outJson(response, result, null);
	}
	
	/**
	 * 通过Ajax请求该方法，获取仪器分类的子节点
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "childEquipment", method = RequestMethod.GET)
	public void getChildEquipment(HttpServletRequest request,
			HttpServletResponse response) {
		String servletUrl = PropertiesUtil.getPropertiesValue("application-config.properties", "servlet_url");
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonrpc", "2.0");
		params.put("id", String.valueOf(System.currentTimeMillis()));
		params.put("method", "equipment_child");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("id", request.getParameter("id"));

		params.put("params", JsonUtil.getJsonString4JavaPOJO(param));
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		
		String result = HttpClientUtil.post(servletUrl, params, headers);
		outJson(response, result, null);
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> queryIndex(HttpServletRequest request) throws ParseException {
		//仪器名称的ID
		String indexId = "1";
		
		if (request.getParameter("indexId") != null) {
			indexId += "," + request.getParameter("indexId");
		}
		
		//查询表头
		List<Map<String, Object>> topHeaders = excelService.getTopHeaders(indexId);
		List<Map<String, Object>> midHeaders = excelService.getMidHeaders(indexId);
		
		//根据条件查询所有指标
		String servletUrl = PropertiesUtil.getPropertiesValue("application-config.properties", "servlet_url");
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonrpc", "2.0");
		params.put("id", String.valueOf(System.currentTimeMillis()));
		params.put("method", "statistics_eqindex");
		
		DateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = dd.parse(request.getParameter("dstart") + " 00:00:00");
		Date endDate = dd.parse(request.getParameter("dend") + " 23:59:59");
		long dstart = startDate.getTime()/1000;
		long dend = endDate.getTime()/1000;
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("eq_name", request.getParameter("eq_name"));
		param.put("eq_type", request.getParameter("eq_type"));
		param.put("eq_org", request.getParameter("eq_org"));
		param.put("eq_contact", request.getParameter("eq_contact"));
		param.put("eq_incharge", request.getParameter("eq_incharge"));
		param.put("lab_org", request.getParameter("lab_org"));
		param.put("lab", request.getParameter("lab"));
		param.put("user", request.getParameter("user"));
		param.put("dstart", dstart);
		param.put("dend", dend);
		param.put("sort_name", request.getParameter("sort_name"));
		param.put("sort", request.getParameter("sort"));

		params.put("params", JsonUtil.getJsonString4JavaPOJO(param));
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		
		String result = HttpClientUtil.post(servletUrl, params, headers);
		Map<String, Object> resultMap = JsonUtil.getMap4Json(result);
		List<Map<String, Object>> contents = 
				JsonUtil.getList4Json(resultMap.get("result").toString(), Map.class);
		
		//根据条件查询所有指标的总计
		Map<String, String> totalParams = new HashMap<String, String>();
		totalParams.put("jsonrpc", "2.0");
		totalParams.put("id", String.valueOf(System.currentTimeMillis()));
		totalParams.put("method", "statistics_eqindex_count");
		totalParams.put("params", JsonUtil.getJsonString4JavaPOJO(param));
		
		String totalResult = HttpClientUtil.post(servletUrl, totalParams, headers);
		Map<String, Object> totalResultMap = JsonUtil.getMap4Json(totalResult);
		Map<String, Object> total = 
				JsonUtil.getMap4Json(totalResultMap.get("result").toString());
		Map<String, Object> indexMap = new HashMap<String, Object>();
		indexMap.put("topHeaders", topHeaders);
		indexMap.put("midHeaders", midHeaders);
		indexMap.put("contents", contents);
		indexMap.put("total", total);
		return indexMap;
	}
}
