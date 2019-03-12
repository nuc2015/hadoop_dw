package cn.edu.llxy.dw.dss.util;

import java.text.SimpleDateFormat;   
import java.util.Date;   
import java.util.Locale;   
  
import net.sf.json.JsonConfig;   
import net.sf.json.processors.JsonValueProcessor;   
  
public class JsonDateValueProcessor implements JsonValueProcessor {   
    private String format ="yyyy-MM-dd";   
    SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.UK);  
    
    public JsonDateValueProcessor() {    
    	
    } 
       
    public Object processArrayValue(Object value, JsonConfig config) {   
        return process(value);   
    }   
  
    public Object processObjectValue(String key, Object value, JsonConfig config) {   
        return process(value);   
    }   
       
    private Object process(Object value){   
        if(value instanceof Date){   
             
            return sdf.format(value);   
        }   
        return value == null ? "" : value.toString();   
    }   
}