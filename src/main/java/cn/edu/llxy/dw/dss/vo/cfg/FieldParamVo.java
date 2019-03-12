package cn.edu.llxy.dw.dss.vo.cfg;

public class FieldParamVo {
	private int index;
	private String colname;
	private String varname;
	
	public FieldParamVo(int index, String colname, String varname) {
		super();
		this.index = index;
		this.colname = colname;
		this.varname = varname;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getColname() {
		return colname;
	}
	public void setColname(String colname) {
		this.colname = colname;
	}
	public String getVarname() {
		return varname;
	}
	public void setVarname(String varname) {
		this.varname = varname;
	}
}
