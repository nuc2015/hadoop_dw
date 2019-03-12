package cn.edu.llxy.dw.dss.vo.cfg;


import java.util.Date;


public class EtlManDbResourceVo {

	private String id;
	private String logicName;
	private String dbHost;
	private String dbPort;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	private String dbSchema;
	private String dbType;
	private String dbConType;
	private String dbDescribe;
	private String dbDisabled;
	private String dbTns;
	private String dbSname;
	private String dbUrl;
	private String dbUserPsdFrom;
	private String dbUserPsdLab;
	private String dbParameter;

	private Date dbCreateTime;
	private String dbCreateUserId;
	private String dbCreateMttId;
	private Date dbUpdateTime;
	private String dbUpdateUserId;
	private String dbUpdateMttId;
	
	private String krb5ConfigPath;
	  
	private String hadoopHomeDir;
	  
	private String keytabPath;
	  
	private String principal;
	
	public String getKrb5ConfigPath() {
		return krb5ConfigPath;
	}

	public void setKrb5ConfigPath(String krb5ConfigPath) {
		this.krb5ConfigPath = krb5ConfigPath;
	}

	public String getHadoopHomeDir() {
		return hadoopHomeDir;
	}

	public void setHadoopHomeDir(String hadoopHomeDir) {
		this.hadoopHomeDir = hadoopHomeDir;
	}

	public String getKeytabPath() {
		return keytabPath;
	}

	public void setKeytabPath(String keytabPath) {
		this.keytabPath = keytabPath;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/** default constructor */
	public EtlManDbResourceVo() {
	}
//	public EtlManDbResourceVo(LabelDbVo vo) {
//		this.setDbType("gbase");
//		this.setDbConType("0");
//		this.setDbHost(vo.getIp());
//		this.setDbName(vo.getServerId());
//		this.setDbPort(vo.getPort());
//		this.setDbUser(vo.getUserName());
//		this.setDbPassword(vo.getPassword());
//	}

	/** minimal constructor */
	public EtlManDbResourceVo(String id, String logicName, String dbHost, String dbPort, String dbName, String dbUser, String dbPassword,
			String dbSchema, String dbType, String dbConType, String dbDisabled, String dbUserPsdFrom) {
		this.id = id;
		this.logicName = logicName;
		this.dbHost = dbHost;
		this.dbPort = dbPort;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
		this.dbSchema = dbSchema;
		this.dbType = dbType;
		this.dbConType = dbConType;
		this.dbDisabled = dbDisabled;
		this.dbUserPsdFrom = dbUserPsdFrom;
	}

	/** full constructor */
	public EtlManDbResourceVo(String id, String logicName, String dbHost, String dbPort, String dbName, String dbUser, String dbPassword,
			String dbSchema, String dbType, String dbConType, String dbDescribe, String dbDisabled, String dbUserPsdFrom) {
		this.id = id;
		this.logicName = logicName;
		this.dbHost = dbHost;
		this.dbPort = dbPort;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
		this.dbSchema = dbSchema;
		this.dbType = dbType;
		this.dbConType = dbConType;
		this.dbDescribe = dbDescribe;
		this.dbDisabled = dbDisabled;
		this.dbUserPsdFrom = dbUserPsdFrom;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogicName() {
		return this.logicName;
	}

	public void setLogicName(String logicName) {
		this.logicName = logicName;
	}

	public String getDbHost() {
		return this.dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public String getDbPort() {
		return this.dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return this.dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUser() {
		return this.dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return this.dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbSchema() {
		return this.dbSchema;
	}

	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}

	public String getDbType() {
		return this.dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbConType() {
		return this.dbConType;
	}

	public void setDbConType(String dbConType) {
		this.dbConType = dbConType;
	}

	public String getDbDescribe() {
		return this.dbDescribe;
	}

	public void setDbDescribe(String dbDescribe) {
		this.dbDescribe = dbDescribe;
	}

	public String getDbDisabled() {
		return this.dbDisabled;
	}

	public void setDbDisabled(String dbDisabled) {
		this.dbDisabled = dbDisabled;
	}

	public String getDbTns() {
		return dbTns;
	}

	public void setDbTns(String dbTns) {
		this.dbTns = dbTns;
	}
	
	public String getDbUserPsdFrom() {
		return dbUserPsdFrom;
	}

	public void setDbUserPsdFrom(String dbUserPsdFrom) {
		this.dbUserPsdFrom = dbUserPsdFrom;
	}

	public Date getDbCreateTime() {
		return dbCreateTime;
	}

	public void setDbCreateTime(Date dbCreateTime) {
		this.dbCreateTime = dbCreateTime;
	}
	
	

	public String getDbUserPsdLab() {
		return dbUserPsdLab;
	}

	public void setDbUserPsdLab(String dbUserPsdLab) {
		this.dbUserPsdLab = dbUserPsdLab;
	}

	public String getDbCreateUserId() {
		return dbCreateUserId;
	}

	public void setDbCreateUserId(String dbCreateUserId) {
		this.dbCreateUserId = dbCreateUserId;
	}

	public String getDbCreateMttId() {
		return dbCreateMttId;
	}

	public void setDbCreateMttId(String dbCreateMttId) {
		this.dbCreateMttId = dbCreateMttId;
	}

	public Date getDbUpdateTime() {
		return dbUpdateTime;
	}

	public void setDbUpdateTime(Date dbUpdateTime) {
		this.dbUpdateTime = dbUpdateTime;
	}

	public String getDbUpdateUserId() {
		return dbUpdateUserId;
	}

	public void setDbUpdateUserId(String dbUpdateUserId) {
		this.dbUpdateUserId = dbUpdateUserId;
	}

	public String getDbUpdateMttId() {
		return dbUpdateMttId;
	}

	public void setDbUpdateMttId(String dbUpdateMttId) {
		this.dbUpdateMttId = dbUpdateMttId;
	}

	public String getDbParameter() {
		return dbParameter;
	}

	public void setDbParameter(String dbParameter) {
		this.dbParameter = dbParameter;
	}


	public String getDbSname() {
		return dbSname;
	}

	public void setDbSname(String dbSname) {
		this.dbSname = dbSname;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	
	
	
	
}