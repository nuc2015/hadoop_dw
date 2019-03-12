package cn.edu.llxy.dw.dss.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.llxy.dw.dss.cfg.Const;
import cn.edu.llxy.dw.dss.exception.CheckedException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

public class StringUtil {
	public static final int TRIM_TYPE_NONE = 0;
	public static final int TRIM_TYPE_LEFT = 1;
	public static final int TRIM_TYPE_RIGHT = 2;
	public static final int TRIM_TYPE_BOTH = 3;
	
	/**
	 * 验证String类型的变量是否为空；如果为空true，否则false；
	 * @param 需要验证的字符串
	 * @return boolean
	 */
	public static boolean stringIsNull(String param){
		return ("".equals(param)||null==param)?true:false;
	}

	/**
	 * 判断一个String 是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static final boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	public static final String replace(String string, String repl, String with) {
		StringBuffer str = new StringBuffer(string);
		for (int i = str.length() - 1; i >= 0; i--) {
			if (str.substring(i).startsWith(repl)) {
				str.delete(i, i + repl.length());
				str.insert(i, with);
			}
		}
		return str.toString();
	}

	public static String decodeLine(String line, String encode) throws CheckedException {
		try {
			return new String(line.getBytes(Const.ISO88591), encode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CheckedException(e.getMessage());
		}
	}

	/**
	 * NVL
	 * 
	 * @param source
	 * @param def
	 * @return
	 */
	public static final String NVL(String source, String def) {
		if (source == null || source.length() == 0)
			return def;
		return source;
	}

	/**
	 * 对字符按照size长度补齐，定义补齐字符
	 * @param str
	 * @param size
	 * @param padChar
	 * @return
	 */
	public static final String leftPad(String str,int size,String padChar){
		return StringUtils.leftPad(str, size, padChar);
	}
	
	/**
	 * 格式化 Y，N 到 Boolean 值
	 * 
	 * @param str
	 * @return
	 */
	public static boolean toBoolean(String str) {
		return str.trim().equals("Y") ? true : false;
	}

	/**
	 * Checks whether the String is a boolean expression.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBoolean(String str) {
		if (str == null) {
			return false;
		}
		String value = str.trim().toUpperCase();
		for (String s : Const.booleanExpression) {
			if (s.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Converts the String to a char using the first character, throwing an
	 * exception on empty Strings.
	 * </p>
	 * 
	 * <pre>
	 *   CharUtils.toChar(null) = IllegalArgumentException
	 *   CharUtils.toChar("")   = IllegalArgumentException
	 *   CharUtils.toChar("A")  = 'A'
	 *   CharUtils.toChar("BA") = 'B'
	 * </pre>
	 * 
	 * @param str
	 *            the character to convert
	 * @return the char value of the first letter of the String
	 * @throws IllegalArgumentException
	 *             if the String is empty
	 */
	public static char toChar(String str) throws IllegalArgumentException {
		return CharUtils.toChar(str);
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode digits. A decimal point is not
	 * a unicode digit and returns false.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = true
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains digits, and is non-null
	 */
	public static boolean isNumeric(String str) {
		return StringUtils.isNumeric(str);
	}

	public static char ByteSplitChar(String hexstr) {
		String hexstrs = hexstr.length() == 1 ? "0" + hexstr : hexstr;
		String str = "0X" + hexstrs;
		return (char) Integer.decode(str).intValue();
	}

	/**
	 * <p>
	 * Produces a new <code>byte</code> array containing the elements between
	 * the start and end indices.
	 * </p>
	 * 
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input
	 * produces null output.
	 * </p>
	 * 
	 * @param array
	 *            the array
	 * @param startIndexInclusive
	 *            the starting index. Undervalue (&lt;0) is promoted to 0,
	 *            overvalue (&gt;array.length) results in an empty array.
	 * @param endIndexExclusive
	 *            elements up to endIndex-1 are present in the returned
	 *            subarray. Undervalue (&lt; startIndex) produces empty array,
	 *            overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end
	 *         indices.
	 */
	public static String subArray(byte[] bytes, int startIndexInclusive, int endIndexExclusive) {
		return new String(ArrayUtils.subarray(bytes, startIndexInclusive, endIndexExclusive));
	}

	/**
	 * splitString
	 * 
	 * @param string
	 * @param separator
	 * @return
	 ** 
	 *         0123456 Example a;b;c;d --> new String[] { a, b, c, d }
	 */
	public static final String[] splitString(String string, char separator) {

		List<String> list = new ArrayList<String>();

		if (string == null || string.length() == 0) {
			return new String[] {};
		}

		int from = 0;
		int end = string.length();

		for (int i = from; i < end; i += 1) {
			if (string.charAt(i) == separator) {
				// OK, we found a separator, the string to add to the list
				list.add(NVL(string.substring(from, i), ""));
				from = i + 1;
			}
		}

		// Wait, if the string didn't end with a separator, we still have
		// information at the end of the string...
		if (from + 1 <= string.length()) {
			list.add(NVL(string.substring(from, string.length()), ""));
		}

		if (string.charAt(end - 1) == separator) {
			list.add(NVL("", ""));
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * SplitLineAndDecode
	 * 
	 * @param line
	 * @param separatorChar
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String[] SplitLineAndDecode(String line, char separatorChar, String encode)
			throws UnsupportedEncodingException {
		// String [] strs=StringUtils.split(line, separatorChar);
		String[] strs = splitString(line, separatorChar);
		String[] results = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			results[i] = new String(strs[i].getBytes(Const.ISO88591), encode);
		}
		return results;
	}

	public static byte[] DecodeLineISO88591(String linestr) throws CheckedException {
		try {
			return linestr.getBytes(Const.ISO88591);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new CheckedException(e.getMessage());
		}
	}

	/**
	 * 以默认获得byge转换到utf-8的编码
	 * @param str
	 * @return
	 */
	public static String toUTF8(String str){
		try {
			return new String(str.getBytes(),Const.UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 以ISO88591转换到UTF-8的编码
	 * @param str
	 * @return
	 */
	public static String fromISO8859toUTF8(String str){
		try {
			return new String(str.getBytes(Const.ISO88591),Const.UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 转换输入string 到指定的编码格式
	 * @param str 输入string
	 * @param code 指定转个编码
	 * @return
	 */
	public static String toStringForCode(String str,String code){
		try {
			return new String(str.getBytes(),code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 在字符串src后填充c,如果src长度大于length，则从后截断
	 * 
	 * @param src
	 *            原字符串
	 * @param length
	 *            需要填充到的长度
	 * @param c
	 *            填充的字符
	 * @return 长度为length的字符串，由src和填充的c组成
	 */
	public static String fillString(String src, int length, char c) {
		StringBuilder sb = new StringBuilder(src);
		if (sb.length() > length) {// 如果原字符串大于指定长度，则从后截断
			return sb.substring(sb.length() - length);
		} else {
			while (sb.length() < length) {
				sb.append(c);
			}
			return sb.toString();
		}
	}

	public static String RemoveRChar(String str){
		return StringUtils.remove(str, '\r');
	}
	/**
	 * @param src
	 * @param trimType
	 * @return
	 */
	public static String getTrimString(String pol, Integer trimType) {
		switch (trimType) {
		case TRIM_TYPE_LEFT: {
			StringBuffer strpol = new StringBuffer(pol);
			while (strpol.length() > 0 && strpol.charAt(0) == ' ')
				strpol.deleteCharAt(0);
			pol = strpol.toString();
		}
			break;
		case TRIM_TYPE_RIGHT: {
			StringBuffer strpol = new StringBuffer(pol);
			while (strpol.length() > 0 && strpol.charAt(strpol.length() - 1) == ' ')
				strpol.deleteCharAt(strpol.length() - 1);
			pol = strpol.toString();
		}
			break;
		case TRIM_TYPE_BOTH:
			StringBuffer strpol = new StringBuffer(pol);
			while (strpol.length() > 0 && strpol.charAt(0) == ' ')
				strpol.deleteCharAt(0);
			while (strpol.length() > 0 && strpol.charAt(strpol.length() - 1) == ' ')
				strpol.deleteCharAt(strpol.length() - 1);
			pol = strpol.toString();
			break;
		default:
			break;
		}
		return pol;
	}

	/**
	 * 替换path 中的 \\ 到 /
	 * @param path
	 * @return
	 */
	public static String replaceChars(String path){
		return StringUtils.replaceChars(path, '\\', '/');
	}
	
	public static boolean isEmptyEncoding(String encodingStr) {
		if (encodingStr == null) {
			return true;
		}
		
		if (StringUtils.isEmpty(encodingStr.trim())) {
			return true;
		}
		
		if (encodingStr.equalsIgnoreCase("DEFAULT")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 在 String 中，"''" 表示单引号。QuotedString 可以包含除单引号之外的任意字符；
	 * 围绕的单引号被移除。UnquotedString 可以包含除单引号和左花括号之外的任意字符。
	 * 因此，格式化后消息字符串为 "'{0}'" 的字符串可以写作 "'''{'0}''" 或 "'''{0}'''"。 
	 * @param resStr
	 * @param params
	 * @return
	 */
	public static String FormatMessage(String resStr,Object...params){
		return java.text.MessageFormat.format(resStr, params);
	}
	
	private static final String OPEN = "%";
	private static final String CLOSE = "%";


	public static String substitute(String aString, Map<String, String> variablesValues) {
		return StringUtil.substitute(aString, variablesValues, OPEN, CLOSE, 0);
	}

	public static String substitute(String aString, Map<String, String> variablesValues, String open, String close, int recursion) {
		if (aString == null)
			return null;

		StringBuffer buffer = new StringBuffer();

		String rest = aString;

		// search for opening string
		int i = rest.indexOf(open);
		while (i > -1) {
			int j = rest.indexOf(close, i + open.length());
			// search for closing string
			if (j > -1) {
				String varName = rest.substring(i + open.length(), j);
				Object value = variablesValues.get(varName);
				if (value == null) {
					value = open + varName + close;
				} else {
					// check for another variable inside this value
					int another = ((String) value).indexOf(open); // check
					// here
					// first for
					// speed
					if (another > -1) {
						if (recursion > 50) // for safety: avoid recursive
						// endless loops with stack overflow
						{
							throw new RuntimeException("Endless loop detected for substitution of variable: " + (String) value);
						}
						value = substitute((String) value, variablesValues, open, close, ++recursion);
					}
				}
				buffer.append(rest.substring(0, i));
				buffer.append(value);
				rest = rest.substring(j + close.length());
			} else {
				// no closing tag found; end the search
				buffer.append(rest);
				rest = "";
			}
			// keep searching
			i = rest.indexOf(open);
		}
		buffer.append(rest);
		return buffer.toString();
	}
	
	/**
	 * 判断当前对象是否是null
	 * @param o 待判断对象
	 * @return 对象为null返回true，否则返回false
	 */
	public static boolean isNull(Object o) {
		return o == null ? true : isNull(o.toString());
	}

   /*** 
    * java正则表达式: 
		(?i)abc 表示abc都忽略大小写 
		a(?i)bc 表示bc忽略大小写 
		a((?i)b)c 表示只有b忽略大小写
    * replaceStrIgnoreCase,直接使用字符串忽略大小写替换
    * @param input 
    * @param regex    
    * @param replacement 
    * @return String
    */
    public static String replaceStrIgnoreCase(String input,String regex,String replacement){  
        return input.replaceAll("(?i)" + regex, replacement);
    }
	    
	 /*** 
	 * 使用Pattern.compile(rexp,Pattern.CASE_INSENSITIVE)表示整体都忽略大小写
     * replacePatternIgnoreCase,忽略大小写替换
     * @param input 
     * @param regex    
     * @param replacement 
     * @return String
     */
    public static StringBuffer replacePatternIgnoreCase(String input,String regex,String replacement){  
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(input);  
        StringBuffer sb = new StringBuffer();  
        boolean result = m.find();  
        while (result)  
        {  
            m.appendReplacement(sb, replacement);  
            result = m.find();  
        }  
        m.appendTail(sb);  
        return sb;  
    }

    /*** 
	 * 使用Pattern.compile(rexp,Pattern.CASE_INSENSITIVE)表示整体都忽略大小写
     * replaceAllPatternIgnoreCase,忽略大小写 
     * @param input 
     * @param regex 
     * @param replacement 
     * @return String
     */
    public static String replaceAllPatternIgnoreCase(String input, String regex, String replacement) {  
         Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);  
         Matcher m = p.matcher(input);  
         String result = m.replaceAll(replacement);  
         return result;  
     }
    
    public static String getRuleStr(String sql){
		sql = sql.replaceAll("\n", " ");
		sql = sql.replaceAll(" {2,}", " ");
		sql = sql.replaceAll(" =", "=");
		sql = sql.replaceAll("= ", "=");
		return sql;
	}
    
    public static void main(String[] args) {
        String input = "I like Java,jAva is very easy and jaVa is so popular.";  
        String regex = "java";  
        String replacement="cccc";
        //原始数据
        System.out.println(input);  
        //方法1、替换字符
        System.out.println(replaceStrIgnoreCase(input, regex, replacement)); 
        //方法2、替换字符
        StringBuffer sb =replacePatternIgnoreCase(input, regex, replacement);  
        System.out.println(sb); 
        //方法3、替换字符
        System.out.println(replaceAllPatternIgnoreCase(input, regex, replacement)); 
	}

}
