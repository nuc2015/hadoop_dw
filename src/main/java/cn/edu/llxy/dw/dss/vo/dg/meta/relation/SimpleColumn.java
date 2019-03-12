package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

public class SimpleColumn {

	//存储字段在表中的序号；
	private int index = -1;
	//字段名称
	public String name;
	//是否允许为空
	public boolean nullable = true;
	//是否主键
	public boolean pk = false;
	//主键序号
	private int pkindex = -1;
	//是否隐藏
	public boolean hidden = false;
	//是否隐藏
	public boolean editable = true;
	//数据字段类型
	public String feildtype = "CHAR";
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getPkindex() {
		return pkindex;
	}
	public void setPkindex(int pkindex) {
		this.pkindex = pkindex;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getFeildtype() {
		return feildtype;
	}
	public void setFeildtype(String feildtype) {
		this.feildtype = feildtype;
	}
	
	
}
