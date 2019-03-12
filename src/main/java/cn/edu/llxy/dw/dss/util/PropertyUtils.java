package cn.edu.llxy.dw.dss.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.CachedIntrospectionResults;

/**
 * 属性拷贝工具类
 */
public class PropertyUtils {
	/**
	 * 转换为vo数组
	 * @param poes
	 * @return
	 */
	public static <E> ArrayList<E> copyToVoList(List poes,Class clz){
		if(poes == null || poes.size()<= 0)
			return null;
		else{
			List converObjects = new ArrayList();
			for(Object o:poes){
				try {
					Object obj = clz.newInstance();
					copyPropertiesBySpring(o, obj);
					converObjects.add(obj);
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
			return (ArrayList<E>)converObjects;
		}
	}
	
	/**
	 * 将对象之间拷贝成VO对象（必须保证VO与PO包及类名命名规则）
	 * @param source
	 * @throws Exception
	 */
	public static Object copyPropertiesToVo(Object source) throws Exception{
		String targetItemClassName = source.getClass().getName();
		targetItemClassName = targetItemClassName.replaceFirst("\\.po\\.", "\\.vo\\.");
		targetItemClassName += "Vo";
		
		Object target = Class.forName(targetItemClassName).newInstance();
		
		copyPropertiesBySpring(source, target);
		return target;
	}
	
	private static PropertyDescriptor queryIdPropertyDescriptor(Object source, String idName){
		Class actualEditable = source.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		PropertyDescriptor targetPd;
		for (int i = 0; i < targetPds.length; i++) {
			targetPd = targetPds[i];
			if(targetPd.getName().equals(idName)){
				return targetPd;
			}
		}
		
		return null;
	}
	
	private static String getIdValue(Object source, String idName) throws Exception{
		PropertyDescriptor idPropertyDescriptor = queryIdPropertyDescriptor(source, idName);
		if(idPropertyDescriptor != null){
			 Method readMethod = idPropertyDescriptor.getReadMethod();
			 if (!Modifier.isPublic(readMethod.getDeclaringClass()  
                     .getModifiers())) {  
                 readMethod.setAccessible(true);  
             }  
             Object value = readMethod.invoke(source, new Object[0]);
             if(value != null)
            	 return value.toString();
             else
            	 return null;
		}else{
			return null;
		}
	}
	
	private static Object getTargetById(List vos, String id, String idName) throws Exception{
		String idValue;
		for(Object o:vos){
			idValue = getIdValue(o, idName);
			
			if(idValue.equals(id)){
				return o;
			}
		}
		return null;
	}
	
	private static void copyDoubleCascade(List vos, List<String> ids, Object source, Object target, String idName, String className) throws Exception{
		vos.add(target);
		
		PropertyDescriptor idPropertyDescriptor = queryIdPropertyDescriptor(source, idName);
		PropertyDescriptor targetIdPropertyDescriptor = queryIdPropertyDescriptor(target, idName);
		if(idPropertyDescriptor != null){
			 Method readMethod = idPropertyDescriptor.getReadMethod();
			 if (!Modifier.isPublic(readMethod.getDeclaringClass()  
                     .getModifiers())) {  
                 readMethod.setAccessible(true);  
             }  
             Object value = readMethod.invoke(source, new Object[0]);
             if(value != null){
            	 Object instance = targetIdPropertyDescriptor.getPropertyType().newInstance();
             	
         		 Method writeMethod = targetIdPropertyDescriptor.getWriteMethod();  
                 if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                         .getModifiers())) {
                     writeMethod.setAccessible(true);  
                 }
                 writeMethod.invoke(target, new Object[] {value});
            	 
            	 if(!ids.contains(value.toString())){
            		 ids.add(value.toString());
            	 }
             }else{
            	 return;
             }
		}
		
		Class actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);  String id;
        for (int i = 0; i < targetPds.length; i++) {  
            PropertyDescriptor targetPd = targetPds[i];
            if (targetPd.getWriteMethod() != null) {  
                PropertyDescriptor sourcePd = getPropertyDescriptor(source  
                        .getClass(), targetPd.getName());  
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();  
                        if (!Modifier.isPublic(readMethod.getDeclaringClass()  
                                .getModifiers())) {
                            readMethod.setAccessible(true);  
                        }  
                        Object value = readMethod.invoke(source, new Object[0]);
                        if(value == null)  
                            continue;
                        //集合类判空处理
                        
                        if(value.getClass().getName().equals(className)){
                        	id = getIdValue(value, idName);
                        	if(ids.contains(id)){
                        		Object instance = targetPd.getPropertyType().newInstance();
                            	
                        		Method writeMethod = targetPd.getWriteMethod();  
    	                        if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
    	                                .getModifiers())) {
    	                            writeMethod.setAccessible(true);  
    	                        }
    	                        writeMethod.invoke(target, new Object[] {getTargetById(vos, id, idName)});
                        		//System.out.println("copy ed............");
    	                        break;
                        	}
                        }
                        
                        if(value instanceof Collection && !(value instanceof java.util.Map)) {
                          Collection newValue = (Collection)value;
                          Collection dest = null;
                          
                          if(targetPd.getPropertyType().getName().equals("java.util.List")){
                        	  dest = new ArrayList();
                          }else if(targetPd.getPropertyType().getName().equals("java.util.Set")){
                        	  dest = new java.util.HashSet();
                          }
                          
                          if(dest == null) continue;
                          
                          Object sourceItem  = null;String targetItemClassName = "";
                          
	                      for(Object cobj:newValue){
	                    	  if(sourceItem == null){
	                    		  sourceItem = cobj;
	                    		  targetItemClassName = sourceItem.getClass().getName();
	                    		  targetItemClassName = targetItemClassName.replaceFirst("\\.po\\.", "\\.vo\\.");
	                    		  targetItemClassName += "Vo";
	                    	  }
	                    	  Object targetItemInstance = Class.forName(targetItemClassName).newInstance();
	                    	  
	                    	  if(cobj.getClass().getName().equals(className)){
	                    		  copyDoubleCascade(vos, ids, cobj, targetItemInstance, idName, className);
	                    		  dest.add(targetItemInstance);
	                    	  }else{
	                    		  String clsName = cobj.getClass().getName().substring(0, cobj.getClass().getName().indexOf("_"));
                      			if(clsName.equals(className)){
                      				copyDoubleCascade(vos, ids, cobj, targetItemInstance, idName, className);
                      			}
	                    	  }
	                      }
	                      
	                      Method writeMethod = targetPd.getWriteMethod();  
                          if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                                  .getModifiers())) {  
                              writeMethod.setAccessible(true);  
                          }  
                          writeMethod.invoke(target, new Object[] { dest });
//                          if(newValue.size() <= 0)  
//                                continue;  
                        }
                        if(!value.getClass().getName().equals(targetPd.getPropertyType().getName())){
                        	Object instance = targetPd.getPropertyType().newInstance();
                        	
                        	if(value.getClass().getName().equals(className)){
                        		//System.out.println("bb:::"+value.getClass().getName());
                        		copyDoubleCascade(vos, ids, value, instance, idName, className);
                        	}else{
                        		if(value.getClass().getName().indexOf("_") > 0){
                        			String clsName = value.getClass().getName().substring(0, value.getClass().getName().indexOf("_"));
                        			if(clsName.equals(className)){
                        				//copyDoubleCascade(vos, ids, value, instance, idName, className);
                        			}
                        		}else{
                        			//System.out.println("========"+value.getClass().getName());
                        			//if(!(value instanceof java.sql.Timestamp))
                        			copyPropertiesBySpring(value, instance);
                        		}
                        	}
                        	 Method writeMethod = targetPd.getWriteMethod();  
                             if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                                     .getModifiers())) {
                                 writeMethod.setAccessible(true);  
                             }  
                             writeMethod.invoke(target, new Object[] { instance });
                        }else{
	                        Method writeMethod = targetPd.getWriteMethod();  
	                        if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
	                                .getModifiers())) {  
	                            writeMethod.setAccessible(true);  
	                        }  
	                        writeMethod.invoke(target, new Object[] { value });
                        }
                    } catch (Throwable ex) {  
                    }  
                }  
            }  
        }
	}
	
	public static void copyPropertiesWithDoubleCascade(Object source, Object target, String idName) throws Exception {
		copyDoubleCascade(new ArrayList(), new ArrayList(), source, target, idName, source.getClass().getName());
	}
	
	
	
	private static void copyPropertiesToPo(List vos, List<String> ids, Object source, Object target, String idName, String className) throws Exception{
		vos.add(target);
		
		PropertyDescriptor idPropertyDescriptor = queryIdPropertyDescriptor(source, idName);
		PropertyDescriptor targetIdPropertyDescriptor = queryIdPropertyDescriptor(target, idName);
		if(idPropertyDescriptor != null){
			 Method readMethod = idPropertyDescriptor.getReadMethod();
			 if (!Modifier.isPublic(readMethod.getDeclaringClass()  
                     .getModifiers())) {  
                 readMethod.setAccessible(true);  
             }  
             Object value = readMethod.invoke(source, new Object[0]);
             if(value != null){
            	 Object instance = targetIdPropertyDescriptor.getPropertyType().newInstance();
             	
         		 Method writeMethod = targetIdPropertyDescriptor.getWriteMethod();  
                 if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                         .getModifiers())) {
                     writeMethod.setAccessible(true);  
                 }
                 writeMethod.invoke(target, new Object[] {value});
            	 
            	 if(!ids.contains(value.toString())){
            		 ids.add(value.toString());
            	 }
             }else{
            	 return;
             }
		}
		
		Class actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);  String id;
        for (int i = 0; i < targetPds.length; i++) {  
            PropertyDescriptor targetPd = targetPds[i];
            if (targetPd.getWriteMethod() != null) {  
                PropertyDescriptor sourcePd = getPropertyDescriptor(source  
                        .getClass(), targetPd.getName());  
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();  
                        if (!Modifier.isPublic(readMethod.getDeclaringClass()  
                                .getModifiers())) {
                            readMethod.setAccessible(true);  
                        }  
                        Object value = readMethod.invoke(source, new Object[0]);
                        if(value == null)  
                            continue;
                        //集合类判空处理
                        
                        if(value.getClass().getName().equals(className)){
                        	id = getIdValue(value, idName);
                        	if(ids.contains(id)){
                        		Object instance = targetPd.getPropertyType().newInstance();
                            	
                        		Method writeMethod = targetPd.getWriteMethod();  
    	                        if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
    	                                .getModifiers())) {
    	                            writeMethod.setAccessible(true);  
    	                        }
    	                        writeMethod.invoke(target, new Object[] {getTargetById(vos, id, idName)});
                        		//System.out.println("copy ed............");
    	                        break;
                        	}
                        }
                        
                        if(value instanceof Collection && !(value instanceof java.util.Map)) {
                          Collection newValue = (Collection)value;
                          Collection dest = null;
                          
                          if(targetPd.getPropertyType().getName().equals("java.util.List")){
                        	  dest = new ArrayList();
                          }else if(targetPd.getPropertyType().getName().equals("java.util.Set")){
                        	  dest = new java.util.HashSet();
                          }
                          
                          if(dest == null) continue;
                          
                          Object sourceItem  = null;String targetItemClassName = "";
                          
	                      for(Object cobj:newValue){
	                    	  if(sourceItem == null){
	                    		  sourceItem = cobj;
	                    		  targetItemClassName = sourceItem.getClass().getName();
	                    	  }
	                    	  Object targetItemInstance = Class.forName(targetItemClassName).newInstance();
	                    	  
	                    	  if(cobj.getClass().getName().equals(className)){
	                    		  copyDoubleCascade(vos, ids, cobj, targetItemInstance, idName, className);
	                    		  dest.add(targetItemInstance);
	                    	  }else{
	                    		  String clsName = cobj.getClass().getName().substring(0, cobj.getClass().getName().indexOf("_"));
                      			if(clsName.equals(className)){
                      				copyDoubleCascade(vos, ids, cobj, targetItemInstance, idName, className);
                      			}
	                    	  }
	                      }
	                      
	                      Method writeMethod = targetPd.getWriteMethod();  
                          if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                                  .getModifiers())) {  
                              writeMethod.setAccessible(true);  
                          }  
                          writeMethod.invoke(target, new Object[] { dest });
//                          if(newValue.size() <= 0)  
//                                continue;  
                        }
                        if(!value.getClass().getName().equals(targetPd.getPropertyType().getName())){
                        	Object instance = targetPd.getPropertyType().newInstance();
                        	
                        	if(value.getClass().getName().equals(className)){
                        		//System.out.println("bb:::"+value.getClass().getName());
                        		copyDoubleCascade(vos, ids, value, instance, idName, className);
                        	}else{
                        		if(value.getClass().getName().indexOf("_") > 0){
                        			String clsName = value.getClass().getName().substring(0, value.getClass().getName().indexOf("_"));
                        			if(clsName.equals(className)){
                        				//copyDoubleCascade(vos, ids, value, instance, idName, className);
                        			}
                        		}else{
                        			//System.out.println("========"+value.getClass().getName());
                        			//if(!(value instanceof java.sql.Timestamp))
                        			copyPropertiesBySpring(value, instance);
                        		}
                        	}
                        	 Method writeMethod = targetPd.getWriteMethod();  
                             if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                                     .getModifiers())) {
                                 writeMethod.setAccessible(true);  
                             }  
                             writeMethod.invoke(target, new Object[] { instance });
                        }else{
	                        Method writeMethod = targetPd.getWriteMethod();  
	                        if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
	                                .getModifiers())) {  
	                            writeMethod.setAccessible(true);  
	                        }  
	                        writeMethod.invoke(target, new Object[] { value });
                        }
                    } catch (Throwable ex) {  
                    }  
                }  
            }  
        }
	}
	public static void copyPropertiesWithPo(Object source, Object target, String idName) throws Exception {
		copyPropertiesToPo(new ArrayList(), new ArrayList(), source, target, idName, source.getClass().getName());
	}
	
	/**
	 * 属性拷贝
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void copyPropertiesBySpring(Object source, Object target) throws Exception { 
        Class actualEditable = target.getClass();  
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);  
        for (int i = 0; i < targetPds.length; i++) {  
            PropertyDescriptor targetPd = targetPds[i];  
            if (source != null && targetPd.getWriteMethod() != null) {  
                PropertyDescriptor sourcePd = getPropertyDescriptor(source  
                        .getClass(), targetPd.getName());  
                //System.out.println(sourcePd);
                if (sourcePd != null && sourcePd.getReadMethod() != null) {  
                    try {  
                        Method readMethod = sourcePd.getReadMethod();  
                        if (!Modifier.isPublic(readMethod.getDeclaringClass()  
                                .getModifiers())) {  
                            readMethod.setAccessible(true);  
                        }  
                        Object value = readMethod.invoke(source, new Object[0]);
                        if(value == null)
                            continue;  
                        //集合类判空处理  
                        if(value instanceof Collection && !(value instanceof java.util.Map)) {
                          Collection newValue = (Collection)value;
                          Collection dest = null;
                          
                          if(targetPd.getPropertyType().getName().equals("java.util.List")){
                        	  dest = new ArrayList();
                          }else if(targetPd.getPropertyType().getName().equals("java.util.Set")){
                        	  dest = new java.util.HashSet();
                          }
                          
                          if(dest == null) continue;
                          
                          Object sourceItem  = null;String targetItemClassName = "";
                          
	                      for(Object cobj:newValue){
	                    	  if(sourceItem == null){
	                    		  sourceItem = cobj;
	                    		  targetItemClassName = sourceItem.getClass().getName();
	                    		  targetItemClassName = targetItemClassName.replaceFirst("\\.po\\.", "\\.vo\\.");
	                    		  targetItemClassName += "Vo";
	                    	  }
	                    	  Object targetItemInstance = Class.forName(targetItemClassName).newInstance();
	                    	  copyPropertiesBySpring(cobj, targetItemInstance);
	                          dest.add(targetItemInstance);
	                      }
	                      
	                      Method writeMethod = targetPd.getWriteMethod();  
                          if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                                  .getModifiers())) {  
                              writeMethod.setAccessible(true);  
                          }  
                          writeMethod.invoke(target, new Object[] { dest });
//                          if(newValue.size() <= 0)  
//                                continue;  
                        }
                        if(!value.getClass().getName().equals(targetPd.getPropertyType().getName())){
                        	Object instance = targetPd.getPropertyType().newInstance();
                        	
                        	if(value instanceof java.sql.Timestamp){
                        		instance = new java.util.Date(((java.sql.Timestamp)value).getTime());
                        	}else if(value instanceof java.util.Date){
								instance = (java.util.Date) value;
							}else{
                        		copyPropertiesBySpring(value, instance);
                        	}
                        	
                        	 Method writeMethod = targetPd.getWriteMethod();  
                             if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                                     .getModifiers())) {  
                                 writeMethod.setAccessible(true);  
                             }  
                             writeMethod.invoke(target, new Object[] { instance });
                        }else{
	                        Method writeMethod = targetPd.getWriteMethod();  
	                        if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
	                                .getModifiers())) {  
	                            writeMethod.setAccessible(true);  
	                        }  
	                        writeMethod.invoke(target, new Object[] { value });
                        }
                    } catch (Throwable ex) {  
                    }  
                }  
            }  
        }  
    }     
	
	
	/**
	 * 属性拷贝
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void copyProperties(Object source, Object target) throws Exception { 
        Class actualEditable = target.getClass();  
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);  
        for (int i = 0; i < targetPds.length; i++) {  
            PropertyDescriptor targetPd = targetPds[i];  
            if (targetPd.getWriteMethod() != null) {  
                PropertyDescriptor sourcePd = getPropertyDescriptor(source  
                        .getClass(), targetPd.getName());  
                if (sourcePd != null && sourcePd.getReadMethod() != null) {  
                    try {  
                        Method readMethod = sourcePd.getReadMethod();  
                        if (!Modifier.isPublic(readMethod.getDeclaringClass()  
                                .getModifiers())) {  
                            readMethod.setAccessible(true);  
                        }  
                        Object value = readMethod.invoke(source, new Object[0]);
                        if(value == null)  
                            continue;  
                        //集合类判空处理  
                        if(value instanceof Collection && !(value instanceof java.util.Map)) {
                          Collection newValue = (Collection)value;
                          Collection dest = null;
                          
                          if(targetPd.getPropertyType().getName().equals("java.util.List")){
                        	  dest = new ArrayList();
                          }else if(targetPd.getPropertyType().getName().equals("java.util.Set")){
                        	  dest = new java.util.HashSet();
                          }
                          
                          if(dest == null) continue;
                          
                          Object sourceItem  = null;String targetItemClassName = "";
                          
	                      for(Object cobj:newValue){
	                    	  if(sourceItem == null){
	                    		  sourceItem = cobj;
	                    		  targetItemClassName = sourceItem.getClass().getName();
	                    	  }
	                    	  Object targetItemInstance = Class.forName(targetItemClassName).newInstance();
	                    	  copyPropertiesBySpring(cobj, targetItemInstance);
	                          dest.add(targetItemInstance);
	                      }
	                      
	                      Method writeMethod = targetPd.getWriteMethod();  
                          if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                                  .getModifiers())) {  
                              writeMethod.setAccessible(true);  
                          }  
                          writeMethod.invoke(target, new Object[] { dest });
//                          if(newValue.size() <= 0)  
//                                continue;  
                        }
                        if(!value.getClass().getName().equals(targetPd.getPropertyType().getName())){
                        	Object instance = targetPd.getPropertyType().newInstance();
                        	copyPropertiesBySpring(value, instance);
                        	 Method writeMethod = targetPd.getWriteMethod();  
                             if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
                                     .getModifiers())) {  
                                 writeMethod.setAccessible(true);  
                             }  
                             writeMethod.invoke(target, new Object[] { instance });
                        }else{
	                        Method writeMethod = targetPd.getWriteMethod();  
	                        if (!Modifier.isPublic(writeMethod.getDeclaringClass()  
	                                .getModifiers())) {  
	                            writeMethod.setAccessible(true);  
	                        }  
	                        writeMethod.invoke(target, new Object[] { value });
                        }
                    } catch (Throwable ex) {  
                    }  
                }  
            }  
        }  
    }   
	
    /** 
     * 获取指定类指定名称的属性描述符 
     * @param clazz 
     * @param propertyName 
     * @return 
     * @throws BeansException 
     */  
    @SuppressWarnings("unchecked")  
    public static PropertyDescriptor getPropertyDescriptor(Class clazz,
            String propertyName) throws BeansException {
//        CachedIntrospectionResults cr = CachedIntrospectionResults.forClass(clazz);
//        return cr.getPropertyDescriptor(propertyName);
    	return null;
    }
      
    /** 
     * 获取指定类得所有属性描述符 
     * @param clazz 
     * @return 
     * @throws BeansException 
     */  
    @SuppressWarnings("unchecked")  
    public static PropertyDescriptor[] getPropertyDescriptors(Class clazz) throws BeansException {  
//        CachedIntrospectionResults cr = CachedIntrospectionResults.forClass(clazz);  
//        return cr.getBeanInfo().getPropertyDescriptors();  
    	return null;
    }
}
