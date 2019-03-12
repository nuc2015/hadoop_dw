package cn.edu.llxy.dw.dss.dc.web;

import java.util.List;

import cn.edu.llxy.dw.dss.vo.dg.meta.relation.Column;

public class QueryResult {
	public final static String QUERY_SUCC = "succ";
	public final static String QUERY_FAIL = "fail";
	
	
	private Object[] header;
	
	private List<Object[]> datas;
	
	//for oracle...
	private List<String> rowIds;
	
	private float userTimes;
	
	private List<Column> metas;
	
	private String result;
	
	private String expMsg;
	
	private String dbType;
	
	private String tableName;
	
	private String querySql;
	
	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public List<String> getRowIds() {
		return rowIds;
	}

	public void setRowIds(List<String> rowIds) {
		this.rowIds = rowIds;
	}

	//1-允许编辑 否则不允许
	private String update;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getExpMsg() {
		return expMsg;
	}

	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}

	public List<Column> getMetas() {
		return metas;
	}

	public void setMetas(List<Column> metas) {
		this.metas = metas;
	}

	public float getUserTimes() {
		return userTimes;
	}

	public void setUserTimes(float userTimes) {
		this.userTimes = userTimes;
	}

	public Object[] getHeader() {
		return header;
	}

	public void setHeader(Object[] header) {
		this.header = header;
	}

	public List<Object[]> getDatas() {
		return datas;
	}

	public void setDatas(List<Object[]> datas) {
		this.datas = datas;
	}
}
