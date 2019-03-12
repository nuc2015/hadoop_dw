package cn.edu.llxy.dw.dss.dg.web.entity;

public class MetaProp {
	//元数据key
	private String key;
	//元数据value
	private Object value;
	//选中状态
	private String selected;
	//链接属性
	private String link;
	
	private String refObj;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRefObj() {
		return refObj;
	}

	public void setRefObj(String refObj) {
		this.refObj = refObj;
	}
}
