package statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.genee.timertask.framework.core.base.test.BaseTest;
import com.genee.timertask.module.statistics.index.impl.IndexStuSamCnt;
import com.genee.timertask.module.statistics.index.impl.IndexUsedSamCnt;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexStuSamCntTest extends BaseTest {

	@Autowired
	@Qualifier("stu_sam_cnt")
	private IndexStuSamCnt indexStuSamCnt;

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Test
	public void test() throws NumberFormatException, ParseException {

		Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();

		EquipmentIndexEntity equipmentIndexEntity1 = new EquipmentIndexEntity(
				UUID.randomUUID().toString(), 2, "3", 1314806400000L);
		equipmentIndexEntity1.setEquipmentId(2);
		equipmentIndexEntity1.setUserId("3");
		equipmentIndexEntity1.setStuSamCnt(50000);
		equipments.put("2#3", equipmentIndexEntity1);

		EquipmentIndexEntity equipmentIndexEntity2 = new EquipmentIndexEntity(
				UUID.randomUUID().toString(), 2, "3", 1314806400000L);
		equipmentIndexEntity2.setEquipmentId(2);
		equipmentIndexEntity2.setUserId("3");
		equipmentIndexEntity2.setStuSamCnt(20000);
		equipments.put("2#3", equipmentIndexEntity2);

		String startDate = "2014-08-21 00:00:00";
		String endDate = "2014-08-21 23:59:59";

		long lStartDate = sdf.parse(startDate).getTime() / 1000L;
		long lEndDate = sdf.parse(endDate).getTime() / 1000L;

		indexStuSamCnt.run(lStartDate, lEndDate, equipments);

		Assert.assertEquals(1, equipments.size());
		for (Iterator<EquipmentIndexEntity> iter = equipments.values()
				.iterator(); iter.hasNext();) {
			EquipmentIndexEntity equipment = iter.next();

			if (StringUtils.isEmpty(equipment.getUserId())) {
				Assert.assertEquals(70000, equipment.getStuSamCnt());
			}
		}
	}
}
