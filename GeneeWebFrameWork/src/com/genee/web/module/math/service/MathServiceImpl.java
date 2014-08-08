package com.genee.web.module.math.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.genee.web.framework.core.base.service.BaseService;


@Controller
@RequestMapping("/math")
public class MathServiceImpl extends BaseService implements MathService {

	@Override
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(@RequestParam int a, @RequestParam int b) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("result", a + b);
		return param;
	}

	@Override
	@RequestMapping(value = "/subtract", method = RequestMethod.POST)
	@ResponseBody
	public int subtract(int a, int b) {
		return a - b;
	}

}
