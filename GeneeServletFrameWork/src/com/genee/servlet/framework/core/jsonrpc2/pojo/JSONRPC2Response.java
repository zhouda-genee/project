package com.genee.servlet.framework.core.jsonrpc2.pojo;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: JSONRPC2Response
 * @Description: 返回jsonrpc对象
 * @author da.zhou@geneegroup.com
 * @date 2014年8月25日 下午2:10:00
 *
 */
public class JSONRPC2Response {

	private String jsonrpc;
	
	private Object result;

	private ErrorMessage error;
	
	private int id;
	
	public JSONRPC2Response (JSONRPC2Request rpc2request, Object result){
		this.jsonrpc = rpc2request.getJsonrpc();
		this.id = rpc2request.getId();
		this.result = result;
	}
	
	public JSONRPC2Response (JSONRPC2Request rpc2request, ErrorMessage error){
		this.jsonrpc = rpc2request.getJsonrpc();
		this.id = rpc2request.getId();
		this.error = error;
	}
	
	public JSONRPC2Response (String jsonrpc, int id, Object result) {
		this.jsonrpc = jsonrpc;
		this.id = id;
		this.result = result;
	}
	
	public JSONRPC2Response (String jsonrpc, int id, ErrorMessage error){
		this.jsonrpc = jsonrpc;
		this.id = id;
		this.error = error;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public ErrorMessage getError() {
		return error;
	}

	public void setError(ErrorMessage error) {
		this.error = error;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		JSONObject responseJsonObject = JSONObject.fromObject(this);
		if (responseJsonObject.optString("error").equals("null"))
			responseJsonObject.remove("error");
		else if (responseJsonObject.optString("result").equals("null"))
			responseJsonObject.remove("result");
		return responseJsonObject.toString();
	}
	
}
