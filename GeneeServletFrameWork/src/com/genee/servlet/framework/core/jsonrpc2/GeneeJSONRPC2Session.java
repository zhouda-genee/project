package com.genee.servlet.framework.core.jsonrpc2;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.genee.servlet.framework.utils.http.HttpClientUtil;
import com.genee.servlet.framework.utils.json.JsonUtil;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

public class GeneeJSONRPC2Session extends JSONRPC2Session {
	
	public static final String VERSION = "2.0";

	public GeneeJSONRPC2Session(URL url) {
		super(url);
	}

	@SuppressWarnings({ "incomplete-switch", "unchecked", "rawtypes" })
	public JSONRPC2Response send(JSONRPC2Request request) throws JSONRPC2SessionException {
		
		// Create and configure URL connection to server endpoint
		String httpServiceUrl = getURL().toString();

		// The params can be omitted if none
		Map<String, String> req = new HashMap<String, String>();
		
		switch (request.getParamsType()) {
			case ARRAY:
				req.put("params", JsonUtil.getJsonString4List(request.getPositionalParams()).replace("[", "").replace("]", ""));
				break;
			case OBJECT:
//				req = request.getNamedParams();
				break;
		}
		
		Map <String,String> nonStdAttributes = null;//request.getNonStdAttributes();
		
		if (nonStdAttributes != null) {
			req.putAll(nonStdAttributes);
		}

		// Send request encoded as JSON
		// 返回结果包含result 或者 error 这两个互斥
		String result = HttpClientUtil.post(httpServiceUrl, req, null);
		JSONObject joResult = JSONObject.fromObject(result);
		
		Map resultMap = new HashMap();
		if (joResult.get("result") != null)
			resultMap.putAll(JsonUtil.getMap4Json(joResult));
		else if (joResult.get("error") != null)
			resultMap.putAll(JsonUtil.getMap4Json(joResult));
		resultMap.put("id", request.getID());
		resultMap.put("jsonrpc", VERSION);

		// Parse and return the response
		JSONRPC2Response response = null;

		try {
			response = JSONRPC2Response.parse(JsonUtil.getJsonString4JavaPOJO(resultMap), 
					getOptions().preservesParseOrder(), 
					getOptions().ignoresVersion(),
					getOptions().parsesNonStdAttributes());

		} catch (JSONRPC2ParseException e) {

			throw new JSONRPC2SessionException(
					"Invalid JSON-RPC 2.0 response",
					JSONRPC2SessionException.BAD_RESPONSE,
					e);
		}

		// Response ID must match the request ID, except for
		// -32700 (parse error), -32600 (invalid request) and 
		// -32603 (internal error)

		Object reqID = request.getID();
		Object resID = response.getID();

		if (reqID != null && resID !=null && reqID.toString().equals(resID.toString()) ) {
			// ok
		}
		else if (reqID == null && resID == null) {
			// ok
		}
		else if (! response.indicatesSuccess() && ( response.getError().getCode() == -32700 ||
				response.getError().getCode() == -32600 ||
				response.getError().getCode() == -32603    )) {
			// ok
		}
		else {
			throw new JSONRPC2SessionException(
					"Invalid JSON-RPC 2.0 response: ID mismatch: Returned " + 
					resID.toString() + ", expected " + reqID.toString(),
					JSONRPC2SessionException.BAD_RESPONSE);
		}

		return response;
	}

	

}
