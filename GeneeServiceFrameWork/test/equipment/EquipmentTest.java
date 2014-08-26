package equipment;

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

public class EquipmentTest extends BaseTest {

	/**
	 * 根据仪器名称模糊查询仪器
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getEquipmentByName() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/equipment/equipmentname";
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", "X射线");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.post(url, param, headers);
		Map map = JsonUtil.getMap4Json(result);
		List<BaseEntity> list = JsonUtil.getList4Json(map.get("baseEntity")
				.toString(), BaseEntity.class);

		// 判断查询出的所有仪器是否包含"X射线"
		for (BaseEntity entity : list) {
			Assert.assertNotEquals(-1, entity.getName().toUpperCase().indexOf("X射线"));
		}
	}
}
