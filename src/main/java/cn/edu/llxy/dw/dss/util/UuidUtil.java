package cn.edu.llxy.dw.dss.util;

import java.util.UUID;

public class UuidUtil {
	
	public static String uuid(){
		String uuid = UUID.randomUUID().toString(); 
		return uuid.replace("-", "");
	}
	
	public static void main(String[] args) {
		 String uuid = UUID.randomUUID().toString();  
	        System.out.println(uuid.length());  
	        System.out.println(uuid);  
	        System.out.println(uuid.replace("-", ""));  
	}

}
