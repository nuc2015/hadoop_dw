package cn.edu.llxy.dw.dss.util.model.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("HeaderReq")
public class ReqHeaderReq {
	private ReqUser User;
	private ReqSystem System;
	private ReqRoute Route;

	public ReqUser getUser() {
		return User;
	}

	public void setUser(ReqUser user) {
		User = user;
	}

	public ReqSystem getSystem() {
		return System;
	}

	public void setSystem(ReqSystem system) {
		System = system;
	}

	public ReqRoute getRoute() {
		return Route;
	}

	public void setRoute(ReqRoute route) {
		Route = route;
	}

}
