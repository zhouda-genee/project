package statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.genee.timertask.framework.core.base.test.BaseTest;
import com.genee.timertask.module.statistics.index.impl.IndexValidDuration;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexValidDurationTest extends BaseTest {

	@Autowired
	@Qualifier("valid_dur")
	private IndexValidDuration indexValidDuration;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void test() throws NumberFormatException, ParseException {
		
		Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();
//		EquipmentIndexEntity equipmentIndexEntity = new EquipmentIndexEntity(UUID.randomUUID().toString(), 20, "739", 1314806400000L);
//		equipments.put("20#739", equipmentIndexEntity);
		
		String startDate = "2014-04-08 00:00:00";
		String endDate = "2014-04-08 23:59:59";
		
		long lStartDate = sdf.parse(startDate).getTime() / 1000L;
		long lEndDate = sdf.parse(endDate).getTime() / 1000L;
		
		indexValidDuration.run(lStartDate, lEndDate, equipments);
		
		Assert.assertEquals(1, equipments.size());
		
	}

}
