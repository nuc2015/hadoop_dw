package cn.edu.llxy.dw.dss.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Beans转化的工具类，属性拷贝 一般用于po与vo之间的转化
 * 
 */
public class ConvertUtil {

	/**
	 * 默认过滤标识
	 */
	public static final Object[] DEFAULT_FILTER = { Collection.class };

	/**
	 * 集合过滤标识，所有Collection的子类属性都将被过滤掉
	 */
	public static final Class<?> COLLECTION_FILTER = Collection.class;

	/**
	 * Set集合过滤标识，所有Set的子类属性都将被过滤掉
	 */
	public static final Class<?> SET_FILTER = Set.class;

	/**
	 * Set集合过滤标识，所有List的子类属性都将被过滤掉
	 */
	public static final Class<?> LIST_FILTER = List.class;

	/**
	 * Set集合过滤标识，所有Map的子类属性都将被过滤掉
	 */
	public static final Class<?> MAP_FILTER = Map.class;

	/**
	 * 属性名过滤标识，凡是属性名为session的属性都将被过滤
	 */
	public static final String SESSION_FILTER = "session";

	/**
	 * bean属性转换，常用于po与vo之间的转换 拷贝的条件是属性名相同即可拷贝 属性类型相同（原始类型和相对应的包装类型为同一类型），直接拷贝属性值
	 * 属性类型不同，进行递归拷贝
	 * 
	 * @param src
	 *            拷贝的源对象
	 * @param des
	 *            拷贝的目标对象
	 * @param filters
	 *            属性过滤条件 可以为以下两种情况 1、属性名，String类型 2、属性类型，Class<?>类型
	 * @throws Exception
	 */
	public static void convert(Object src, Object des, Object... filters)
			throws Exception {
		if (src == null) {
			des = src;
			return;
		}
		Class<?> srcClass = src.getClass();
		Class<?> desClass = des.getClass();
		if ((ReflectUtil.isPrimitiveType(srcClass) || ReflectUtil
				.isWrapClass(srcClass))
				&& ReflectUtil.isClassIgnoredPrimitive(srcClass, desClass)) {
			des = src;
			return;
		}
		Field[] fields = srcClass.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isFinal(field.getModifiers())
					&& !Modifier.isStatic(field.getModifiers())) {
				if (filterFiled(field, filters)) {
					continue;
				}
				String fieldName = field.getName();
				if (ReflectUtil.containsField(desClass, fieldName)) {
					Field desField = desClass.getDeclaredField(fieldName);
					if (desField != null) {
						String isMethodName = "is"
								+ StringUtils.capitalize(fieldName);
						String getMethodName = "get"
								+ StringUtils.capitalize(fieldName);
						String setMethodName = "set"
								+ StringUtils.capitalize(fieldName);
						Class<?> srcType = field.getType();
						Class<?> desType = desField.getType();
						Object value = null;
						Method getMethod = null;
						if (ReflectUtil.containsMethod(srcClass, isMethodName)) {
							getMethod = srcClass.getDeclaredMethod(
									isMethodName, new Class<?>[] {});
						} else if (ReflectUtil.containsMethod(srcClass,
								getMethodName)) {
							getMethod = srcClass.getDeclaredMethod(
									getMethodName, new Class<?>[] {});
						}

						if (getMethod != null) {
							value = getMethod.invoke(src, new Object[] {});
						} else {
							field.setAccessible(true);
							value = field.get(src);
							field.setAccessible(false);
						}

						if (value == null) {
							continue;
						}
						
						Object desFieldValue = null;
						if(ReflectUtil.isSameCollectionIgnoredArray(srcClass, desClass)){
							continue;
						}
						if (ReflectUtil.isClassIgnoredPrimitive(srcType,
								desType)) {
							desFieldValue = value;
						} else {
							desFieldValue = desType.newInstance();
							convert(value, desFieldValue, filters);
						}
						
						Method setMethod = null;
						if (ReflectUtil.containsMethod(desClass, setMethodName)) {
							setMethod = desClass.getDeclaredMethod(
									setMethodName, new Class<?>[] { desType });
						}

						if (setMethod != null) {
							setMethod.invoke(des, desFieldValue);
							continue;
						} else {
							desField.setAccessible(true);
							desField.set(des, desFieldValue);
							desField.setAccessible(false);
							continue;
						}
					}
				}
			}
		}
	}

	/**
	 * 根据过滤条件筛选字段
	 * 
	 * @param field
	 * 
	 * @param filters
	 *            可以为以下两种情况 1、属性名，String类型 2、属性类型，Class<?>类型
	 * @return
	 * @throws Exception
	 */
	public static boolean filterFiled(Field field, Object... filters)
			throws Exception {
		if (filters == null)
			return false;
		for (Object filter : filters) {
			if (filter instanceof String) {
				if (field.getName().equals(filter)) {
					return true;
				}
			} else if (filter instanceof Class<?>) {
				if (ReflectUtil.isClassIgnoredPrimitive(field.getClass(),
						(Class<?>) filter)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 不加过滤条件的属性转换
	 * 
	 * @param src
	 * @param des
	 * @throws Exception
	 */
	public static void convert(Object src, Object des) throws Exception {
		convert(src, des, new Object[] {});
	}

}
