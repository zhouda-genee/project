package com.genee.servlet.framework.core.jsonrpc2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.genee.servlet.framework.core.jsonrpc2.GeneeJSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

@WebServlet(description = "json-rpc 2.0 server", urlPatterns = { "/API2" })
public class JSONRPC2Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = Logger.getLogger(getClass());
       
    public JSONRPC2Servlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		// 初始化配置文件
	}

	public void destroy() {
		// 销毁配置文件
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 封闭jsonrpc对象，并记录日志
		String rpcContext = request.getParameter("rpc2");
		JSONObject jsonObj = JSONObject.fromObject(rpcContext);
		Object __param = jsonObj.get("params");
		JSONRPC2Request rpc2request = null;
		if (__param instanceof java.util.List)
			rpc2request = new JSONRPC2Request(jsonObj.optString("method"), (List<Object>) __param, jsonObj.optInt("id"));
		else if (__param instanceof java.util.Map)
			rpc2request = new JSONRPC2Request(jsonObj.optString("method"), (Map <String,Object>) __param, jsonObj.optInt("id"));
		
		// 取得method
		String url = null;
		String method = rpc2request.getMethod();
		
		// 根据method去配置文件获取地址及参数
		if (method.equals("add"))
			url = "http://localhost:8088/geneefw/math/add";
		else if (method.equals("subtract"))
			url = "http://localhost:8088/geneefw/math/subtract";
		
		// 请求服务地址并返回结果，将集果封装成JSONRPC2Response
		GeneeJSONRPC2Session rpcSession = new GeneeJSONRPC2Session(new URL(url));
		try {
			JSONRPC2Response rpc2response = rpcSession.send(rpc2request);
			JSONObject responseJsonObject = JSONObject.fromObject(rpc2response);
			if ("null".equals(responseJsonObject.optString("error")))
				responseJsonObject.remove("error");
			else if ("null".equals(responseJsonObject.optString("result")))
				responseJsonObject.remove("result");
			if ("null".equals(responseJsonObject.optString("nonStdAttributes")))
				responseJsonObject.remove("nonStdAttributes");
			responseJsonObject.put("jsonrpc", GeneeJSONRPC2Session.VERSION);
			PrintWriter out = response.getWriter();  
		    out.print(responseJsonObject.toString());  
		    out.flush();  
		    out.close();  
		} catch (JSONRPC2SessionException e) {
			e.printStackTrace();
		}
	}

}
