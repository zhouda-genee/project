package com.genee.timertask.module.statistics;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genee.timertask.framework.core.base.controller.BaseController;
import com.genee.timertask.framework.utils.dateutil.DateUtil;

@Controller
public class TaskEquipmentController extends BaseController {
	
	@Autowired
	private TaskEquipmentIndex taskEquipmentIndex;
	
	@RequestMapping(value = "index/run", method = RequestMethod.GET)
	public void run(HttpServletRequest request, HttpServletResponse response) {	
		Map<String, Object> result = new HashMap<String, Object>();
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		try{
			taskEquipmentIndex.run(DateUtil.string2Date(startDate), DateUtil.string2Date(endDate));
			result.put("request-status", "success");
		} catch(Exception ex) {
			result.put("request-status", "failure");
		}
		outJson(response, result, null);		
	}

}
