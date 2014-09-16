package statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.genee.timertask.framework.core.base.test.BaseTest;
import com.genee.timertask.framework.utils.dateutil.DateUtil;
import com.genee.timertask.module.statistics.TaskEquipmentIndex;

@RunWith(SpringJUnit4ClassRunner.class)
public class TaskEquipmentTest extends BaseTest {
	
	@Autowired
	private TaskEquipmentIndex taskEquipmentIndex;
	
	@Test
	public void test() throws Exception {
		
		String startDate = "2013-01-01";
		String endDate = "2014-01-01";
		
		System.out.println("开始时间：" + DateUtil.currentSystemTimeString());
		taskEquipmentIndex.runHM(DateUtil.string2Date(startDate), DateUtil.string2Date(endDate));
		System.out.println("结束时间：" + DateUtil.currentSystemTimeString());
		
	}
	
	@Test
	public void test2(){
		System.out.println("Test start.");
        ApplicationContext context = new ClassPathXmlApplicationContext("quartz-config.xml");
        //如果配置文件中将startQuertz bean的lazy-init设置为false 则不用实例化
        //context.getBean("startQuertz");
        System.out.print("Test end..");
	}

}
