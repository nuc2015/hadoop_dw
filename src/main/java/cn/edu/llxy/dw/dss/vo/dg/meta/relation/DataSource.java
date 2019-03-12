package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBasePackage;

public class DataSource extends AbstractBasePackage{
	public static String META_MDL = "relation.DataSource";
	
	//数据类型为MYSQL
	public final static String DS_TYPE_MYSQL = "MYSQL";
	
	//主机地址
	public String host;
	//服务端口
	public String port;
	//用户
	public String user;
	//访问密码
	public String pwd;
	//服务名
	public String serverName;
	
	private String bind;
	
	public String keytabPath;
	
	public String krb5ConfigPath;
	  
	public String hadoopHomeDir;
	  
	public String principal;
	
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

	public DataSource(){
		
	}

	public DataSource(String host,String port,String serverName,String user,String pwd){
		this.host = host;
		this.port = port;
		this.serverName = serverName;
		this.user = user;
		this.pwd = pwd;
	}
	
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}
}
