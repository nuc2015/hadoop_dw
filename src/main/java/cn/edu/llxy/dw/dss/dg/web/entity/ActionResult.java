package cn.edu.llxy.dw.dss.dg.web.entity;

/**
 * 请求响应json类型
 */
public class ActionResult {
	public static final String ACTION_TYPE_AJAX = "ajax";
	
	public static final String ACTION_TYPE_NORMAL = "normal";
	
	public static final String ACTION_RESULT_SUCC = "succ";
	
	public static final String ACTION_RESULT_FAIL = "fail";
	
	/**
	 * 请求类型
	 */
	private String actionType;
	
	/**
	 * 响应结果
	 */
	private String result;
	
	/**
	 * 响应结果
	 */
	private String msg;
	
	private String msgCode;
	
	private String expInfo;
	
	/**
	 * 实体id
	 */
	private String entityId;
	
	private float userTimes;

	public float getUserTimes() {
		return userTimes;
	}

	public void setUserTimes(float userTimes) {
		this.userTimes = userTimes;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getExpInfo() {
		return expInfo;
	}

	public void setExpInfo(String expInfo) {
		this.expInfo = expInfo;
	}
}
