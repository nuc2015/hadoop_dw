package cn.edu.llxy.dw.dss.util.model.resp;

import org.dom4j.Element;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("HeaderResp")
public class RespHeaderResp {
	private String RespResult;
	private String RespTime;
	private String RespCode;
	private String RespDesc;

	public String getRespResult() {
		return RespResult;
	}

	public void setRespResult(String respResult) {
		RespResult = respResult;
	}

	public String getRespTime() {
		return RespTime;
	}

	public void setRespTime(String respTime) {
		RespTime = respTime;
	}

	public String getRespCode() {
		return RespCode;
	}

	public void setRespCode(String respCode) {
		RespCode = respCode;
	}

	public String getRespDesc() {
		return RespDesc;
	}

	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}

	@Override
	public String toString() {
		return "RepHeaderResp [RespResult=" + RespResult + ", RespTime=" + RespTime + ", RespCode=" + RespCode + ", RespDesc=" + RespDesc + "]";
	}

	public static RespHeaderResp parseXml(Element headerElm) {
		RespHeaderResp headerRest = new RespHeaderResp();
		headerRest.setRespCode(headerElm.elementText("RespCode"));
		headerRest.setRespDesc(headerElm.elementText("RespDesc"));
		headerRest.setRespResult(headerElm.elementText("RespResult"));
		headerRest.setRespTime(headerElm.elementText("RespTime"));
		return headerRest;
	}
}
