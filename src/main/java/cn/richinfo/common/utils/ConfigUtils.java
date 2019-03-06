package cn.richinfo.common.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
	
	private static Properties sysConfig = null;
	static {
		sysConfig = new Properties();
		InputStream inputStream = ConfigUtils.class.getResourceAsStream("/sysConfig.properties");
		try {
			sysConfig.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static String getByKey(String key) {
		return sysConfig.getProperty(key);
	}

	public synchronized static String getByKey(String key,String defaultValue) {
		String configValue = sysConfig.getProperty(key);
		if(configValue==null || configValue.equals(""))
			return defaultValue;
		else
			return configValue;
	}
	public synchronized static boolean reloadConfig() {
		boolean result = true;
		sysConfig = new Properties();
		InputStream inputStream = ConfigUtils.class.getResourceAsStream("/sysConfig.properties");
		try {
			sysConfig.load(inputStream);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
