package cn.edu.llxy.dw.dss.md.service.impl;

import cn.edu.llxy.dw.dss.api.CarnMetaUtil;
import cn.edu.llxy.dw.dss.base.brick.service.IDictService;
import cn.edu.llxy.dw.dss.base.brick.vo.DictCodeMapVo;
import cn.edu.llxy.dw.dss.base.brick.vo.DictVo;
import cn.edu.llxy.dw.dss.dg.web.entity.*;
import cn.edu.llxy.dw.dss.md.dao.MetaDao;
import cn.edu.llxy.dw.dss.md.ftp.Ftp;
import cn.edu.llxy.dw.dss.md.service.MetaService;
import cn.edu.llxy.dw.dss.po.dg.MetaCore;
import cn.edu.llxy.dw.dss.po.dg.MetaMeta;
import cn.edu.llxy.dw.dss.po.dg.MetaRel;
import cn.edu.llxy.dw.dss.po.dg.Para;
import cn.edu.llxy.dw.dss.util.model.resp.RespMessage;
import cn.edu.llxy.dw.dss.vo.adhoc.QueryInfo;
import cn.edu.llxy.dw.dss.vo.cfg.EtlManDbResourceVo;
import cn.edu.llxy.dw.dss.vo.dg.MetaCoreVo;
import cn.edu.llxy.dw.dss.vo.dg.MetaMetaVo;
import cn.edu.llxy.dw.dss.vo.dg.MetaRelVo;
import cn.edu.llxy.dw.dss.vo.dg.meta.core.AbstractBaseObject;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.*;
import cn.edu.llxy.dw.dss.database.EtlDataBaseFactory;
import cn.edu.llxy.dw.dss.database.EtlDataBaseInterface;
import cn.edu.llxy.dw.dss.database.FieldInfoVo;
import cn.edu.llxy.dw.dss.dc.web.QueryResult;
import cn.edu.llxy.dw.dss.hadoop.hive.HiveKbsUitls;
import cn.edu.llxy.dw.dss.md.ftp.FtpUtil;
import cn.edu.llxy.dw.dss.util.CsvWriter;
import cn.edu.llxy.dw.dss.util.DateUtil;
import cn.edu.llxy.dw.dss.util.model.MetaData;
import jxl.CellType;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Label;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.lang.Boolean;
import java.util.List;

@Service(value="metaService")
@Transactional(readOnly = false)
public class MetaServiceImpl implements MetaService {

	private static Map<String, String> operMap;
	
	private final static String[] METAONE_PARAS = new String[]{"RESTful.url","metaone.url","metaone.user","metaone.pwd", "meta.mdl.domain"
			,"meta.mdl.pdm","meta.mdl.pdm.clm","meta.mdl.pdm.rel","meta.mdl.ldm","meta.mdl.ldm.clm","meta.mdl.ldm.rel"};
	
	private static Map<String, WritableCellFormat> exportXlsFmt = new HashMap();

	@Resource
    MetaDao metaDao;
	
	@Resource(name = "dictService")
	private IDictService dictService;
	
	@Autowired
	private EtlDataBaseFactory etlDataBaseFactory;
	
	@PostConstruct
	public void init() {
		if (operMap == null) {
			operMap = new HashMap();
		}
		operMap.put("lt", "<");
		operMap.put("gt", ">");
		operMap.put("le", "<=");
		operMap.put("ge", ">=");
		operMap.put("eq", "=");
		operMap.put("ne", "<>");
		operMap.put("in", "in");
		operMap.put("nin", "not in");
		operMap.put("bt", "between");
		operMap.put("null", "is null");
		operMap.put("nnull", "is not null");
		operMap.put("like", "like");
		operMap.put("nlike", "not like");
		operMap.put("EQUAL", "=");
		operMap.put("=", "=");
		operMap.put("LESS_THAN", "<");
		operMap.put("NOT_EQUAL", "<=");
		
		operMap.put("LESS_THAN_OR_EQUAL", "<=");
		operMap.put("GREATER_THAN", ">");
		operMap.put("GREATER_THAN_OR_EQUAL", ">=");
		operMap.put("NOT_EQUAL", "<=");
		operMap.put("NULL", "is null");
		operMap.put("NOT_NULL", "is not null");
		operMap.put("EMPTY", "=");
		operMap.put("NOT_EMPTY", "<>");
		
		operMap.put("starts_with", "like");
		operMap.put("starts_with_case_sensitive", "like");
		operMap.put("ends_with", "like");
		operMap.put("ends_with_case_sensitive", "like");
	}

	public Map<String, String> getOperMap() {
		return this.operMap;
	}
	

	/**
	 * 获取元数据实例
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Object getMetaCore(String id) {
		MetaCore cm = (MetaCore)metaDao.get(MetaCore.class, id);
		return wrapperObj(cm);
	}

	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MetaCore getMetaCorePo(String id) throws Exception {
		MetaCore cm = (MetaCore)metaDao.get(MetaCore.class, id);
		return cm;
	}
	

	/**
	 *
	 * @param metaCore
	 * @param oldName
	 * @throws Exception
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateMetaCore(MetaCore metaCore, String oldName) throws Exception {
		metaDao.update(metaCore);
	}

	/**
	 *
	 * @param metaName
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MetaMetaVo queryMetaMeta(String metaName) {
		Object mm = metaDao.get(MetaMeta.class, metaName);
		if(mm != null){
			MetaMetaVo mmv = new MetaMetaVo();
			try {
				BeanUtils.copyProperties(mm, mmv);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mmv;
		}
		else return null;
	}

	/**
	 *
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	public String[] queryDsSchemas(DataSource ds) throws Exception {
		EtlManDbResourceVo dbvo =  getEtlManDbResourceVo(ds);
		EtlDataBaseInterface database = null;
		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			database.connect();

			List<String> schemaList = new ArrayList();
			if(ds.getType().equals("Mysql")){
				schemaList.add(ds.getServerName());
			}
			else if(ds.getType().equals("Oracle")){
				schemaList.add(ds.getUser());
			}else{
				String serverName = ds.getServerName();
				if(serverName.indexOf(";") > 0){
					serverName = serverName.substring(0, serverName.indexOf(";"));
				}
				schemaList.add(serverName);
			}
			return schemaList.toArray(new String[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(database != null){
				database.disconnect();
			}
		}
		return null;
	}

	/**
	 * 获取转换后的元数据实例
	 * @param cls
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.NEVER)
	public Object getMetaInst(Class cls, String id) {
		MetaCore cm = (MetaCore)metaDao.get(MetaCore.class, id);
		if(cm == null)
			return null;
		else{
			try {
				Object obj = cls.newInstance();
				
				BeanUtils.copyProperties(cm, obj);
				
				//处理RefObject
				if(cm.getRefObject() != null){
					Object refInstance =  getMetaCore(cm.getRefObject());
						
					Method setRefObjectMtd = getDeclaredMethod(obj, "setRefObject", new Class[] {AbstractBaseObject.class});
					//		cls.getMethod("setRefObject", new Class[] {AbstractBasePackage.class});
					if(setRefObjectMtd != null)
						fillRefObj(obj,getDeclaredSpField(obj, "refObject") ,setRefObjectMtd,refInstance);
				}
				
				//处理json对象
				if(cm.getEntity()!= null && !cm.getEntity().equals("null")){
					JSONObject jasonObject = JSONObject.fromObject(cm.getEntity());
					Map<String, Object> map2 = (Map) jasonObject;
					
					Field[] myDeclaredFields = cls.getFields();
					Field field;Object value ;
					for(int i=0; i<myDeclaredFields.length; i++){
						field = myDeclaredFields[i];
						String firstLetter = null;String setMethodName;Method method;
						if(!field.getName().equals("META_MDL") && !field.getName().equals("MD_TYPE_NORMAL")
								&& !field.getName().equals("MD_TYPE_REFTABLE")
								&& !field.getName().equals("DS_TYPE_MYSQL")
								&& !field.getName().equals("MD_TYPE_DICT"))
						{
							field.setAccessible(true);
							value = map2.get(field.getName());
										
							firstLetter = field.getName().substring(0, 1).toUpperCase();
							setMethodName = "set" + firstLetter
				                   + field.getName().substring(1);
								
							method = cls.getMethod(setMethodName, new Class[] { field.getType() });
							fill(obj,field,method,value+"");
						}
					}
					//return obj;
				}
				return obj;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 填充RefObject属性
	 * @param bean
	 * @param field
	 * @param method
	 * @param value
	 */
	private static void fillRefObj(Object bean, Field field, Method method,
			Object value) {
		if (value == null)
			return;
		Object[] oo = new Object[1];
		
		oo[0] = value;
		try {
			method.invoke(bean, oo);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 
     * 将字符串值转换为合适的值填充到对象的指定域 
     * @param bean 被填充的java bean 
     * @param field 需要填充的域 
     * @param value 字符串值 
     */ 
	public static void fill(Object bean, Field field, Method method,
			String value) {
		if (value == null || "null".equalsIgnoreCase(value))
			return;

		try {
			Object[] oo = new Object[1];

			String type = field.getType().getName();

			if ("java.lang.String".equals(type)) {
				oo[0] = value;
			} else if ("java.lang.Integer".equals(type) || "int".equals(type)) {
				if (value.length() > 0)
					oo[0] = Integer.valueOf(value);
			} else if ("java.lang.Float".equals(type) || "float".equals(type)) {
				if (value.length() > 0)
					oo[0] = Float.valueOf(value);
			} else if ("java.lang.Double".equals(type) || "double".equals(type)) {
				if (value.length() > 0)
					oo[0] = Double.valueOf(value);
			} else if ("java.math.BigDecimal".equals(type)) {
				if (value.length() > 0)
					oo[0] = new BigDecimal(value);
			} else if ("java.util.Date".equals(type)) {
				if (value.length() > 0)
					oo[0] = DateUtil.parseDate(value, "yyyy-mm-dd hh:mi:ss")
					;
			} else if ("java.sql.Timestamp".equals(type)) {
				if (value.length() > 0)
					oo[0] = DateUtil.parseDate(value, "yyyy-mm-dd hh:mi:ss");
			} else if ("java.lang.Boolean".equals(type) || "boolean".equals(type)) {
				if (value.length() > 0)
					oo[0] = Boolean.valueOf(value);
			} else if ("java.lang.Long".equals(type)) {
				if (value.length() > 0)
					oo[0] = Long.valueOf(value);
			}

			method.invoke(bean, oo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取方法
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	private static Method getDeclaredMethod(Object object, String methodName,
			Class<?>... parameterTypes) {
		Method method = null;

		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
				if (method != null)
					return method;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return method;
	}
	
	/**
	 * 获取字段定义
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static Field getDeclaredSpField(Object object, String fieldName) {
		Field field = null;

		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				if (field != null)
					return field;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return field;
	}
	
	/**
	 * 对象转换，将公告MetaCore对象转换为具体实体对象
	 * @param cm
	 * @return
	 */
	private Object wrapperObj(MetaCore cm){
		MetaMetaVo mm = queryMetaMeta(cm.getMetaMdl());
		Class cls = null;
		try {
			cls = Class.forName(mm.getClz());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(cm == null)
			return null;
		else{
			try {
				Object obj = cls.newInstance();
				
				BeanUtils.copyProperties(cm, obj);
				
				//处理RefObject
				if(cm.getRefObject() != null){
					Object refInstance =  getMetaCore(cm.getRefObject());
						
					//Method setRefObjectMtd = cls.getMethod("setRefObject", new Class[] {AbstractBasePackage.class});
					Method setRefObjectMtd = getDeclaredMethod(obj, "setRefObject", new Class[] {AbstractBaseObject.class});
					
					if(setRefObjectMtd != null)
						fillRefObj(obj,getDeclaredSpField(obj, "refObject"),setRefObjectMtd,refInstance);
				}
				
				//处理json对象
				if(cm.getEntity() != null && !cm.getEntity().equals("null")){
					JSONObject jasonObject = JSONObject.fromObject(cm.getEntity());
					Map<String, Object> map2 = (Map) jasonObject;
					
					Field[] myDeclaredFields = cls.getFields();
					Field field;Object value ;
					for(int i=0; i<myDeclaredFields.length; i++){
						field = myDeclaredFields[i];
						String firstLetter = null;String setMethodName;Method method;
						if(field!= null && !field.getName().equals("META_MDL") && !field.getName().equals("MD_TYPE_NORMAL")
								&& !field.getName().equals("MD_TYPE_REFTABLE") && !field.getName().equals("DS_TYPE_MYSQL")&& !field.getName().equals("MD_TYPE_DICT"))
						{
							field.setAccessible(true);
							value = map2.get(field.getName());
										
							firstLetter = field.getName().substring(0, 1).toUpperCase();
							setMethodName = "set" + firstLetter
				                      + field.getName().substring(1);
								
							method = cls.getMethod(setMethodName, new Class[] { field.getType() });
							fill(obj,field,method,value+"");
						}
					}
				}
				return obj;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}


	/**
	 *
	 * @param dsId
	 * @return
	 * @throws Exception
	 */
	public List<MetaCoreVo> querySchema(String dsId) throws Exception {
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);
		List<MetaCoreVo> ret = new ArrayList();

		String[] schemas = queryDsSchemas(ds);
		for(String schema:schemas){
			MetaCoreVo mcv = new MetaCoreVo();
			mcv.setId(dsId + "."+schema);
			mcv.setType("relation.Schema");
			mcv.setName(schema);
			mcv.setMetaMdl("relation.Schema");
			mcv.setPrtId(dsId);
			ret.add(mcv);
		}
		
		return ret;
	}

	/**
	 * 查询建模模型id
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	@Override
	public MetaNode queryDdlModelInfo(String nodeId) throws Exception {
		MetaCore meta = (MetaCore)metaDao.get(MetaCore.class, nodeId);
		
		MetaNode node = new MetaNode();
		
		List<String> modles = metaDao.queryByHql("select mc.name from MetaCore mc where mc.metaMdl=? and mc.prtId=?",meta.getMetaMdl(), meta.getPrtId());
		node.setModels(modles.toArray(new String[modles.size()]));
		
		List<MetaCore> columns = metaDao.queryByHql("from MetaCore mc where mc.metaMdl=? and mc.prtId=?", meta.getMetaMdl()+"Clm", nodeId);
		
		List<Column> retColumns = new ArrayList();
		for(MetaCore column:columns){
			Column c = new Column();
			BeanUtils.copyProperties(column, c);
			
			//处理json对象
			if(column.getEntity() != null && !column.getEntity().equals("null")){
				JSONObject jasonObject = JSONObject.fromObject(column.getEntity());
				Map<String, Object> map2 = (Map) jasonObject;
				
				Field[] myDeclaredFields = Column.class.getFields();
				Field field;Object value ;
				for(int i=0; i<myDeclaredFields.length; i++){
					field = myDeclaredFields[i];
					String firstLetter = null;String setMethodName;Method method;
					if(field!= null && !field.getName().equals("META_MDL") && !field.getName().equals("MD_TYPE_NORMAL")
							&& !field.getName().equals("MD_TYPE_REFTABLE")&& !field.getName().equals("MD_TYPE_DICT"))
					{
						field.setAccessible(true);
						value = map2.get(field.getName());
									
						firstLetter = field.getName().substring(0, 1).toUpperCase();
						setMethodName = "set" + firstLetter
			                      + field.getName().substring(1);
							
						method = Column.class.getMethod(setMethodName, new Class[] { field.getType() });
						fill(c,field,method,value+"");
					}
				}
			}
			
			if(c.getType() == null || c.getType().equals("null")){
				c.setType("");
			}
			
			if(c.getChName() == null || c.getChName().equals("null")){
				c.setChName("");
			}
			if(c.getName() == null || c.getName().equals("null")){
				c.setName("");
			}
			
			if(c.getRemarks() == null || c.getRemarks().equals("null")){
				c.setRemarks("");
			}
			
			if(c.getCmt() == null || c.getCmt().equals("null")){
				c.setCmt("");
			}
			
			
			retColumns.add(c);
		}
		
		List<MetaRel> relations = metaDao.queryByHql("from MetaRel rel where rel.src=?", nodeId);
		
		List<MetaRelVo> relationVos = new ArrayList();
 		for(MetaRel relation:relations){
 			MetaRelVo relVo = new MetaRelVo();
 			BeanUtils.copyProperties(relation, relVo);
 			relationVos.add(relVo);
		}
 		node.setRelations(relationVos);
		
		node.setName(meta.getName());
		node.setChName(meta.getChName());
		node.setMsg(meta.getCmt());
		
		node.setColumns(retColumns);
		
		return node;
	}

	/**
	 *
	 * @param modelNames
	 * @return
	 * @throws Exception
	 */
	public List<MetaNode> querySelectModelInfo(String[] modelNames) throws Exception{
		if(modelNames.length > 0){
			String[] modelInfo = modelNames[0].split("\\.");
			DataSource ds = (DataSource)getMetaInst(DataSource.class, modelInfo[0]);

			EtlManDbResourceVo dbvo =  getEtlManDbResourceVo(ds);
			EtlDataBaseInterface database = null;
			try {
				database = etlDataBaseFactory.getDatabase(dbvo);
				database.connect();

				DatabaseMetaData dbmd = database.getConnection().getMetaData();

				String[] modelTmpArr;ResultSet rs,pkRs;List<String> pkColumns;

				List<MetaNode> metaNodes = new ArrayList();
				for(String modelName:modelNames){
					modelTmpArr = modelName.split("\\.");

					MetaNode node = new MetaNode();
					node.setName(modelTmpArr[2]);

					rs = dbmd.getColumns(null, modelTmpArr[1], modelTmpArr[2], null);
					pkRs = dbmd.getPrimaryKeys(null, modelTmpArr[1], modelTmpArr[2]);
					List<Key> keys = new ArrayList();

					pkColumns = new ArrayList();
					while(pkRs.next()){
						pkColumns.add(pkRs.getString("COLUMN_NAME"));
					}
					List<Column> columns = new ArrayList();
					int nullable;

					if(ds.getType().toLowerCase().indexOf("hive") >= 0){
						while(rs.next()){
							Column column = new Column();
							column.setName(rs.getString("COLUMN_NAME"));
							column.setLength(rs.getInt("COLUMN_SIZE"));
							if(rs.getString("TYPE_NAME").toLowerCase().equals("string")){
								column.setType("varchar");
								column.setLength(255);
							}else{
								column.setType(rs.getString("TYPE_NAME"));
							}

							column.setPrecision(rs.getInt("DECIMAL_DIGITS"));
							column.setChName(rs.getString("REMARKS"));
							column.setCmt(rs.getString("REMARKS"));

							nullable = rs.getInt("NULLABLE");
							if(nullable == 0){
								column.setNullable(false);
							}else{
								column.setNullable(true);
							}
							if(pkColumns.contains(column.getName())){
								column.setPk(true);
							}

							columns.add(column);
						}
					}else{
						while(rs.next()){
							Column column = new Column();
							column.setName(rs.getString("COLUMN_NAME"));
							column.setType(rs.getString("TYPE_NAME"));

							column.setLength(rs.getInt("COLUMN_SIZE"));
							column.setPrecision(rs.getInt("DECIMAL_DIGITS"));
							column.setChName(rs.getString("REMARKS"));
							column.setCmt(rs.getString("REMARKS"));

							nullable = rs.getInt("NULLABLE");
							if(nullable == 0){
								column.setNullable(false);
							}else{
								column.setNullable(true);
							}

							if(pkColumns.contains(column.getName())){
								column.setPk(true);
							}

							columns.add(column);
						}
					}

					node.setColumns(columns);

					metaNodes.add(node);
				}
				return metaNodes;
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}finally{
				if(database != null){
					database.disconnect();
				}
			}
		}else{
			return null;
		}
	}

	/**
	 * 查询表元数据详细信息
	 * @param dsId
	 * @param schema
	 * @param type
	 * @param modelName
	 * @return
	 */
	@Override
	public MetaNode queryModelInfo(String dsId, String schema, String type, String modelName) throws Exception{
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);

		EtlManDbResourceVo dbvo =  getEtlManDbResourceVo(ds);
		EtlDataBaseInterface database = null;
		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			database.connect();

			if(schema.indexOf("?")> 0){
				schema = schema.substring(0, schema.indexOf("?"));
			}

			MetaNode node = new MetaNode();
			node.setDbType(ds.getType());
			if("table".equals(type)){
				node.setMeta(queryMetaMeta("relation.Table"));
			}else if("view".equals(type)){
				node.setMeta(queryMetaMeta("relation.View"));
			}

			MetaProp propName = new MetaProp();
			propName.setKey("名称");
			propName.setValue(modelName);
			node.setProperties(new ArrayList());
			node.getProperties().add(propName);

			DatabaseMetaData dbmd = database.getConnection().getMetaData();

			if("Oracle".equals(ds.getType())){
				schema = schema.toUpperCase();
			}

			ResultSet rs = dbmd.getColumns(null, schema, modelName, null);

			ResultSet pkRs = dbmd.getPrimaryKeys(null, schema, modelName);
			List<Key> keys = new ArrayList();

			List<String> pkColumns = new ArrayList();
			while(pkRs.next()){
				pkColumns.add(pkRs.getString("COLUMN_NAME"));
			}

			ResultSet impRs = dbmd.getImportedKeys(null, schema, modelName);
			while(impRs.next()){
				Key key = new Key();
				key.setName(impRs.getString("FK_NAME"));

				key.setFkClm(impRs.getString("FKCOLUMN_NAME"));
				key.setRefSchema(impRs.getString("PKTABLE_CAT"));
				key.setRefTable(impRs.getString("PKTABLE_NAME"));
				key.setRefClms(impRs.getString("PKCOLUMN_NAME"));
				key.setUpdateRule(impRs.getString("UPDATE_RULE"));
				key.setDeleteRule(impRs.getString("DELETE_RULE"));

				key.setType("外键");
				keys.add(key);
			}
			node.setKeys(keys);

			if("HIVE2KBS".equals(ds.getType()) || "HIVE2".equals(ds.getType())){

			}else{
				ResultSet idxRs = dbmd.getIndexInfo(null, schema, modelName, false, false);
				List<Index> indexs = new ArrayList();
				while(idxRs.next()){
					Index idx = new Index();
					idx.setName(idxRs.getString("INDEX_NAME"));
					idx.setClms(idxRs.getString("COLUMN_NAME"));
					idx.setType(idxRs.getString("NON_UNIQUE"));
					idx.setIdxFun(idxRs.getString("TYPE"));

					indexs.add(idx);
				}
				node.setIndex(indexs);
			}

			List<Column> columns = new ArrayList();
			int nullable;
			while(rs.next()){
				Column column = new Column();
				column.setName(rs.getString("COLUMN_NAME"));
				column.setType(rs.getString("TYPE_NAME"));
				column.setLength(rs.getInt("COLUMN_SIZE"));
				column.setPrecision(rs.getInt("DECIMAL_DIGITS"));
				column.setChName(rs.getString("REMARKS")==null?"":rs.getString("REMARKS"));
				column.setCmt(rs.getString("REMARKS")==null?"":rs.getString("REMARKS"));

				nullable = rs.getInt("NULLABLE");
				if(nullable == 0){
					column.setNullable(false);
				}else{
					column.setNullable(true);
				}

				if(pkColumns.contains(column.getName())){
					column.setPk(true);
				}

				columns.add(column);
			}
			node.setColumns(columns);
			return node;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(database != null){
				database.disconnect();
			}
		}
		return null;
	}

	/**
	 * 从数据库查询schema下的所有表或者视图
	 * @param dsId
	 * @param schema
	 * @param type          列席  tables-表   views-视图
	 * @param modelNameRules
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MetaCoreVo> queryDsModels(String dsId, String schema, String type, String modelNameRules)
			throws Exception {
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);

		if(dsId.indexOf("hive")>-1){
			return getHiveTables(dsId.split("\\|")[0], dsId.split("\\|")[1], dsId.split("\\|")[2], "");
		}
		EtlManDbResourceVo dbvo =  getEtlManDbResourceVo(ds);
		EtlDataBaseInterface database = null;
		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			database.connect();

			if(schema.indexOf("?")> 0){
				schema = schema.substring(0, schema.indexOf("?"));
			}

			List<MetaCoreVo> models = new ArrayList();String[] tableNameArray;

			if("tables".equals(type)){
				String[] tables = database.getTablenames(schema, modelNameRules, true);
				for(String tableName:tables){
					MetaCoreVo mv = new MetaCoreVo();
					mv.setName(tableName);

					mv.setMetaMdl("relation.Table");
					tableNameArray = tableName.split("\\.");
					//临时用
					if(tableNameArray.length == 2){
						mv.setName(tableNameArray[1]);
						mv.setFileName(tableNameArray[1]);

						mv.setId(dsId+"."+schema+"."+tableNameArray[1]);
					}
					else{
						mv.setFileName(tableName);
					}
					models.add(mv);
				}
			}
			if("views".equals(type)){
				String[] views  = database.getViews(schema, modelNameRules, true);
				for(String viewName:views){
					MetaCoreVo mv = new MetaCoreVo();
					mv.setName(viewName);
					mv.setMetaMdl("relation.View");

					tableNameArray = viewName.split("\\.");
					//临时用
					if(tableNameArray.length == 2){
						mv.setName(tableNameArray[1]);
						mv.setFileName(tableNameArray[1]);
						mv.setId(dsId+"."+schema+"."+tableNameArray[1]);
					}
					else
						mv.setFileName(viewName);

					models.add(mv);
				}
			}
			return models;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(database != null){
				database.disconnect();
			}
		}
		return null;
	}

	/**
	 * 查询表字段信息
	 * @param dsId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Column> queryMyQueryColumns(String dsId, String sql) throws Exception {
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);
		
		Boolean userHasModelPmn = true;
		
		List<MetaCore> clmMetas = null;
		List<Column> columns = queryQueryMetas(ds, sql);
		
		return columns;
	}
	
	public List<Column> queryQueryMetas(DataSource ds, String sql){
		EtlManDbResourceVo dbvo =  getEtlManDbResourceVo(ds);
		
		EtlDataBaseInterface database = null;
		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			database.connect();
			
			//查询数据库中实际的字段信息
			String schema = null;
			if("Oracle".equals(ds.getType()))
			{
				schema = ds.getUser();
				schema = schema.toUpperCase();
			}else if("Mysql".equals(ds.getType())){
				schema = ds.getServerName();
			}
			List<FieldInfoVo> fields = database.getQuerySqlFields(sql, schema);
			List<MetaCore> columns = null;
			
			List<Column> clmMetas = wrapperDbFiledsToMetaCore(fields, columns);
			return clmMetas;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(database != null){
				database.disconnect();
			}
		}
		return null;
	}

	
	private List<Column> wrapperDbFiledsToMetaCore(List<FieldInfoVo> fields, List<MetaCore> columns){
		List<Column> ret = new ArrayList();
		for(FieldInfoVo fiv:fields){
			ret.add(formatColumn(mergeMetadata(fiv, columns)));
		}
		return ret;
	}
	
	private Column formatColumn(Column c){
		if(c != null){
			if(c.getMgrType() == null || c.getMgrType().trim().equals("")){
				c.setMgrType("-");
			}else if(c.getMgrType().equals("d")){
				c.setMgrType("维度");
			}else if(c.getMgrType().equals("k")){
				c.setMgrType("指标");
			}
		}
		return c;
	}
	
	/**
	 * 对表字段元数据进行合并操作
	 * @param dbField
	 * @param metaColumns
	 * @return
	 */
	private Column mergeMetadata(FieldInfoVo dbField, List<MetaCore> metaColumns){
		if(dbField.getComments() != null){
			if(dbField.getComments().indexOf(".") > 0){
				dbField.setComments(dbField.getComments().substring(dbField.getComments().indexOf(".")+1));
			}
		}
		
		//表字段没有在元数据系统中
		if(metaColumns == null || metaColumns.isEmpty()){
			MetaCoreVo mv = new MetaCoreVo();
			mv.setName(dbField.getName());
			mv.setCmt(dbField.getComments());
			mv.setType(dbField.getOriginalColumnTypeDesc());
			mv.setDataType(dbField.getType()+"");
			mv.setPrecision(dbField.getPrecision());
			mv.setLength(dbField.getLength());
			
			Column c = new Column();
			try {
				BeanUtils.copyProperties(mv, c);
				c.setTitle(dbField.getComments());
				c.setId(mv.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.setFieldInMetas(false);
			c.setTableName(dbField.getTableName());
			c.setPk(dbField.isPk());
			
			return c;
		}else{
			boolean fieldInMetas = false;
			for(MetaCore mc:metaColumns){
				//如果字段在元数据信息中，则从元数据中获取字段信息
				if(mc.getName().toLowerCase().equals(dbField.getName().toLowerCase())){
					fieldInMetas = true;
					
					Column c = new Column();
					try {
						BeanUtils.copyProperties(mc, c);
						c.setId(mc.getId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					c.setFieldInMetas(true);
					return c;
				}
			}
			if(fieldInMetas == false){
				MetaCoreVo mv = new MetaCoreVo();
				mv.setName(dbField.getName());
				mv.setCmt(dbField.getComments());
				mv.setType(dbField.getOriginalColumnTypeDesc());
				mv.setDataType(dbField.getType()+"");
				mv.setPrecision(dbField.getPrecision());
				mv.setLength(dbField.getLength());
				
				Column c = new Column();
				try {
					BeanUtils.copyProperties(mv, c);
					c.setTitle(dbField.getComments());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.setFieldInMetas(false);
				
				return c;
			}
			return null;
		}
	}
	

	/**
	 *
	 * @param dbType
	 * @param precision
	 * @param value
	 * @return
	 */
	public WritableCellFormat getDataCellFormat(int dbType, int precision, Object value) {
		String sdbType = "";
		if(dbType ==  Types.NUMERIC){
			if(value != null && value.toString().indexOf(".") > 0){
				String[] valueArray = value.toString().split("\\.");
				if(valueArray.length > 1){
					sdbType = dbType + ":" + valueArray[1].length();
				}
			}else{
				sdbType = dbType + ":" + precision;
			}
		}else{
			sdbType = dbType+"";
		}
		
		if(exportXlsFmt.get(sdbType) == null){
			CellType type = null;
			WritableCellFormat wcf = null;
			try {
			if (dbType == Types.VARCHAR || dbType == Types.CHAR
					|| dbType == Types.CLOB) {
				WritableFont wf = new WritableFont(WritableFont.TIMES, 10,
						WritableFont.NO_BOLD, false);// 最后一个为是否italic
				wcf = new WritableCellFormat(wf);
				wcf.setAlignment(Alignment.CENTRE);
			} else if (dbType == Types.BIGINT
					|| dbType == Types.TINYINT
					|| dbType == Types.SMALLINT
					|| dbType == Types.BIGINT) {
				// type = CellType.NUMBER;
				NumberFormat nf = new NumberFormat("#");
				wcf = new WritableCellFormat(nf);
				wcf.setAlignment(Alignment.RIGHT);
			}else if(dbType == Types.NUMERIC){
				String fmt = "#.";
				
				if(value != null && value.toString().indexOf(".")>0){
					String[] xvalue = value.toString().split("\\.");
					if(xvalue.length>1){
						precision = xvalue[1].length();
					}
				}
				for(int i=0; i<precision; i++){
					fmt += "0";
				}
				
				NumberFormat nf = new NumberFormat(fmt);
				wcf = new WritableCellFormat(nf);
				wcf.setAlignment(Alignment.RIGHT);
			}else if (dbType == Types.FLOAT
					|| dbType == Types.DOUBLE
					|| dbType == Types.DECIMAL) {
				NumberFormat nf = new NumberFormat("#.00");
				wcf = new WritableCellFormat(nf);
				wcf.setAlignment(Alignment.RIGHT);
			} else if (dbType == Types.DATE
					|| dbType == Types.TIME
					|| dbType == Types.TIMESTAMP) {
				DateFormat df = new DateFormat(
						"yyyy-MM-dd hh:mm:ss");
				wcf = new WritableCellFormat(df);
			} else {
				WritableFont wf = new WritableFont(WritableFont.TIMES, 10,
						WritableFont.NO_BOLD, false);// 最后一个为是否italic
				wcf = new WritableCellFormat(wf);
			}
				wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
				// 边框
				wcf.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.GRAY_25);
				wcf.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.GRAY_25);
				wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.GRAY_25);
				wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.GRAY_25);
				// 背景色
				wcf.setBackground(Colour.WHITE);
				//wcf.setWrap(true);// 自动换行
				
				exportXlsFmt.put(sdbType, wcf);
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		return exportXlsFmt.get(sdbType);
	}

	/**
	 *
	 * @param sheet
	 */
	public void initialSheetSetting(WritableSheet sheet) {
		try {
			sheet.getSettings().setDefaultColumnWidth(10); // 设置列的默认宽度
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param sheet
	 * @param col
	 * @param row
	 * @param data
	 * @param format
	 */
	public void insertOneCellData(WritableSheet sheet, Integer col,
			Integer row, Object data, WritableCellFormat format) {
		try {
			if (data instanceof Double) {
				jxl.write.Number labelNF = new jxl.write.Number(col, row,
						(Double) data, format);
				sheet.addCell(labelNF);
			} else if (data instanceof BigDecimal) {
				jxl.write.Number labelNF = new jxl.write.Number(col, row,
						new Double(data.toString()), format);
				sheet.addCell(labelNF);
			}else if (data instanceof Integer) {
				jxl.write.Number labelNF = new jxl.write.Number(col, row,
						(Integer) data, format);
				sheet.addCell(labelNF);
			}
			else if (data instanceof Boolean) {
				jxl.write.Boolean labelB = new jxl.write.Boolean(col, row,
						(Boolean) data, format);
				sheet.addCell(labelB);
			} else if (data instanceof Timestamp || data instanceof Date|| data instanceof java.sql.Date) {
				DateTime labelDT = new DateTime(col, row,(Date) data, format);
				sheet.addCell(labelDT);
			} else {
				if(data == null){
					data = "";
				}
				Label label = new Label(col, row, data.toString(), format);
				sheet.addCell(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出文件
	 * @param tableName     表名
	 * @param fileName      文件名
	 * @param dsId          数据源id
	 * @param sql           查询sql
	 * @param exportType    导出类型
	 * @throws Exception
	 */
	public void exportSqlData(String tableName, String fileName, String dsId, String sql, String exportType) throws Exception{
		QueryInfo info = queryExportData(tableName, dsId, sql);
		
		if("txt".equals(exportType)){
			FileWriter fos = null;BufferedWriter bw = null;
			try{
			File file = new File(fileName);
			fos=new FileWriter(file);
			bw=new BufferedWriter(fos);
			
			StringBuffer strb = null;
			int lastIdx = info.getPlainValue().get(0).length - 1;
			for(Object[] data:info.getPlainValue()){
				strb=new StringBuffer();
				
				for(int i=0; i<data.length; i++){
					strb.append(data[i]);
					if(i == lastIdx){
						
					}else{
						strb.append(",");
					}
				}
				bw.write(new String(strb.toString().getBytes("UTF-8"),"UTF-8"));
				bw.write("\r\n");//写入换行
			}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(bw != null){
					bw.close();
				}
				if(fos != null){
					fos.close();
				}
			}
		}else if ("xls".equals(exportType)){
			Integer[] clmType = info.getColTypes();Integer[] precision = info.getPrecision();
			WritableWorkbook workbook = null;
			try {
				OutputStream os = new FileOutputStream(fileName);  
				workbook = Workbook.createWorkbook(os);  
				WritableSheet sheet = workbook.createSheet("sheet 1", 0); 
				initialSheetSetting(sheet);
				Label label;Object[] tmp;  
				int row = 0;
				// 查询SQL报表模板信息
				
				for(int i=0; i<info.getPlainValue().size(); i++){
					//行数据
					tmp = info.getPlainValue().get(i);
					for(int j =0; j<tmp.length; j++){
						//按一列一列的方式插入至每行
						if(clmType == null || precision == null){
							insertOneCellData(sheet, j, i + row, tmp[j],  
									getDataCellFormat(12,0,tmp[j]));
						}else{
							insertOneCellData(sheet, j, i + row, tmp[j],  
								getDataCellFormat(clmType[j],precision[j],tmp[j]));
						}
					}
				}
				workbook.write();
			}catch (Exception e) {  
				e.printStackTrace();  
			}finally{
				if(workbook != null){
					workbook.close();
				}
			}
		}else if("csv".equals(exportType)){
			CsvWriter wr =new CsvWriter(fileName,',',Charset.forName("UTF-8"));
			for(int i=0; i<info.getPlainValue().size(); i++){
				wr.writeRecord(info.getPlainValue().get(i)); 
			}
			wr.close();
		}
	}
	
	private QueryInfo queryExportData(String tableName, String dsId, String sql){
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);
		String schema = "";
		EtlManDbResourceVo dbvo = getEtlManDbResourceVo(ds);
		EtlDataBaseInterface database = null;

		QueryInfo info = new QueryInfo();
		info.setReportName(tableName);

		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			database.connect();

			Statement stmt = database.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData resultMetas = rs.getMetaData();
			int colCount = resultMetas.getColumnCount();

			int crt = 0;List<Object[]> rows = new ArrayList();
			while (rs.next()) {
				if (crt == 0) {
					Object[] head = new Object[colCount];
					Integer[] clmTypes = new Integer[colCount];
					Integer[] precision = new Integer[colCount];
					for (int i = 1; i <= colCount; i++) {
						head[i - 1] = resultMetas.getColumnName(i);
						clmTypes[i - 1] = resultMetas.getColumnType(i);
						precision[i - 1] = resultMetas.getScale(i);
					}
					info.setColTypes(clmTypes);
					info.setPrecision(precision);

					rows.add(head);
				}
				Object[] row = new Object[colCount];
				for (int i = 1; i <= colCount; i++) {
					row[i - 1] = rs.getObject(i);
				}
				rows.add(row);
				crt ++;
			}

			info.setPlainValue( database.getRowsWithHeader(sql, -1));

			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(database != null){
				try {
					database.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 *
	 * @param dsId
	 * @param sqlArray
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int[] executeSql(String dsId, String[] sqlArray) throws Exception {
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);
		
		EtlManDbResourceVo dbvo = getEtlManDbResourceVo(ds);
		EtlDataBaseInterface database = null;
		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			
			database.connect();
			Statement st=database.getConnection().createStatement();
			
			int[] querResults = null;
			if("HIVE2KBS".equals(ds.getType()) || "HIVE".equals(ds.getType())){
				
				querResults = new int[sqlArray.length];
				for(int i=0; i<sqlArray.length; i++){
					querResults[i] = st.executeUpdate(sqlArray[i]);
				}
			}else{
				for(int i=0; i<sqlArray.length; i++){
					if(sqlArray[i] != null && !sqlArray[i].trim().equals(""))
					st.addBatch(sqlArray[i]);
				}
				querResults = st.executeBatch();
			}
			return querResults;
		} catch (Exception e) {
			e.printStackTrace();
			
			database.rollback();
			throw e;
		}finally{
			if(database != null){
				try {
					database.disconnect();
				} catch (Exception e) {
					throw e;
				}
			}
		}
	}
	
	/**
	 *
	 * @param dsId
	 * @param schema
	 * @param sqlArray
	 * @return
	 * @throws Exception
	 */
	@Override
	public int[] executeDDLSql(String dsId, String schema, String[] sqlArray) throws Exception {
		String tmpTableInfo;
		//if(dsId.indexOf("|")>-1 && dsId.indexOf("hive")>-1){
		if(true){
			int[] querResults = new int[sqlArray.length];
			for(int i=0; i<sqlArray.length; i++){
				if(HiveKbsUitls.execNonQuerySql(sqlArray[i])){
					querResults[i] = 1;
				}
			}
			return querResults;
		}

		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);

		EtlManDbResourceVo dbvo = getEtlManDbResourceVo(ds);
		dbvo.setDbSchema(schema);
		EtlDataBaseInterface database = null;
		try {
			database = etlDataBaseFactory.getDatabase(dbvo);

			database.connect();
			Statement st=database.getConnection().createStatement();

			int[] querResults = null;
			if("HIVE2KBS".equals(ds.getType()) || "HIVE".equals(ds.getType())){

				querResults = new int[sqlArray.length];
				for(int i=0; i<sqlArray.length; i++){
					querResults[i] = st.executeUpdate(sqlArray[i]);
				}
			}else{
				for(int i=0; i<sqlArray.length; i++){
					if(sqlArray[i] != null && !sqlArray[i].trim().equals(""))
						st.addBatch(sqlArray[i]);
				}
				querResults = st.executeBatch();
			}
			return querResults;
		} catch (Exception e) {
			e.printStackTrace();

			throw e;
		}finally{
			if(database != null){
				try {
					database.disconnect();
				} catch (Exception e) {
					throw e;
				}
			}
		}
	}
	
	private EtlManDbResourceVo getEtlManDbResourceVo(DataSource ds){
		EtlManDbResourceVo dbvo = new EtlManDbResourceVo();
		dbvo.setDbType(ds.getType());
		dbvo.setDbConType(DatabaseMeta.TYPE_ACCESS_NATIVE+"");
		dbvo.setDbHost(ds.getHost());
		dbvo.setDbName(ds.getServerName());
		dbvo.setDbPort(ds.getPort());
		dbvo.setDbUser(ds.getUser());
		dbvo.setDbPassword(ds.getPwd());
		dbvo.setKrb5ConfigPath(ds.getKrb5ConfigPath());
		dbvo.setHadoopHomeDir(ds.getHadoopHomeDir());
		dbvo.setKeytabPath(ds.getKeytabPath());
		dbvo.setPrincipal(ds.getPrincipal());
		return dbvo;
	}


	/**
	 *
	 * @param dsId
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryResult queryWrapperDataWithHeader(String dsId, String sql) throws Exception {
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsId);

		EtlManDbResourceVo dbvo = getEtlManDbResourceVo(ds);
		
		EtlDataBaseInterface database = null;
		
		if (sql.indexOf("#") > 0) {
			String[] sqlArray = sql.split("#");
			StringBuffer sb = new StringBuffer();
			sb.append(sqlArray[0]);
			String[] sqlSplitArray;
			for (int i = 1; i < sqlArray.length; i++) {
				sqlSplitArray = sqlArray[i].split(" ");
				if (sqlSplitArray.length > 0) {
					if (sb.toString().trim().endsWith("in")) {
						sb.append(" ('') ");
					} else {
						sb.append("'' ");
					}

					if (sqlSplitArray.length > 1) {
						for (int x = 1; x < sqlSplitArray.length; x++) {
							sb.append(sqlSplitArray[x]).append(" ");
						}
					}
				}
			}

			sql = sb.toString();
		}
		
		String tempSql = sql;
		if(("mysql".equalsIgnoreCase(ds.getType()) || "gbase".equalsIgnoreCase(ds.getType())) && sql.toLowerCase().indexOf("limit")<0)
			tempSql += " limit 50";
		else if("oracle".equalsIgnoreCase(ds.getType()) && sql.toLowerCase().indexOf("rownum")<0){
			tempSql = "select t.*, t.rowid from (" + sql + ") t where rownum <=50";
		}else if("db2".equalsIgnoreCase(ds.getType())){
			tempSql += " fetch first 50 rows only";
		}
		
		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			database.connect();
			List<Object[]> queryResultSet = database.getRowsWithHeader(tempSql, -1);
			
			QueryResult qr = new QueryResult();
			if(queryResultSet !=null && queryResultSet.size()>0){
				qr.setHeader(queryResultSet.get(0));
				queryResultSet.remove(0);
			}
			
			qr.setDatas(queryResultSet);
			qr.setDbType(ds.getType());
			qr.setDatas(queryResultSet);
			qr.setResult(QueryResult.QUERY_SUCC);
			
			return qr;
		} catch (Exception e) {
			QueryResult qr = new QueryResult();
			qr.setResult(QueryResult.QUERY_FAIL);
			qr.setExpMsg(e.getMessage());
			
			return qr;
		}finally{
			if(database != null){
				try {
					database.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查询子节点
	 * @return
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MetaCoreVo> queryChildrens(String prtId) {
		List<MetaCore> children = metaDao.queryByHql("from MetaCore mc where mc.prtId=?", prtId);
		
		List<MetaCoreVo> wrapperNodes = new ArrayList();
		for(MetaCore mc:children){
			MetaCoreVo mcv = new MetaCoreVo();
			try {
				BeanUtils.copyProperties(mc, mcv);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wrapperNodes.add(mcv);
		}
		return wrapperNodes;
	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	public MetaCoreVo getMetaVo(String id) {
		MetaCore core = (MetaCore)metaDao.get(MetaCore.class, id);
		MetaCoreVo vo = new MetaCoreVo();
		try {
			BeanUtils.copyProperties(core, vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	/**
	 * 查询分类下面物理模型
	 * @param prtId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MetaCoreVo> queryPdmModels(String prtId) throws Exception {
		List<MetaCore> rootNodes = metaDao.queryByHql("from MetaCore mc where mc.prtId=? and mc.metaMdl=?", prtId, "model.PhysicalModel");
		List<MetaCoreVo> wrapperNodes = new ArrayList();
		
		for(MetaCore mc:rootNodes){
			MetaCoreVo vo = new MetaCoreVo();
			try {
				BeanUtils.copyProperties(mc, vo);
				wrapperNodes.add(vo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wrapperNodes;
	}

	/**
	 * 查询分类下面概念模型
	 * @param prtId
	 * @param bizDomain
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MetaCoreVo> queryCdmModels(String prtId, String bizDomain) throws Exception {
		List<MetaCore> rootNodes = metaDao.queryByHql("from MetaCore mc where mc.prtId=? and mc.metaMdl=?", prtId, "model.LogicModel");
		List<MetaCoreVo> wrapperNodes = new ArrayList();
		
		for(MetaCore mc:rootNodes){
			MetaCoreVo vo = new MetaCoreVo();
			try {
				BeanUtils.copyProperties(mc, vo);
				wrapperNodes.add(vo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wrapperNodes;
	}


	/**
	 * 更新物理模型字段信息
	 * @param clms
	 * @param prtId
	 * @param delNodes
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public MetaNode updatePdmModelNode(JSONArray clms, String prtId, String delNodes, JSONArray relations) throws Exception {
		MetaCore tableMeta = this.getMetaCorePo(prtId);

		if(delNodes != null){
			String[] delNodesArr = delNodes.split(",");
			
			MetaCore delXmetaNode;
			for(String delNode:delNodesArr){
				if(delNode != null && !delNode.equals("")){
					delXmetaNode = this.getMetaCorePo(delNode);
					metaDao.delete(delXmetaNode);
				}
			}
		}
		
		JSONArray updateClm;
		for(int i=0; i<clms.size(); i++){
			updateClm = clms.getJSONArray(i);
			
			if(updateClm.getString(1) != null && !updateClm.getString(1).equals("") && !updateClm.getString(1).equals("null")){
			
				String len , precision, pk, nullable;
				if(updateClm.getString(0) == null || updateClm.getString(0).equals("") || updateClm.getString(0).equals("null")){
					//insert 
					MetaCore mc = new MetaCore();

					mc.setName(updateClm.getString(1));
					mc.setChName(updateClm.getString(3));

					mc.setRemarks(updateClm.getString(4));
					mc.setCmt(updateClm.getString(9));

					mc.setType(updateClm.getString(2));
					len = updateClm.getString(5);
					precision = updateClm.getString(6);

					pk = updateClm.getString(8);
					nullable = updateClm.getString(7);

					if(len == null || len.equals("")|| len.equals("null")){
						mc.setLength(0);
					}else{
						mc.setLength(Integer.parseInt(len));
					}
					if(precision == null || precision.equals("")|| precision.equals("null")){
						mc.setPrecision(0);
					}else{
						mc.setPrecision(Integer.parseInt(precision));
					}
					
					if(nullable == null){
						nullable = "false";
					}else if("1".equals(nullable) || "true".equals(nullable)){
						nullable = "true";
					}else{
						nullable = "false";
					}
					
					if(pk == null){
						pk = "false";
					}else if("1".equals(pk) || "true".equals(pk)){
						pk = "true";
					}else{
						pk = "false";
					}
					
					mc.setMetaMdl("model.PhysicalModelClm");
					mc.setPrtId(prtId);
					mc.setEntity("{\"nullable\":"+nullable+", \"pk\":"+pk+"}");
					metaDao.save(mc);
				}else{
					MetaCore updateMc = (MetaCore)metaDao.get(MetaCore.class, updateClm.getString(0));
					
					String oldName = updateMc.getName();
					
					updateMc.setName(updateClm.getString(1));
					updateMc.setChName(updateClm.getString(3));

					updateMc.setRemarks(updateClm.getString(4));
					updateMc.setCmt(updateClm.getString(9));

					updateMc.setType(updateClm.getString(2));
					len = updateClm.getString(5);
					precision = updateClm.getString(6);

					pk = updateClm.getString(8);
					nullable = updateClm.getString(7);

					if(len == null || len.equals("")|| len.equals("null")){
						updateMc.setLength(0);
					}else{
						updateMc.setLength(Integer.parseInt(len));
					}
					if(precision == null || precision.equals("")|| precision.equals("null")){
						updateMc.setPrecision(0);
					}else{
						updateMc.setPrecision(Integer.parseInt(precision));
					}
					
					if(nullable == null){
						nullable = "false";
					}else if("1".equals(nullable) || "true".equals(nullable)){
						nullable = "true";
					}else{
						nullable = "false";
					}
					
					if(pk == null){
						pk = "false";
					}else if("1".equals(pk) || "true".equals(pk)){
						pk = "true";
					}else{
						pk = "false";
					}
					
					updateMc.setMetaMdl("model.PhysicalModelClm");
					updateMc.setPrtId(prtId);
					updateMc.setEntity("{\"nullable\":"+nullable+", \"pk\":"+pk+"}");
					metaDao.update(updateMc);
				}
			}
		}
		metaDao.executeHql("delete from MetaRel mr where mr.src=?", new String[]{prtId});
		if(relations != null){
			JSONArray relation;
			for(int i=0; i<relations.size(); i++){
				relation = relations.getJSONArray(i);
				if(relation.getString(1) != null && !relation.getString(1).equals("") && !relation.getString(1).equals("null")){
					MetaRel rel = new MetaRel();
					rel.setSrc(prtId);
					rel.setCmt(relation.getString(0));
					rel.setSrcName(relation.getString(1));
					rel.setDest(relation.getString(2));
					rel.setDestName(relation.getString(3));
					
					rel.setRelType(relation.getString(4));
					rel.setCalType(relation.getString(5));
					rel.setRelDtl(relation.getString(6));
					
					metaDao.save(rel);
				}
			}
		}
		
		MetaNode node = queryDdlModelInfo(prtId);
		return node;
	}


	/**
	 * 添加物理模型
	 * @param prtNodeId
	 * @param modelName
	 * @param modelClms
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public MetaNode addPdmModelNode(String prtNodeId, String modelName, JSONArray modelClms) throws Exception {
		if(modelName != null && modelName.length() > 0){
			MetaCore cdmModel = new MetaCore();
			cdmModel.setName(modelName);
			cdmModel.setMetaMdl("model.PhysicalModel");
			cdmModel.setPrtId(prtNodeId);
			
			metaDao.save(cdmModel);
			
			JSONArray updateClm;String len , precision, pk, nullable;
			for(int i=0; i<modelClms.size(); i++){
				updateClm = modelClms.getJSONArray(i);
					if(updateClm.getString(1) != null && !updateClm.getString(1).equals("") && !updateClm.getString(1).equals("null")){
					
					MetaCore updateMc = new MetaCore();
					
					updateMc.setName(updateClm.getString(1));
					updateMc.setChName(updateClm.getString(3));
					
					updateMc.setRemarks(updateClm.getString(4));
					updateMc.setCmt(updateClm.getString(9));
					
					updateMc.setType(updateClm.getString(2));
					len = updateClm.getString(5);
					precision = updateClm.getString(6);
					
					pk = updateClm.getString(8);
					nullable = updateClm.getString(7);
					
					if(len == null || len.equals("")|| len.equals("null")){
						updateMc.setLength(0);
					}else{
						updateMc.setLength(Integer.parseInt(len));
					}
					if(precision == null || precision.equals("")|| precision.equals("null")){
						updateMc.setPrecision(0);
					}else{
						updateMc.setPrecision(Integer.parseInt(precision));
					}
					
					
					updateMc.setMetaMdl("model.PhysicalModelClm");
					updateMc.setPrtId(cdmModel.getId());
					updateMc.setEntity("{\"nullable\":"+nullable+", \"pk\":"+pk+"}");
					metaDao.save(updateMc);
				}
			}
			
			MetaNode node = queryDdlModelInfo(cdmModel.getId());
			node.setNodeId(cdmModel.getId());
			node.setName(modelName);
			return node;
		}
		return null;
	}
	
	private MetaData createMetaData(String prjName, String bizDomain, String domainPath, String enName, 
			String cnName, String cmt, String modeType, String parentPath,String metaMdl, Map<String,String> extParas){
		Map<String,String> attributeMap = new HashMap();
		String[] domains = null ;
		if(domainPath != null){
			domains = domainPath.split("\\/");
		}
		
		MetaData metaData = new MetaData();
		
		if("meta.mdl.domain".equals(modeType)){
			attributeMap.put("project_name", prjName);
			attributeMap.put("name_en", cnName);
			attributeMap.put("name_cn", cnName);
			attributeMap.put("description", cmt);
			metaData.setName(cnName);
		}else if("meta.mdl.pdm".equals(modeType)){
			attributeMap.put("project_name", prjName);
			attributeMap.put("model_domain1", domains[1]);
			attributeMap.put("model_domain1", domains[2]);
			attributeMap.put("name_en", enName);
			attributeMap.put("name_cn", cnName);
			attributeMap.put("description", cmt);
			attributeMap.put("model_domain", bizDomain);
			attributeMap.put("model_logic", "");
			metaData.setName(enName);
		}else if("meta.mdl.ldm".equals(modeType)){
			attributeMap.put("project_name", prjName);
			attributeMap.put("model_domain1", domains[1]);
			attributeMap.put("model_domain1", domains[2]);
			attributeMap.put("name_en", enName);
			attributeMap.put("name_cn", cnName);
			attributeMap.put("description", cmt);
			attributeMap.put("model_domain", bizDomain);
			metaData.setName(enName);
		}else if("meta.mdl.ldm.clm".equals(modeType) || "meta.mdl.pdm.clm".equals(modeType)){
			attributeMap.put("project_name", prjName);
			attributeMap.put("model_domain1", domains[1]);
			attributeMap.put("model_domain1", domains[2]);
			
			if("meta.mdl.ldm.clm".equals(modeType)){
				attributeMap.put("model_logic_table", domains[3]);
			}else{
				attributeMap.put("model_physical_table", domains[3]);
			}
			
			attributeMap.put("name_en", enName);
			attributeMap.put("name_cn", cnName);
			attributeMap.put("type", extParas.get("type"));
			attributeMap.put("length", extParas.get("length"));
			attributeMap.put("prec", extParas.get("prec"));
			attributeMap.put("is_pk", extParas.get("is_pk"));
			attributeMap.put("is_null", extParas.get("is_null"));
			attributeMap.put("description", cmt);
			attributeMap.put("model_domain", bizDomain);
			metaData.setName(enName);
		}else if("meta.mdl.pdm.rel".equals(modeType) || "meta.mdl.ldm.rel".equals(modeType)){
			attributeMap.put("name", enName);
			attributeMap.put("left_name_en", extParas.get("left_name_en"));
			attributeMap.put("left_column", extParas.get("left_column"));
			attributeMap.put("right_name_en", extParas.get("right_name_en"));
			attributeMap.put("right_column", extParas.get("right_column"));
			attributeMap.put("relation", extParas.get("relation"));
			attributeMap.put("description", cmt);
			metaData.setName(enName);
		}
		metaData.setModelId(metaMdl);
		
		metaData.setAttributeList(attributeMap);
		
		return metaData;
	}
	
	/**
	 * 检查域名是否存在
	 * @param parentNode
	 * @throws Exception
	 */
	private void checkOrAddPdmDomainPath(MetaCore parentNode, Map<String,String> metaOneParas) throws Exception{
		DictVo category = dictService.getDict("dw.category");
		List<DictCodeMapVo> categoryCodes = category.getCodeMapVos();
		
		String metaOneNodePath;DictVo subDomain;RespMessage repSubsystem;
		for(DictCodeMapVo categoryCode:categoryCodes){
			metaOneNodePath = metaOneParas.get("RESTful.url") + parentNode.getName() + "/模型/物理模型" ;
			if(CarnMetaUtil.pathExist(metaOneParas.get("metaone.url"), metaOneNodePath + "/"+ categoryCode.getName())){

			}else{
				MetaData metaData = createMetaData(parentNode.getName(), null, null, categoryCode.getCode(), categoryCode.getName(), "", 
						"meta.mdl.domain", null, metaOneParas.get("meta.mdl.domain"), null);
				repSubsystem = CarnMetaUtil.restAdd(metaOneParas.get("metaone.url"), metaOneNodePath , metaOneParas.get("metaone.user"), metaOneParas.get("metaone.pwd"),metaData);
				if (repSubsystem.toString().contains("RespResult=Success")){
					subDomain = dictService.getDict(categoryCode.getCode());
					
					for(DictCodeMapVo subCategoryCode:subDomain.getCodeMapVos()){
						MetaData subMetaData = createMetaData(parentNode.getName(), null, null, subCategoryCode.getCode(), subCategoryCode.getName(), "", 
								"meta.mdl.domain", null, metaOneParas.get("meta.mdl.domain"), null);
						repSubsystem = CarnMetaUtil.restAdd(metaOneParas.get("metaone.url"), metaOneNodePath + "/"+ categoryCode.getName() , metaOneParas.get("metaone.user"), metaOneParas.get("metaone.pwd"),subMetaData);
						
						if (repSubsystem.toString().contains("RespResult=Success")){
						}else{
							throw new RuntimeException("项目域[" + parentNode.getName() + "]域["+categoryCode.getName()+":" + subCategoryCode.getName() +"]添加失败");
						}
					}
				}else{
					throw new RuntimeException("项目域[" + parentNode.getName() + "]域["+categoryCode.getName()+"]添加失败");
				}
			}
		}
	}


	/**
	 * 删除节点
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delMetaNode(String nodeId) throws Exception {
		String[] nodes = nodeId.split(",");
		
		String delSub = "delete from MetaCore mc where mc.prtId=?";
		
		MetaCore mc ;
		for(String node:nodes){
			metaDao.executeHql(delSub, new Object[]{nodeId});
			
			mc = this.getMetaCorePo(node);
			
			Para metaSyncType = getPara("meta.sync.to.metaone");
			if("open".equals(metaSyncType.getValue())){
				Map<String,String> metaOneParas = this.getParas(METAONE_PARAS);
				MetaCore parentNode = this.getMetaCorePo(mc.getPrtId());
				String bizDomainPath = dictService.getBizDomainPath(mc.getBizDomain());

				if(mc.getMetaMdl().equals("model.PhysicalModel")){
					MetaData delNode = new MetaData();
					delNode.setName(mc.getName());
					String metaOneNodePath = metaOneParas.get("RESTful.url") + parentNode.getName() + "/模型/物理模型" + bizDomainPath + "/";
					CarnMetaUtil.restDelete(metaOneParas.get("metaone.url"), metaOneNodePath, delNode);
				}else if(mc.getMetaMdl().equals("model.LogicModel")){
					MetaData delNode = new MetaData();
					delNode.setName(mc.getName());
					String metaOneNodePath = metaOneParas.get("RESTful.url") + parentNode.getName() + "/模型/逻辑模型" + bizDomainPath + "/";
					CarnMetaUtil.restDelete(metaOneParas.get("metaone.url"), metaOneNodePath, delNode);
				}
			}
			metaDao.delete(mc);
		}
	}

	public void deployModels(String schema, String nodes, String opers) throws Exception{
		
	}
	
	private boolean isPk(JSONObject entitys){
		if(entitys.get("pk") != null && !entitys.get("pk").equals("null") && ( entitys.getBoolean("pk") || entitys.get("pk").equals("true"))){
			return true;
		}
		else{
			return false;
		}
	}
	
	private String getClmTypeString(MetaCore clm, String metaMdl){
		JSONObject entity = JSONObject.fromObject(clm.getEntity());
		int len = 0;
		String slen = "";
		String prec = "";
		if(clm.getType().toLowerCase().equals("varchar") || clm.getType().toLowerCase().equals("char") || clm.getType().toLowerCase().equals("float")
				|| clm.getType().toLowerCase().equals("long") || clm.getType().toLowerCase().equals("number") || clm.getType().toLowerCase().equals("int") || clm.getType().toLowerCase().equals("decimal") || clm.getType().toLowerCase().equals("bigint")){
			len = clm.getLength() == null ? 0 : clm.getLength();
			prec = clm.getPrecision() == null ? "" : clm.getPrecision() + "";
			if(len <= 0){
				if(clm.getType().toLowerCase().equals("int")){
					slen = "";
				}else if(clm.getType().toLowerCase().equals("varchar")){
					slen = "100";
				}else if(clm.getType().toLowerCase().equals("char")){
					slen = "10";
				}else if(clm.getType().toLowerCase().equals("float") || clm.getType().toLowerCase().equals("long") || clm.getType().toLowerCase().equals("number")){
					slen = "10";
					if(prec == null || prec.equals("")){
						prec = "0";
					}
				}
			}else{
				slen = len +"";
				if(prec == null || prec.equals("")){
					prec = "0";
				}
			}
		}
		
		StringBuffer clmTypeBuffer = new StringBuffer();
		if(clm.getType().toLowerCase().equals("varchar")){
			if(metaMdl.equalsIgnoreCase("Oracle")){
				clmTypeBuffer.append("varchar2").append("(").append(len).append(")");
			}else if(metaMdl.equalsIgnoreCase("HIVE2KBS") || metaMdl.equalsIgnoreCase("HIVE")){
				clmTypeBuffer.append("string");
			}else{
				clmTypeBuffer.append("varchar").append("(").append(len).append(")");
			}
		}else if(clm.getType().toLowerCase().equals("char")){
			if(metaMdl.equalsIgnoreCase("HIVE2KBS") || metaMdl.equalsIgnoreCase("HIVE") ){
				clmTypeBuffer.append("string");
			}else{
				clmTypeBuffer.append(clm.getType()).append("(").append(len).append(")");
			}
		}else if(clm.getType().toLowerCase().equals("float")
				|| clm.getType().toLowerCase().equals("long") || clm.getType().toLowerCase().equals("number") || clm.getType().toLowerCase().equals("decimal") || clm.getType().toLowerCase().equals("int") || clm.getType().toLowerCase().equals("bigint") || clm.getType().toLowerCase().equals("smallint")){
			if(metaMdl.equalsIgnoreCase("HIVE2KBS") || metaMdl.equalsIgnoreCase("HIVE") ){
				if(clm.getType().toLowerCase().equals("long")){
					clmTypeBuffer.append("BIGINT");
				}else if(clm.getType().toLowerCase().equals("number")){
					clmTypeBuffer.append("DOUBLE");
				}else{
					clmTypeBuffer.append(clm.getType());
				}
			}else{
				if(clm.getType().toLowerCase().equals("number")){
					if(metaMdl.toLowerCase().equals("mysql")){
						clmTypeBuffer.append("numeric").append("(").append(len).append(",").append(prec).append(")");
					}else{
						clmTypeBuffer.append(clm.getType()).append("(").append(len).append(",").append(prec).append(")");
					}
				}if(clm.getType().toLowerCase().equals("decimal")){
					if(metaMdl.toLowerCase().equals("mysql")){
						clmTypeBuffer.append("decimal").append("(").append(len).append(",").append(prec).append(")");
					}else{
						clmTypeBuffer.append(clm.getType()).append("(").append(len).append(",").append(prec).append(")");
					}
				}else if(clm.getType().toLowerCase().equals("long")){
					clmTypeBuffer.append("bigint");
				}else if(clm.getType().toLowerCase().equals("int") || clm.getType().toLowerCase().equals("bigint")){
					if(metaMdl.toLowerCase().equals("mysql")){
						if(slen.length() <= 0){
							clmTypeBuffer.append("int");
						}else{
							clmTypeBuffer.append("int").append("(").append(len).append(")");
						}
					}else{
						clmTypeBuffer.append(clm.getType()).append("(").append(len).append(",").append(prec).append(")");
					}
				}else{
					clmTypeBuffer.append(clm.getType()).append("(").append(len).append(",").append(prec).append(")");
				}
			}
		}else if(clm.getType().toLowerCase().equals("date") || clm.getType().toLowerCase().equals("time") || clm.getType().toLowerCase().equals("datetime")
				|| clm.getType().toLowerCase().equals("timestamp")){
			if(metaMdl.equalsIgnoreCase("HIVE2KBS") || metaMdl.equalsIgnoreCase("HIVE") ){
				if(clm.getType().toLowerCase().equals("time")){
					clmTypeBuffer.append("TIMESTAMP");
				}else{
					clmTypeBuffer.append(clm.getType());
				}
			}else if(metaMdl.toLowerCase().equals("mysql")){
				clmTypeBuffer.append(clm.getType());
			}else if(metaMdl.equalsIgnoreCase("Oracle")){
				if(clm.getType().toLowerCase().equals("time")){
					clmTypeBuffer.append("timestamp");
				}else{
					clmTypeBuffer.append(clm.getType());
				}
			}
		}else if(clm.getType().toLowerCase().equals("text") || clm.getType().toLowerCase().equals("string")){
			if(metaMdl.equalsIgnoreCase("HIVE2KBS") || metaMdl.equalsIgnoreCase("HIVE") ){
				clmTypeBuffer.append("string");
			}else{
				clmTypeBuffer.append("text");
			}
		}
		
		return clmTypeBuffer.toString();
	}
	
	public List<MetaCore> queryChildrenPo(String prtId, String metaMdl) throws Exception{
		return metaDao.queryByHql("from MetaCore mc where mc.prtId=? and mc.metaMdl=?", prtId, metaMdl);
	}
	
	private boolean columnInModels(FieldInfoVo vo, List<MetaCore> modelCmls){
		for(MetaCore modelClm:modelCmls){
			if(modelClm.getName().toLowerCase().equals(vo.getName().toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	private MetaDiff columnTypeDiff(FieldInfoVo vo, List<MetaCore> modelCmls, int idx){
		String type ;
		String length ;
		String pre;
		
		MetaDiff diff = null ;
		for(MetaCore modelClm:modelCmls){
			if(modelClm.getName().toLowerCase().equals(vo.getName().toLowerCase())){
				String xtype = modelClm.getType();
				if(xtype.indexOf(",") > 0){
					String[] xtypeArray = xtype.split(",");
					
					type = xtypeArray[0].substring(0, xtypeArray[0].indexOf("("));
					length = xtypeArray[0].substring(xtypeArray[0].indexOf("(") + 1);
					pre = xtypeArray[1].substring(0, xtypeArray[1].length() -1);
					
					if(type.equalsIgnoreCase(vo.getOriginalColumnType()) && length.equalsIgnoreCase(vo.getLength()+"") && pre.equalsIgnoreCase(vo.getPrecision()+"")){
						diff = new MetaDiff();
						diff.setDiffCmt("一致");
						diff.setDiffResult("1");
						diff.setDplClm(vo.getName());
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}else{
						diff = new MetaDiff();
						diff.setDiffCmt("字段类型不一致");
						diff.setDiffResult("0");
						diff.setDplClm(vo.getName() + " " +vo.getOriginalColumnType()+"("+vo.getLength()+"," + vo.getPrecision()+ ")");
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}
				}else{
					if (xtype.indexOf("(") > 0) {
						type = xtype.substring(0, xtype.indexOf("("));
						length = xtype.substring(xtype.indexOf("(") + 1, xtype.indexOf(")"));						
					} else {
						//处理timestamp、datetime等不包含长度的字段类型 2017/6/7 dengjie_bds
						type = xtype;
						length = modelClm.getLength()+"";
					}
					
					if((type.equalsIgnoreCase("date") || type.equalsIgnoreCase("time") || type.equalsIgnoreCase("datetime") || type.equalsIgnoreCase("timestamp") || type.equalsIgnoreCase(vo.getOriginalColumnType()))){
						diff = new MetaDiff();
						diff.setDiffCmt("一致");
						diff.setDiffResult("1");
						diff.setDplClm(vo.getName());
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}else if(type.equalsIgnoreCase(vo.getOriginalColumnType()) && length.equalsIgnoreCase(vo.getLength()+"")){
						diff = new MetaDiff();
						diff.setDiffCmt("一致");
						diff.setDiffResult("1");
						diff.setDplClm(vo.getName());
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}
					else if(type.indexOf("text")!=-1&&vo.getOriginalColumnType().equalsIgnoreCase("VARCHAR")
							&&length.equals("-1")&&vo.getLength()>=21845){
						diff = new MetaDiff();
						diff.setDiffCmt("一致");
						diff.setDiffResult("1");
						diff.setDplClm(vo.getName());
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}
					else if(vo.getOriginalColumnType().equalsIgnoreCase(type+" UNSIGNED")
							&&length.equalsIgnoreCase(vo.getLength()+"")){
						diff = new MetaDiff();
						diff.setDiffCmt("一致");
						diff.setDiffResult("1");
						diff.setDplClm(vo.getName());
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}
					else if(type.equalsIgnoreCase("string")
							&& type.equalsIgnoreCase(vo.getOriginalColumnType())
							&& length.equalsIgnoreCase("-1")
							&& vo.getLength()==2147483647){
						diff = new MetaDiff();
						diff.setDiffCmt("一致");
						diff.setDiffResult("1");
						diff.setDplClm(vo.getName());
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}else if(type.equalsIgnoreCase("int") && type.equalsIgnoreCase(vo.getOriginalColumnType())){
						if(vo.getLength() == 9 && modelClm.getLength() >0){
							diff = new MetaDiff();
							diff.setDiffCmt("一致");
							diff.setDiffResult("1");
							diff.setDplClm(vo.getName());
							diff.setIdx(idx+"");
							diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
							return diff;
						}else if(type.equalsIgnoreCase(vo.getOriginalColumnType())
								&& length.equalsIgnoreCase("-1")
								&& vo.getLength()==11){
							diff = new MetaDiff();
							diff.setDiffCmt("一致");
							diff.setDiffResult("1");
							diff.setDplClm(vo.getName());
							diff.setIdx(idx+"");
							diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
							return diff;
						}
					}else if(type.equalsIgnoreCase("long") && type.equalsIgnoreCase(vo.getOriginalColumnType())){
						diff = new MetaDiff();
						diff.setDiffCmt("一致");
						diff.setDiffResult("1");
						diff.setDplClm(vo.getName());
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}else{
						diff = new MetaDiff();
						diff.setDiffCmt("字段类型不一致");
						diff.setDiffResult("0");
						diff.setDplClm(vo.getName() + " " +vo.getOriginalColumnType()+"("+vo.getLength()+ ")");
						diff.setIdx(idx+"");
						diff.setMetaClm(modelClm.getName() + " " + modelClm.getType());
						return diff;
					}
				}
			}
		}
		return diff;
	}
	

	private MetaDiff checkClmDiffSummary(List<FieldInfoVo> physicalClms, List<MetaCore> modelClms, String tableName){
		List<MetaDiff> ret = new ArrayList();
		
		int i = 1;
		for(FieldInfoVo clm:physicalClms){
			if(columnInModels(clm, modelClms)){
				MetaDiff diff = columnTypeDiff(clm, modelClms, i);
				if(diff != null){
					ret.add(diff);
				}
				i++;
			}else{
				MetaDiff diff = new MetaDiff();
				diff.setDiffCmt("表增加了字段：" + clm.getName());
				diff.setDiffResult("0");
				diff.setDplClm(clm.getName());
				diff.setIdx(i+"");
				diff.setMetaClm("");
				
				ret.add(diff);
				i++;
			}
		}
		
		for(MetaCore model:modelClms){
			if(modelInClms(model, physicalClms)){
			}else{
				MetaDiff diff = new MetaDiff();
				diff.setDiffCmt("表删除了字段：" + model.getName());
				diff.setDiffResult("0");
				diff.setDplClm("");
				diff.setIdx(i+"");
				diff.setMetaClm(model.getName());
				
				ret.add(diff);
				i++;
			}
		}
		
		MetaDiff summaryDiff = new MetaDiff();
		boolean isDiff = false;String summInfo = "";
		for(MetaDiff diff:ret){
			if(diff.getDiffResult().equals("0")){
				isDiff = true;
				
				summInfo += diff.getDiffCmt() + "</br>";
			}
		}
		
		if(isDiff){
			summaryDiff.setDiffResult("0");
			summaryDiff.setDplClm(tableName);
			summaryDiff.setMetaClm(tableName);
			summaryDiff.setDiffCmt(summInfo);
		}else{
			summaryDiff.setDiffResult("1");
			summaryDiff.setDplClm(tableName);
			summaryDiff.setMetaClm(tableName);
			summaryDiff.setDiffCmt("一致");
		}
		
		return summaryDiff;
	}
	
	private boolean modelInClms(MetaCore model, List<FieldInfoVo> clms){
		for(FieldInfoVo clm:clms){
			if(clm.getName().toLowerCase().equals(model.getName().toLowerCase())){
				return true;
			}
		}
		
		return false;
	}



	/**
	 * 比对模型差异
	 * @param dsInfo
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public MetaDiff cmpModelDiff(String dsInfo, String modelId) throws Exception{
		String[] dsInfoArr = dsInfo.split("\\.");
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsInfoArr[0]);

		EtlManDbResourceVo dbvo =  getEtlManDbResourceVo(ds);
		dbvo.setDbSchema(dsInfoArr[1]);
		EtlDataBaseInterface database = null;
		
		MetaCore tableInfo = this.getMetaCorePo(modelId);
		List<MetaCore> clms = this.queryChildrenPo(modelId, "model.PhysicalModelClm");
		
		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			database.connect();
			
			List<FieldInfoVo> fields = database.getTableFields(tableInfo.getName());
			MetaDiff diff = checkClmDiffSummary(fields, clms, tableInfo.getName());
			return diff;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			if(database != null){
				database.disconnect();
			}
		}
	}
	
	public String createDDLSql(String dbType, MetaCore phyModel, String schema){
		StringBuffer sb = new StringBuffer();
		
		String modelName = phyModel.getName();
		
		if("HbaseKBS".equals(dbType)){
			sb.append("create '" + schema + ":" + modelName + "' ,{NAME => 'info'}" );
		}else{
			sb.append("create table ").append(modelName).append("(\r\n");
			
			JSONObject tmp ;
			
			List<MetaCore> pks = new ArrayList();
			List<MetaCore> normals = new ArrayList();
			
			List<MetaCore> clms = null;
			
			List<MetaRel> relations = null;
			
			try {
				clms = this.queryChildrenPo(phyModel.getId(), "model.PhysicalModelClm");
				relations = metaDao.queryByHql("from MetaRel mr where mr.src=?", phyModel.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(MetaCore clm:clms){
				tmp = JSONObject.fromObject(clm.getEntity());
				
				if(isPk(tmp)){
					pks.add(clm);
				}else{
					normals.add(clm);
				}
			}
			
			StringBuffer fkDdl = new StringBuffer();
			if("mysql".equalsIgnoreCase(dbType.toLowerCase()))
			{
				if(relations != null && relations.size() > 0){
					String ondelete;
					String onupdate;
					for(MetaRel mr:relations){
						if(mr.getCalType() == null || mr.getCalType().equals("") || mr.getCalType().equals("null")){
							onupdate = "restrict";
						}else{
							onupdate = mr.getCalType().toLowerCase();
						}
						
						if(mr.getRelType() == null || mr.getRelType().equals("") || mr.getRelType().equals("null")){
							ondelete = "restrict";
						}else{
							ondelete = mr.getCalType().toLowerCase();
						}
						
						fkDdl.append("alter table ").append(modelName).append(" add constraint ").append(mr.getCmt()).append(" foreign key (")
						.append(mr.getSrcName()).append(")\r\n").append("references ").append(mr.getDest()).append(" (").append(mr.getDestName())
						.append(") on delete ").append(ondelete).append("  on update ").append(onupdate).append("\r\n");
					}
				}
				if(pks == null || pks.size()<= 0){
					for(MetaCore clm:clms){
						sb.append("\t").append(clm.getName()).append(" ").append(getClmTypeString(clm, dbType)).append(",\r\n");
					}
					
					String xtmp = sb.substring(0, sb.length() -3 );
					sb.setLength(0);
					
					sb.append(xtmp).append("\r\n)");
				}else{
					for(MetaCore clm:clms){
						sb.append("\t").append(clm.getName()).append(" ").append(getClmTypeString(clm, dbType)).append(",\r\n");
					}
					
					String xtmp = sb.substring(0, sb.length() -1 );
					sb.setLength(0);
					
					//sb.append(xtmp);
					String pkString = "";
					for(MetaCore pk:pks){
						pkString += pk.getName()+",";
					}
					pkString = pkString.substring(0, pkString.length() -1);
					
					sb.append(xtmp).append("\tprimary key (").append(pkString).append(")\r\n").append(")");
				}
			}else if("Oracle".equalsIgnoreCase(dbType)){
				if(relations != null && relations.size() > 0){
					String ondelete;
					String onupdate;
					for(MetaRel mr:relations){
						if(mr.getCalType() == null || mr.getCalType().equals("") || mr.getCalType().equals("null")){
							onupdate = "restrict";
						}else{
							onupdate = mr.getCalType().toLowerCase();
						}
						
						if(mr.getRelType() == null || mr.getRelType().equals("") || mr.getRelType().equals("null")){
							ondelete = "restrict";
						}else{
							ondelete = mr.getCalType().toLowerCase();
						}
						
						fkDdl.append("alter table \"").append(modelName).append("\" add constraint ").append(mr.getCmt()).append(" foreign key (\"")
						.append(mr.getSrcName()).append("\")\r\n").append("references \"").append(mr.getDest()).append("\" (\"").append(mr.getDestName())
						.append("\") ").append("\r\n");
					}
				}
				
				if(pks == null || pks.size()<= 0){
					for(MetaCore clm:clms){
						sb.append("\t").append(clm.getName()).append(" ").append(getClmTypeString(clm,dbType)).append(",\r\n");
					}
					
					String xtmp = sb.substring(0, sb.length() -1 );
					sb.setLength(0);
					
					sb.append(xtmp).append(")");
				}else{
					for(MetaCore clm:clms){
						sb.append("\t").append(clm.getName()).append(" ").append(getClmTypeString(clm,dbType)).append(",\r\n");
					}
					
					String xtmp = sb.substring(0, sb.length() -1 );
					sb.setLength(0);
					
					//sb.append(xtmp);
					String pkString = "";
					for(MetaCore pk:pks){
						pkString += pk.getName()+",";
					}
					pkString = pkString.substring(0, pkString.length() -1);
					
					sb.append(xtmp).append("\tprimary key (").append(pkString).append(")").append(")");
				}
			}else if("HIVE".equalsIgnoreCase(dbType) || "HIVE2KBS".equalsIgnoreCase(dbType)){
				sb = new StringBuffer();
				sb.append("create table " + schema + "." + modelName).append("(\r\n");
				for(int x=0; x<clms.size();x++){
					if(x == (clms.size() -1 )){
						if(fkDdl.length()>10){
							sb.append(clms.get(x).getName()).append(" ").append(getClmTypeString(clms.get(x),dbType)).append(",\r\n");
						}else{
							sb.append(clms.get(x).getName()).append(" ").append(getClmTypeString(clms.get(x),dbType)).append("\r\n");
						}
					}else{
						sb.append(clms.get(x).getName()).append(" ").append(getClmTypeString(clms.get(x),dbType)).append(",\r\n");
					}
				}

				/*for(MetaCore clm:clms){
					sb.append(clm.getName()).append(" ").append(getClmTypeString(clm,dbType)).append(",\r\n");
				}*/

				String xtmp = sb.substring(0, sb.length() -1 );
				sb.setLength(0);
					
				sb.append(xtmp).append(")\r\n");
				sb.append("row format delimited fields terminated by '|'");
				//sb.append(xtmp).append(" primary key (").append(pkString).append(")").append(xtmp).append(")");
				//}
			}
			
			if(fkDdl.length()>10){
				sb.append(";\r\n").append(fkDdl);
			}else{
				
			}
		}
		
		return sb.toString();
	}

	/**
	 * 导出建表语句
	 * @param dsInfo
	 * @param models
	 * @param fileName
	 * @throws Exception
	 */
	public void exportDDLSql(String dsInfo, String models, String fileName) throws Exception {
		String[] dsInfoArr = dsInfo.split("\\.");
		String[] modelsArr = models.split(",");
		DataSource ds = (DataSource) getMetaInst(DataSource.class, dsInfoArr[0]);

		String tmpSql;
		MetaCore metaNode;

		FileWriter fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(fileName);
			fos = new FileWriter(file);
			bw = new BufferedWriter(fos);

			StringBuffer strb = null;

			for (String model : modelsArr) {
				metaNode = this.getMetaCorePo(model);
				tmpSql = createDDLSql(ds.getType(), metaNode, dsInfoArr[1]);
				bw.write(new String(tmpSql.toString().getBytes("UTF-8"), "UTF-8"));
				bw.write(";\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				bw.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * @param dsInfo
	 * @param models
	 * @return
	 * @throws Exception
	 */
	public List<MetaNode> queryDplModelInfo(String dsInfo, String models) throws Exception{
		String[] modelsArr = models.split(",");

		//MetaCore meta = this.getMetaCorePo(id)
		String[] dsInfoArr = dsInfo.split("\\.");
		DataSource ds = (DataSource)getMetaInst(DataSource.class, dsInfoArr[0]);

		EtlManDbResourceVo dbvo =  getEtlManDbResourceVo(ds);
		dbvo.setDbSchema(dsInfoArr[1]);
		EtlDataBaseInterface database = null;

		try {
			database = etlDataBaseFactory.getDatabase(dbvo);
			database.connect();

			MetaCore metaNode;List<MetaNode> ret = new ArrayList();
			for(String model:modelsArr){
				metaNode = this.getMetaCorePo(model);

				MetaNode retNode = new MetaNode();
				retNode.setNodeId(metaNode.getId());
				retNode.setName(metaNode.getName());
				retNode.setSql(createDDLSql(ds.getType(), metaNode, dsInfoArr[1]));

				if(database.checkTableExists(metaNode.getName())){
					retNode.setResult("exist");
					retNode.setMsg("存在同名表");
				}else{
					retNode.setResult("only");
					retNode.setMsg("不存在同名表");
				}
				ret.add(retNode);
			}

			return ret;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			if(database != null){
				database.disconnect();
			}
		}
	}

	/**
	 * 模型另存为
	 * @param prtNodeId
	 * @param modelInfo
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	@Override
	public MetaNode saveAsPdmModelNode(String prtNodeId, JSONArray modelInfo, String modelName) throws Exception {
		if(modelName != null && modelName.length() > 0){
			MetaCore metaCore = (MetaCore)metaDao.get(MetaCore.class, prtNodeId);
			
			MetaCore copyMetaCore = new MetaCore();
			BeanUtils.copyProperties(metaCore, copyMetaCore);
			copyMetaCore.setId(null);
			copyMetaCore.setName(modelName);
			metaDao.save(copyMetaCore);
			
			JSONArray updateClm;String len , precision, pk, nullable;
			for(int i=0; i<modelInfo.size(); i++){
				updateClm = modelInfo.getJSONArray(i);
					if(updateClm.getString(1) != null && !updateClm.getString(1).equals("") && !updateClm.getString(1).equals("null")){
					
					MetaCore updateMc = new MetaCore();
					
					updateMc.setName(updateClm.getString(1));
					updateMc.setChName(updateClm.getString(7));
					
					updateMc.setRemarks(updateClm.getString(8));
					updateMc.setCmt(updateClm.getString(9));
					
					updateMc.setType(updateClm.getString(2));
					len = updateClm.getString(3);
					precision = updateClm.getString(4);
					
					pk = updateClm.getString(6);
					nullable = updateClm.getString(5);
					
					if(len == null || len.equals("")|| len.equals("null")){
						updateMc.setLength(0);
					}else{
						updateMc.setLength(Integer.parseInt(len));
					}
					if(precision == null || precision.equals("")|| precision.equals("null")){
						updateMc.setPrecision(0);
					}else{
						updateMc.setPrecision(Integer.parseInt(precision));
					}
					
					
					updateMc.setMetaMdl("model.PhysicalModelClm");
					updateMc.setPrtId(copyMetaCore.getId());
					
					if(nullable == null){
						nullable = "false";
					}else if("1".equals(nullable) || "true".equals(nullable)){
						nullable = "true";
					}else{
						nullable = "false";
					}
					
					if(pk == null){
						pk = "false";
					}else if("1".equals(pk) || "true".equals(pk)){
						pk = "true";
					}else{
						pk = "false";
					}
					
					updateMc.setEntity("{\"nullable\":"+nullable+", \"pk\":"+pk+"}");
					metaDao.save(updateMc);
				}
			}

			MetaNode node = queryDdlModelInfo(copyMetaCore.getId());
			node.setNodeId(copyMetaCore.getId());
			node.setName(modelName);
			return node;
		}
		return null;
	}



	/**
	 * 查询所有元数据信息
	 * @param prtId
	 * @param metaMdl
	 * @return
	 * @throws Exception
	 */
	@Override
	public List queryMetaNodes(String prtId, String metaMdl) throws Exception {
		String hql = "";
		if(prtId == null || prtId.equals("")){
			hql = "select meta.id, meta.name from MetaCore meta where meta.metaMdl=?";
			
			return metaDao.queryByHql(hql, metaMdl);
		}else{
			hql = "select meta.id, meta.name from MetaCore meta where meta.prtId=? and meta.metaMdl=?";
			return metaDao.queryByHql(hql, prtId, metaMdl);
		}
	}

	/**
	 * 查询数据源
	 * @param prtId
	 * @param metaMdl
	 * @return
	 * @throws Exception
	 */
	@Override
	public List queryStructureMetaNodes(String prtId, String metaMdl) throws Exception {
		String hql = "";
		if(prtId == null || prtId.equals("")){
			hql = "select meta.id, meta.name from MetaCore meta where meta.metaMdl=? and (meta.type<>? and meta.type<>? and meta.type<>?)";
			return metaDao.queryByHql(hql, metaMdl,"hdfs", "FTP", "pkg");
		}else{
			hql = "select meta.id, meta.name from MetaCore meta where meta.prtId=? and meta.metaMdl=? and (meta.type<>? and meta.type<>? and meta.type<>?)";
			return metaDao.queryByHql(hql, prtId, metaMdl,"hdfs", "FTP", "pkg");
		}
	}


	/**
	 *
	 * @param metas
	 * @param prtId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> importPyhModelToBigtable(List<MetaNode> metas, String prtId) throws Exception{
		Column c; List<String> importModels = new ArrayList();
		
		
		MetaCore prjMeta = this.getMetaCorePo(prtId);
		
		String tmpBizDomainPath;
		
		//MetaCore prjMeta = this.getMetaCorePo(prtId);
		
		
		for(MetaNode meta:metas){
			MetaCore copyMetaCore = new MetaCore();
			copyMetaCore.setChName(meta.getChName());
			
			if(sameNameModelExist(prtId, meta.getName(), "model.PhysicalModel")){
				copyMetaCore.setName(meta.getName() + "_copy" + System.currentTimeMillis());
			}else{
				copyMetaCore.setName(meta.getName());
			}
			
			if(copyMetaCore.getChName() == null || copyMetaCore.getChName().equals("")){
				copyMetaCore.setChName(meta.getName());
			}
			copyMetaCore.setMetaMdl("model.PhysicalModel");
			copyMetaCore.setPrtId(prtId);
			copyMetaCore.setEntity("{}");
			
			metaDao.save(copyMetaCore);
			meta.setNodeId(copyMetaCore.getId());
			
			importModels.add(copyMetaCore.getName());
			for(Object col:meta.getColumns()){
				c = (Column)col;
				
				MetaCore updateMc = new MetaCore();
				
				updateMc.setName(c.getName());
				updateMc.setChName(c.getChName());
				
				updateMc.setRemarks(c.getRemarks());
				updateMc.setCmt(c.getCmt());
				
				updateMc.setType(c.getType());
				updateMc.setLength(c.getLength());
				updateMc.setPrecision(c.getPrecision());
				
				updateMc.setMetaMdl("model.PhysicalModelClm");
				updateMc.setPrtId(copyMetaCore.getId());
				updateMc.setEntity("{\"nullable\":"+c.isNullable()+", \"pk\":"+c.isPk()+"}");
				metaDao.save(updateMc);
			}
		}
		
		return importModels;
	}


	/**
	 * 判断是否存在同名模型
	 * @param prtId
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean sameNameModelExist(String prtId, String modelName, String metaMdl) throws Exception {
		String hql = "select count(mc.id) from MetaCore mc where mc.prtId=? and LOWER(mc.name) =? and mc.metaMdl=?";
		
		List cnt = metaDao.queryByHql(hql, prtId, modelName.toLowerCase(), metaMdl);
		Long existRows = Long.parseLong(cnt.get(0).toString());

		if(existRows > 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 判断更新模式下模型名称是否重复
	 * @param prtId
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updSameNameModelExist(String prtId, String modelName, String modelId, String metaMdl) throws Exception {
		String hql = "from MetaCore mc where mc.prtId=? and LOWER(mc.name) =? and mc.id<>? and mc.metaMdl=?";

		long existRows = metaDao.countByHql(hql, prtId, modelName.toLowerCase(), modelId, metaMdl);

		if(existRows > 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveDataSource(DataSource ds, String prtId) throws Exception {
		if(ds != null){
			MetaCore mc = new MetaCore();
			try {
				BeanUtils.copyProperties(ds, mc);
				ds.wrapperPersonalProperties(ds);
				mc.setEntity(ds.getPersonalProperties());
				Date now = new Date();
				mc.setCrtDate(now);
				mc.setLastUpdate(now);
				mc.setMetaMdl("phy.DataSource");
				mc.setPrtId(prtId);
				
				metaDao.save(mc);
				ds.setId(mc.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改数据源
	 * @param ds
	 * @param uid
	 * @throws Exception
	 */
	public void updateDataSource(DataSource ds, String uid) throws Exception {
		if(ds != null){
			MetaCore sourceMc = (MetaCore)metaDao.get(MetaCore.class, uid);
			
			sourceMc.setName(ds.getName());
			sourceMc.setType(ds.getType());
			sourceMc.setFileHeader(ds.getFileHeader());
			sourceMc.setFileName(ds.getFileName());
			sourceMc.setCmt(ds.getCmt());
			
			if(ds.getLvel() != null){
				sourceMc.setLvel(ds.getLvel());
			}
			//BeanUtils.copyProperties(ds, sourceMc);
			ds.wrapperPersonalProperties(ds);
			sourceMc.setEntity(ds.getPersonalProperties());
			Date now = new Date();
			sourceMc.setLastUpdate(now);
			
			metaDao.update(sourceMc);
		}
	}

	/**
	 * 测试数据源是否可以访问
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActionResult checkDsConnect(DataSource ds) throws Exception {
		ActionResult result = new ActionResult();
		EtlManDbResourceVo dbvo = new EtlManDbResourceVo();
		dbvo.setDbName(ds.getName());
		dbvo.setDbType(ds.getType());
		dbvo.setDbConType(DatabaseMeta.TYPE_ACCESS_NATIVE + "");
		dbvo.setDbHost(ds.getHost());
		dbvo.setDbName(ds.getServerName());
		dbvo.setDbPort(ds.getPort());
		dbvo.setDbUser(ds.getUser());
		dbvo.setDbPassword(ds.getPwd());

		dbvo.setKrb5ConfigPath(ds.getKrb5ConfigPath());
		dbvo.setHadoopHomeDir(ds.getHadoopHomeDir());
		dbvo.setKeytabPath(ds.getKeytabPath());
		dbvo.setPrincipal(ds.getPrincipal());

		EtlDataBaseInterface database = etlDataBaseFactory.getDatabase(dbvo);
		String msg = database.testConnection();
		result.setMsg(msg);
		result.setResult("");
		return result;
	}

	/**
	 * 查询物理模型字段信息
	 * @param parentId
	 * @param nodeName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> queryPdmModelClms(String parentId, String nodeName) throws Exception {
		MetaCore mc = this.getMetaCorePo(parentId);
		
		String prtId = parentId;
		if(mc.getMetaMdl().equals("model.PhysicalModel")){
			prtId = mc.getPrtId();
		}
		
		String hql = "from MetaCore mc where mc.prtId=? and mc.metaMdl=? and mc.name=?";
		String queryClmsHql = "select mc.name from MetaCore mc where mc.prtId=? and mc.metaMdl=?";
		
		List<MetaCore> metas = metaDao.queryByHql(hql, prtId, "model.PhysicalModel", nodeName);
		if(metas != null && metas.size() >0){
			MetaCore model = metas.get(0);
			
			return metaDao.queryByHql(queryClmsHql, model.getId(), "model.PhysicalModelClm");
		}else{
			return null;
		}
	}

	/**
	 *
	 * @param parentId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> queryModelNames(String parentId, String type) throws Exception {
		MetaCore mc = this.getMetaCorePo(parentId);
		
		String prtId = parentId;
		if(mc.getMetaMdl().equals("model.PhysicalModel") || mc.getMetaMdl().equals("model.LogicModel")){
			prtId = mc.getPrtId();
		}
		
		String hql = "select mc.name from MetaCore mc where mc.prtId=? and mc.metaMdl=?";
		return metaDao.queryByHql(hql, prtId, type);
	}

	/**
	 *
	 * @param paraName
	 * @return
	 * @throws Exception
	 */
	@Override
	public Para getPara(String paraName) throws Exception {
		return (Para)metaDao.get(Para.class, paraName);
	}
	
	public Map<String,String> getParas(String[] paraNames) throws Exception{
		String[] placePos = new String[paraNames.length];
		
		List<String> paraNameArray = new ArrayList();
		
		for(int i=0; i<paraNames.length; i++){
			placePos[i] = "?";
			paraNameArray.add(paraNames[i]);
		}
		
		String paras = StringUtils.join(placePos, ',');
		String hql = "from Para para where para.name in(" + paras + ")";
		
		List<Para> results = metaDao.queryByHql(hql, paraNameArray.toArray());
		Map<String,String> paraMap = new HashMap();
		for(Para result:results){
			paraMap.put(result.getName(), result.getValue());
		}
		
		return paraMap;
	}

	/**
	 * Hive数据更新
	 * @param ds
	 * @param cnt
	 * @return
	 * @throws Exception
	 */
	@Override
	public void executeHiveUpdate(String localFilePath, DataSource ds, String tableName, String cnt) throws Exception {
		JSONArray datas = JSONArray.fromObject(cnt);
		String ctm = System.currentTimeMillis()+"";
		String fileName = localFilePath + File.separator + ctm;
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName)),
                 "UTF-8"));
		
		JSONArray row;String rowString;
		
		int dataLen = datas.size() -1 ;
		for(int i=0; i<dataLen; i++){
			rowString = "";
			row = datas.getJSONArray(i);
			for(int j=0; j<row.size(); j++){
				if(j == (row.size() -1)){
					rowString += row.getString(j);
				}else{
					rowString += row.getString(j) + "\t";
				}
				rowString += row.getString(j);
			}
			
			bw.write(rowString);
			bw.newLine();
		}
		bw.close();
		
		Ftp f=new Ftp();
		f.setIpAddr(ds.getHost());
		f.setUserName(ds.getFileHeader());
		f.setPath(ds.getCmt());
		f.setPwd(ds.getFileName());
		FtpUtil.connectFtp(f);
		File file = new File(fileName);  
		FtpUtil.upload(file);
		
		EtlManDbResourceVo dbvo = new EtlManDbResourceVo();
		dbvo.setDbName(ds.getName());
		dbvo.setDbType(ds.getType());
		dbvo.setDbConType(DatabaseMeta.TYPE_ACCESS_NATIVE + "");
		dbvo.setDbHost(ds.getHost());
		dbvo.setDbName(ds.getServerName());
		dbvo.setDbPort(ds.getPort());
		dbvo.setDbUser(ds.getUser());
		dbvo.setDbPassword(ds.getPwd());
		
		dbvo.setKrb5ConfigPath(ds.getKrb5ConfigPath());
		dbvo.setHadoopHomeDir(ds.getHadoopHomeDir());
		dbvo.setKeytabPath(ds.getKeytabPath());
		dbvo.setPrincipal(ds.getPrincipal());
		
		EtlDataBaseInterface database = etlDataBaseFactory.getDatabase(dbvo);
		database.connect();
		
		Statement stmt = database.getConnection().createStatement();
		
		String truncateSql = "truncate table " + tableName;
		
		stmt.execute(truncateSql);
		String filePath = "'/tmp/" + ctm + "'" ;
		String insertSql = "load data local inpath " + filePath + " into table student";
		stmt.execute(insertSql);
	}


	/**
	 * 删除项目节点，同时删除节点下面所有子节点
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deletePrjNode(String nodeId) throws Exception {
		List<MetaCore> nodes = metaDao.queryByHql("from MetaCore mc where mc.prtId=?", nodeId);
		
		String delRelHql = "delete from MetaRel mr where mr.src=?";
		String delClmsHql = "delete from MetaCore mc where mc.prtId=?";
		
		for(MetaCore node:nodes){
			if("model.LogicModel".equals(node.getMetaMdl()) || "model.PhysicalModel".equals(node.getMetaMdl())){
				//delete model relation
				metaDao.executeHql(delRelHql, new String[]{node.getId()});
				
				//delete child clm
				metaDao.executeHql(delClmsHql, new String[]{node.getId()});
			}
			metaDao.delete(node);
		}
		metaDao.executeHql("delete from MetaCore mc where mc.id=?", new String[]{nodeId});
	}

	/**
	 *
	 * @param mc
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addMetaCore(MetaCore mc) throws Exception {
		metaDao.save(mc);
	}


	/**
	 * hive库信息
	 */
	public List<MetaCoreVo> getHiveDatabaseInfo(String projectId){
		String nameSpaceSql = "select t.namespace from MetaCore t where t.id=? ";
		List<String> list = metaDao.queryByHql(nameSpaceSql, projectId);
		String nameSpace = list.get(0);
		String hql = "select distinct t.userGroup from HadoopGroup t where t.projectId=? ";
		List<String> groupList = metaDao.queryByHql(hql, nameSpace);
		List<MetaCore> databaseList = HiveKbsUitls.getDataBases(groupList.get(0), projectId);
		List<MetaCoreVo> resultList = new ArrayList<>();
		if(null!=databaseList && databaseList.size()>0){
			metaDao.executeHql("delete from MetaCore mc where mc.prtId=? and metaMdl='hive.database' ", new String[]{projectId});
			MetaCoreVo metaCoreVo;
			for(MetaCore metaCore : databaseList){
				metaCoreVo = new MetaCoreVo();
				BeanUtils.copyProperties(metaCore, metaCoreVo);
				metaCoreVo.setId(metaCore.getId() + "|" + metaCore.getBizDomain());
				metaCore.setName("hive_" + metaCore.getName());
				metaDao.save(metaCore);
				resultList.add(metaCoreVo);
			}
		}
		return resultList;
	}

	/**
	 *
	 * @param database
	 * @param role
	 * @param privilege
	 * @param databaseId
	 * @return
	 */
	public List<MetaCoreVo> getHiveTables(String database, String role, String privilege, String databaseId){
		List<MetaCore> tabList = HiveKbsUitls.getTables(database, role, privilege,databaseId);
		List<MetaCoreVo> tableVoList = new ArrayList<>();
		MetaCoreVo metaCoreVo;
		for(MetaCore metaCore : tabList){
			metaCoreVo = new MetaCoreVo();
			BeanUtils.copyProperties(metaCore, metaCoreVo);
			metaCoreVo.setId(metaCore.getBizDomain());
			tableVoList.add(metaCoreVo);
		}
		return tableVoList;
	}

	/**
	 *
	 * @param tableId
	 * @return
	 */
	public MetaNode getHiveTableColumns(String tableId){
		MetaNode node = new MetaNode();
		node.setDbType("");
		MetaProp propName = new MetaProp();
		propName.setKey("名称");
		propName.setValue(tableId.split(HiveKbsUitls.SPLIT_STR)[2]);
		node.setProperties(new ArrayList());
		node.getProperties().add(propName);
		node.setName(tableId.split(HiveKbsUitls.SPLIT_STR)[2]);

		List<Column> columns = HiveKbsUitls.getColumns(tableId.split(HiveKbsUitls.SPLIT_STR)[1], tableId.split(HiveKbsUitls.SPLIT_STR)[2]);
		node.setColumns(columns);
		node.setMsg("归属物理库: " + tableId.split(HiveKbsUitls.SPLIT_STR)[1]);
		return node;
	}

	/**
	 *
	 * @param projectId
	 * @return
	 */
	public boolean syncDatabase(String projectId){
		getHiveDatabaseInfo(projectId);
		return true;
	}
}
