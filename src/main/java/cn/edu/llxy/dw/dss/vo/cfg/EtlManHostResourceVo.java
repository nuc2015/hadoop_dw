package cn.edu.llxy.dw.dss.vo.cfg;

import java.util.Date;

public class EtlManHostResourceVo {
	private String id;
	private String hostName;
	private String hostIp;
	private String hostPort;
	private String hostUser;
	private String hostPassword;
	private String hostProtocol;
	private String hostDescribe;
	private String hostDisabled;
	
	private String systemType;
	

	private Date hostCreateTime;
	private String hostCreateUserId;
	private String hostCreateMttId;
	private Date hostUpdateTime;
	private String hostUpdateUserId;
	private String hostUpdateMttId;

	/** default constructor */
	public EtlManHostResourceVo() {
	}

	/** minimal constructor */
	public EtlManHostResourceVo(String id, String hostName, String hostIp, String hostPort, String hostUser,
			String hostPassword, String hostProtocol, String hostDescribe) {
		this.id = id;
		this.hostName = hostName;
		this.hostIp = hostIp;
		this.hostPort = hostPort;
		this.hostUser = hostUser;
		this.hostPassword = hostPassword;
		this.hostProtocol = hostProtocol;
		this.hostDescribe = hostDescribe;
	}

	/** full constructor */
	public EtlManHostResourceVo(String id, String hostName, String hostIp, String hostPort, String hostUser,
			String hostPassword, String hostProtocol, String hostDescribe, String hostDisabled) {
		this.id = id;
		this.hostName = hostName;
		this.hostIp = hostIp;
		this.hostPort = hostPort;
		this.hostUser = hostUser;
		this.hostPassword = hostPassword;
		this.hostProtocol = hostProtocol;
		this.hostDescribe = hostDescribe;
		this.hostDisabled = hostDisabled;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostIp() {
		return this.hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostPort() {
		return this.hostPort;
	}

	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}

	public String getHostUser() {
		return this.hostUser;
	}

	public void setHostUser(String hostUser) {
		this.hostUser = hostUser;
	}

	public String getHostPassword() {
		return this.hostPassword;
	}

	public void setHostPassword(String hostPassword) {
		this.hostPassword = hostPassword;
	}

	public String getHostProtocol() {
		return this.hostProtocol;
	}

	public void setHostProtocol(String hostProtocol) {
		this.hostProtocol = hostProtocol;
	}

	public String getHostDescribe() {
		return this.hostDescribe;
	}

	public void setHostDescribe(String hostDescribe) {
		this.hostDescribe = hostDescribe;
	}

	public String getHostDisabled() {
		return this.hostDisabled;
	}

	public void setHostDisabled(String hostDisabled) {
		this.hostDisabled = hostDisabled;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public Date getHostCreateTime() {
		return hostCreateTime;
	}

	public void setHostCreateTime(Date hostCreateTime) {
		this.hostCreateTime = hostCreateTime;
	}

	public String getHostCreateUserId() {
		return hostCreateUserId;
	}

	public void setHostCreateUserId(String hostCreateUserId) {
		this.hostCreateUserId = hostCreateUserId;
	}

	public String getHostCreateMttId() {
		return hostCreateMttId;
	}

	public void setHostCreateMttId(String hostCreateMttId) {
		this.hostCreateMttId = hostCreateMttId;
	}

	public Date getHostUpdateTime() {
		return hostUpdateTime;
	}

	public void setHostUpdateTime(Date hostUpdateTime) {
		this.hostUpdateTime = hostUpdateTime;
	}

	public String getHostUpdateUserId() {
		return hostUpdateUserId;
	}

	public void setHostUpdateUserId(String hostUpdateUserId) {
		this.hostUpdateUserId = hostUpdateUserId;
	}

	public String getHostUpdateMttId() {
		return hostUpdateMttId;
	}

	public void setHostUpdateMttId(String hostUpdateMttId) {
		this.hostUpdateMttId = hostUpdateMttId;
	}

	

	
}