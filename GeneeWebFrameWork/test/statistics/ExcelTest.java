package statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.genee.web.framework.core.base.test.BaseTest;
import com.genee.web.framework.utils.poi.ExcelUtil;
import com.genee.web.module.constants.ExcelType;
import com.genee.web.module.service.statistics.ExcelService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExcelTest extends BaseTest {
	@Autowired
	private ExcelService excelService;

	String identities;

	@Before
	public void init() {
		identities = "1,2,4,5,6,8,11,13";
	}

	/**
	 * 获取Excel的一级标题
	 */
	@Test
	public void getTopHeaders() {
		List<Map<String, Object>> topHeaders = excelService
				.getTopHeaders(identities);
		Assert.assertEquals(topHeaders.size(), 2);
		Assert.assertEquals(topHeaders.get(0).get("name").toString(), "基本信息");
		Assert.assertEquals(topHeaders.get(1).get("name").toString(), "使用信息");
	}

	/**
	 * 获取Excel的二级标题
	 */
	@Test
	public void getMidHeaders() {
		List<Map<String, Object>> midHeaders = excelService
				.getMidHeaders(identities);
		Assert.assertEquals(midHeaders.size(), 8);
		Assert.assertEquals(midHeaders.get(0).get("name").toString(), "仪器名称");
		Assert.assertEquals(midHeaders.get(1).get("name").toString(), "仪器价格");
		Assert.assertEquals(midHeaders.get(2).get("name").toString(), "联系人");
		Assert.assertEquals(midHeaders.get(3).get("name").toString(), "入网时长");
		Assert.assertEquals(midHeaders.get(4).get("name").toString(), "故障时长");
		Assert.assertEquals(midHeaders.get(5).get("name").toString(), "使用机时");
		Assert.assertEquals(midHeaders.get(6).get("name").toString(), "有效机时");
		Assert.assertEquals(midHeaders.get(7).get("name").toString(), "科研机时");
	}

	/**
	 * 导出excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Test
	public void exportExcel() throws FileNotFoundException, IOException {
		List<Map<String, Object>> topHeaders = excelService
				.getTopHeaders(identities);
		List<Map<String, Object>> midHeaders = excelService
				.getMidHeaders(identities);
		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
		Map<String, Object> content1 = new HashMap<String, Object>();
		content1.put("eq_name", "仪器名称1");
		content1.put("eq_price", "11.11");
		content1.put("linkman", "联系人1");
		content1.put("innet_dur", "11");
		content1.put("fault_dur", "22");
		content1.put("used_dur", "33");
		content1.put("valid_dur", "44");
		content1.put("scientific_dur", "55");

		Map<String, Object> content2 = new HashMap<String, Object>();
		content2.put("eq_name", "仪器名称2");
		content2.put("eq_price", "22.22");
		content2.put("linkman", "联系人2");
		content2.put("innet_dur", "22");
		content2.put("fault_dur", "33");
		content2.put("used_dur", "44");
		content2.put("valid_dur", "55");
		content2.put("scientific_dur", "66");

		contents.add(content1);
		contents.add(content2);
		
		Map<String, Object> total = new HashMap<String, Object>();
		total.put("eq_price", "33.33");
		total.put("linkman", "联系人1,2");
		total.put("innet_dur", "33");
		total.put("fault_dur", "55");
		total.put("used_dur", "77");
		total.put("valid_dur", "99");
		total.put("scientific_dur", "121");
		
		Workbook workbook = ExcelUtil.buildExcel(ExcelType.XLSX, topHeaders,
				midHeaders, contents, total);
		workbook.write(new FileOutputStream(new File(
				"/Users/hujinzhe/Downloads/test.xlsx")));
	}
}
