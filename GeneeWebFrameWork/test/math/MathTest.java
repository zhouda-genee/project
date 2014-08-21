package math;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.genee.web.framework.core.base.test.BaseTest;
import com.genee.web.framework.utils.http.HttpClientUtil;

public class MathTest extends BaseTest {

	@Test
	public void add() {
		String url = "http://localhost:8088/geneefw/API2";
		String param = "{\"jsonrpc\": \"2.0\", \"method\": \"add\", \"params\": [5, 6], \"id\": 1}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("rpc2", param);
		String result = HttpClientUtil.post(url, params, null);
		System.out.println(result);
	}
	
	@Test
	public void subtract() {
		String url = "http://localhost:8088/geneefw/API2";
		String param = "{\"jsonrpc\": \"2.0\", \"method\": \"subtract\", \"params\": {\"minuend\": 42, \"subtrahend\": 23}, \"id\": 4}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("rpc2", param);
		String result = HttpClientUtil.post(url, params, null);
		System.out.println(result);
	}
	
	@Test
	public void test(){
		String url = "http://localhost:8088/geneefw/math/add";
		Map<String, String> param = new HashMap<String, String>();
		param.put("a", "5");
		param.put("b", "6");
		String result = HttpClientUtil.post(url, param, null);
		System.out.println(result);
	}
	
	@Test
	public void test2(){
		String url = "http://localhost:8088/geneefw/math/subtract";
		Map<String, String> param = new HashMap<String, String>();
		param.put("a", "8");
		param.put("b", "5");
		String result = HttpClientUtil.post(url, param, null);
		System.out.println(result);
	}
	
	public static void main(String[] args){
		/*Date date = new Date(1407995906 * 1000L);
//		System.out.println(date.getTime() * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(date));
		System.out.println(System.currentTimeMillis() / 1000L);*/
		/*Date date =  new Date();
		System.out.println(date.getTime());
		System.out.println(date.getTime()/1000L);*/
		
		long time = 1230739200; 
		Timestamp timestamp = new Timestamp(time);
		System.out.println(timestamp.getTime());
		
	}
	
}
