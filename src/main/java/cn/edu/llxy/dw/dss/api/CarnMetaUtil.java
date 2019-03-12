package cn.edu.llxy.dw.dss.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cn.edu.llxy.dw.dss.util.model.resp.RespMessage;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;

import cn.edu.llxy.dw.dss.util.model.MetaData;
import cn.edu.llxy.dw.dss.util.model.req.ReqBodyReq;
import cn.edu.llxy.dw.dss.util.model.req.ReqData;
import cn.edu.llxy.dw.dss.util.model.req.ReqHeaderReq;
import cn.edu.llxy.dw.dss.util.model.req.ReqMessage;
import cn.edu.llxy.dw.dss.util.model.req.ReqRoute;
import cn.edu.llxy.dw.dss.util.model.req.ReqSystem;
import cn.edu.llxy.dw.dss.util.model.req.ReqUser;
import com.thoughtworks.xstream.XStream;

public class CarnMetaUtil {
	private static final String RESTFUL_PATH = "/RESTful/metadata/";
    public static final String EXTERNAL = "/external?";

	public static String restQuery(String baseUri, String value) {
		JerseyClient client = JerseyClientBuilder.createClient();
		WebTarget wt = client.target(baseUri);
		JerseyWebTarget jwt = (JerseyWebTarget) wt;
		
		Response resp = jwt.path(value).request(MediaType.APPLICATION_XML_TYPE).get();

		String ret = null;
		try {
			ret = resp.readEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}
	
	public static RespMessage restAdd(String baseUri, String parentPath, String username, String password, MetaData metaData) {
		JerseyClient client = JerseyClientBuilder.createClient();
		WebTarget wt = client.target(baseUri);
		JerseyWebTarget jwt = (JerseyWebTarget) wt;

		ReqMessage message = new ReqMessage();
		ReqHeaderReq headerReq = new ReqHeaderReq();
		ReqUser user = new ReqUser();
		ReqSystem system = new ReqSystem();
		ReqRoute route = new ReqRoute();
		ReqBodyReq bodyReq = new ReqBodyReq();
		ReqData reqData = new ReqData();

		user.setClientID(username);
		user.setPassword(password);
		headerReq.setUser(user);
		headerReq.setSystem(system);
		headerReq.setRoute(route);
		reqData.setMetadata(metaData);
		bodyReq.setReqData(reqData);
		message.setHeaderReq(headerReq);
		message.setBodyReq(bodyReq);

		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		String xml = xstream.toXML(message);

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response resp = jwt.path(parentPath).request(MediaType.APPLICATION_XML_TYPE).post(entity);

		String ret = null;
		try {
			ret = resp.readEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RespMessage.parseXml(ret);
	}
	
	public static RespMessage restDelete(String baseUri,String parentPath, MetaData metaData) {
		JerseyClient client = JerseyClientBuilder.createClient();
		WebTarget wt = client.target(baseUri);
		JerseyWebTarget jwt = (JerseyWebTarget) wt;

		Response resp = jwt.path(parentPath + metaData.getName()).request(MediaType.APPLICATION_XML_TYPE).delete();
		String ret = null;
		try {
			ret = resp.readEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RespMessage.parseXml(ret);
	}

	public static RespMessage restUpdate(String baseUri, String parentPath, String username, String password, MetaData metaDataOld, MetaData metaDataNew) {
		JerseyClient client = JerseyClientBuilder.createClient();
		WebTarget wt = client.target(baseUri);
		JerseyWebTarget jwt = (JerseyWebTarget) wt;

		ReqMessage message = new ReqMessage();
		ReqHeaderReq headerReq = new ReqHeaderReq();
		ReqUser user = new ReqUser();
		ReqSystem system = new ReqSystem();
		ReqRoute route = new ReqRoute();
		ReqBodyReq bodyReq = new ReqBodyReq();
		ReqData reqData = new ReqData();

		user.setClientID(username);
		user.setPassword(password);
		headerReq.setUser(user);
		headerReq.setSystem(system);
		headerReq.setRoute(route);
		reqData.setMetadata(metaDataNew);
		bodyReq.setReqData(reqData);
		message.setHeaderReq(headerReq);
		message.setBodyReq(bodyReq);

		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		String xml = xstream.toXML(message);

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response resp = jwt.path(parentPath + metaDataOld.getName()).request(MediaType.APPLICATION_XML_TYPE).put(entity);

		String ret = null;
		try {
			ret = resp.readEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RespMessage.parseXml(ret);
	}
	
	public static void main(String args[]){
		MetaData delNode = new MetaData();
		delNode.setName("tb_test_del");
		String metaOneNodePath = "/RESTful/metadata/PAAS/B域数据处理应用/模型/逻辑模型/基础数据/参与人主题域/" ;
		RespMessage reponse = CarnMetaUtil.restDelete("http://172.21.2.160:8080/metaone", metaOneNodePath, delNode);
		
		System.out.println(reponse);
	}
	
	public static boolean pathExist(String baseUri, String path){
		String ret = CarnMetaUtil.restQuery(baseUri, path);
		
		if(ret.indexOf("<RespResult>Success</RespResult>") > 0){
			return true;
		}else{
			return false;
		}
	}
}
