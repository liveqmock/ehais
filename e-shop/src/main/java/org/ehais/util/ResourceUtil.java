package org.ehais.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceUtil {
	private static final Locale locale = Locale.getDefault();  
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config/config",locale);

	private void ResourceUtil() {
	}


	/**
	 * 获得某个属性值
	 * 
	 * @return
	 */
	public static final String getProValue(String name) {
		return bundle.getString(name);
	}
	
}
