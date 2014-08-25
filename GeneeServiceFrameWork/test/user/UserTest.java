package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.genee.service.framework.core.base.test.BaseTest;
import com.genee.service.framework.utils.http.HttpClientUtil;
import com.genee.service.framework.utils.json.JsonUtil;
import com.genee.service.module.pojo.BaseEntity;

public class UserTest extends BaseTest {

	/**
	 * 模糊查询所有联系人
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getContact() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/user/contact";
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", "胡");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.post(url, param, headers);
		Map map = JsonUtil.getMap4Json(result);
		List<BaseEntity> list = JsonUtil.getList4Json(map.get("baseEntity")
				.toString(), BaseEntity.class);

		// 判断查询出的所有联系人名称是否包含"胡"
		for (BaseEntity entity : list) {
			Assert.assertNotEquals(-1, entity.getName().indexOf("胡"));
		}
	}

	/**
	 * 模糊查询所有负责人
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getIncharge() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/user/incharge";
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", "胡");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.post(url, param, headers);
		Map map = JsonUtil.getMap4Json(result);
		List<BaseEntity> list = JsonUtil.getList4Json(map.get("baseEntity")
				.toString(), BaseEntity.class);

		// 判断查询出的所有负责人名称是否包含"胡"
		for (BaseEntity entity : list) {
			Assert.assertNotEquals(-1, entity.getName().indexOf("胡"));
		}
	}
	
	/**
	 * 模糊查询所有使用者
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getUserInRecord() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/user/recuser";
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", "胡");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.post(url, param, headers);
		Map map = JsonUtil.getMap4Json(result);
		List<BaseEntity> list = JsonUtil.getList4Json(map.get("baseEntity")
				.toString(), BaseEntity.class);

		// 判断查询出的所有负责人名称是否包含"胡"
		for (BaseEntity entity : list) {
			Assert.assertNotEquals(-1, entity.getName().indexOf("胡"));
		}
	}
}
