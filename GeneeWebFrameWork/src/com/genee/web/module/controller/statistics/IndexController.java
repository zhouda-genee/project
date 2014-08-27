/**
 * 
 */
package com.genee.web.module.controller.statistics;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.genee.web.framework.core.base.controller.BaseController;
import com.genee.web.framework.core.error.LogInstance;
import com.genee.web.framework.utils.json.JsonUtil;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.RoleEntity;
import com.genee.web.module.service.statistics.IIndexService;

/**
 * @author yanan.che 2014年8月13日
 */
@Controller
@RequestMapping("statistics/indexconfig/")
public class IndexController extends BaseController {
	@Autowired
	private IIndexService iIndexService;
	
	/**
	 * 指标类型明细页面
	 * @return 
	 */
	@RequestMapping(value = "showtypes", method = RequestMethod.GET)
	public String showType(HttpServletRequest request, HttpServletResponse response){
		return "statistics/indexconfig/types";
	}
		
	/**
	 * 为客户端提供所有类型指标明细的json格式的字符串
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "type", method = RequestMethod.GET)
	public void showTypes(HttpServletRequest request,
			HttpServletResponse response) {	
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LogInstance.formatMessage("获取指标类型明细", request);
			List<IndexTypeEntity> types = iIndexService.searchIndexDetailByType();	
			result.put("result", types);
			result.put("request-status", "success");
		} catch (Exception ex) {
			LogInstance.error(ex);
			result.put("result", ex.getMessage());
			result.put("request-status", "failure");
		}
		outJson(response, result, null);
	}
	
	/**
	 * 角色指标明细页面
	 * @return
	 */
	@RequestMapping("showroles")
	public String showRoles(){
		return "statistics/indexconfig/roles";
	}
	
	/**
	 * 为客户端提供所有角色指标明细的json格式的字符串
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getallroles", method = RequestMethod.POST)
	public void getRoles(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LogInstance.formatMessage("获取角色指标明细", request);
			List<RoleEntity> roles = iIndexService.searchIndexDetailByRole();
			result.put("result", roles);
			result.put("request-status", "success");
		} catch (Exception ex) {
			LogInstance.error(ex);
			result.put("result", ex.getMessage());
			result.put("request-status", "failure");
		}
		outJson(response, result, null);
	}
	
	/**
	 * 修改某一角色的指标
	 * @param roleId
	 * @return
	 */	
	@RequestMapping(value = "editrole", method=RequestMethod.POST) 
	public void editRole(HttpServletRequest request, HttpServletResponse response) {
		int roleId = (int)request.getSession().getAttribute("roleId");
		String[] arrCkb = request.getParameterValues("ckb");
		iIndexService.updateIndexRoleRelation(arrCkb, roleId);
		System.out.println(roleId);
		System.out.println(arrCkb);
	}
	
	/**
	 * 在页面中显示角色表中的角色名称
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping("roles")
	public String showroles(HttpServletRequest request, HttpServletResponse response){
		List<RoleEntity> roleEntities = iIndexService.searchAllRoleDetail();
		request.setAttribute("roles", roleEntities);
		System.out.println(roleEntities);
		String[] arrckb = request.getParameterValues("ckb");
 		System.out.println(arrckb);
		return "statistics/indexconfig/showroles";
	}
	
	/**
	 * 角色指标配置页面
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping(value = "role/{roleId}", method = RequestMethod.GET)
	public String showRole(HttpServletRequest request, HttpServletResponse response, @PathVariable int roleId) {
		request.getSession().setAttribute("roleId", roleId);	
		RoleEntity role = iIndexService.searchIndexDetailByRole(roleId);
		request.getSession().setAttribute("role", role);
		return "statistics/indexconfig/role";
	}	
	
	/**
	 * 通过Ajax请求该方法，获取某一角色的已有指标
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping(value = "/{roleId}", method = RequestMethod.POST)
	public void getrole(HttpServletRequest request, HttpServletResponse response, @PathVariable int roleId){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LogInstance.formatMessage("为某一角色分配指标", request);
			RoleEntity role = iIndexService.searchIndexDetailByRole(roleId);	
			List<RoleEntity> roleList = new ArrayList<RoleEntity>();
			roleList.add(role);			
			result.put("result",roleList);
			result.put("request-status", "success");
		} catch (Exception ex) {
			LogInstance.error(ex);
			result.put("result", ex.getMessage());
			result.put("request-status", "failure");
		}
		outJson(response, result, null);			
	}
	
	/**
	 * 测试查询某一角色的指标
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(HttpServletRequest request, HttpServletResponse response){
		return "statistics/indexconfig/roleindex";
	}
}
