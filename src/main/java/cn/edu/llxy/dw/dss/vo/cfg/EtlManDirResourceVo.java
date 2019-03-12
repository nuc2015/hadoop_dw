package cn.edu.llxy.dw.dss.vo.cfg;

import java.util.Date;

public class EtlManDirResourceVo {

	private String id;
	private String nodeId;
	private String dirName;
	private String dirKey;
	private String dirPath;
	private String dirDescribe;
	private String dirDisabled;
	private String dirFlag;
	private String dirFatherKey;
	


	private Date dirCreateTime;
	private String dirCreateUserId;
	private String dirCreateMttId;
	private Date dirUpdateTime;
	private String dirUpdateUserId;
	private String dirUpdateMttId;

	// Constructors

	/** default constructor */
	public EtlManDirResourceVo() {
	}

	public String getDirFlag() {
		return dirFlag;
	}

	public void setDirFlag(String dirFlag) {
		this.dirFlag = dirFlag;
	}

	public String getDirFatherKey() {
		return dirFatherKey;
	}

	public void setDirFatherKey(String dirFatherKey) {
		this.dirFatherKey = dirFatherKey;
	}

	/** minimal constructor */
	public EtlManDirResourceVo(String id) {
		this.id = id;
	}

	/** full constructor */
 

	public String getId() {
		return this.id;
	}

	public EtlManDirResourceVo(String id, String nodeId, String dirName,
			String dirKey, String dirPath, String dirDescribe,
			String dirDisabled, String dirFlag, String dirFatherKey) {
		super();
		this.id = id;
		this.nodeId = nodeId;
		this.dirName = dirName;
		this.dirKey = dirKey;
		this.dirPath = dirPath;
		this.dirDescribe = dirDescribe;
		this.dirDisabled = dirDisabled;
		this.dirFlag = dirFlag;
		this.dirFatherKey = dirFatherKey;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getDirName() {
		return this.dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getDirKey() {
		return this.dirKey;
	}

	public void setDirKey(String dirKey) {
		this.dirKey = dirKey;
	}

	public String getDirPath() {
		return this.dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getDirDescribe() {
		return this.dirDescribe;
	}

	public void setDirDescribe(String dirDescribe) {
		this.dirDescribe = dirDescribe;
	}

	public String getDirDisabled() {
		return this.dirDisabled;
	}

	public void setDirDisabled(String dirDisabled) {
		this.dirDisabled = dirDisabled;
	}

	public Date getDirCreateTime() {
		return dirCreateTime;
	}

	public void setDirCreateTime(Date dirCreateTime) {
		this.dirCreateTime = dirCreateTime;
	}

	public String getDirCreateUserId() {
		return dirCreateUserId;
	}

	public void setDirCreateUserId(String dirCreateUserId) {
		this.dirCreateUserId = dirCreateUserId;
	}

	public String getDirCreateMttId() {
		return dirCreateMttId;
	}

	public void setDirCreateMttId(String dirCreateMttId) {
		this.dirCreateMttId = dirCreateMttId;
	}

	public Date getDirUpdateTime() {
		return dirUpdateTime;
	}

	public void setDirUpdateTime(Date dirUpdateTime) {
		this.dirUpdateTime = dirUpdateTime;
	}

	public String getDirUpdateUserId() {
		return dirUpdateUserId;
	}

	public void setDirUpdateUserId(String dirUpdateUserId) {
		this.dirUpdateUserId = dirUpdateUserId;
	}

	public String getDirUpdateMttId() {
		return dirUpdateMttId;
	}

	public void setDirUpdateMttId(String dirUpdateMttId) {
		this.dirUpdateMttId = dirUpdateMttId;
	}

	

}