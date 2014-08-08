package com.genee.web.framework.utils.http;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.genee.web.framework.utils.dateutil.DateUtil;

/**
 * <pre>
 * HTTP请求代理类
 * </pre>
 * 
 * @author benl
 * @version 1.0, 2007-7-3
 */
public class HttpUtil {
	/**
	 * 连接超时
	 */
	private static int connectTimeOut = 50000;

	/**
	 * 读取数据超时
	 */
	private static int readTimeOut = 50000;

	/**
	 * 请求编码
	 */
	private static String requestEncoding = "GBK";

	private static Logger logger = Logger.getLogger(HttpUtil.class);

	/**
	 * <pre>
	 * 发送带参数的GET的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String doGet(String reqUrl, Map<String, String> parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<Entry<String, String>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry<String, String> element = (Entry<String, String>) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(), HttpUtil.requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			url_con.setConnectTimeout(HttpUtil.connectTimeOut);//（单位：毫秒）jdk 1.5换成这个,连接超时
			url_con.setReadTimeout(HttpUtil.readTimeOut);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			logger.error("网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}

		return responseContent;
	}

	/**
	 * <pre>
	 * 发送不带参数的GET的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @return HTTP响应的字符串
	 */
	public static String doGet(String reqUrl, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			String queryUrl = reqUrl;
			int paramIndex = reqUrl.indexOf("?");

			if (paramIndex > 0) {
				queryUrl = reqUrl.substring(0, paramIndex);
				String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
				String[] paramArray = parameters.split("&");
				for (int i = 0; i < paramArray.length; i++) {
					String string = paramArray[i];
					int index = string.indexOf("=");
					if (index > 0) {
						String parameter = string.substring(0, index);
						String value = string.substring(index + 1, string.length());
						params.append(parameter);
						params.append("=");
						params.append(URLEncoder.encode(value, HttpUtil.requestEncoding));
						params.append("&");
					}
				}

				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(queryUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			url_con.setConnectTimeout(HttpUtil.connectTimeOut);//（单位：毫秒）jdk 1.5换成这个,连接超时
			url_con.setReadTimeout(HttpUtil.readTimeOut);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			logger.error("网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}

		return responseContent;
	}

	/**
	 * <pre>
	 * 发送带参数的POST的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String doPost(String reqUrl, Map<String, String> parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		InputStream in = null;
		BufferedReader rd = null;
		OutputStream os = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<Entry<String, String>> iter = parameters.entrySet().iterator(); iter .hasNext();) {
				Entry<String, String> element = (Entry<String, String>) iter.next();
				params.append(element.getKey());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue(), "UTF-8"));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			// ================================ 开始记录日志 =====================================
			StringBuilder sb = new StringBuilder();
			sb.append("【 线程号：" + Thread.currentThread().getName() + "\t地址：" + reqUrl + "\t开始时间：" + DateUtil.currentDate() + " " + DateUtil.currentTime() + " 】");
			sb.append("\n").append("\t请求参数列表：" + params);
			for (Iterator<Entry<String, String>> entry = parameters.entrySet().iterator(); entry.hasNext();) {
				Entry<String, String> result = entry.next();
				sb.append("\n").append("\t\t").append(result.getKey() + "：").append(result.getValue());
			}
			logger.warn(sb.toString());
			
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setConnectTimeout(HttpUtil.connectTimeOut);//（单位：毫秒）jdk 1.5换成这个,连接超时
			url_con.setReadTimeout(HttpUtil.readTimeOut);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			os = url_con.getOutputStream();
			os.write(b, 0, b.length);
			os.flush();
			os.close();

			in = url_con.getInputStream();
			rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			
			StringBuffer tempStr = new StringBuffer();
			String tempLine = "";
			while ((tempLine = rd.readLine()) != null) {
				tempStr.append(tempLine);
			}
			responseContent = tempStr.toString();
			// ================================ 结束记录日志 =====================================
			logger.warn("\t返回xml结果：" + responseContent);
			logger.warn("【 线程号：" + Thread.currentThread().getName() + "\t地址：" + reqUrl + "\t结束时间：" + DateUtil.currentDate() + " " + DateUtil.currentTime() + " 】");
			
			rd.close();
			in.close();
		} catch (IOException e) {
			logger.error("网络故障", e);
			e.printStackTrace();
		} finally {
			try {
				if (url_con != null)
					url_con.disconnect();
				if (in != null)
					in.close();
				if (rd != null)
					rd.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		return responseContent;
	}
	
	/**
	 * 下载文件到本地
	 * @param reqUrl
	 * @param parameters
	 * @param recvEncoding
	 * @param target 			存储路径<br/>
	 * @return
	 */
	public static String downloadFile(String reqUrl, Map<String, String> parameters, String recvEncoding, String target, String fileName) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<Entry<String, String>> iter = parameters.entrySet().iterator(); iter .hasNext();) {
				Entry<String, String> element = (Entry<String, String>) iter.next();
				params.append(element.getKey());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue(), HttpUtil.requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			url_con.setConnectTimeout(HttpUtil.connectTimeOut);//（单位：毫秒）jdk 1.5换成这个,连接超时
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			String spx = "";
			String[] args = url_con.getContentType().split(";");
			String contentType = args[0];
			if (contentType.equals("application/msword")) {
				spx = ".doc";
			} else if (contentType.equals("application/vnd.ms-excel")) {
				spx = ".xls";
			} else if (contentType.equals("application/vnd.ms-powerpoint")) {
				spx = ".ppt";
			} else if (contentType.equals("image/gif")) {
				spx = ".gif";
			} else if (contentType.equals("image/png")) {
				spx = ".png";
			} else if (contentType.equals("image/jpg")) {
				spx = ".doc";
			} else if (contentType.equals("text/plain")) {
				spx = ".txt";
			} else if (contentType.equals("application/pdf")) {
				spx = ".pdf";
			}
			
			byte[] buffer = new byte[4 * 1024];
			int read;
			FileOutputStream os = new FileOutputStream(target + fileName + spx);
			InputStream in = url_con.getInputStream();//重定向输入
			while ((read = in.read(buffer)) > 0) {//读取输出
				os.write(buffer, 0, read);//写入本地文件
			}
			os.close();
			in.close();
		} catch (IOException e) {
			logger.error("网络故障", e);
			e.printStackTrace();
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;
	}

	/**
	 * @return 连接超时(毫秒)
	 * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static int getConnectTimeOut() {
		return HttpUtil.connectTimeOut;
	}

	/**
	 * @return 读取数据超时(毫秒)
	 * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
	 */
	public static int getReadTimeOut() {
		return HttpUtil.readTimeOut;
	}

	/**
	 * @return 请求编码
	 * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
	 */
	public static String getRequestEncoding() {
		return requestEncoding;
	}

	/**
	 * @param connectTimeOut
	 *            连接超时(毫秒)
	 * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static void setConnectTimeOut(int connectTimeOut) {
		HttpUtil.connectTimeOut = connectTimeOut;
	}

	/**
	 * @param readTimeOut
	 *            读取数据超时(毫秒)
	 * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
	 */
	public static void setReadTimeOut(int readTimeOut) {
		HttpUtil.readTimeOut = readTimeOut;
	}

	/**
	 * @param requestEncoding
	 *            请求编码
	 * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
	 */
	public static void setRequestEncoding(String requestEncoding) {
		HttpUtil.requestEncoding = requestEncoding;
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("actionType", "1");
		// map.put("issueId", "33");
		String temp = HttpUtil.doPost(
				"http://192.168.0.99/AgentPortal/autoHandler", map, "GBK");
		System.out.println("返回的消息是:" + temp);

	}
	
}
