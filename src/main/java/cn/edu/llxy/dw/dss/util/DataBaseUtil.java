package cn.edu.llxy.dw.dss.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.edu.llxy.dw.dss.vo.dg.meta.relation.DataSource;

public class DataBaseUtil {
	//数据库驱动Class定义
	private static Map<String,String> DRIVER_CLASS_MAP = new HashMap();
	
	public static Map getClassMap(){
		if(DRIVER_CLASS_MAP.size() <= 0)
		{
			DRIVER_CLASS_MAP.put("Oracle", "oracle.jdbc.driver.OracleDriver");
			//DRIVER_CLASS_MAP.put("pkg", "oracle.jdbc.driver.OracleDriver");
			DRIVER_CLASS_MAP.put("sqlserver2000", "net.sourceforge.jtds.jdbc.Driver");
			DRIVER_CLASS_MAP.put("sqlserver2005", "net.sourceforge.jtds.jdbc.Driver");
			DRIVER_CLASS_MAP.put("DB2", "com.ibm.db2.jcc.DB2Driver");
			DRIVER_CLASS_MAP.put("Mysql", "com.mysql.jdbc.Driver");
			DRIVER_CLASS_MAP.put("Gbase", "com.gbase.jdbc.Driver");
		}
		return DRIVER_CLASS_MAP;
	}
	
	/**
	 * 根据配置获取数据库连接URL
	 * @param ds
	 * @return
	 */
	public static String getJDBCUrl(DataSource ds)
	{
		String url = "";
		if(ds.getType().toUpperCase().equals("Oracle".toUpperCase()) || ds.getType().equals("pkg")){
			url = "jdbc:oracle:thin:@"+ds.getHost()+":"+ds.getPort()+":"+ds.getServerName();
		}else if(ds.getType().toUpperCase().startsWith("sqlserver".toUpperCase())){
			url = "jdbc:jtds:sqlserver://" + ds.getHost()+":"+ds.getPort()+";DatabaseName="+ds.getServerName();
		}else if(ds.getType().toUpperCase().equals("DB2".toUpperCase())){
			url = "jdbc:db2://" + ds.getHost()+":"+ds.getPort()+"/"+ds.getServerName();
		}else if(ds.getType().toUpperCase().equals("Mysql".toUpperCase())){
			url = "jdbc:mysql://" + ds.getHost()+":"+ds.getPort()+"/"+ds.getServerName();
		}else if(ds.getType().toUpperCase().equals("Gbase".toUpperCase())){
			url = "jdbc:gbase://" + ds.getHost()+":"+ds.getPort()+"/"+ds.getServerName();
		}
		
		return url;
	}
	
	/**
	 * 根据配置获取数据库连接
	 * @param ds
	 * @return
	 */
	public static Connection getConnection(DataSource ds)
	{
		String driverClass = getClassMap().get(ds.getType())+"";

		String url = getJDBCUrl(ds);

		Driver driver = null;

		try 
		{
			driver = (Driver) Class.forName(driverClass)
				.newInstance();
			DriverManager.registerDriver(driver); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String duser = ds.getUser();
			String pwd   = ds.getPwd();
			
			//Connection cn = DriverManager.getConnection( url, ds.getDuser(), ds.getPwd() );
			Connection cn = DriverManager.getConnection( url, duser, pwd );
			
			return cn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return null;
	}
}
