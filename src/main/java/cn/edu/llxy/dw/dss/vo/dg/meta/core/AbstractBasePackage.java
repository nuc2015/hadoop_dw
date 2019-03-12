package cn.edu.llxy.dw.dss.vo.dg.meta.core;


public abstract class AbstractBasePackage extends AbstractBaseObject{
	public static String META_MDL = "core.AbstractBasePackage";
	//对象类型为包
	private static final String OBJECT_TYPE_PKG = "pkg";
	//包-元模型
	private static final String META_MDL_PKG    = "core.pkg";

	public AbstractBasePackage(){
		this.setType(OBJECT_TYPE_PKG);
	}
}