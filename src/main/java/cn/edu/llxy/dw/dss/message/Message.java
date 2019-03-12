package cn.edu.llxy.dw.dss.message;

import cn.edu.llxy.dw.dss.exception.ResourceManager;


/**
 * Message.java get i18n msg form config file
 * 
 */
public class Message {

	protected Message() {
	}

	protected static String getString(String baseName, String key) {
		return getString(baseName, key, new String[] {});
	}

	protected static String getString(String baseName, String key, String param1) {
		return getString(baseName, key, new String[] { param1 });
	}

	protected static String getString(String baseName, String key, String param1, String param2) {
		return getString(baseName, key, new String[] { param1, param2 });
	}

	protected static String getString(String baseName, String key, String param1, String param2, String param3) {
		return getString(baseName, key, new String[] { param1, param2, param3 });
	}

	protected static String getString(String baseName, String key, String[] params) {
		String value;
		if (params == null || params.length == 0) {
			value = ResourceManager.getInstance(baseName).getResource(key);
			if (value == null) {
				value = "errKey:" + key;
			}
		} else {
			value = ResourceManager.getInstance(baseName).getResource(key, (Object[]) params);
			if (value == null) {
				value = "errKey:" + key + ";param:";
				for (int i = 0; i < params.length; i++) {
					if (i == 0) {
						value += params[i];
					} else {
						value += ("," + params[i]);
					}
				}
			}
		}
		return value;
	}
}
