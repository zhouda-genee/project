package organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.genee.service.framework.core.base.test.BaseTest;
import com.genee.service.framework.utils.http.HttpClientUtil;
import com.genee.service.framework.utils.json.JsonUtil;
import com.genee.service.module.pojo.TagEntity;

public class OrganizationTest extends BaseTest {
	private static final String ORGANIZATION_NAME = "组织机构";

	/**
	 * 查询组织结构根节点
	 */
	@Test
	public void getRootOrganization() {
		String url = "http://localhost:8088/geneeservicefw/api/rest/organization/root";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.get(url, headers);
		TagEntity entity = (TagEntity) JsonUtil.getObject4JsonString(result,
				TagEntity.class);

		// 判断查询出的根节点名称是否为"组织结构"
		Assert.assertEquals(ORGANIZATION_NAME, entity.getName());
	}

	/**
	 * 查询组织结构根节点
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getChildOrganization() {
		// 查询根节点
		String url = "http://localhost:8088/geneeservicefw/api/rest/organization/root";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", MediaType.APPLICATION_JSON);
		String result = HttpClientUtil.get(url, headers);
		TagEntity entity = (TagEntity) JsonUtil.getObject4JsonString(result,
				TagEntity.class);

		// 查询根节点下的所有子节点
		String curl = "http://localhost:8088/geneeservicefw/api/rest/organization/child?id="
				+ entity.getId();
		String cresult = HttpClientUtil.get(curl, headers);
		List<TagEntity> clist = JsonUtil.getList4Json(cresult, TagEntity.class);

		// 判断查询出的根节点的ccount和子节点个数是否一致
		Assert.assertEquals(entity.getCcount(), clist.size());
	}
}
