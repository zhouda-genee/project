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
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.impl.IndexOpenDuration;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexOpenDurationTest extends BaseTest {

	@Autowired
	@Qualifier("open_dur")
	private IndexOpenDuration indexOpenDuration;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void test() throws NumberFormatException, ParseException {
		
		Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();
		
		EquipmentIndexEntity equipmentIndexEntity1 = new EquipmentIndexEntity(UUID.randomUUID().toString(), 2, "3", 1314806400000L);
		equipmentIndexEntity1.setEquipmentId(2);
		equipmentIndexEntity1.setUserId("3");
		equipmentIndexEntity1.setUsedDur((long)50000);
		equipmentIndexEntity1.setOwnerUsedDur((long)10000);
		equipments.put("2#3", equipmentIndexEntity1);
		
		EquipmentIndexEntity equipmentIndexEntity2 = new EquipmentIndexEntity(UUID.randomUUID().toString(), 2, "4", 1314806400000L);
		equipmentIndexEntity2.setEquipmentId(2);
		equipmentIndexEntity2.setUserId("4");
		equipmentIndexEntity2.setUsedDur((long)20000);
		equipmentIndexEntity2.setOwnerUsedDur((long)20000);
		equipments.put("2#4", equipmentIndexEntity2);
		
		String startDate = "2011-09-01 00:00:00";
		String endDate = "2011-09-01 23:59:59";
		
		long lStartDate = sdf.parse(startDate).getTime() / 1000L;
		long lEndDate = sdf.parse(endDate).getTime() / 1000L;
		
		indexOpenDuration.run(lStartDate, lEndDate, equipments);
		
		Assert.assertEquals(3, equipments.size());
		for (Iterator<EquipmentIndexEntity> iter = equipments.values().iterator(); iter.hasNext();) {
			EquipmentIndexEntity equipment = iter.next();
			// 仪器记录，不包含使用者
			if (StringUtils.isEmpty(equipment.getUserId())){
				Assert.assertEquals(70000, equipment.getUsedDur());
				Assert.assertEquals(30000, equipment.getOwnerUsedDur());
				Assert.assertEquals(40000, equipment.getOpenDur());
			}
		}
	}

}
