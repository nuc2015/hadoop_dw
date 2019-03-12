package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;

public class DataRule extends AbstractBaseObject{
	/**
	 * 元数据对应元模型
	 */
	public static String META_MDL = "etl.datarule";
	
	//层次放LVEL
	/**
	 * 稽核型-一致性、合规性、波动性
	 */
	public String auditType;
	//接口类型，放cycle
	/**
	 * 接口名称
	 */
	public String itfName;
	/**
	 * 接口表名
	 */
	public String itfTableName;
	/**
	 * 字段名
	 */
	public String clmName;
	
	//稽核规则放name
	/**
	 * 规则ID
	 */
	public String ruleId;
	
	//系统内规则ID放 bizNo
	
	/**
	 * 告警对象
	 */
	public String alertTarget;
	
	/**
	 * 运行平台
	 */
	public String runEnv;
	
	/**
	 * 优先级
	 */
	public String priority;
	
	/**
	 * 运行状态
	 */
	public String runStat;
	
	//业务分类放bizDomain
	//稽核类型放 type
	
	/**
	 * 告警类型
	 */
	public String alertType;

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getItfName() {
		return itfName;
	}

	public void setItfName(String itfName) {
		this.itfName = itfName;
	}

	public String getItfTableName() {
		return itfTableName;
	}

	public void setItfTableName(String itfTableName) {
		this.itfTableName = itfTableName;
	}

	public String getClmName() {
		return clmName;
	}

	public void setClmName(String clmName) {
		this.clmName = clmName;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getAlertTarget() {
		return alertTarget;
	}

	public void setAlertTarget(String alertTarget) {
		this.alertTarget = alertTarget;
	}

	public String getRunEnv() {
		return runEnv;
	}

	public void setRunEnv(String runEnv) {
		this.runEnv = runEnv;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getRunStat() {
		return runStat;
	}

	public void setRunStat(String runStat) {
		this.runStat = runStat;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
}
