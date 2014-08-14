package com.genee.web.module.math.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.genee.web.framework.core.base.controller.BaseController;
import com.genee.web.module.math.service.MathService;

@Controller
@RequestMapping("/math")
public class MathController extends BaseController{
	
	@Autowired
	private MathService mathService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mathAdd(@RequestParam("params") int[] params){
		Map<String, Object> result = new HashMap<String, Object>();
		int val = mathService.add(params[0], params[1]);
		result.put("result", val);
		return result;
	}
	
	@ResponseBody
	public Map<String, Object> mathSubtract(@RequestParam Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		int val = mathService.subtract(Integer.valueOf(String.valueOf(param.get("minuend"))), Integer.valueOf(String.valueOf(param.get("subtrahend"))));
		result.put("result", val);
		return result;
	}
	
}

