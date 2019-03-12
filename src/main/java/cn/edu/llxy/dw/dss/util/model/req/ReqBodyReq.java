package cn.edu.llxy.dw.dss.util.model.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BodyReq")
public class ReqBodyReq {
	private ReqData ReqData;

	public ReqData getReqData() {
		return ReqData;
	}

	public void setReqData(ReqData reqData) {
		ReqData = reqData;
	}
}
