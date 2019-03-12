package cn.edu.llxy.dw.dss.util.model.resp;

import java.io.StringReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Message")
public class RespMessage {
	private RespHeaderResp HeaderResp;
	private RespBodyResp BodyResp;

	public RespHeaderResp getHeaderResp() {
		return HeaderResp;
	}

	public void setHeaderResp(RespHeaderResp headerResp) {
		HeaderResp = headerResp;
	}

	public RespBodyResp getBodyResp() {
		return BodyResp;
	}

	public void setBodyResp(RespBodyResp bodyResp) {
		BodyResp = bodyResp;
	}

	@Override
	public String toString() {
		return "RepMessage [HeaderResp=" + HeaderResp + ", BodyResp=" + BodyResp + "]";
	}

	public static RespMessage parseXml(String messageXml) {
		RespMessage message = new RespMessage();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(new StringReader(messageXml));
			Element rootElm = document.getRootElement();
			Element headerElm = rootElm.element("HeaderResp");
			if (headerElm != null) {
				message.HeaderResp = RespHeaderResp.parseXml(headerElm);
			}
			Element bodyElm = rootElm.element("BodyResp");
			if (bodyElm != null) {
				message.BodyResp = RespBodyResp.parseXml(bodyElm);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return message;
	}

	public static RespMessage parseListXml(String messageXml) {
		RespMessage message = new RespMessage();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(new StringReader(messageXml));
			Element rootElm = document.getRootElement();
			Element headerElm = rootElm.element("HeaderResp");
			if (headerElm != null) {
				message.HeaderResp = RespHeaderResp.parseXml(headerElm);
			}
			Element bodyElm = rootElm.element("BodyResp");
			if (bodyElm != null) {
				message.BodyResp = RespBodyResp.parseListXml(bodyElm);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return message;
	}
}
