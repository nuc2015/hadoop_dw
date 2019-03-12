package cn.edu.llxy.dw.dss.database;

import cn.edu.llxy.dw.dss.vo.cfg.EtlManDbResourceVo;

public interface EtlDataBaseFactory {

	EtlDataBaseInterface getDatabase(EtlManDbResourceVo dbResource)
			throws Exception;
	
	/**
	 * 获得一个etl数据库实例
	 * 
	 * @param name 名称
	 * @param type 数据类型
	 * @param access 访问方式
	 * @param host 主机，IP
	 * @param db 数据库名称
	 * @param port 理解端口号
	 * @param user 用户
	 * @param pass 密码
	 * @return
	 * @throws Exception
	 */
//	public EtlDataBaseInterface getDatabase(String name, String type,
//			String access, String host, String db, String port, String user,
//			String pass) throws Exception;
//	
//	public EtlDataBaseInterface getDatabase(String name, String type,
//			String access, String host, String db, String port, String user,
//			String pass,String label) throws Exception ;

}
