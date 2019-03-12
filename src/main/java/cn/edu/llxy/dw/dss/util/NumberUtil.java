package cn.edu.llxy.dw.dss.util;

import java.text.DecimalFormat;

import cn.edu.llxy.dw.dss.exception.CheckedException;
import org.apache.commons.lang.math.NumberUtils;

public class NumberUtil {
	
	
	/**
	 * 传入16进制或者8进制的字符串，生成int类型
	 * @param str
	 * @return
	 */
	public static int createInt(String str){
		return NumberUtils.createInteger(str);
	}
	/**
	 * 格式化 string，返回 int 值
	 * 
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		return NumberUtils.toInt(str);
	}

	/**
	 * 格式化string，返回long值
	 * 
	 * @param str
	 * @return
	 */
	public static long toLong(String str) {
		return NumberUtils.toLong(str);
	}

	/**
	 * <p>
	 * Checks whether the String a valid Java number.
	 * </p>
	 * 
	 * <p>
	 * Valid numbers include hexadecimal marked with the <code>0x</code> qualifier,
	 * scientific notation and numbers marked with a type qualifier (e.g. 123L).
	 * </p>
	 * 
	 * <p>
	 * <code>Null</code> and empty String will return <code>false</code>.
	 * </p>
	 * 
	 * @param str
	 *            the <code>String</code> to check
	 * @return <code>true</code> if the string is a correctly formatted number
	 */
	public static boolean isNumber(String str) {
		return NumberUtils.isNumber(str);
	}
	
	/**
	 * <p>
	 * Checks whether the <code>String</code> contains only digit characters.
	 * </p>
	 * 
	 * <p>
	 * <code>Null</code> and empty String will return <code>false</code>.
	 * </p>
	 * 
	 * @param str
	 *            the <code>String</code> to check
	 * @return <code>true</code> if str contains only unicode numeric
	 */
	public static boolean isDigits(String str) {
		return NumberUtils.isDigits(str);
	}

	/**
	 * Checks whether the <code>String</code> is a Integer.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		if (!isNumber(str)) {
			return false;
		}
		if (str.length() > Math.max(new Integer(Integer.MAX_VALUE).toString().length(), new Integer(Integer.MIN_VALUE).toString().length())) {
			return false;
		}
		Long value = Long.parseLong(str);
		if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
			return true;
		}
		return false;
	}

	public static String format(String pattern, String str) throws CheckedException {
		if (isNumber(str)) {
			return format(pattern, Double.parseDouble(str));
		} else {
			throw new CheckedException("The input number is invalid.");
		}
	}

	public static String format(String pattern, double x) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(x);
	}
	
//	//test main
//	public static void main(String[] args) {
//		System.out.println(((byte)toInt("09")));
//	}
}
