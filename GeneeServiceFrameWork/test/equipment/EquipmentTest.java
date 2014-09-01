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
import com.genee.service.module.pojo.TagEntity;

public class EquipmentTest extends BaseTest {

	private static final String EQUIPMENT_NAME = "仪器分类";
	
	/**
	 * 根据仪器名称模糊查询仪器
	 */
	@SuppressWarnings("unchecked" )
	@Test
	public void getEquipmentByName() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/equipment/equipmentname";
		Map<String, String> param = new HashMap<String, String>();
		param.put("name", "X射线");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.post(url, param, headers);
		List<BaseEntity> list = JsonUtil.getList4Json(result, BaseEntity.class);

		// 判断查询出的所有仪器是否包含"X射线"
		for (BaseEntity entity : list) {
			Assert.assertNotEquals(-1, entity.getName().toUpperCase().indexOf("X射线"));
		}
	}

	/**
	 * 查询仪器分类根节点
	 */
	@Test
	public void getRootEquipment() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/equipment/root";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.get(url, headers);
		TagEntity entity = (TagEntity) JsonUtil.getObject4JsonString(result,
				TagEntity.class);

		// 判断查询出的根节点名称是否为"仪器分类"
		Assert.assertEquals(EQUIPMENT_NAME, entity.getName());
	}

	/**
	 * 查询仪器分类根节点
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	public void getChildEquipment() {
		// 查询根节点
		String url = "http://localhost:8088/geneeservicefw/api/rest/equipment/root";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.get(url, headers);
		TagEntity entity = (TagEntity) JsonUtil.getObject4JsonString(result,
				TagEntity.class);

		// 查询根节点下的所有子节点
		String curl = "http://localhost:8088/geneeservicefw/api/rest/equipment/child?id="
				+ entity.getId();
		String cresult = HttpClientUtil.get(curl, headers);
		List<TagEntity> clist = JsonUtil.getList4Json(cresult, TagEntity.class);

		// 判断查询出的根节点的ccount和子节点个数是否一致
		Assert.assertEquals(entity.getCcount(), clist.size());
	}
}
