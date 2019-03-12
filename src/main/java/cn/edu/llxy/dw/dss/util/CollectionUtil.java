package cn.edu.llxy.dw.dss.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectionUtil {
	
	public static List<String> convertArrayToList(String[] array){
		List<String> result = new ArrayList<String>();
		if (null != array) {
			for (int i = 0; i < array.length; i++) {
				result.add(array[i]);
			}
			return result;
		}else {
			return null;
		}
	}
	
	public static boolean containElement(List<String> list,List<String> list2){
		for (String temp:list) {
			for (String temp2:list2) {
				if (temp.equals(temp2)) {
					return true;
				}
			}
		}
		return false;
	};
	
	public static List<String> convertStringToList(String values){
		List<String> result = new ArrayList<String>();
		if (null==values||"".equals(values)) return null;
		if (values.indexOf(",")==-1) {
			result.add(values);
		}else if(values.indexOf(",")>=0){			
			result.addAll(CollectionUtil.convertArrayToList(values.split(",")));
		}
		return result;
	}

	public static HashMap<String,String> convertStringToMap(String values){
		HashMap<String,String> result = new HashMap<String, String>();
		if (null==values||"".equals(values)) return null;
		if (values.indexOf(",")==-1&&values.indexOf(":")!=-1) {
			result.put(CollectionUtil.convertArrayToList(values.split(":")).get(0),CollectionUtil.convertArrayToList(values.split(":")).get(1));
		}else if(values.indexOf(",")>=0&&values.indexOf(":")!=-1){
			for(String temp:CollectionUtil.convertArrayToList(values.split(","))){
				
				result.put(CollectionUtil.convertArrayToList(temp.split(":")).get(0),CollectionUtil.convertArrayToList(temp.split(":")).get(1));
			}
		}else{
			return null;
		} 
		System.out.println("CollectionUtil.converStringToMap classed...");
		return result;
	}
	
	public static List<String> convertStringToList(String values,String split){
	    	split = (null==split||"".equals(split))?",":split;
		List<String> result = new ArrayList<String>();
		if (null==values||"".equals(values)) return null;
		if (values.indexOf(split)==-1) {
			result.add(values);
		}else if(values.indexOf(split)>=0){			
			result.addAll(CollectionUtil.convertArrayToList(values.split(split)));
		}
		return result;
	}
	
	public static void main(String[] args) {
		String tempString  =  "1:男,2:女";
		HashMap<String,String> temp = CollectionUtil.convertStringToMap(tempString);
		System.out.println(temp.size());
		
	}
}
