package com.genee.servlet.framework.core.jsonrpc2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.genee.servlet.framework.core.jsonrpc2.JSONRPC2Session;
import com.genee.servlet.framework.core.jsonrpc2.XMLServiceBuilder;
import com.genee.servlet.framework.core.jsonrpc2.pojo.ErrorMessage;
import com.genee.servlet.framework.core.jsonrpc2.pojo.JSONRPC2Request;
import com.genee.servlet.framework.core.jsonrpc2.pojo.JSONRPC2Response;
import com.genee.servlet.framework.core.jsonrpc2.pojo.XMLServiceEntity;
import com.genee.servlet.framework.utils.dateutil.DateUtil;
import com.genee.servlet.framework.utils.json.JsonUtil;

@WebServlet(description = "json-rpc 2.0 server", urlPatterns = { "/API2" })
public class JSONRPC2Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("genee");
	
	private static Map<String, XMLServiceEntity> services = null;
       
    public JSONRPC2Servlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		if (services == null)
			services = XMLServiceBuilder.buildService();
	}

	public void destroy() {
		if (services != null){
			Iterator<XMLServiceEntity> iter = services.values().iterator();
			while (iter.hasNext()){
				iter.remove();
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONRPC2Request rpc2request = null;
		JSONRPC2Response rpc2response = null;
		
		logIn(request); // 记录输入日志
		
		try{
			// 封装rpc2request
			rpc2request = postTingRPCRequest(request);
			if (!services.containsKey(rpc2request.getMethod())) {
				rpc2response = new JSONRPC2Response(rpc2request, new ErrorMessage("没有[" + rpc2request.getMethod() + "]服务"));
			} else {
				XMLServiceEntity service = services.get(rpc2request.getMethod());
				rpc2response = JSONRPC2Session.send(rpc2request, service);
			}
		} catch (Exception ex){
			rpc2response = new JSONRPC2Response(rpc2request, new ErrorMessage(ex.toString()));
		}
		
		logOut(rpc2response); // 记录输出日志
		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();  
	    out.print(rpc2response.toString());  
	    out.flush();  
	    out.close();  
	}
	
	private JSONRPC2Request postTingRPCRequest(HttpServletRequest request){
		
		String id = request.getParameter("id");
		String method = request.getParameter("method");
		String jsonrpc = request.getParameter("jsonrpc");
		Map<String, Object> params = null;
		if (request.getParameter("params") != null){
			String arg = request.getParameter("params");
			params = JsonUtil.getMap4Json(arg);
		}
		
		JSONRPC2Request rpc2Request = new JSONRPC2Request();
		try {
			rpc2Request.setId(Integer.parseInt(id));
		} catch (NumberFormatException ex){
			logger.error(ex.getMessage());
			throw new NumberFormatException("参数ID转换异常");
		}
		rpc2Request.setJsonrpc(jsonrpc);
		rpc2Request.setMethod(method);
		rpc2Request.setParams(params);
		
		return rpc2Request;
		
	}
	
	private void logIn(HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		
		sb.append("【 线程号：" + Thread.currentThread().getId() + "(" + Thread.currentThread().getName() + ")" + "\t开始时间：" + DateUtil.currentDate() + " " + DateUtil.currentTime() + " 】").append("\n");
		sb.append("\t").append("IP：" + request.getRemoteAddr()).append("\n");
		sb.append("\t").append("请求的字符集编码：" + request.getCharacterEncoding()).append("\n");
		
        Enumeration<String> e = request.getHeaderNames();  
        while(e.hasMoreElements()){  
            String str=e.nextElement();
            sb.append("\t").append(str + "：" + request.getHeader(str)).append("\n");
        }
        
        Enumeration<String> params = request.getParameterNames();
		sb.append("\t").append("请求参数列表：").append("\n");
		while(params.hasMoreElements()){
			String param = params.nextElement();
			sb.append("\t\t").append(param + "：").append(request.getParameter(param)).append("\n");
		}
        
		logger.info(sb.toString());
	}
	
	private void logOut(JSONRPC2Response response){
		StringBuilder sb = new StringBuilder();
		sb.append("【 线程号：" + Thread.currentThread().getId() + "("
						+ Thread.currentThread().getName() + ")" + "\t结束时间："
						+ DateUtil.currentDate() + " " + DateUtil.currentTime()
						+ " 】").append("\n");
		sb.append("\t").append("返回结果：" + response.toString());
		logger.info(sb.toString());
	}

}
