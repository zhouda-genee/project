/**
 * 
 */
package com.genee.web.module.controller.statistics;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genee.web.framework.core.base.controller.BaseController;
import com.genee.web.module.service.statistics.ExcelService;
import com.genee.web.module.service.statistics.IndexService;

/**
 * @author jinzhe.hu 2014年9月1日
 */
@Controller
@RequestMapping("statistics/result/")
public class ResultController extends BaseController {
	@Autowired
	private IndexService iIndexService;
	
	@Autowired
	private ExcelService excelService;
		
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String execute(HttpServletRequest request, HttpServletResponse response){
		return "statistics/result/search-result";
	}
}
