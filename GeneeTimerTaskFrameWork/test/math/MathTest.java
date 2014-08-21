package math;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import sun.misc.BASE64Encoder;

import com.genee.timertask.framework.core.base.test.BaseTest;
import com.genee.timertask.framework.utils.http.HttpClientUtil;

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
	
	public static void main(String[] args) throws ParseException{
		/*Date date = new Date(1407995906 * 1000L);
//		System.out.println(date.getTime() * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(date));
		System.out.println(System.currentTimeMillis() / 1000L);*/
		/*Date date =  new Date();
		System.out.println(date.getTime());
		System.out.println(date.getTime()/1000L);*/
		
		/*long time = 1230739200; 
		Timestamp timestamp = new Timestamp(time);
		System.out.println(timestamp.getTime());*/
		
		/*String uuid = UUID.randomUUID().toString();
		System.out.println(uuid + ": " + uuid.length());
		String base64 = new BASE64Encoder().encode(uuid.getBytes());
		System.out.println(base64 + ": " + base64.length());*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str1 = "2014-08-08";
		String str2 = "2014-08-08 00:00:00";
		System.out.println(sdf.parse(str1).getTime());
		System.out.println(sdf1.parse(str2).getTime());
		
	}
	
}
