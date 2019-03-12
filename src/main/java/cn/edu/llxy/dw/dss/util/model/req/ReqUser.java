package cn.edu.llxy.dw.dss.util.model.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("User")
public class ReqUser {
	private String ClientID;
	private String Password;

	public String getClientID() {
		return ClientID;
	}

	public void setClientID(String clientID) {
		ClientID = clientID;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
}
