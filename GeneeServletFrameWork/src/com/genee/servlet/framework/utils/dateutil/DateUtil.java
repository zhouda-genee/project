package com.genee.servlet.framework.utils.dateutil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public enum DateType {
		YEAR, MONTH, DAY, HH, MI, SS, YYYY_MM_DD, YYYYMMDD
	}

	public static java.sql.Date maxDate() {
		return java.sql.Date.valueOf("9999-09-09");
	}

	/**
	 * 得到当前应用服务器的系统日期
	 * 
	 * @return 系统日期
	 */
	public static Timestamp sysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 得到当前应用服务器的系统日期
	 * 
	 * @return 系统日期
	 */
	public static java.sql.Date sysDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * 得到当前应用服务器的系统时间
	 * 
	 * @return 系统日期
	 */
	public static java.sql.Time sysTime() {
		return new java.sql.Time(System.currentTimeMillis());
	}

	/**
	 * 得到当前应用服务器的系统日期<br>
	 * 字符串类型
	 * 
	 * @return 系统日期
	 */
	public static String sysDate4Str() {
		return new java.sql.Date(System.currentTimeMillis()).toString();
	}

	/**
	 * 得到d1与d2之间相差数值
	 * <p>
	 * 数值可以为年份，DateType.YEAR<br>
	 * 数值可以为月份，DateType.MONTH<br>
	 * 数值可以为天数，DateType.DAY<br>
	 * 数值可以为小时，DateType.HH<br>
	 * 数值可以为分钟，DateType.MI<br>
	 * 数值可以为秒，DateType.SS
	 * 
	 * @param d1
	 *            日期1(较大的日期)
	 * @param d2
	 *            日期2(较小的日期)
	 * @param dataType
	 *            数值类型
	 * @return 相差数值 <br>
	 *         d1早于d2时，返回-1。<br>
	 *         dateType类型不正确时，返回-2。
	 */
	public static int dateBetween(Date d1, Date d2, DateType dateType) {
		if (!d2.before(d1)) {
			return -1;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (DateType.YEAR.equals(dateType)) {
			return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		}
		if (DateType.MONTH.equals(dateType)) {
			return (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12
					+ c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}
		if (DateType.DAY.equals(dateType)) {
			return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 86400000L);
		}
		if (DateType.HH.equals(dateType)) {
			return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 3600000L);
		}
		if (DateType.MI.equals(dateType)) {
			return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 60000L);
		}
		if (DateType.SS.equals(dateType)) {
			return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000L);
		}
		return -2;
	}

	/**
	 * 将精确到秒的字符串转换为日期类型
	 * <p>
	 * 字符串格式需要为yyyy-MM-dd hh:mm:ss
	 * 
	 * @param date
	 *            将精确到秒的字符串
	 * @return Timestamp对象
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static Date string2Timestamp(String date) {
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			clsFormat.parse(date);
			return new Timestamp(clsFormat.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将精确到秒的字符串转换为日期类型
	 * <p>
	 * 字符串格式需要为yyyy-MM-dd hh:mm:ss
	 * 
	 * @param date
	 *            将精确到秒的字符串
	 * @return Timestamp对象
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static Timestamp str2Timestamp(String date, String pattern) {
		SimpleDateFormat clsFormat = new SimpleDateFormat(pattern);
		try {
			clsFormat.parse(date);
			return new Timestamp(clsFormat.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将精确到天的字符串转换为日期类型
	 * <p>
	 * 字符串格式需要为yyyy-MM-dd
	 * 
	 * @param date
	 *            将精确到天的字符串
	 * @return java.sql.Date对象
	 * @throws ParseException
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static java.sql.Date string2Date(String date) {
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new java.sql.Date(clsFormat.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将字符型时间转换为Time型
	 * <p>
	 * 字符串格式需要为HH:mm:ss
	 * 
	 * @param date
	 *            将精确到天的字符串
	 * @return Timestamp对象
	 * @throws ParseException
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static java.sql.Time string2Time(String time) {
		SimpleDateFormat clsFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			return new java.sql.Time(clsFormat.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// 将精确到天的字符串对象转换为日期类型
	public static java.sql.Date object2Date(Object date) {
		return string2Date((String) date);
	}

	/**
	 * 将字符串转为指定格式的日期
	 * <p>
	 * 日期字符串与字符串格式需要匹配
	 * 
	 * @param date
	 *            日期字符串
	 * @param frm
	 *            字符串格式
	 * @return Timestamp对象
	 * @throws ParseException
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static java.sql.Date string2Date(String date, String frm) {
		SimpleDateFormat clsFormat = new SimpleDateFormat(frm);
		try {
			clsFormat.parse(date);
			return new java.sql.Date(clsFormat.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将日期类型转换为精确到天的字符串
	 * <p>
	 * 字符串格式为yyyy-MM-dd
	 * 
	 * @param date
	 *            日期类型
	 * @return String 日期
	 * 
	 */
	public static String date2String(Date date) {
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd");
		return clsFormat.format(date);
	}

	/**
	 * 将日期类型转换为指定格式的字符串
	 * 
	 * 
	 * @param date
	 *            日期类型
	 * @param frm
	 *            需要转换的格式,具体格式参加java说明
	 * @return String 日期
	 * 
	 */
	public static String date2String(Date date, String frm) {
		SimpleDateFormat clsFormat = new SimpleDateFormat(frm);
		return clsFormat.format(date);
	}

	/**
	 * 日期加运算
	 * 
	 * @param date
	 *            需要加的日期
	 * @param dateType
	 *            需要加的类型<br>
	 *            数值可以为年份，DateType.YEAR<br>
	 *            数值可以为月份，DateType.MONTH<br>
	 *            数值可以为天数，DateType.DAY<br>
	 *            数值可以为小时，DateType.HH<br>
	 *            数值可以为分钟，DateType.MI<br>
	 *            数值可以为秒，DateType.SS
	 * @param num
	 *            需要加的数值
	 * @return 加完之后的日期
	 */
	public static Date addDate(Date date, DateType dateType, int num) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		if (DateType.YEAR.equals(dateType)) {
			c1.add(Calendar.YEAR, num);
			return c1.getTime();
		}
		if (DateType.MONTH.equals(dateType)) {
			c1.add(Calendar.MONTH, num);
			return c1.getTime();
		}
		if (DateType.DAY.equals(dateType)) {
			c1.add(Calendar.DAY_OF_MONTH, num);
			return c1.getTime();
		}
		if (DateType.HH.equals(dateType)) {
			c1.add(Calendar.HOUR_OF_DAY, num);
			return c1.getTime();
		}
		if (DateType.MI.equals(dateType)) {
			c1.add(Calendar.MINUTE, num);
			return c1.getTime();
		}
		if (DateType.SS.equals(dateType)) {
			c1.add(Calendar.SECOND, num);
			return c1.getTime();
		}
		return date;
	}

	/**
	 * 返回前一天。
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date theDayBefore(Date date) {
		Calendar cdate = Calendar.getInstance();
		cdate.setTime(date);
		cdate.add(Calendar.DATE, -1);
		java.sql.Date ldate = new java.sql.Date(cdate.getTime().getTime());
		return ldate;
	}

	/**
	 * 得到当前月的第一天
	 * 
	 * @return
	 */
	public static java.sql.Date getNowMonth1() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date sysDate = new java.sql.Date(date.getTime().getTime());
		return sysDate;

	}

	/**
	 * 得到指定日期的在这个月的第一日. 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 得到上月1日
	 * 
	 * @return
	 */
	public static java.sql.Date getlastMonth() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		date.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date sysDate = new java.sql.Date(date.getTime().getTime());
		return sysDate;

	}

	/**
	 * 获得次月1日日期
	 * 
	 * @param calcdate
	 * @return
	 * @throws ParseException
	 */
	public static String getMinDate(String calcdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.setTime(sdf.parse(calcdate));
		date.add(Calendar.MONTH, 1);// 设置月为下个月
		date.set(Calendar.DAY_OF_MONTH, date.getMinimum(Calendar.DAY_OF_MONTH));// 设置日为月第一天
		String min_date = sdf.format(date.getTime());
		return min_date;
	}

	/**
	 * 获得次月1日日期
	 * 
	 * @param calcdate
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date getMinDate(Date calcdate) {
		Calendar date = Calendar.getInstance();
		date.setTime(calcdate);
		date.add(Calendar.MONTH, 1);// 设置月为下个月
		date.set(Calendar.DAY_OF_MONTH, date.getMinimum(Calendar.DAY_OF_MONTH));// 设置日为月第一天
		java.sql.Date sysDate = new java.sql.Date(date.getTime().getTime());
		return sysDate;
	}

	/**
	 * 日期比较
	 * <p>
	 * 开始日期在结束日期之前返回-1<br>
	 * 开始日期等于结束日期返回0<br>
	 * 开始日期在结束日期之后返回1
	 * 
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static int compareDate(Date startDate, Date endDate) {

		// 去除掉时分秒的时间
		startDate = DateUtil.string2Date(DateUtil.date2String(startDate));
		endDate = DateUtil.string2Date(DateUtil.date2String(endDate));

		if (startDate.before(endDate)) {
			return -1;
		}
		if (startDate.equals(endDate)) {
			return 0;
		}
		if (startDate.after(endDate)) {
			return 1;
		}
		return -2;
	}

	/**
	 * 得到本年指定日期的相关月份的最后一日 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonthThisYear(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 得到指定日期的当月最后一天
	 * 
	 * @param date
	 *            指定日期
	 * @return 当月最后一天
	 */
	public static java.sql.Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return new java.sql.Date(cal.getTime().getTime());
	}

	/**
	 * 得到前一个月的1号
	 * 
	 * @param date
	 *            指定日期
	 * @return 前一个月的1号
	 */
	public static java.sql.Date getBeforeMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return new java.sql.Date(cal.getTime().getTime());
	}

	/**
	 * 得到日期中的“日”
	 * 
	 * @param date
	 *            指定日期
	 * @return 日期中的“日”
	 */
	public static int getDayOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public boolean equals(Object obj) {
		System.out.println("hello");
		return false;
	}

	/**
	 * 得到指定日期的在这个月的第一日
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date getFirstDayOfDateMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date ldate = new java.sql.Date(cal.getTime().getTime());
		return ldate;
	}

	/**
	 * 将java.sql.Timestamp类型日期，或java.util.Date类型转换为java.sql.Date类型
	 * 
	 * @param obj
	 * @return
	 */
	public static java.sql.Date obj2SqlDate(Object obj) {
		if (obj instanceof java.util.Date) {
			return new java.sql.Date(((java.util.Date) obj).getTime());
		}
		throw new RuntimeException("入参不为日期类型");
	}

	/**
	 * 将java.sql.Date类型日期，或java.util.Date类型转换为java.sql.Timestamp类型
	 * 
	 * @param obj
	 * @return
	 */
	public static Timestamp obj2Timestamp(Object obj) {
		if (obj instanceof java.util.Date) {
			return new java.sql.Timestamp(((java.util.Date) obj).getTime());
		}
		throw new RuntimeException("入参不为日期类型");
	}

	/**
	 * 计算年龄
	 * <p>
	 * 当前系统日期减去生日，得到年龄
	 * 
	 * @param birthday
	 *            生日
	 * @return 年龄
	 */
	public static double calcAge(java.util.Date birthday) {
		return DateUtil.calcAge(birthday,
				new java.sql.Date(System.currentTimeMillis()));

	}

	/**
	 * 计算年龄
	 * <p>
	 * 指定日期减去生日，得到年龄
	 * 
	 * @param birthday
	 *            生日
	 * @param date
	 *            指定日期
	 * @return 年龄
	 */
	public static double calcAge(java.util.Date birthday, java.util.Date date) {
		Calendar _birthday = Calendar.getInstance();
		_birthday.setTime(birthday);
		Calendar _sysDate = Calendar.getInstance();
		_sysDate.setTimeInMillis(date.getTime());
		return _sysDate.get(Calendar.YEAR)
				- _birthday.get(Calendar.YEAR)
				+ (_sysDate.get(Calendar.MONTH) - _birthday.get(Calendar.MONTH))
				/ 12.00
				+ (_sysDate.get(Calendar.DAY_OF_MONTH) - _birthday
						.get(Calendar.DAY_OF_MONTH)) / 365.00;
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.calcAge(DateUtil.string2Date("1988-3-5")));
		System.out.println(DateUtil.calcAge(DateUtil.string2Date("1988-3-6")));
		System.out.println(DateUtil.calcAge(DateUtil.string2Date("1988-3-7")));
	}

	public static String currentDate() {
		return date2String(sysDate());
	}

	public static String currentTime() {
		return date2String(sysTime(), "hh:mm:ss");
	}
	
	/**
	 * 两个日期相减
	 * @param begin
	 * @param end
	 * @return
	 */
	public static long dateSubtract(String begin, String end) {
		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
			java.util.Date beginDate= format.parse(begin);    
			java.util.Date endDate = format.parse(end);
			return (beginDate.getTime()-endDate.getTime())/(24*60*60*1000);    
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}    
	}
	
	/**
	 * 根据日期，获取该月的天数
	 * @param date
	 * @return
	 */
	public static int getDayCountByMonth(String date){
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.string2Date(date));
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);  
	}
	
}
