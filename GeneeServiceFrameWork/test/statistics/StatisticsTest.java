package statistics;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.genee.service.framework.utils.http.HttpClientUtil;

public class StatisticsTest {

	@Test
	public void test() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("eq_name", "X射线");
		params.put("dstart", "1314806400");
		params.put("dend", "1314806400");
		params.put("size", "15");
		params.put("page", "1");
		/*params.put("eq_type", "");
		params.put("eq_org", "");
		params.put("eq_contact", "");
		params.put("eq_incharge", "");
		params.put("lab_org", "");
		params.put("lab", "");
		params.put("user", "");
		params.put("sort_name", "");
		params.put("sort", "");*/
		String url = "http://localhost:8088/geneeservicefw/api/rest/statistics/eqindex/page";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String res = HttpClientUtil.post(url, params, headers);
		System.out.println(res);
	}
	
	@Test
	public void test1() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("eq_name", "X射线");
		params.put("dstart", "1314806400");
		params.put("dend", "1314806400");
		/*params.put("eq_type", "");
		params.put("eq_org", "");
		params.put("eq_contact", "");
		params.put("eq_incharge", "");
		params.put("lab_org", "");
		params.put("lab", "");
		params.put("user", "");
		params.put("sort_name", "");
		params.put("sort", "");*/
		String url = "http://localhost:8088/geneeservicefw/api/rest/statistics/eqindex";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String res = HttpClientUtil.post(url, params, headers);
		System.out.println(res);
	}
	
	@Test
	public void test2() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("eq_name", "X射线");
		params.put("dstart", "1314806400");
		params.put("dend", "1314806400");
		/*params.put("eq_type", "");
		params.put("eq_org", "");
		params.put("eq_contact", "");
		params.put("eq_incharge", "");
		params.put("lab_org", "");
		params.put("lab", "");
		params.put("user", "");
		params.put("sort_name", "");
		params.put("sort", "");*/
		String url = "http://localhost:8088/geneeservicefw/api/rest/statistics/eqindex/count";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String res = HttpClientUtil.post(url, params, headers);
		System.out.println(res);
	}

}
