package cn.edu.llxy.dw.dss.util;

import java.io.IOException;

public class Base64Code {

	/** 
	  * 编码 
	  * @param bstr 
	  * @return String 
	  */ 
	  public static String encode(byte[] bstr){  
	  return new sun.misc.BASE64Encoder().encode(bstr);  
	  }  
	
	/**
	 * 解码
	 * 
	 * @param str
	 * @return string
	 */
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bt;
	}
	
	/**
	 * 解码
	 * 
	 * @param str
	 * @return string
	 */
	public static String decodeString(String str) {
		String ressult = "";
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
			ressult = new String(bt,"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ressult;
	}
}
