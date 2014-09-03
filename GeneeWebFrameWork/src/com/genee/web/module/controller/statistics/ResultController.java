/**
 * 
 */
package com.genee.web.module.controller.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genee.web.framework.core.base.controller.BaseController;
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

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		return "statistics/result/search-result";
	}

	/**
	 * 通过Ajax请求该方法，获取某一角色的所有指标类型
	 * 
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "roleindextype", method = RequestMethod.GET)
	public void getRoleIndexType(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		//Map<String, Object> sessionMap =  (Map<String, Object>) request.getSession().getAttribute(SessionAttributeType.PARAM_USER);
		//String roleId = sessionMap.get(SessionAttributeType.ROLE).toString();
		String roleId = "1";
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
	 * @param roleId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "roleindex", method = RequestMethod.GET)
	public void getRoleIndex(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		//Map<String, Object> sessionMap =  (Map<String, Object>) request.getSession().getAttribute(SessionAttributeType.PARAM_USER);
		//String roleId = sessionMap.get(SessionAttributeType.ROLE).toString();
		String roleId = "1";
		List<IndexEntity> entities = userService.queryIndexEntityByRole(roleId);
		result.put("result", entities);
		result.put("request-status", "success");
		outJson(response, result, null);
	}
}
