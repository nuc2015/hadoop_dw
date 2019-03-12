package cn.edu.llxy.dw.dss.message;

/**
 * Message.java get i18n msg form config file
 * 
 */
public class LogMessage extends Message {
	public static final String LOG_MESSAGE = "log_message";

	public static String getString(String key) {
		return Message.getString(LOG_MESSAGE, key);
	}

	public static String getString(String key, String param1) {
		return Message.getString(LOG_MESSAGE, key, param1);
	}

	public static String getString(String key, String param1, String param2) {
		return Message.getString(LOG_MESSAGE, key, param1, param2);
	}

	public static String getString(String key, String param1, String param2, String param3) {
		return getString(LOG_MESSAGE, key, param1, param2, param3);
	}
	
	public static String getString(String key, String[] params) {
		return getString(LOG_MESSAGE, key, params);
	}
}
