package lab;

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

public class LabTest extends BaseTest {

	/**
	 * 根据课题组名称模糊查询课题组
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getLabByName() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/lab/labname";
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", "孔勇发");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.post(url, param, headers);
		List<BaseEntity> list = JsonUtil.getList4Json(result, BaseEntity.class);

		// 判断查询出的所有课题组是否包含"孔勇发"
		for (BaseEntity entity : list) {
			Assert.assertNotEquals(-1, entity.getName().indexOf("孔勇发"));
		}
	}
}
