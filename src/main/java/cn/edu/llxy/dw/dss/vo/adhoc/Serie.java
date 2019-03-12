package cn.edu.llxy.dw.dss.vo.adhoc;

import java.util.List;

public class Serie {
	private String name;
	
	private String type;
	
	private String mapType;
	
	private List<Object> data;
	
	private String stack;
	
	private MarkPoint markPoint;
	
	private MarkLine  markLine;
	
	//饼图
	private Object radius = "55%";
	//饼图
	private String[] center = new String[]{"50%","50%"};
	
	private Label label;

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public MarkPoint getMarkPoint() {
		return markPoint;
	}

	public void setMarkPoint(MarkPoint markPoint) {
		this.markPoint = markPoint;
	}

	public MarkLine getMarkLine() {
		return markLine;
	}

	public void setMarkLine(MarkLine markLine) {
		this.markLine = markLine;
	}

	public Object getRadius() {
		return radius;
	}

	public void setRadius(Object radius) {
		this.radius = radius;
	}

	public String[] getCenter() {
		return center;
	}

	public void setCenter(String[] center) {
		this.center = center;
	}

	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}
}
