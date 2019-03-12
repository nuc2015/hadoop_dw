package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

/**
 * 关系数据库触发器
 */
public class Trigger extends AbstractBaseObject{
	public static String META_MDL = "relation.Trigger";
	//触发器体
	public String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}