package cn.edu.llxy.dw.dss.hadoop.hive;

import api.HiveJDBC;
import cn.edu.llxy.dw.dss.po.dg.MetaCore;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.Column;
import cn.edu.llxy.dw.dss.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class HiveKbsUitls {
	private Configuration conf;
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	private static String hiveUrl;
	private static String user;
	private static String keytabPath;
	private static String krbConfPath;
	private static String hadoopHome;

	private static Logger log = LoggerFactory.getLogger(HiveKbsUitls.class);
	public static String SPLIT_STR = "-@-";

	static{
		try{
			Class.forName(driverName);

			Properties properties = new Properties();
			InputStream is = HiveKbsUitls.class.getClassLoader().getResourceAsStream("config.properties");
			properties.load(is);
			user = properties.getProperty("ibdcUser");
			krbConfPath = properties.getProperty("krb5Conf");
			hiveUrl = properties.getProperty("hiveUrl");
			keytabPath = properties.getProperty("keytabFile");
			hadoopHome = properties.getProperty("hadoopHome");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private static Connection getConnection(){
		Connection conn = null;
		try{
			Configuration conf = new Configuration();
			conf.set("hadoop.security.authentication", "Kerberos");
			System.setProperty("java.security.krb5.conf", krbConfPath);
			if(null != hadoopHome){
				System.setProperty("hadoop.home.dir", hadoopHome);
			}
			UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab(user, keytabPath);
			conn = DriverManager.getConnection(hiveUrl);
		}catch (Exception e){
			e.printStackTrace();
			log.error("-------------------------" + e.getMessage());
		}
		return conn;
	}

	public static List<MetaCore> getDataBases(String userGroup, String projectId){
		String roleSql = "show role grant group " + userGroup;
		String databaseSql = "show grant role ";
		List<Map<String, Object>> roleList = execQuerySql(roleSql);
		String dataBase = "", privilege = "";
		String tableName = "", role = "";
		List<MetaCore> resultList = new ArrayList<>();
		List<String> databaseNameList = new ArrayList<>();
		MetaCore metaCore;
		if(null!=roleList && roleList.size()> 0){
			for(Map<String, Object> map : roleList){
				role = (String)map.get("role");
				List<Map<String, Object>> databaseList = execQuerySql(databaseSql + role);
				if(null!=databaseList && databaseList.size()>0) {
					for (Map<String, Object> map1 : databaseList) {
						dataBase = (String) map1.get("database");
						privilege = (String) map1.get("privilege");
						if(dataBase.startsWith("hdfs://") || dataBase.startsWith("*")){
							continue;
						}
						if(databaseNameList.contains(dataBase)){
							continue;
						}else{
							databaseNameList.add(dataBase);
						}
						metaCore = new MetaCore();
						metaCore.setName(dataBase);
						metaCore.setChName(dataBase);
						metaCore.setBizDomain(dataBase + "|" + role + "|" + privilege);
						metaCore.setMetaMdl("hive.database");
						metaCore.setPrtId(projectId);
						metaCore.setCrtDate(new Date());
						metaCore.setType("hive.database");
						log.info(metaCore.toString());
						resultList.add(metaCore);
					}
				}
			}
		}
		return resultList;
	}

	public static List<MetaCore> getTables(String database, String role, String privilege, String databaseId){
		List<MetaCore> tabList = new ArrayList<>();
		MetaCore metaCore;
		Connection conn = null;
		try{
			String tableName = "";
			if("all".equals(privilege) || "*".equals(privilege)){
				String sql1 = "use " + database;
				String sql2 = "show tables ";
				conn = getConnection();
				execNonQuerySql(sql1, conn);
				List<Map<String, Object>> list2 = execQuerySql(sql2, conn);
				if(null!=list2 && list2.size()>0){
					for(Map<String,Object> tableMap : list2){
						tableName = (String)tableMap.get("tab_name");
						metaCore = new MetaCore();
						metaCore.setName(tableName);
						metaCore.setChName(tableName);
						metaCore.setMetaMdl("relation.Table");
						metaCore.setBizDomain(databaseId + SPLIT_STR + database + SPLIT_STR + tableName + SPLIT_STR + "hive_table");
						metaCore.setPrtId(databaseId);
						tabList.add(metaCore);
					}
				}
			} else{
				String sql3 = "show grant role " + role + " on database " + database;
				List<Map<String, Object>> list3 = execQuerySql(sql3);
				if(null!=list3 && list3.size()>0){
					for(Map<String,Object> tableMap : list3){
						tableName = (String)tableMap.get("table");
						if(StringUtil.isEmpty(tableName)){
							if(!StringUtil.isEmpty((String)tableMap.get("database"))){
								return getTables(database, role, "all", databaseId);
							}
						}else{
							metaCore = new MetaCore();
							metaCore.setName(tableName);
							metaCore.setChName(tableName);
							metaCore.setMetaMdl("relation.Table");
							metaCore.setBizDomain(databaseId + SPLIT_STR + database + SPLIT_STR + tableName + SPLIT_STR + "hive_table");
							metaCore.setPrtId(databaseId);
							tabList.add(metaCore);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(conn);
		}
		return tabList;
	}

	public static List<Column> getColumns(String database, String table){
		List<Column> columnList = new ArrayList<>();
		try{
			Column column;
			String col_name = "", data_type = "", comment = "";
			String sql = "describe "+ database +"." + table;
			List<Map<String, Object>> list = execQuerySql(sql);
			if(null!=list && list.size()>0){
				for(Map<String, Object> map : list){
					if(null == map.get("col_name") || map.get("col_name").toString().startsWith("#") || "".equals(map.get("col_name"))){
						break;
					}
					column = new Column();
					col_name = (String)map.get("col_name");
					column.setName(col_name);
					column.setChName(comment);
					data_type = map.get("data_type")==null?"":(String)map.get("data_type");
					if(data_type.indexOf("(")>0){
						column.setType(data_type.substring(0, data_type.indexOf("(")));
						int end = data_type.indexOf(",")>-1?data_type.indexOf(","):data_type.indexOf(")");
						String length = data_type.substring(data_type.indexOf("(")+1, end);
						column.setLength(Integer.parseInt(length));
					}else{
						column.setType(data_type);
					}
					comment = map.get("comment")==null?"":(String)map.get("comment");
					column.setCmt(comment);
					column.setNullable(true);
					columnList.add(column);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error(e.toString());
		}
		return columnList;
	}

	public static boolean checkTableExists(String database, String table){
		List<MetaCore> list = getTables(database, null, "all", null);
		boolean flag = false;
		for(MetaCore metaCore : list){
			if(metaCore.getName().equalsIgnoreCase(table)){
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static String getTableCreateSql(String dbName, String tableName){
		String sql = "show create table " + dbName + "." + tableName;;
		List<Map<String,Object>> list = execQuerySql(sql);
		StringBuilder createSql = new StringBuilder();
		if(list.size()>0){
			for(Map<String, Object> map : list){
				if(null != map.get("createtab_stmt")){
					createSql.append(map.get("createtab_stmt")).append("\n");
				}
			}
		}
		return createSql.toString();
	}

	public static List<Map<String,Object>> getTableDataLimit100(String dbName, String tableName){
		String sql = "select * from " + dbName + "." + tableName + " limit 100 ";
		List<Map<String,Object>> list = execQuerySql(sql);
		return list;
	}

	public static List<Map<String,Object>> getTableStaticInfo(String dbName, String tableName){
		String sql = " desc formatted " + dbName + "." + tableName;
		List<Map<String,Object>> list = execQuerySql(sql);
		return list;
	}

	/**
	 * 执行hive sql
	 * 需要调用者自己关闭connection
	 */
	public static boolean execNonQuerySql(String sql, Connection conn){
		return getExecuteBoolean(sql, conn);
	}

	/**
	 * 执行hive sql
	 */
	public static boolean execNonQuerySql(String sql){
		//Connection conn = getConnection();
		Connection conn = null;
		try {
			conn = HiveJDBC.getHiveConn("hadoop_dw");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getExecuteBoolean(sql, conn);
	}

	private static boolean getExecuteBoolean(String sql, Connection conn) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		boolean flag = true;
		//Statement statement = null;
		PreparedStatement statement = null;
		try{
			//statement = conn.createStatement();
			statement = conn.prepareStatement(sql);
			log.info("-------------- 执行sql 为: " + sql);
			statement.executeUpdate();
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}finally {
			close(statement);
		}
		return flag;
	}

	/**
	 * 执行hive sql
	 */
	public static List<Map<String, Object>> execQuerySql(String sql){
		try {
			return getExecuteList(sql, HiveJDBC.getHiveConn("hadoop_dw"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 执行hive sql
	 * 需要调用者自己关闭connection
	 */
	public static List<Map<String, Object>> execQuerySql(String sql, Connection conn){
		return getExecuteList(sql, conn);
	}

	private static List<Map<String, Object>> getExecuteList(String sql, Connection conn) {
		Statement statement = null;
		ResultSet res = null;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			statement = conn.createStatement();
			res = statement.executeQuery(sql);
			log.info("--------------  执行sql 为: " + sql);
			while (res.next()){
				ResultSetMetaData md = res.getMetaData();
				int columnCount = md.getColumnCount();   //获得列数
				log.info("-------------- 获得列数 : " + columnCount);
				Map<String,Object> rowData = new LinkedHashMap<String,Object>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), res.getObject(i));
				}
				list.add(rowData);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}finally {
			close(res);
			close(statement);
		}
		return list;
	}

	/**
	 * 关闭连接
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}

	/**
	 * 关闭Statement
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt = null;
		}
	}

	/**
	 * 关闭PreparedStatement
	 * @param pst
	 */
	public static void close(PreparedStatement pst) {
		try {
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pst = null;
		}
	}

	/**
	 * 关闭ResultSet
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs = null;
		}
	}

	public static void main(String[] args){
//		getTables("ptemp3", "oozieweb", "all", "");
		// ooziewebdb.tb_mid_par_user_day

//		getTableCreateSql("ooziewebdb", "tb_mid_par_user_day");
//		getTableDataLimit50("ooziewebdb", "student");
		getTableStaticInfo("ooziewebdb", "student");
	}
}
