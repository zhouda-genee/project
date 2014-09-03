package com.genee.servlet.framework.core.jsonrpc2.pojo;

import java.util.Map;

/**
 * 
 * @ClassName: JSONRPC2Request
 * @Description: jsonrpc2请求对象
 * @author da.zhou@geneegroup.com
 * @date 2014年8月25日 下午2:01:57
 *
 */
public class JSONRPC2Request {

	private String jsonrpc;

	private String method;

	private Map<String, Object> params;
	
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
