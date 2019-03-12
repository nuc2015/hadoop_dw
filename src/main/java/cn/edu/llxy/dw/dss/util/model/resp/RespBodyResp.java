package cn.edu.llxy.dw.dss.util.model.resp;

import org.dom4j.Element;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BodyResp")
public class RespBodyResp {
	@XStreamAlias("RespData")
	private RespData respData;

	public RespData getRespData() {
		return respData;
	}

	public void setRespData(RespData respData) {
		this.respData = respData;
	}

	@Override
	public String toString() {
		return "RepBodyResp [respData=" + respData + "]";
	}

	public static RespBodyResp parseXml(Element bodyElm) {
		RespBodyResp bodyResp = new RespBodyResp();
		Element dataElm = bodyElm.element("RespData");
		if (dataElm != null) {
			bodyResp.setRespData(RespData.parseXml(dataElm));
		}
		return bodyResp;
	}

	public static RespBodyResp parseListXml(Element bodyElm) {
		RespBodyResp bodyResp = new RespBodyResp();
		Element dataElm = bodyElm.element("RespData");
		if (dataElm != null) {
			bodyResp.setRespData(RespData.parseListXml(dataElm));
		}
		return bodyResp;
	}
}
