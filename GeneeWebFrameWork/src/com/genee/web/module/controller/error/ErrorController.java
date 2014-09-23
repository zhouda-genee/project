package com.genee.web.module.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
	
	
	@RequestMapping(value = "error", method = RequestMethod.GET)
	public String error(){
		return "statistics/common/oauth_error";
	}

}
