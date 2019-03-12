package cn.edu.llxy.dw.dao.etl.impl;


import api.HiveJDBC;
import api.MysqlJDBC;
import cn.edu.llxy.dw.dao.base.impl.BaseDao;
import cn.edu.llxy.dw.dao.etl.EtlDao;
import cn.edu.llxy.dw.dssframework.hibernate4.dao.impl.BaseDaoImpl;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EtlDaoImpl extends BaseDaoImpl implements EtlDao {

    private Connection conn = null;

    /**
     * mysql数据
     * @param tableName
     * @param args
     * @return
     */
    @Override
    public List<Map<String, Object>> getMysqlTableData(String dbName, String tableName, String... args) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        PreparedStatement pst = null;
        String sql = "select * from "+tableName;
        try {
            conn = MysqlJDBC.getConn(dbName);
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                Map<String, Object> entity = new HashMap<String, Object>();
                for (int i = 1; i<=columnCount; i++){
                    entity.put(md.getColumnName(i), rs.getObject(i));
                }
                dataList.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * hive数据
     * @param tableName
     * @param args
     * @return
     */
    @Override
    public List<Map<String, Object>> getHiveTableData(String dbName, String tableName, String... args) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        PreparedStatement pst = null;
        String sql = "select * from "+tableName;
        try {
            conn = HiveJDBC.getHiveConn(dbName);
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                Map<String, Object> entity = new HashMap<String, Object>();
                for (int i = 1; i<=columnCount; i++){
                    entity.put(md.getColumnName(i), rs.getObject(i));
                }
                dataList.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
