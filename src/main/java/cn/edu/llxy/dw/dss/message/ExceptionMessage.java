package cn.edu.llxy.dw.dss.message;

public class ExceptionMessage extends Message {

	private static final String EXCEPTION_MESSAGE = "exception";

	public static String getString(String key) {
		return Message.getString(EXCEPTION_MESSAGE, key);
	}

	public static String getString(String key, String param1) {
		return Message.getString(EXCEPTION_MESSAGE, key, param1);
	}

	public static String getString(String key, String param1, String param2) {
		return Message.getString(EXCEPTION_MESSAGE, key, param1, param2);
	}

	public static String getString(String key, String param1, String param2, String param3) {
		return getString(EXCEPTION_MESSAGE, key, param1, param2, param3);
	}

	public static String getString(String key, String[] params) {
		return getString(EXCEPTION_MESSAGE, key, params);
	}
}
