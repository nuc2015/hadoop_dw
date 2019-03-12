package cn.edu.llxy.dw.dss.vo.cfg;

import java.util.ArrayList;
import java.util.List;

public class TableVarVo {
	private Long dbid;
	private String schema;
	private String tablename;
	private String varname;
	private List<FieldParamVo> fieldParams;

	public TableVarVo(Long dbid, String schema, String tablename, String varname) {
		super();
		this.dbid = dbid;
		this.schema = schema;
		this.tablename = tablename;
		this.varname = varname;
		this.fieldParams = new ArrayList<FieldParamVo>();
	}

	public void add(FieldParamVo fieldVo) {
		fieldParams.add(fieldVo);
	}

	public List<FieldParamVo> getFieldParams() {
		return fieldParams;
	}

	public void setFieldParams(List<FieldParamVo> fieldParams) {
		this.fieldParams = fieldParams;
	}

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getVarname() {
		return varname;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}
}
