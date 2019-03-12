package cn.edu.llxy.dw.dss.util.model.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("System")
public class ReqSystem {
	private String ReqSource;
	private String ReqTime;

	public String getReqSource() {
		return ReqSource;
	}

	public void setReqSource(String reqSource) {
		ReqSource = reqSource;
	}

	public String getReqTime() {
		return ReqTime;
	}

	public void setReqTime(String reqTime) {
		ReqTime = reqTime;
	}
}
