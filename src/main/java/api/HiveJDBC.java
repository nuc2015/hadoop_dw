package api;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * JDBC 操作 Hive（注：JDBC 访问 Hive 前需要先启动HiveServer2）
 */
public class HiveJDBC {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://master.hadoop:10000/";
    private static String user = "root";
    private static String password = "";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static Connection getHiveConn(String db){
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url+db,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 加载驱动、创建连接
    @Before
    public void init() throws Exception {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url+"hadoop_dw",user,password);
        stmt = conn.createStatement();
    }

    // 创建数据库
    @Test
    public void createDatabase() throws Exception {
        String sql = "create database hadoop_dw";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询所有数据库
    @Test
    public void showDatabases() throws Exception {
        String sql = "show databases";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 创建表
    @Test
    public void createTable() throws Exception {
        String sql = "create table t_1(id int, name string, password string) row format delimited fields terminated by ','";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询所有表
    @Test
    public void showTables() throws Exception {
        String sql = "show tables";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 查看表结构
    @Test
    public void descTable() throws Exception {
        String sql = "desc t_1";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2));
        }
    }

    // 加载数据
    @Test
    public void loadData() throws Exception {
        String filePath = "/root/a.txt";
        String sql = "load data local inpath '" + filePath + "' overwrite into table t_1";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询数据
    @Test
    public void selectData() throws Exception {
        String sql = "select * from t_1";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        System.out.println("id" + "\t" + "name" + "\t" + "password");
        while (rs.next()) {
            System.out.println(rs.getString("t_1.id") + "\t\t" + rs.getString("t_1.name") + "\t\t" + rs.getString("t_1.password"));
        }
    }

    // 统计查询（会运行mapreduce作业）
    @Test
    public void countData() throws Exception {
        String sql = "select count(1) from t_1";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getInt(1) );
        }
    }

    // 删除数据库
    @Test
    public void dropDatabase() throws Exception {
        String sql = "drop database if exists hadoop_dw";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 删除数据库表
    @Test
    public void deopTable() throws Exception {
        String sql = "drop table if exists t_1";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 释放资源
    @After
    public void destory() throws Exception {
        if ( rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
