/**
 * DateJsonValueProcessor.java
 * www.swt.com.core.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-1-7 		ZhouDa
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
 */

package com.genee.servlet.framework.utils.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * ClassName:DateJsonValueProcessor Function: TODO ADD FUNCTION Reason: TODO ADD
 * REASON
 * 
 * @author ZhouDa
 * @version
 * @since Ver 1.1
 * @Date 2011-1-7 上午10:38:29
 * 
 * @see
 * 
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private DateFormat dateFormat;

	/**
	 * 
	 * @param datePattern
	 *            日期格式
	 */
	public DateJsonValueProcessor(String datePattern) {
		if (null == datePattern)
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		else
			dateFormat = new SimpleDateFormat(datePattern);
	}

	/**
	 * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object,
	 *      net.sf.json.JsonConfig)
	 */
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return process(arg0);
	}

	/**
	 * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String,
	 *      java.lang.Object, net.sf.json.JsonConfig)
	 */
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		return process(arg1);
	}

	private Object process(Object value) {
		return dateFormat.format((Date) value);
	}

}
