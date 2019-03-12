package cn.edu.llxy.dw.dss.md.service;

import java.util.List;
import java.util.Map;

import cn.edu.llxy.dw.dss.vo.dg.MetaCoreVo;
import cn.edu.llxy.dw.dss.vo.dg.MetaMetaVo;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.Column;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.DataSource;

import cn.edu.llxy.dw.dss.dc.web.QueryResult;
import cn.edu.llxy.dw.dss.dg.web.entity.ActionResult;
import cn.edu.llxy.dw.dss.dg.web.entity.MetaDiff;
import cn.edu.llxy.dw.dss.dg.web.entity.MetaNode;
import cn.edu.llxy.dw.dss.po.dg.MetaCore;
import cn.edu.llxy.dw.dss.po.dg.Para;

import net.sf.json.JSONArray;

public interface MetaService {

	/**
	 * 
	 * @param dsId
	 * @return
	 */
	public List<MetaCoreVo> querySchema(String dsId) throws Exception;
	

	/**
	 * 获取元数据实体
	 * @param id
	 * @return
	 */
	public MetaCoreVo getMetaVo(String id);
	
	/**
	 * 获取元数据实例
	 * @param id
	 * @return
	 */
	public Object getMetaCore(String id);
	
	/**
	 * 获取元模型实例
	 * @param metaName
	 * @return
	 */
	public MetaMetaVo queryMetaMeta(String metaName) ;
	
	/**
	 * 获取转换后的元数据实例
	 * @param cls
	 * @param id
	 * @return
	 */
	public Object getMetaInst(Class cls, String id) ;
	

	/**
	 * 从数据库查询schema下的所有表或者视图
	 * @param dsId
	 * @param schema
	 * @param type          列席  tables-表   views-视图
	 * @param modelNameRules
	 * @return
	 * @throws Exception
	 */
	public List<MetaCoreVo> queryDsModels(String dsId, String schema, String type, String modelNameRules)
			throws Exception;
	
	/**
	 * 查询分类下面概念模型
	 * @param prtId
	 * @param bizDomain
	 * @return
	 * @throws Exception
	 */
	public List<MetaCoreVo> queryCdmModels(String prtId, String bizDomain) throws Exception;
	
	/**
	 * 查询分类下面物理模型
	 * @param prtId
	 * @param bizDomain
	 * @return
	 * @throws Exception
	 */
	public List<MetaCoreVo> queryPdmModels(String prtId) throws Exception;
	
	/**
	 * 查询表元数据详细信息
	 * @param dsId
	 * @param schema
	 * @param type
	 * @param modelName
	 * @return
	 */
	public MetaNode queryModelInfo(String dsId, String schema, String type, String modelName)throws Exception;
	
	/**
	 * 查询建模模型id
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public MetaNode queryDdlModelInfo(String nodeId) throws Exception;

	
	/**
	 * 查询表字段信息
	 * @param dsId
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Column> queryMyQueryColumns(String dsId, String sql) throws Exception;

	/**
	 *
	 * @param dsId
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public QueryResult queryWrapperDataWithHeader(String dsId, String sql) throws Exception;

	/**
	 *
	 * @param dsId
	 * @param sqlArray
	 * @return
	 * @throws Exception
	 */
	public int[] executeSql(String dsId, String[] sqlArray) throws Exception;

	/**
	 *
	 * @param dsId
	 * @param schema
	 * @param sqlArray
	 * @return
	 * @throws Exception
	 */
	public int[] executeDDLSql(String dsId, String schema, String[] sqlArray) throws Exception;
	

	/**
	 * 导出文件
	 * @param tableName     表名
	 * @param fileName      文件名
	 * @param dsId          数据源id
	 * @param sql           查询sql
	 * @param exportType    导出类型
	 * @throws Exception
	 */
	public void exportSqlData(String tableName, String fileName, String dsId, String sql, String exportType) throws Exception;
	
	/**
	 * 查询子节点
	 * @param parId
	 * @return
	 */
	public List<MetaCoreVo> queryChildrens(String prtId) throws Exception;
	
	/**
	 * 更新物理模型字段信息
	 * @param clms
	 * @param prtId
	 * @param delNodes 
	 * @return
	 * @throws Exception
	 */
	public MetaNode updatePdmModelNode(JSONArray clms, String prtId, String delNodes, JSONArray relations) throws Exception;
	

	/**
	 * 删除节点
	 * @param node
	 * @throws Exception
	 */
	public void delMetaNode(String nodeId)throws Exception;

	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MetaCore getMetaCorePo(String id)throws Exception; ;
	

	/**
	 *
	 * @param metaCore
	 * @param oldName
	 * @throws Exception
	 */
	public void updateMetaCore(MetaCore metaCore, String oldName) throws Exception;
	
	/**
	 * 判断是否存在同名模型
	 * @param prtId
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public boolean sameNameModelExist(String prtId, String modelName, String metaMdl) throws Exception;

	/**
	 * 判断更新模式下模型名称是否重复
	 * @param prtId
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public boolean updSameNameModelExist(String prtId, String modelName, String modelId, String metaMdl) throws Exception;
	
	/**
	 * 查询所有元数据信息
	 * @param prtId
	 * @param metaMdl
	 * @return
	 * @throws Exception
	 */
	public List queryMetaNodes(String prtId, String metaMdl) throws Exception;
	
	/**
	 * 查询选中的模型信息
	 * @param modelNames
	 * @return
	 * @throws Exception
	 */
	public List<MetaNode> querySelectModelInfo(String[] modelNames) throws Exception;
	

	/**
	 * 添加物理模型
	 * @param prtNodeId
	 * @param bizDomain
	 * @param modelName
	 * @param modelClms
	 * @return
	 * @throws Exception
	 */
	public MetaNode addPdmModelNode(String prtNodeId, String modelName, JSONArray modelClms) throws Exception;
	
	/**
	 * 模型另存为
	 * @param prtNodeId
	 * @param modelInfo
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public MetaNode saveAsPdmModelNode(String prtNodeId, JSONArray modelInfo, String modelName) throws Exception;
	
	/**
	 * @param dsInfo
	 * @param models
	 * @return
	 * @throws Exception
	 */
	public List<MetaNode> queryDplModelInfo(String dsInfo, String models) throws Exception;
	
	/**
	 * 比对模型差异
	 * @param dsInfo
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public MetaDiff cmpModelDiff(String dsInfo, String modelId) throws Exception;

	
	/**
	 * 测试数据源是否可以访问
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	public ActionResult checkDsConnect(DataSource ds) throws Exception;
	
	/**
	 * 添加数据源
	 * @param ds            数据源
	 * @param prtId         应用id
	 * @throws Exception
	 */
	public void saveDataSource(DataSource ds, String prtId) throws Exception;

	/**
	 * 查询子节点
	 * @param prtId
	 * @param metaMdl
	 * @return
	 * @throws Exception
	 */
	public List<MetaCore> queryChildrenPo(String prtId, String metaMdl) throws Exception;
	
	/**
	 * Hive数据更新
	 * @param ds
	 * @param cnt
	 * @return
	 * @throws Exception
	 */
	public void executeHiveUpdate(String localFilePath, DataSource ds, String tableName, String cnt) throws Exception;
	
	/**
	 * 查询物理模型字段信息
	 * @param parentId
	 * @param nodeName
	 * @return
	 * @throws Exception
	 */
	public List<String> queryPdmModelClms(String parentId, String nodeName) throws Exception;
	
	/**
	 * 
	 * @param parentId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<String> queryModelNames(String parentId, String type) throws Exception;
	
	/**
	 * 修改数据源
	 * @param ds
	 * @param uid
	 * @throws Exception
	 */
	public void updateDataSource(DataSource ds, String uid) throws Exception;
	
	/**
	 * 删除项目节点，同时删除节点下面所有子节点
	 * @param dsId
	 * @param path
	 * @throws Exception
	 */
	public void deletePrjNode(String nodeId) throws Exception;
	
	/**
	 * 导出建表语句
	 * @param dsInfo
	 * @param models
	 * @param fileName
	 * @throws Exception
	 */
	public void exportDDLSql(String dsInfo, String models, String fileName) throws Exception;

	/**
	 *
	 * @param paraName
	 * @return
	 * @throws Exception
	 */
	public Para getPara(String paraName)  throws Exception;
	
	public Map<String,String> getParas(String[] paraNames) throws Exception;
	
	/**
	 * 添加节点
	 * @param mc
	 * @throws Exception
	 */
	public void addMetaCore(MetaCore mc) throws Exception;

	/**
	 *
	 * @param projectId
	 * @return
	 */
	List<MetaCoreVo> getHiveDatabaseInfo(String projectId);

	/**
	 *
	 * @param database
	 * @param role
	 * @param privilege
	 * @param databaseId
	 * @return
	 */
	List<MetaCoreVo> getHiveTables(String database, String role, String privilege, String databaseId);

	/**
	 *
	 * @param tableId
	 * @return
	 */
	MetaNode getHiveTableColumns(String tableId);

	/**
	 *
	 * @param projectId
	 * @return
	 */
	boolean syncDatabase(String projectId);

	/**
	 * 查询数据源
	 * @param prtId
	 * @param metaMdl
	 * @return
	 * @throws Exception
	 */
	List queryStructureMetaNodes(String prtId, String metaMdl) throws Exception;

	/**
	 *
	 * @param metas
	 * @param prtNodeId
	 * @return
	 * @throws Exception
	 */
	public List<String> importPyhModelToBigtable(List<MetaNode> metas, String prtNodeId) throws Exception;

}
