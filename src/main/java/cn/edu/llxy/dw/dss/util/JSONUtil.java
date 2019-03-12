package cn.edu.llxy.dw.dss.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class JSONUtil {
	/**
	 * 将json中的值反射到实例中
	 * @param clz
	 * @param instance
	 * @param json
	 * @return
	 */
	public static Object getInstanceFromJson(Class clz, Object instance, JSONObject json) {
		if(instance == null)
			return null;
		else{
			try {
				Object obj = clz.newInstance();
				
				PropertyUtils.copyPropertiesBySpring(instance, obj);
				
							
				//处理json对象
				//JSONObject jasonObject = JSONObject.fromObject(cm.getEntity());
				Map<String, Object> map2 = (Map) json;
				
				Field[] myDeclaredFields = clz.getFields();
				Object value ;String fieldName;
				
				Map.Entry<String, Object> entry;
				String firstLetter = null;String setMethodName;Method method;
				for(Iterator<Map.Entry<String, Object>> ite = map2.entrySet().iterator(); ite.hasNext(); ){
					entry = ite.next();
					value = entry.getValue();
					fieldName = entry.getKey();
					if(!fieldName.equals("DS_TYPE_MYSQL")){
						firstLetter = fieldName.substring(0, 1).toUpperCase();
						setMethodName = "set" + firstLetter
			                   + fieldName.substring(1);
							
						method = clz.getMethod(setMethodName, new Class[] {value.getClass() });
						fillValue(obj,value.getClass().getName(),method,value+"");
					}
				}
				
				return obj;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/** 
     * 将字符串值转换为合适的值填充到对象的指定域 
     * @param bean 被填充的java bean 
     * @param field 需要填充的域 
     * @param value 字符串值 
     */ 
	public static void fillValue(Object bean, String type, Method method,
			String value) {
		if (value == null || "null".equalsIgnoreCase(value))
			return;

		try {
			Object[] oo = new Object[1];

			//String type = field.getType().getName();

			if ("java.lang.String".equals(type)) {
				oo[0] = value;
			} else if ("java.lang.Integer".equals(type) || "int".equals(type)) {
				if (value.length() > 0)
					oo[0] = Integer.valueOf(value);
			} else if ("java.lang.Float".equals(type) || "float".equals(type)) {
				if (value.length() > 0)
					oo[0] = Float.valueOf(value);
			} else if ("java.lang.Double".equals(type) || "double".equals(type)) {
				if (value.length() > 0)
					oo[0] = Double.valueOf(value);
			} else if ("java.math.BigDecimal".equals(type)) {
				if (value.length() > 0)
					oo[0] = new BigDecimal(value);
			} else if ("java.util.Date".equals(type)) {
				if (value.length() > 0)
					oo[0] = DateUtil.parseDate(value, "yyyy-mm-dd hh:mi:ss")
					;
			} else if ("java.sql.Timestamp".equals(type)) {
				if (value.length() > 0)
					oo[0] = DateUtil.parseDate(value, "yyyy-mm-dd hh:mi:ss");
			} else if ("java.lang.Boolean".equals(type) || "boolean".equals(type)) {
				if (value.length() > 0)
					oo[0] = Boolean.valueOf(value);
			} else if ("java.lang.Long".equals(type)) {
				if (value.length() > 0)
					oo[0] = Long.valueOf(value);
			}

			method.invoke(bean, oo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getObjFromJsonArrStr(String source, Class<?> beanClass) {
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(source);
		return JSONObject.toBean(jsonObject, beanClass);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getListFromJsonArrStr(String jsonArrStr,Class<?> clazz) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List list = new ArrayList();
		for (int i = 0; i < jsonArr.size(); i++) {
			list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));
		}
		return list;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getListFromJsonArrStr(JSONArray jsonArr,Class<?> clazz) {
		List list = new ArrayList();
		for (int i = 0; i < jsonArr.size(); i++) {
			list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));
		}
		return list;
	}
}
