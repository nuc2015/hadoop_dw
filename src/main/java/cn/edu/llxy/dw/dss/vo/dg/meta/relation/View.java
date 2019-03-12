package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

/**
 * 关系数据库视图
 */
import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

public class View extends AbstractBaseObject{
	public static String META_MDL = "relation.View";
	//是否只读
	public boolean readonly = false;
	//视图体
	public String code;

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
