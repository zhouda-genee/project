package com.genee.web.framework.core.base.test;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:BaseTest Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author FunnyWang
 * @version
 * @since Ver 1.1
 * @Date 2010-6-23 上午09:29:24
 * 
 * @see
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseTest {
	protected transient final Logger logger = Logger.getLogger(getClass());
}
