package cn.edu.llxy.dw.dss.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 反射工具类
 * 
 */
public class ReflectUtil {
	private static Logger log = Logger.getLogger(ReflectUtil.class);
	/**
	 * 判断给类中有没有相应方法名的方法
	 * 
	 * @param clz
	 * @param methodName
	 * @return
	 */
	public static boolean containsMethod(Class<?> clz, String methodName) {
		Method[] methods = clz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断类中有没有相应属性名的属性
	 * 
	 * 
	 * @param clz
	 * @param fieldName
	 * @return
	 */
	public static boolean containsField(Class<?> clz, String fieldName) {
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 原始类型转换成包装类型
	 * 
	 * @param primitiveType
	 * @return
	 */
	public static Class<?> convertToWrapClass(Class<?> primitiveType) {
		Class<?> wrapClass = null;
		if (primitiveType == boolean.class) {
			wrapClass = Boolean.class;
		} else if (primitiveType == int.class) {
			wrapClass = Integer.class;
		} else if (primitiveType == short.class) {
			wrapClass = Short.class;
		} else if (primitiveType == long.class) {
			wrapClass = Long.class;
		} else if (primitiveType == byte.class) {
			wrapClass = Byte.class;
		} else if (primitiveType == float.class) {
			wrapClass = Float.class;
		} else if (primitiveType == double.class) {
			wrapClass = Double.class;
		} else if (primitiveType == char.class) {
			wrapClass = Character.class;
		}
		return wrapClass;
	}

	/**
	 * 包装类型转换为原始类型
	 * 
	 * @param wrapClass
	 * @return
	 * @throws Exception
	 */
	public static Class<?> convertToPrimitiveType(Class<?> wrapClass)
			throws Exception {
		return (Class<?>) wrapClass.getField("TYPE").get(null);
	}

	/**
	 * 判断是否是包装类
	 * 
	 * @param clz
	 * @return
	 */
	public static boolean isWrapClass(Class<?> clz) {
		try {
			return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isPrimitiveType(Class<?> clz) {
		return clz.isPrimitive();
	}

	/**
	 * 判断类的继承关系
	 * 
	 * @param subClass
	 * @param parentClass
	 * @return
	 */
	public static boolean isSubClass(Class<?> subClass, Class<?> parentClass) {
		if (Arrays.asList(subClass.getInterfaces()).contains(parentClass)) {
			return true;
		}
		return false;
	}
	

	/**
	 * 判断是不是同一类型，或是某类的子类
	 * 
	 * @param clz
	 * @param desClz
	 * @return
	 */
	public static boolean isClass(Class<?> clz, Class<?> desClz) {
		if (isSubClass(clz, desClz) || clz == desClz) {
			return true;
		}
		return false;
	}
	

	/**
	 * 判断是不是同一类型，或是某类的子类，原始类型和包装类型如果相对于那个，被认为是一种类型 如：boolean.class 和
	 * Boolean.class是同一类型
	 * 
	 * @param clz
	 * @param desClz
	 * @return
	 * @throws Exception
	 */
	public static boolean isClassIgnoredPrimitive(Class<?> clz, Class<?> desClz)
			throws Exception {
		if (clz.isPrimitive() && isWrapClass(desClz)) {
			if (clz == convertToPrimitiveType(desClz)) {
				return true;
			}
		} else if (isWrapClass(clz) && desClz.isPrimitive()) {
			if (convertToPrimitiveType(clz) == desClz) {
				return true;
			}
		} else {
			if (isSubClass(clz, desClz) || clz == desClz) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSameCollectionIgnoredArray(Class<?> clz,
			Class<?> desClz)throws Exception{
		if (Collection.class.isAssignableFrom(clz) && desClz.isArray()) {
			return true;
		} else if(Collection.class.isAssignableFrom(clz) && Collection.class.isAssignableFrom(desClz)){
			return true;
		}else if(clz.isArray() &&Collection.class.isAssignableFrom(desClz)){
			return true;
		}
		return false;
	}

	public static boolean isClassIgnoredPrimitveAndArray(Class<?> clz,
			Class<?> desClz) throws Exception {
		if (clz.isPrimitive() && isWrapClass(desClz)) {
			if (clz == convertToPrimitiveType(desClz)) {
				return true;
			}
		} else if (isWrapClass(clz) && desClz.isPrimitive()) {
			if (convertToPrimitiveType(clz) == desClz) {
				return true;
			}
		} else if(isSameCollectionIgnoredArray(clz,desClz)){
			return true;
		}else {
			if (isSubClass(clz, desClz) || clz == desClz) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
//		Foo f = new Foo();
//		Field[] fields = f.getClass().getDeclaredFields();
//		for (Field field : fields) {
//			if (Map.class.isAssignableFrom(field.getType()))
//				if (ParameterizedType.class.isAssignableFrom(field
//						.getGenericType().getClass())) {
//					ParameterizedType pt = (ParameterizedType) field
//							.getGenericType();
//					System.out.println(pt.getActualTypeArguments()[0]);
//				}
//		}
		List<String> s = new ArrayList<String>();
		s.add("h");
		s.add("l");
		List ss = Arrays.asList(s);
		System.out.println(ss.get(0).getClass());
		
		Integer[] is = new Integer[]{1,2};
		Object obj = is;
		System.out.println(obj.getClass().getComponentType());
		List fs = Arrays.asList(obj);
		System.out.println(Array.get(obj, 1));
		System.out.println(Array.getLength(obj));
	}

	static class Foo {
		private Map<String, Long> map = new HashMap<String, Long>();
		private List<Integer> list = new ArrayList<Integer>();
		private Set set = new HashSet();
		private int i;
		private Foo f;

		public Map<String, Long> getMap() {
			return map;
		}

		public void setMap(Map<String, Long> map) {
			this.map = map;
		}

		public List<Integer> getList() {
			return list;
		}

		public void setList(List<Integer> list) {
			this.list = list;
		}

		public Set getSet() {
			return set;
		}

		public void setSet(Set set) {
			this.set = set;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public Foo getF() {
			return f;
		}

		public void setF(Foo f) {
			this.f = f;
		}
	}
	
    private static Object operate(Object obj, String fieldName,
            Object fieldVal, String type)
    {
        Object ret = null;
        try
        {
            
            Class<? extends Object> classType = obj.getClass();
            
            Field fields[] = classType.getDeclaredFields();
            for (int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                if (field.getName().equals(fieldName))
                {
                   
                    String firstLetter = fieldName.substring(0, 1)
                            .toUpperCase(); 
                    if ("set".equals(type))
                    {
                        String setMethodName = "set" + firstLetter
                                + fieldName.substring(1); 
                        Method setMethod = classType.getMethod(setMethodName,
                                new Class[] {field.getType()}); 
                        ret = setMethod.invoke(obj, new Object[] {fieldVal});
                    }
                    if ("get".equals(type))
                    {
                        String getMethodName = "get" + firstLetter
                                + fieldName.substring(1); 
                        Method getMethod = classType.getMethod(getMethodName,
                                new Class[] {});
                        ret = getMethod.invoke(obj, new Object[] {});
                    }
                    return ret;
                }
            }
        }
        catch (Exception e)
        {
            log.warn("reflect error:" + fieldName, e);
        }
        return ret;
    }
   
    public static Object getVal(Object obj, String fieldName)
    {
        return operate(obj, fieldName, null, "get");
    }
   
    public static void setVal(Object obj, String fieldName, Object fieldVal)
    {
        operate(obj, fieldName, fieldVal, "set");
    }
   
   
    private static Method getDeclaredMethod(Object object, String methodName,
            Class<?>[] parameterTypes)
    {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
        {
            try
            {
                //superClass.getMethod(methodName, parameterTypes);
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            }
            catch (NoSuchMethodException e)
            {
                //Method  不在当前类定义, 继续向上转型 
            	//e.printStackTrace();
            }
        }
       
        return null;
    }
   
   
    private static void makeAccessible(Field field)
    {
        if (!Modifier.isPublic(field.getModifiers()))
        {
            field.setAccessible(true);
        }
    }
   
   
    private static Field getDeclaredField(Object object, String filedName)
    {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
        {
            try
            {
                return superClass.getDeclaredField(filedName);
            }
            catch (NoSuchFieldException e)
            {
            	//e.printStackTrace();
            	// Field 不在当前类定义, 继续向上转型 
            }
        }
        return null;
    }
   
   
    public static Object invokeMethod(Object object, String methodName,
            Class<?>[] parameterTypes, Object[] parameters)
            throws InvocationTargetException
    {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
       
        if (method == null)
        {
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] on target [" + object + "]");
        }
       
        method.setAccessible(true);
       
        try
        {
            return method.invoke(object, parameters);
        }
        catch (IllegalAccessException e)
        {
           
        }
       
        return null;
    }
   
   
    public static void setFieldValue(Object object, String fieldName,
            Object value)
    {
        Field field = getDeclaredField(object, fieldName);
       
        if (field == null)
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
       
        makeAccessible(field);
       
        try
        {
            field.set(object, value);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
   
   
    public static Object getFieldValue(Object object, String fieldName)
    {
        Field field = getDeclaredField(object, fieldName);
        if (field == null)
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
       
        makeAccessible(field);
       
        Object result = null;
        try
        {
            result = field.get(object);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
       
        return result;
    }
   
}
