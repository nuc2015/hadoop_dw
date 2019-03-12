package cn.edu.llxy.dw.dss.util.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("attribute")
public class MetaAttribute {
	private String name; // 属性名
	private String value; // 属性值

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MetaAttribute [name=" + name + ", value=" + value + "]";
	}
}
