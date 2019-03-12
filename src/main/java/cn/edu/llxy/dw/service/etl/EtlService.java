package cn.edu.llxy.dw.service.etl;

import java.util.List;
import java.util.Map;

public interface EtlService {
    /**
     * 根据表id查询该表的数据
     * @param tableId
     * @return
     */
    List<Map<String, Object>> queryTableData(String tableId, String... args);

    /**
     * 获取转换后的元数据实例
     * @param cls
     * @param id
     * @return
     */
    public Object getMetaInst(Class cls, String id) ;
}
