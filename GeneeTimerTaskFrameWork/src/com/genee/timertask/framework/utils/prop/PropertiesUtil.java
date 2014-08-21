package com.genee.timertask.framework.utils.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	private static final String CONFIG_PATH = "app_config/";

	private PropertiesUtil(){}
	/**
	 * 读取src目录下的配置文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Properties getPropertiesBySrc(String fileName){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PATH + fileName);
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
	
}
