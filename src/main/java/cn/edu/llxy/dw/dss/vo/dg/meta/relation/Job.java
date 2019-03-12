package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;


/**
 * ETL 作业定义
 */
public class Job extends AbstractBaseObject{
	//父类type存储抽取、加载
	/**
	 * 元数据对应元模型
	 */
	public static String META_MDL = "etl.job";
	
	//接口号
	public String infNo;
	//作业ID
	public String jobId;
	
	//日全、日增、月全月增
	public String cycle;
	
	//定时、非定时、实时
	public String timeType;
	
	//时间串，触发规则
	public String triggerRule;

	public String getInfNo() {
		return infNo;
	}

	public void setInfNo(String infNo) {
		this.infNo = infNo;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getTriggerRule() {
		return triggerRule;
	}

	public void setTriggerRule(String triggerRule) {
		this.triggerRule = triggerRule;
	}
}
