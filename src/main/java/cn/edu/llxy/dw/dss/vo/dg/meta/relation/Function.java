package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

/**
 * 关系数据库方法
 */
public class Function extends AbstractBaseObject{
	public static String META_MDL = "relation.Function";
	//方法体
	public String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}