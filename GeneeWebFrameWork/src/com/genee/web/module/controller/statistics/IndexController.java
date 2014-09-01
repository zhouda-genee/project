/**
 * 
 */
package com.genee.web.module.controller.statistics;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genee.web.framework.core.base.controller.BaseController;
import com.genee.web.module.pojo.IndexTypeEntity;
import com.genee.web.module.pojo.RoleEntity;
import com.genee.web.module.service.statistics.IndexService;

/**
 * @author yanan.che 2014年8月13日
 */
@Controller
@RequestMapping("statistics/indexconfig/")
public class IndexController extends BaseController {
	@Autowired
	private IndexService iIndexService;
		
	/**
	 * 为客户端提供所有类型指标明细的json格式的字符串
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "type", method = RequestMethod.GET)
	public void showTypes(HttpServletRequest request, HttpServletResponse response) {	
		Map<String, Object> result = new HashMap<String, Object>();
		List<IndexTypeEntity> types = iIndexService.searchIndexDetailByType();	
		result.put("result", types);
		result.put("request-status", "success");
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
	}
		
	
	/**
	 * 通过Ajax请求该方法，获取某一角色的已有指标
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
	public void getrole(HttpServletRequest request, HttpServletResponse response, @PathVariable int roleId){
		Map<String, Object> result = new HashMap<String, Object>();
		RoleEntity role = iIndexService.searchIndexDetailByRole(roleId);	
		List<RoleEntity> roleList = new ArrayList<RoleEntity>();
		roleList.add(role);			
		result.put("result",roleList);
		result.put("request-status", "success");
		outJson(response, result, null);			
	}
	
	/**
	 * 测试查询某一角色的指标
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "roles", method = RequestMethod.GET)
	public String test(HttpServletRequest request, HttpServletResponse response){
		return "statistics/indexconfig/roleindex";
	}
}
