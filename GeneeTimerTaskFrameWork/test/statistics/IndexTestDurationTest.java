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
import com.genee.timertask.module.statistics.index.impl.IndexTestDuration;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexTestDurationTest extends BaseTest {
	
	@Autowired
	@Qualifier("test_dur")
	private IndexTestDuration IndexTestDuration;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void test() throws NumberFormatException, ParseException {
		
		Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();
		String startDate = "2012-01-01 00:00:00";
		String endDate = "2012-01-01 23:59:59";
		
		long lStartDate = sdf.parse(startDate).getTime() / 1000L;
		long lEndDate = sdf.parse(endDate).getTime() / 1000L;
		
		IndexTestDuration.run(lStartDate, lEndDate, equipments);
		
		EquipmentIndexEntity equipment = equipments.get("80#372");
		Assert.assertEquals(0, equipment.getTestDur());
		
	}

}
