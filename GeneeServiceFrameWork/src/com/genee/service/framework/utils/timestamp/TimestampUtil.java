package com.genee.service.framework.utils.timestamp;

import java.util.Date;

public class TimestampUtil {
	
	/**
	 * 
	 * @Title: timestampToDate 
	 * @Description: 时间戳整型转换成Date类型 
	 * @param @param timestamp
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date timestampToDate(long timestamp){
		// java的时间戳是13位的
		if (String.valueOf(timestamp).length() == 10)
			timestamp = Long.parseLong(String.valueOf(timestamp * 1000L));
		return new Date(timestamp);
	}
	
	/**
	 * 
	 * @Title: dateToTimestamp10 
	 * @Description: 日期转时间戳10位长度 
	 * @param  date
	 * @return long
	 * @throws
	 */
	public static long dateToTimestamp10(Date date){
		return dateToTimestamp13(date)/1000L;
	}
	
	/**
	 * 
	 * @Title: dateToTimestamp13 
	 * @Description: 日期转时间戳13位长度 
	 * @param  date
	 * @return long
	 * @throws
	 */
	public static long dateToTimestamp13(Date date){
		return date.getTime();
	}

}
