package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

/**
 * 关系数据库字段列
 */
public class Column extends AbstractBaseObject{
	
	public static String META_MDL = "relation.Column";
	
	//存储字段在表中的序号；
	private int index;
	//是否可以为空
	public boolean nullable;
	//是否主键
	public boolean pk;
	//是否接口
	public boolean itf;
	
	private String defValue;
	
	//字段是否在元数据中保存
	private boolean fieldInMetas;
	
	private String tableName;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public boolean isItf() {
		return itf;
	}
	public void setItf(boolean itf) {
		this.itf = itf;
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	public boolean isFieldInMetas() {
		return fieldInMetas;
	}
	public void setFieldInMetas(boolean fieldInMetas) {
		this.fieldInMetas = fieldInMetas;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
