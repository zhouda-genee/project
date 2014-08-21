package math;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.genee.service.framework.utils.http.HttpClientUtil;

public class MathTest {

	@Test
	public void test() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/math/add";
		Map<String, String> params = new HashMap<String, String>();
		params.put("a", "123");
		params.put("b", "456");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		String res = HttpClientUtil.post(url, params, headers);
		System.out.println(res);
	}

}
