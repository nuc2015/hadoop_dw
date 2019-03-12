package cn.edu.llxy.dw.dss.vo.dg.meta.relation;


/**
 * 关系数据库字段列
 */
public class ColumnRule extends Column{

	/**
	 * 算法类型RuleId
	 */
	private String ruleId;
	/**
	 * 算法类型Id
	 */
	private String arithmeticId;

	/**
	 * 算法类型Name
	 */
	private String aName;
	/**
	 * 算法类型
	 */
	private String aType;
	/**
	 * 定位 
	 */
	private String aPos;
	/**
	 * 算法参数
	 */
	private String aParas;
	
	
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getArithmeticId() {
		return arithmeticId;
	}
	public void setArithmeticId(String arithmeticId) {
		this.arithmeticId = arithmeticId;
	}
	
	public String getaName() {
		return aName;
	}
	public void setaName(String aName) {
		this.aName = aName;
	}
	
	public String getaType() {
		return aType;
	}
	public void setaType(String aType) {
		this.aType = aType;
	}
	
	public String getaPos() {
		return aPos;
	}
	public void setaPos(String aPos) {
		this.aPos = aPos;
	}
	public String getaParas() {
		return aParas;
	}
	public void setaParas(String aParas) {
		this.aParas = aParas;
	}
	
}
