package cn.edu.llxy.dw.dss.util.model.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Message")
public class ReqMessage {
	private ReqHeaderReq HeaderReq;
	private ReqBodyReq BodyReq;

	public ReqHeaderReq getHeaderReq() {
		return HeaderReq;
	}

	public void setHeaderReq(ReqHeaderReq headerReq) {
		HeaderReq = headerReq;
	}

	public ReqBodyReq getBodyReq() {
		return BodyReq;
	}

	public void setBodyReq(ReqBodyReq bodyReq) {
		BodyReq = bodyReq;
	}
}
