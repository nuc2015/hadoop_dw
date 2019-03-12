package cn.edu.llxy.dw.dss.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import net.sf.json.JSONObject;

public class HttpUtil {
	/**
	 * 操作类型为新增
	 */
	public static String ACTION_ADD = "1";
	
	/**
	 * 操作类型为删除
	 */
	public static String ACTION_DELETE = "0";
	
	/**
	 * 操作类型为修改
	 */
	public static String ACTION_UPDATE = "2";
	
	public static JSONObject get(String url, String language) throws Exception{
		HttpClient client = new HttpClient();
		HttpMethod method=new GetMethod(url);
		HttpMethod methodLogin=new GetMethod("http://192.168.50.66:9000/uop/comm/v1/cas/noPwdLogin?language="+language+"&userName=13560145146&loginType=1");
		client.executeMethod(methodLogin);
		
		client.executeMethod(method);
		String responseBody = method.getResponseBodyAsString();
		method.releaseConnection();
		
		if(responseBody != null){
			JSONObject responseJsonObj = JSONObject.fromObject(responseBody);
			
			return responseJsonObj;
		}else{
			return null;
		}
	}
	
	/**
	 * 测试地址
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getTestLoginUserInfo() throws Exception{
		String demoUserInfo = "";
		return JSONObject.fromObject(demoUserInfo);
	}
	
	public static void dsOper(String url, String jwt, Map paras) throws Exception {
		HttpClient client = new HttpClient();
		PostMethod post = null;
		try {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = paras.entrySet().iterator();
			list.add(new NameValuePair("jwt", jwt));
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new NameValuePair(elem.getKey(), elem.getValue()));
			}
			

			PostMethod httpPost =new PostMethod(url);  
	        NameValuePair[] param = list.toArray(new NameValuePair[0]);
	        httpPost.setRequestBody(param);   
	        client.executeMethod(httpPost);  
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (post != null) {
				try {
					post.releaseConnection();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static JSONObject getLoginUserInfo(String url) throws Exception{
		HttpClient client = new HttpClient();
		HttpMethod method=new GetMethod(url);
		HttpMethod methodLogin=new GetMethod(url);
		client.executeMethod(methodLogin);
		
		client.executeMethod(method);
		//String responseBody = method.getResponseBodyAsString();
		
		InputStream str = method.getResponseBodyAsStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(str));
		StringBuffer stringBuffer = new StringBuffer();
		String result = "";
		while((result = br.readLine()) != null){
			stringBuffer.append(result);
		}
		
		String responseBody = stringBuffer.toString();
		method.releaseConnection();
		str.close();
		br.close();
				
		if(responseBody != null){
			JSONObject responseJsonObj = JSONObject.fromObject(responseBody);
			
			return responseJsonObj;
		}else{
			return null;
		}
	}
	
	public static JSONObject loginAndGet(String url, String mobile, String language) throws Exception{
		HttpClient client = new HttpClient();
		HttpMethod method=new GetMethod(url);
		HttpMethod methodLogin=new GetMethod("http://192.168.50.66:9000/uop/comm/v1/cas/noPwdLogin?language="+language+"&userName="+mobile+"&loginType=1");
		client.executeMethod(methodLogin);
		
		client.executeMethod(method);
		String responseBody = method.getResponseBodyAsString();
		method.releaseConnection();
		
		if(responseBody != null){
			JSONObject responseJsonObj = JSONObject.fromObject(responseBody);
			
			return responseJsonObj;
		}else{
			return null;
		}
	}
}
