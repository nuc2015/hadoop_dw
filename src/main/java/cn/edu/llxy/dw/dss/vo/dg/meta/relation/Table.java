package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

/**
 * 关系数据库表
 */
public class Table extends AbstractBaseObject{
	public static String META_MDL = "relation.Table";
	//是否临时表
	public boolean temporatory = false;

	public boolean isTemporatory() {
		return temporatory;
	}

	public void setTemporatory(boolean temporatory) {
		this.temporatory = temporatory;
	}
}
