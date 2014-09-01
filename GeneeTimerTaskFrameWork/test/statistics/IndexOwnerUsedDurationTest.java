package statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.genee.timertask.framework.core.base.test.BaseTest;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.impl.IndexOwnerUsedDuration;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexOwnerUsedDurationTest extends BaseTest {

	@Autowired
	@Qualifier("owner_used_dur")
	private IndexOwnerUsedDuration indexOwnerUsedDuration;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void test() throws NumberFormatException, ParseException {
		
		Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();
		EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(UUID.randomUUID().toString(), 20, "739", 1314806400000L);
		equipmentIndexEntity.setUsedDur((long)123.4);
		equipments.put("2#3", equipmentIndexEntity);
		
		String startDate = "2011-09-01 00:00:00";
		String endDate = "2011-09-01 23:59:59";
		
		long lStartDate = sdf.parse(startDate).getTime() / 1000L;
		long lEndDate = sdf.parse(endDate).getTime() / 1000L;
		
		indexOwnerUsedDuration.run(lStartDate, lEndDate, equipments);
		
		for (Iterator<EquipmentIndexEntity> iter = equipments.values().iterator(); iter.hasNext();) {
			EquipmentIndexEntity equipment = iter.next();
			// 机主使用机时不为0,则认为是机主
			if (equipment.getOwnerUsedDur() != 0)
				Assert.assertEquals(equipment.getUsedDur(), equipment.getOwnerUsedDur());
		}
	}

}
