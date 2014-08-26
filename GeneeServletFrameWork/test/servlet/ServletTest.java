package servlet;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.genee.servlet.framework.utils.http.HttpClientUtil;
import com.genee.servlet.framework.utils.json.JsonUtil;

public class ServletTest {
	
	private static String URL = "http://localhost:8088/geneeservletfw/API2";

	@Test
	public void equipmentName() {
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("jsonrpc", "2.0");
		reqParam.put("id", "1");
		reqParam.put("method", "equipment_name");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "X射线");
		reqParam.put("params", JsonUtil.getJsonString4JavaPOJO(params));
		String result = HttpClientUtil.post(URL, reqParam, null);
		System.out.println(result);
	}
	
	@Test
	public void labName() {
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("jsonrpc", "2.0");
		reqParam.put("id", "1");
		reqParam.put("method", "lab_name");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "孔勇发");
		reqParam.put("params", JsonUtil.getJsonString4JavaPOJO(params));
		String result = HttpClientUtil.post(URL, reqParam, null);
		System.out.println(result);
	}
	
	@Test
	public void orgRoot() {
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("jsonrpc", "2.0");
		reqParam.put("id", "1");
		reqParam.put("method", "org_root");
		String result = HttpClientUtil.post(URL, reqParam, null);
		System.out.println(result);
	}
	
	@Test
	public void orgChild() {
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("jsonrpc", "2.0");
		reqParam.put("id", "1");
		reqParam.put("method", "org_child");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "2");
		reqParam.put("params", JsonUtil.getJsonString4JavaPOJO(params));
		String result = HttpClientUtil.post(URL, reqParam, null);
		System.out.println(result);
	}
	
	@Test
	public void userContact() {
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("jsonrpc", "2.0");
		reqParam.put("id", "1");
		reqParam.put("method", "user_contact");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "胡");
		reqParam.put("params", JsonUtil.getJsonString4JavaPOJO(params));
		String result = HttpClientUtil.post(URL, reqParam, null);
		System.out.println(result);
	}

}
