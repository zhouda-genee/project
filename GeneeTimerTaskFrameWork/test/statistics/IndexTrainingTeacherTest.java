package statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.genee.timertask.framework.core.base.test.BaseTest;
import com.genee.timertask.framework.utils.json.JsonUtil;
import com.genee.timertask.module.pojo.EquipmentIndexEntity;
import com.genee.timertask.module.statistics.index.impl.IndexTrainingTeacher;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexTrainingTeacherTest extends BaseTest{

	@Autowired
	@Qualifier("train_tea")
	private IndexTrainingTeacher indexTrainingTeacher;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void test() throws NumberFormatException, ParseException {
		
		Map<String, EquipmentIndexEntity> equipments = new HashMap<String, EquipmentIndexEntity>();
		
		String startDate = "2011-09-02 00:00:00";
		String endDate = "2011-09-02 23:59:59";
		
		long lStartDate = sdf.parse(startDate).getTime() / 1000L;
		long lEndDate = sdf.parse(endDate).getTime() / 1000L;
		
		indexTrainingTeacher.run(lStartDate, lEndDate, equipments);
		
		System.out.println(JsonUtil.getJsonString4JavaPOJO(equipments));
		
	}

}
