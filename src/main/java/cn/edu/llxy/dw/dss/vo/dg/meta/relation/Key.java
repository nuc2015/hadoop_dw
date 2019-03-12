package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import java.util.List;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

/**
 * 关系数据库key

 */
public class Key extends AbstractBaseObject{
	public static String META_MDL = "relation.Key";

	//创建主键的名称
	private String constraintName;
	
	//关系型数据库主键信息,联合主键按照主键顺序逗号分隔连接
	private List<String> primaryKeyList;
	//引用列
	public String refClms;
	
	public String fkClm;
	
	public String refSchema;
	
	public String refTable;
	
	public String updateRule;
	
	public String deleteRule;
	
	public String getFkClm() {
		return fkClm;
	}
	public void setFkClm(String fkClm) {
		this.fkClm = fkClm;
	}
	public String getRefSchema() {
		return refSchema;
	}
	public void setRefSchema(String refSchema) {
		this.refSchema = refSchema;
	}
	public String getRefTable() {
		return refTable;
	}
	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}
	public String getUpdateRule() {
		return updateRule;
	}
	public void setUpdateRule(String updateRule) {
		this.updateRule = updateRule;
	}
	public String getDeleteRule() {
		return deleteRule;
	}
	public void setDeleteRule(String deleteRule) {
		this.deleteRule = deleteRule;
	}
	public String getRefClms() {
		return refClms;
	}
	public void setRefClms(String refClms) {
		this.refClms = refClms;
	}
	public String getConstraintName() {
		return constraintName;
	}
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	public List<String> getPrimaryKeyList() {
		return primaryKeyList;
	}
	public void setPrimaryKeyList(List<String> primaryKeyList) {
		this.primaryKeyList = primaryKeyList;
	}
	
}