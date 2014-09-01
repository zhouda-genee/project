package com.genee.web.module.service.statistics;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author jinzhe.hu
 * 
 * @date 2014年8月28日
 */

@Transactional
public interface ExcelService {
	/**
	 * 获取Excel的一级标题
	 * 
	 * @param identities 指标ID列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getTopHeaders(List<Integer> identities);
	
	/**
	 * 获取Excel的二级标题
	 * 
	 * @param identities 指标ID列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMidHeaders(List<Integer> identities);
}
