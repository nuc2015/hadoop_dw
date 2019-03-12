package cn.edu.llxy.dw.dss.exception;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import cn.edu.llxy.dw.dss.cfg.Const;
import cn.edu.llxy.dw.dss.util.CfgUtils;

public class ResourceManager {

	private static ResourceManager instance;
	private static HashMap<String, HashMap<String, String>> resources = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, String> resource = new HashMap<String, String>();
	private static Locale locale = new Locale("zh", "CN");

	
	public ResourceManager(){
		
	}
	private ResourceManager(String baseName) {
		init(baseName);
	}
	public void init(){
		try {
			CfgUtils ut =new CfgUtils();
			String localStr = ut.getProperty(Const.LOCALE_KEY);
			if (Const.EN_US.equals(localStr)) {
				locale = new Locale(Const.EN_US, "US");
			} else {
				locale = new Locale(Const.ZH_CN, "CN");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void init(String baseName) {

		ResourceBundle exceptions = ResourceBundle.getBundle(baseName, locale);
		resource = new HashMap<String, String>();
		Set<String> keys = exceptions.keySet();
		for (String key : keys) {
			resource.put(key, exceptions.getString(key));
		}
		resources.put(baseName, resource);
	}

	public static Locale getLocale() {
		return locale;
	}

	public static void setLocale(Locale locale) {
		ResourceManager.locale = locale;
	}

	public synchronized static ResourceManager getInstance(String baseName) {
		resource = resources.get(baseName);
		if (resource == null) {
			instance = new ResourceManager(baseName);
		}
		return instance;
	}
	
	public void setResource(ResourceBundle resourcebundle){
		Set<String> keys = resourcebundle.keySet();
		for (String key : keys) {
			resource.put(key, resourcebundle.getString(key));
		}
	}

	/**
	 * getResource
	 * 
	 * @param key
	 * @return
	 */
	public String getResource(String key) {
		return resource.get(key);
	}

	/**
	 * getResource
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public String getResource(String key, Object... params) {
		String resStr = getResource(key);
		if (resStr == null) {
			return null;
		} else {
			return java.text.MessageFormat.format(resStr, params);
		}
	}
}
