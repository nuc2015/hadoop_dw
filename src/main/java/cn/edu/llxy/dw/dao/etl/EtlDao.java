package cn.edu.llxy.dw.dao.etl;

import cn.edu.llxy.dw.dssframework.hibernate4.dao.IBaseDao;

import java.util.List;
import java.util.Map;

public interface  EtlDao extends IBaseDao {
    /**
     * 查询表数据
     * @param tableId
     * @param args
     * @return
     */
    List<Map<String, Object>> getMysqlTableData(String dbName, String tableName, String... args);

    /**
     * 查询表数据
     * @param tableId
     * @param args
     * @return
     */
    List<Map<String, Object>> getHiveTableData(String dbName, String tableName, String... args);
}
