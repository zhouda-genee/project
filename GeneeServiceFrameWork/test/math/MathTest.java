package math;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.genee.service.framework.utils.http.HttpClientUtil;

public class MathTest {

	@Test
	public void test() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/math/getusers2";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		String res = HttpClientUtil.get(url, headers);
		System.out.println(res);
	}

}
