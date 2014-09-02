package statistics;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.genee.web.framework.core.base.test.BaseTest;
import com.genee.web.module.service.statistics.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
public class RoleTest extends BaseTest {
	
	@Autowired
	private UserService userService;

	@Test
	public void testRole() {
		String role = userService.queryRoleByUser(1);
		Assert.assertEquals("1", role);
	}
	
	@Test
	public void testLab(){
		String role = userService.queryRoleByUser(81);
		Assert.assertEquals("2", role);
	}
	
	@Test
	public void testContact(){
		String role = userService.queryRoleByUser(2);
		Assert.assertEquals("3", role);
	}
	
	@Test
	public void testContactAndLab(){
		String role = userService.queryRoleByUser(16);
		Assert.assertEquals("23", role);
	}

}
