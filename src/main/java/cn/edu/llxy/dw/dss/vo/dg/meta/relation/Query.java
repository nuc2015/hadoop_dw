package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import java.util.List;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

/**
 * 数据查询定义类
 */
public class Query extends AbstractBaseObject{
	public static String META_MDL = "relation.Query";
	
	/**
	 * 查询字段
	 */
	private List<QueryField> fields;
	
	/**
	 * 查询结果
	 */
	private List<QueryField> conditions;

	public List<QueryField> getFields() {
		return fields;
	}

	public void setFields(List<QueryField> fields) {
		this.fields = fields;
	}

	public List<QueryField> getConditions() {
		return conditions;
	}

	public void setConditions(List<QueryField> conditions) {
		this.conditions = conditions;
	}
}
