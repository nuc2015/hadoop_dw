package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

/**
 * 关系数据库索引
 */
public class Index extends AbstractBaseObject{
	public static String META_MDL = "relation.Index";
	
	//列
	public String clms;
	
	public boolean compress;
	
	public String idxFun;

	public String getIdxFun() {
		return idxFun;
	}

	public void setIdxFun(String idxFun) {
		this.idxFun = idxFun;
	}

	public String getClms() {
		return clms;
	}

	public void setClms(String clms) {
		this.clms = clms;
	}

	public boolean isCompress() {
		return compress;
	}

	public void setCompress(boolean compress) {
		this.compress = compress;
	}
}
