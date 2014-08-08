package com.genee.web.framework.core.error;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.genee.web.framework.utils.dateutil.DateUtil;

public class LogInstance {

	private final static Logger logger = Logger.getLogger("genee");

	private LogInstance() {
	};

	/**
	 * 适配weaver #writelog(Object)
	 * 
	 * @param obj
	 */
	public static void error(Object obj) {
		if (obj instanceof Exception)
			writeError((Exception) obj);
		else
			logger.error(obj);
	}

	/**
	 * 适配weaver #writelog(String, Exception)
	 * 
	 * @param message
	 * @param ex
	 */
	public static void error(Object message, Exception ex) {
		logger.error(message);
		writeError(ex);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void debug(String message) {
		logger.debug(message);
	}

	public static void info(String message) {
		logger.info(message);
	}

	private static void writeError(Throwable e) {
		StackTraceElement[] exception = e.getStackTrace();
		StringBuilder sb = new StringBuilder();
		sb.append("\t" + e.getClass().getName() + ":" + e.getMessage());
		StackTraceElement ste = null;
		for (int i = 0; i < exception.length; i++) {
			ste = exception[i];
			sb.append("\n\t    at " + ste.getClassName() + "."
					+ ste.getMethodName() + "(" + ste.getFileName() + ":"
					+ ste.getLineNumber() + ")");
		}
		logger.error(sb.toString());
		moreException(e.getCause());
	}

	private static void moreException(Throwable e) {
		if (e != null) {
			logger.error("\tCaused by: " + e.getClass().getName() + ":"
					+ e.getMessage() + "");
			StackTraceElement[] exception = e.getStackTrace();
			StackTraceElement ste = null;
			for (int i = 0; i < exception.length; i++) {
				ste = exception[i];
				logger.error("\t    at " + ste.getClassName() + "."
						+ ste.getMethodName() + "(" + ste.getFileName() + ":"
						+ ste.getLineNumber() + ")");
			}
			moreException(e.getCause());
		}
	}

	public static void formatMessage(String functionName, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("【 ========================\t线程号："
				+ Thread.currentThread().getName() + "\t功能名称：" + functionName
				+ "\t操作时间：" + DateUtil.currentDate() + " "
				+ DateUtil.currentTime() + "\t======================== 】");
		if (request != null) {
			sb.append("\n").append("\t请求参数列表：");
			for (Enumeration<String> requestEnum = request.getParameterNames(); requestEnum.hasMoreElements();) {
				String name = requestEnum.nextElement();
				sb.append("\n").append("\t\t").append(name + "：").append(request.getParameter(name));
			}
		}
		warn(sb.toString());
	}

}
