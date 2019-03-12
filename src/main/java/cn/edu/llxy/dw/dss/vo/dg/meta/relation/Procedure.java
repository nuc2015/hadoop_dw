package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

/**
 * 关系数据库存储视图
 */
public class Procedure extends AbstractBaseObject{
	public static String META_MDL = "relation.Procedure";
	//存储过程体
	public String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
