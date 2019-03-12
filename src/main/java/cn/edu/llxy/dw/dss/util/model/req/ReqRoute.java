package cn.edu.llxy.dw.dss.util.model.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Route")
public class ReqRoute {
	private String RouteType;
	private String RouteId;

	public String getRouteId() {
		return RouteId;
	}

	public void setRouteId(String routeId) {
		RouteId = routeId;
	}

	public String getRouteType() {
		return RouteType;
	}

	public void setRouteType(String routeType) {
		RouteType = routeType;
	}
}
