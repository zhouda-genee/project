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
import com.genee.timertask.module.statistics.index.impl.IndexOffcampusCharge;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexOffcampusChargeTest extends BaseTest {

	@Autowired
	@Qualifier("off_cam_charge")
	private IndexOffcampusCharge indexOffcampusCharge;

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings("deprecation")
	@Test
	public void test() throws NumberFormatException, ParseException {

		Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();

		EquipmentIndexEntity equipmentIndexEntity1 = new EquipmentIndexEntity(
				UUID.randomUUID().toString(), 2, "3", 1314806400000L);
		equipmentIndexEntity1.setEquipmentId(2);
		equipmentIndexEntity1.setUserId("3");
		equipmentIndexEntity1.setOffCamCharge(49999.77);
		equipments.put("2#3", equipmentIndexEntity1);

		EquipmentIndexEntity equipmentIndexEntity2 = new EquipmentIndexEntity(
				UUID.randomUUID().toString(), 2, "3", 1314806400000L);
		equipmentIndexEntity2.setEquipmentId(2);
		equipmentIndexEntity2.setUserId("3");
		equipmentIndexEntity2.setOffCamCharge(20000.33);
		equipments.put("2#3", equipmentIndexEntity2);

		String startDate = "2014-09-01 00:00:00";
		String endDate = "2014-09-01 23:59:59";

		long lStartDate = sdf.parse(startDate).getTime() / 1000L;
		long lEndDate = sdf.parse(endDate).getTime() / 1000L;

		indexOffcampusCharge.run(lStartDate, lEndDate, equipments);

		Assert.assertEquals(1, equipments.size());
		for (Iterator<EquipmentIndexEntity> iter = equipments.values()
				.iterator(); iter.hasNext();) {
			EquipmentIndexEntity equipment = iter.next();

			if (StringUtils.isEmpty(equipment.getUserId())) {
				Assert.assertEquals(70000L, equipment.getOffCamCharge());
			}
		}
	}
}
