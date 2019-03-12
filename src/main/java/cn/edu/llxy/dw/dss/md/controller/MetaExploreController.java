package cn.edu.llxy.dw.dss.md.controller;

import cn.edu.llxy.dw.dss.vo.dg.MetaCoreVo;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.Column;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.DataSource;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.Table;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.View;
import cn.edu.llxy.dw.dss.vo.sys.UserVo;
import cn.edu.llxy.dw.dss.dc.web.QueryResult;
import cn.edu.llxy.dw.dss.dg.web.entity.ActionResult;
import cn.edu.llxy.dw.dss.dg.web.entity.MetaDiff;
import cn.edu.llxy.dw.dss.dg.web.entity.MetaNode;
import cn.edu.llxy.dw.dss.dg.web.entity.MetaTreeNode;
import cn.edu.llxy.dw.dss.hadoop.hive.HiveKbsUitls;
import cn.edu.llxy.dw.dss.md.ctx.SecurityContext;
import cn.edu.llxy.dw.dss.md.service.MetaService;
import cn.edu.llxy.dw.dss.po.dg.MetaCore;
import cn.edu.llxy.dw.dss.po.dg.Para;
import cn.edu.llxy.dw.dss.util.Base64;
import cn.edu.llxy.dw.dss.util.DateUtil;
import cn.edu.llxy.dw.dss.util.HttpUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/meta")
public class MetaExploreController {

	// 节点类型为根节点
	private final static String NODE_TYPE_ROOT = "-1";

	// 节点类型为目录的元模型类型
	private final static String[] NODE_META_PKG = { "core.BasePackage", "phy.DataSource", "relation.DataSource",
			"relation.Schema", "biz.SubSystem", "phy.hive", "phy.hbase", "phy.hdfs", "hive.database", "hbase.database"};

	@Resource(name = "metaService")
	private MetaService metaService;


	@RequestMapping(value = "/index")
	public String apxList(HttpServletRequest request) throws Exception {
		//initUser(request);

		return "meta/index";
	}

	private void initUser(HttpServletRequest request) {
		UserVo user = new UserVo();
		user.setAccount("admin");
		SecurityContext.saveUserToSeesion(user, request);
	}

	/**
	 * 查询模型字段信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryPdmModelClms")
	@ResponseBody
	public List<String> queryPdmModelClms(HttpServletRequest request) throws Exception{
		String modelName = request.getParameter("modelName");
		String parentNode = request.getParameter("parentNode");
		return metaService.queryPdmModelClms(parentNode, modelName);
	}

	/**
	 * 查询模型名
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryModelNames")
	@ResponseBody
	public List<String> queryModelNames(HttpServletRequest request) throws Exception{
		String parentNode = request.getParameter("parentNode");
		String type = request.getParameter("type");
		return metaService.queryModelNames(parentNode, type);
	}

	/**
	 * 查询节点信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryNodeInfo")
	@ResponseBody
	public MetaNode queryNodeInfo(HttpServletRequest request) throws Exception{
		String prtNodeId = request.getParameter("nodeId");
		String[] schemaInfos = prtNodeId.split("\\.");
		String nodeType = request.getParameter("nodeType");
		String modelName = request.getParameter("modelName");
		MetaNode node = null ;
		//node = metaService.getHiveTableColumns(prtNodeId);
		node = metaService.queryModelInfo(schemaInfos[0], schemaInfos[1], nodeType, schemaInfos[2]);
		return node;
	}

	/**
	 * 查询模型详细的元数据信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryModelNodeInfo")
	@ResponseBody
	public MetaNode queryModelNodeInfo(HttpServletRequest request) throws Exception{
		String nodeId = request.getParameter("nodeId");
		MetaNode node = metaService.queryDdlModelInfo(nodeId);
		return node;
	}


	/**
	 * 修改模型的元数据信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePdmModelNodeInfo")
	@ResponseBody
	public MetaNode updatePdmModelNodeInfo(HttpServletRequest request) throws Exception{
		String prtNodeId = request.getParameter("prtNodeId");
		String modelInfo = request.getParameter("modelInfo");
		String delNodes  = request.getParameter("delNodes");
		String modelName = request.getParameter("modelName");
		String chName = request.getParameter("chName");
		String cmt = request.getParameter("cmt");
		String rnodes = request.getParameter("rnodes");
		
		MetaCore core = metaService.getMetaCorePo(prtNodeId);
		String oldName = core.getName();
		
		if(modelName != null){
			if(metaService.updSameNameModelExist(core.getPrtId(), modelName, prtNodeId, "model.PhysicalModel")){
				MetaNode node = new MetaNode();
				node.setResult("fail");
				node.setMsg("模型名称同名，请重新设置模型名称！");
				return node;
			}
			core.setName(modelName);
			core.setChName(chName);
			core.setCmt(cmt);
		}
		
		metaService.updateMetaCore(core, oldName);
		
		JSONArray updateClms = JSONArray.fromObject(modelInfo);
		JSONArray relations = JSONArray.fromObject(rnodes);
		MetaNode node = metaService.updatePdmModelNode(updateClms, prtNodeId, delNodes, relations);
		
		return node;
	}

	/**
	 * 添加模型元数据信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addPdmModelNodeInfo")
	@ResponseBody
	public MetaNode addPdmModelNodeInfo(HttpServletRequest request) throws Exception{
		String prtNodeId = request.getParameter("prtNodeId");
		String modelName = request.getParameter("modelName");
		String nodes  = request.getParameter("nodes");
		
		if(metaService.sameNameModelExist(prtNodeId, modelName, "model.PhysicalModel")){
			MetaNode node = new MetaNode();
			node.setResult("fail");
			node.setMsg("模型名称同名，请重新设置模型名称！");
			return node;
		}
		JSONArray modelClms = JSONArray.fromObject(nodes);
		MetaNode node = metaService.addPdmModelNode(prtNodeId, modelName, modelClms);
		
		return node;
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryMetaNodes")
	@ResponseBody
	public List queryMetaNodes(HttpServletRequest request) throws Exception{
		String prtId = request.getParameter("prtId");
		String metaMdl = request.getParameter("metaMdl");
		String withStrc = request.getParameter("withStrc");
		
		if("1".equals(withStrc)){
			return metaService.queryStructureMetaNodes(prtId, metaMdl);
		}else{
			return metaService.queryMetaNodes(prtId, metaMdl);
		}
	}

	/**
	 *模型另存为
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveAsPdmModelNodeInfo")
	@ResponseBody
	public MetaNode saveAsPdmModelNodeInfo(HttpServletRequest request) throws Exception{
		String prtNodeId = request.getParameter("prtNodeId");
		String modelName = request.getParameter("modelName");
		String nodes  = request.getParameter("nodes");
		MetaCore core = metaService.getMetaCorePo(prtNodeId);
		
		if(metaService.sameNameModelExist(core.getPrtId(), modelName, "model.PhysicalModel")){
			MetaNode node = new MetaNode();
			node.setResult("fail");
			node.setMsg("模型名称同名，请重新设置模型名称！");
			return node;
		}
		
		JSONArray modelClms = JSONArray.fromObject(nodes);
		MetaNode node = metaService.saveAsPdmModelNode(prtNodeId, modelClms, modelName);
		node.setResult("succ");
		
		return node;
	}
	
	/**
	 * 模型比对
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cmpModels")
	@ResponseBody
	public List<MetaNode> cmpModels(HttpServletRequest request) throws Exception{
		String schema = request.getParameter("schema");
		String nodes = request.getParameter("nodes");
		return metaService.queryDplModelInfo(schema, nodes);
	}


	/**
	 * 导出DDL语句
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportModelDDL")
	@ResponseBody
	public String exportModelDDL(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String schema = request.getParameter("schema");
		String nodes = request.getParameter("nodes");
		
		String tplPath = request.getSession().getServletContext().getRealPath("tpl") + File.separator;
		String exportFile = tplPath + "papp_" + DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_TINY) + ".sql" ;
		metaService.exportDDLSql(schema, nodes, exportFile);
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			File expFile = new File(exportFile);
			long fileLength = expFile.length();

			String contentType = "text/plain";

			String fileViewName = expFile.getName();
			String agent = request.getHeader("User-Agent");
			boolean isMSIE = (agent != null && (agent.indexOf("MSIE") != -1 || agent.indexOf("Trident") != -1));
			if (isMSIE) {
				response.setHeader("Content-disposition",
						"attachment; filename=" + java.net.URLEncoder.encode(fileViewName, "UTF8"));
			} else {
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String(fileViewName.getBytes("utf-8"), "ISO8859-1"));
			}
			// }
			response.setContentType(contentType);
			response.setHeader("Content-Length", String.valueOf(fileLength));

			//
			bis = new BufferedInputStream(new FileInputStream(exportFile));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	/**
	 * 导入
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/importPdmModel")
	@ResponseBody
	public List<String> importPdmModel(HttpServletRequest request) throws Exception{
		String prtNodeId = request.getParameter("prtNodeId");
		String nodes = request.getParameter("nodes");
		List<MetaNode> metas = new ArrayList<>();
		/*for(String node : nodes.split(",")){
			metas.add(metaService.getHiveTableColumns(node));
		}*/
		metas = metaService.querySelectModelInfo(nodes.split(","));
		List<String> importModels = metaService.importPyhModelToBigtable(metas, prtNodeId);
		return importModels;
	}
	
	
	@RequestMapping(value="/delMetaNode")
	@ResponseBody
	public ActionResult delMetaNode(HttpServletRequest request) throws Exception{
		String nodeId = request.getParameter("nodeId");
		metaService.delMetaNode(nodeId);
		ActionResult result = new ActionResult();
		result.setMsgCode("succ");
		return result;
	}
	
	/**
	 * hadoop_dw
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/checkDsConnect")
	@ResponseBody
	public ActionResult checkDsConnect(HttpServletRequest request) throws Exception{
		String type = request.getParameter("type");
		String host = request.getParameter("host");
		String port = request.getParameter("port");
		String serverName = request.getParameter("serverName");
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		String krb5ConfigPath = request.getParameter("krb5ConfigPath");
		String hadoopHomeDir = request.getParameter("hadoopHomeDir");
		String keytabPath = request.getParameter("keytabPath");
		String principal = request.getParameter("principal");
		
		DataSource ds = new DataSource();
		ds.setType(type);
		ds.setHost(host);
		ds.setPort(port);
		ds.setServerName(serverName);
		ds.setUser(user);
		ds.setPwd(pwd);
		
		ds.setKrb5ConfigPath(krb5ConfigPath);
		ds.setHadoopHomeDir(hadoopHomeDir);
		ds.setKeytabPath(keytabPath);
		ds.setPrincipal(principal);
		ActionResult result = null;
		try{
			result = metaService.checkDsConnect(ds);
		}catch(Exception e){
			e.printStackTrace();
			result = new ActionResult();
			result.setResult("fail");
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getMetaCore")
	@ResponseBody
	public Object getMetaCore(HttpServletRequest request) throws Exception{
		String nodeId = request.getParameter("nodeId");
		Object metaObj = metaService.getMetaCore(nodeId);
		
		if(metaObj instanceof DataSource){
			DataSource ds = (DataSource)metaObj;
			
			if(ds.getKeytabPath() != null){
				ds.setKeytabPath(ds.getKeytabPath().replaceAll("\\\\", "\\\\\\\\"));
			}
			if(ds.getKrb5ConfigPath() != null){
				ds.setKrb5ConfigPath(ds.getKrb5ConfigPath().replaceAll("\\\\", "\\\\\\\\"));
			}
			if(ds.getHadoopHomeDir() != null){
				ds.setHadoopHomeDir(ds.getHadoopHomeDir().replaceAll("\\\\", "\\\\\\\\"));
			}
			if(ds.getLvel() != null){
				ds.setLvel(ds.getLvel().replaceAll("\\\\", "\\\\\\\\"));
			}
			if(ds.getPrincipal() != null){
				ds.setPrincipal(ds.getPrincipal().replaceAll("\\\\", "\\\\\\\\"));
			}
			
			return ds;
		}else{
			return metaObj;
		}
	}
	
	@RequestMapping(value="/delNode")
	@ResponseBody
	public ActionResult delNode(HttpServletRequest request) throws Exception{
		String nodeId = request.getParameter("nodeId");
		
		MetaCore metaCore = metaService.getMetaCorePo(nodeId);
		
		ActionResult result = new ActionResult();
		try{
			metaService.delMetaNode(nodeId);
			result.setResult("succ");
			result.setMsg("删除成功");
		}catch(Exception e){
			result.setResult("fail");
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 添加数据源
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addConnection")
	@ResponseBody
	public ActionResult addConnection(HttpServletRequest request) throws Exception{
		String type = request.getParameter("type");
		String host = request.getParameter("host");
		String port = request.getParameter("port");
		String serverName = request.getParameter("serverName");
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		String prtId = request.getParameter("prtId");
		
		String krb5ConfigPath = request.getParameter("krb5ConfigPath");
		String hadoopHomeDir = request.getParameter("hadoopHomeDir");
		String keytabPath = request.getParameter("keytabPath");
		String principal = request.getParameter("principal");
		String rootPath  = request.getParameter("rootPath");
		
		String hostUser = request.getParameter("hostUser");
		String hostPwd = request.getParameter("hostPwd");
		String hostDir = request.getParameter("hostDir");
		
		String uid = request.getParameter("uid");
		
		DataSource ds = new DataSource();
		
		if(rootPath != null && rootPath.length() > 0){
			ds.setLvel(rootPath);
		}
		ds.setType(type);
		ds.setHost(host);
		ds.setPort(port);
		ds.setServerName(serverName);
		ds.setUser(user);
		ds.setPwd(pwd);
		ds.setName(name);
		ds.setFileHeader(hostUser);
		ds.setFileName(hostPwd);
		ds.setCmt(hostDir);
		
		ds.setKrb5ConfigPath(krb5ConfigPath);
		ds.setHadoopHomeDir(hadoopHomeDir);
		ds.setKeytabPath(keytabPath);
		ds.setPrincipal(principal);
		
		ActionResult result = new ActionResult();
		try{
			if(uid != null && !uid.equals("")){
				metaService.updateDataSource(ds, uid);
				result.setMsg("数据源修改成功");
			}else{
				metaService.saveDataSource(ds, prtId);
				result.setMsg("数据源添加成功");
			}
			result.setResult("succ");
		}catch(Exception e){
			result.setResult("fail");
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/cmpModelDiff")
	@ResponseBody
	public MetaDiff cmpModelDiff(HttpServletRequest request) throws Exception{
		String modelId = request.getParameter("modelId");
		String dsInfo = request.getParameter("dsInfo");
		return metaService.cmpModelDiff(dsInfo, modelId);
	}
	
	@RequestMapping(value="/deployModel")
	@ResponseBody
	public ActionResult deployModel(HttpServletRequest request) throws Exception{
		String sql = request.getParameter("esql");
		String dsInfo = request.getParameter("dsInfo");
		// sql解密
		sql = Base64.decode(sql);

		sql = sql.replaceAll("&qveuxxxx;", "|");

		String[] sqlArray = sql.split(";");
		String[] dsInfoArr = dsInfo.split("\\.");

		ActionResult result = new ActionResult();
		try {
			int[] affRows = metaService.executeDDLSql(dsInfoArr[0], dsInfoArr[1], sqlArray);
			result.setResult("succ");
			String affInfo = "";
			for (int i = 0; i < affRows.length; i++) {
				if (i == (affRows.length - 1)) {
					affInfo += affRows[i];
				} else {
					affInfo += affRows[i] + ",";
				}
			}
			result.setMsg("受影响的行分别为:" + affInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult("fail");
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportSqlData")
	public ModelAndView exportSqlData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tableName = request.getParameter("tableName");
		String dsId = request.getParameter("dsId");
		String exportType = request.getParameter("exportType");
		String sql = request.getParameter("expSql");
		
		String fileName = request.getSession().getServletContext().getRealPath("tpl") + File.separator
				+ System.currentTimeMillis() + "." + exportType;
		File file = new File(fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		sql = Base64.decode(sql);
		metaService.exportSqlData(tableName, fileName, dsId, sql, exportType);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			long fileLength = new File(fileName).length();

			String contentType = "application/x-xls";
			if ("txt".equals(exportType)) {
				contentType = "text/plain";
			}
			if("csv".equals(exportType)){
				fileLength = fileLength +1;
			}
			// String exportDate = request.getParameter("exportDate");
			String fileViewName = tableName + "_" + DateUtil.getCurrentTime2() + "." + exportType;

			String agent = request.getHeader("User-Agent");
			boolean isMSIE = (agent != null && (agent.indexOf("MSIE") != -1 || agent.indexOf("Trident") != -1));

			// if(fileName.endsWith("png") || fileName.endsWith("jpg") ||
			// fileName.endsWith("jpeg") || fileName.endsWith("gif")){
			// contentType = "image/jpeg";
			// }else{
			if (isMSIE) {
				response.setHeader("Content-disposition",
						"attachment; filename=" + java.net.URLEncoder.encode(fileViewName, "UTF8"));
			} else {
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String(fileViewName.getBytes("utf-8"), "ISO8859-1"));
			}
			// }
			response.setContentType(contentType);

			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(fileName));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			if ("csv".equals(exportType))
				bos.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/excuteSql")
	@ResponseBody
	public ActionResult excuteSql(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long start = System.currentTimeMillis();
		
		String schema = request.getParameter("schema");
		String dsId   = request.getParameter("dsId");
		String sql    = request.getParameter("queryql");
		String tableName = request.getParameter("tableName");
		String hiveDataCnt = request.getParameter("hiveDataCnt");
		
		//sql解密
		sql = Base64.decode(sql);
		
		String[] sqlArray = sql.split(";");
		
		ActionResult result = new ActionResult();
		
		DataSource ds = (DataSource)metaService.getMetaInst(DataSource.class, dsId);
		QueryResult qr = null;
		
		if("HIVE2KBS".equals(ds.getType()) || "HIVE2".equals(ds.getType()) || "HIVE".equals(ds.getType())){
			String localFilePath = request.getSession().getServletContext().getRealPath("tpl");
			
			try{
				metaService.executeHiveUpdate(localFilePath, ds,tableName, hiveDataCnt);
				result.setResult("succ");
				result.setMsg("数据更新成功！");
			}catch(Exception e){
				result.setResult("fail");
				result.setMsg(e.getMessage());
			}
		}else{
			try{
				int[] affRows = metaService.executeSql(dsId, sqlArray);
				result.setResult("succ");
				
				String affInfo = "";
				for(int i=0; i<affRows.length; i++){
					if(i == (affRows.length - 1)){
						affInfo += affRows[i];
					}else{
						affInfo += affRows[i] + ",";
					}
				}
				
				result.setMsg("受影响的行分别为:"+ affInfo);
			}catch(Exception e){
				e.printStackTrace();
				result.setResult("fail");
				result.setMsg(e.getMessage());
			}
		}
		
		long end = System.currentTimeMillis();
		float seconds = (end - start) / 1000F; 
		result.setUserTimes(seconds);
		
		return result;
	}
	
	/**
	 * 执行sql查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/excuteSqlQuery")
	@ResponseBody
	public QueryResult excuteSqlQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long start = System.currentTimeMillis();
		
		String schema = request.getParameter("schema");
		String dsId   = request.getParameter("dsId");
		String sql    = request.getParameter("queryql");
		String tableName = request.getParameter("tableName");
		
		//sql解密
		sql = Base64.decode(sql);
		sql = restoreEscaped(sql);

		DataSource ds = (DataSource)metaService.getMetaInst(DataSource.class, dsId);
		QueryResult qr = null;

		qr = metaService.queryWrapperDataWithHeader(dsId, sql);

		if(tableName !=  null && !tableName.equals("")){
			try{
				List<Column> metas = metaService.queryMyQueryColumns(dsId, sql);
				qr.setMetas(metas);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			List<Column> metas = metaService.queryMyQueryColumns(dsId, sql);
			qr.setMetas(metas);
		}

		List<String> tables = new ArrayList();

		if(qr != null && qr.getMetas() != null){
			for(Column col:qr.getMetas()){
				if(tables.contains(col.getTableName())){
				}else{
					tables.add(col.getTableName());
				}
			}
		}
		if(tables.size() ==1){
			qr.setUpdate("1");
			qr.setTableName(tables.get(0));
		}else{
			qr.setUpdate("0");
		}

		long end = System.currentTimeMillis();
		float seconds = (end - start) / 1000F;
		qr.setUserTimes(seconds);
		
		qr.setQuerySql(sql);
		return qr;
	}

	private String restoreEscaped(String htmlStr) {
		if (StringUtils.isBlank(htmlStr)) {
			return htmlStr;
		}
		return htmlStr.replace("&#39;", "'").replace("&lt;", "<").replace("&gt;", ">")
				.replace("&quot;", "\"").replace("&amp", "&").replace("&nbsp;", " ");
	}


	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deletePrjNode")
	@ResponseBody
	public ActionResult deletePrjNode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String nodeId = request.getParameter("nodeId");
		ActionResult result = new ActionResult();
		try{
			metaService.deletePrjNode(nodeId);
			result.setResult("succ");
			result.setMsg("节点删除成功");
		}catch(Exception e){
			result.setResult("fail");
			result.setMsg(e.getMessage());
		}
		return result;
	}


	/**
	 * 树
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryTreeNode")
	@ResponseBody
	public List queryTreeNode(HttpServletRequest request) throws Exception{
		String prtNodeId = request.getParameter("id");
		String nodeType  = request.getParameter("nodeType");
		String pkgName   = request.getParameter("n");
		String view = request.getParameter("view");
		List object = null;
		
		UserVo user = SecurityContext.getUserFromSession(request);

		if(prtNodeId == null || NODE_TYPE_ROOT.equals(prtNodeId)){//根目录
			object = createRootDir(NODE_TYPE_ROOT);
		} else if(MetaTreeNode.NODE_TYPE_RELATION_PDM.equals(nodeType)
				|| MetaTreeNode.NODE_TYPE_RELATION_DB.equals(nodeType)
				|| MetaTreeNode.NODE_TYPE_RELATION_ITF.equals(nodeType)
				|| MetaTreeNode.NODE_TYPE_RELATION_HOST.equals(nodeType)){//根目录展开
			List<MetaCoreVo> nodes  = metaService.queryChildrens(prtNodeId);
			object = wrapperTreeNodes(nodes);
		}else if(MetaTreeNode.NODE_TYPE_PKG.endsWith(nodeType)){//目录展开
			List<MetaCoreVo> nodes  = metaService.queryChildrens(prtNodeId);
			object = wrapperTreeNodes(nodes);
		}else if("phy.DataSource".equals(nodeType)){//展开落地库
			List<MetaCoreVo> retNodes = metaService.querySchema(prtNodeId);
			object = wrapperTreeNodes(retNodes);
		}else if(MetaTreeNode.NODE_TYPE_SCHEMA.equals(nodeType)){//展开数据库
			object = createSchemaVirtualDir(prtNodeId);
		}else if (MetaTreeNode.NODE_TYPE_RELATION_TABLES.equals(nodeType)) {//表
			String[] schemaInfos = prtNodeId.split("\\.");
			List<MetaCoreVo> nodes = metaService.queryDsModels(schemaInfos[0], schemaInfos[1], "tables", null);
			object = wrapperTreeNodes(nodes);
		}else if (MetaTreeNode.NODE_TYPE_RELATION_VIEWS.equals(nodeType)) {//视图
			String[] schemaInfos = prtNodeId.split("\\.");
			List<MetaCoreVo> nodes = metaService.queryDsModels(schemaInfos[0], schemaInfos[1], "views", null);
			object = wrapperTreeNodes(nodes);
		}else{
			List<MetaCoreVo> nodes = metaService.queryChildrens(prtNodeId);
			object = wrapperTreeNodes(nodes);
		}
		return object;
	}

	/**
	 * 创建Schema下级虚拟目录
	 * 
	 * @param prtNodeId
	 * @return
	 */
	private List<MetaTreeNode> createSchemaVirtualDir(String prtNodeId) {
		List<MetaTreeNode> ret = new ArrayList();

		MetaTreeNode node = new MetaTreeNode();
		node.setName("表");
		node.setId(prtNodeId);
		node.setNodeType(MetaTreeNode.NODE_TYPE_RELATION_TABLES);
		node.setIsParent(true);
		ret.add(node);

		MetaTreeNode node2 = new MetaTreeNode();
		node2.setName("视图");
		node2.setId(prtNodeId);
		node2.setNodeType(MetaTreeNode.NODE_TYPE_RELATION_VIEWS);
		node2.setIsParent(true);
		ret.add(node2);

		MetaTreeNode node3 = new MetaTreeNode();
		node3.setName("存储过程");
		node3.setId(prtNodeId);
		node3.setNodeType(MetaTreeNode.NODE_TYPE_RELATION_PROCEDURES);
		node3.setIsParent(true);
		ret.add(node3);

		return ret;
	}

	/**
	 * 创建根目录
	 * @param prtNodeId
	 * @return
	 */
	private List<MetaTreeNode> createRootDir(String prtNodeId) {
		List<MetaTreeNode> ret = new ArrayList();
		
		MetaTreeNode node1 = new MetaTreeNode();
		node1.setName("模型");
		node1.setId(MetaTreeNode.NODE_TYPE_RELATION_PDM);
		node1.setNodeType(MetaTreeNode.NODE_TYPE_RELATION_PDM);
		node1.setIsParent(true);
		ret.add(node1);
		
		MetaTreeNode node2 = new MetaTreeNode();
		node2.setName("接口");
		node2.setId(MetaTreeNode.NODE_TYPE_RELATION_ITF);
		node2.setNodeType(MetaTreeNode.NODE_TYPE_RELATION_ITF);
		node2.setIsParent(true);
		ret.add(node2);
		
		MetaTreeNode node3 = new MetaTreeNode();
		node3.setName("落地库");
		node3.setId(MetaTreeNode.NODE_TYPE_RELATION_DB);
		node3.setNodeType(MetaTreeNode.NODE_TYPE_RELATION_DB);
		node3.setIsParent(true);
		ret.add(node3);
		
		MetaTreeNode node4 = new MetaTreeNode();
		node4.setName("文本数据");
		node4.setId(MetaTreeNode.NODE_TYPE_RELATION_HOST);
		node4.setNodeType(MetaTreeNode.NODE_TYPE_RELATION_HOST);
		node4.setIsParent(true);
		ret.add(node4);
		return ret;
	}


    /**
     * 将数据库节点封装为页面展示树结构
     *
     * @param nodes
     * @return
     */
    private List<MetaTreeNode> wrapperTreeNodes(List<MetaCoreVo> nodes) {
        List<MetaTreeNode> ret = new ArrayList();
        for (MetaCoreVo mc : nodes) {
            MetaTreeNode tn = new MetaTreeNode();
            tn.setId(mc.getId() + "");
            String name = mc.getChName();
            // 默认显示中文名称，如果中文名称为空则显示英文名称
            if (mc.getChName() == null || mc.getChName().trim().equals("")) {
                name = mc.getName();
            }
            tn.setName(mc.getName());
            tn.setTitle(name);

            tn.setNodeType(mc.getMetaMdl());

            tn.setIconSkin(getNodeIcon(mc.getMetaMdl()));

            tn.setIsParent(isDirectory(mc));

            tn.setpId(mc.getPrtId());

            tn.setNodeFrom("crt");

            ret.add(tn);
        }
        return ret;
    }

	/**
	 * 将数据库节点封装为页面展示树结构
	 *
	 * @param nodes
	 * @return
	 */
	private List<MetaTreeNode> wrapperTreePoNodes(List<MetaCore> nodes) {
		List<MetaTreeNode> ret = new ArrayList();
		for (MetaCore mc : nodes) {
			MetaTreeNode tn = new MetaTreeNode();
			tn.setId(mc.getId() + "");
			String name = mc.getChName();
			// 默认显示中文名称，如果中文名称为空则显示英文名称
			if (mc.getChName() == null || mc.getChName().trim().equals("")) {
				name = mc.getName();
			}
			tn.setName(name);
			tn.setTitle(mc.getName());
			tn.setNodeType(mc.getMetaMdl());
			if(mc.getType().equals("hdfs")){
//				tn.setIconSkin("hbase");

				if(mc.getLvel() != null && !mc.getLvel().equals(""))
				{
					tn.setName(name+"(" + mc.getLvel()+ ")");
				}else{
					tn.setName(name);
				}
			}else{
				tn.setIconSkin(getNodeIcon(mc.getMetaMdl()));
			}

			tn.setIsParent(isDirectory(mc));

			tn.setNodeFrom("crt");

			ret.add(tn);
		}
		return ret;
	}

	/**
	 * 获取节点图标类型
	 * 
	 * @param metaMdl
	 * @return
	 */
	private String getNodeIcon(String metaMdl) {
		String[] metaMdlArray;
		if (metaMdl != null && metaMdl.indexOf(".") > 0) {
			metaMdlArray = metaMdl.split("\\.");

			if ("BasePackage".equals(metaMdlArray[1]))
				return "";
			else
				return "";
		}
		return "";
	}

	/**
	 * 判断节点是否为目录
	 * 
	 * @param ms
	 * @return
	 */
	private boolean isDirectory(MetaCoreVo ms) {
		for (String s : NODE_META_PKG) {
			if (s.equals(ms.getMetaMdl()))
				return true;
		}

		if (ms.getMetaMdl().equals(Table.META_MDL) || ms.getMetaMdl().equals(View.META_MDL)) {
			return true;
		}

		if (ms.getMetaMdl().equals(Column.META_MDL)) {
			return false;
		}

		return false;
	}
	
	/**
	 * 判断节点是否为目录
	 * 
	 * @param ms
	 * @return
	 */
	private boolean isDirectory(MetaCore ms) {
		for (String s : NODE_META_PKG) {
			if (s.equals(ms.getMetaMdl()))
				return true;
		}

		if (ms.getMetaMdl().equals(Table.META_MDL) || ms.getMetaMdl().equals(View.META_MDL)) {
			return true;
		}

		if (ms.getMetaMdl().equals(Column.META_MDL)) {
			return false;
		}

		return false;
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/syncDatabase")
	@ResponseBody
	public ActionResult syncDatabase(HttpServletRequest request) throws Exception{
		String id = request.getParameter("nodeId");
		ActionResult result = new ActionResult();
		if(metaService.syncDatabase(id)){
			result.setMsgCode("succ");
			result.setMsg("同步成功！");
		}else{
			result.setMsgCode("fail");
			result.setMsg("同步失败！");
		}
		return result;
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getTableStaticInfo")
	@ResponseBody
	public Map<String, Object> getTableStaticInfo(HttpServletRequest request) throws Exception{
		Map<String, Object> result = new HashMap();
		String dbName = request.getParameter("dbName");
		String tableName = request.getParameter("tableName");
		String[] clumns = {"Database:","Owner:","CreateTime:","Location:","Table Parameters:"};
		List<Map<String, Object>> list = HiveKbsUitls.getTableStaticInfo(dbName, tableName);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if(list.size()>0){
			HashMap<String, Object> tmpMap = null;
			List<Object> clist = new ArrayList<>();
			List<HashMap<String, Object>> colsList = new ArrayList<HashMap<String, Object>>();

			tmpMap = new HashMap();
			tmpMap.put("field", "col_name");
			tmpMap.put("title", "col_name");
			tmpMap.put("width", "20%");
			colsList.add(tmpMap);
			tmpMap = new HashMap();
			tmpMap.put("field", "data_type");
			tmpMap.put("title", "data_type");
			tmpMap.put("width", "60%");
			colsList.add(tmpMap);
			tmpMap = new HashMap();
			tmpMap.put("field", "comment");
			tmpMap.put("title", "comment");
			tmpMap.put("width", "20%");
			colsList.add(tmpMap);

			clist.add(colsList);
			result.put("cols", clist);

			for(Map<String, Object> transMap : list){
				if((Arrays.asList(clumns).contains(transMap.get("col_name").toString().trim()) || transMap.get("col_name").equals("")) &&
						null != transMap.get("data_type")) {
					dataList.add(transMap);
				}
			}
		}else{
			result.put("cols", new ArrayList<>());
		}
		result.put("data", dataList);
		return result;
	}


	/**
	 * 导出hive表数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportHiveTableData")
	public ModelAndView exportHiveTableData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dbName = request.getParameter("dbName");
		String tableName = request.getParameter("tableName");
		List<Map<String, Object>> list = HiveKbsUitls.getTableDataLimit100(dbName, tableName);
		List<String> colsList = new ArrayList<String>();
		if(list.size()>0){
			Map<String, Object> map = list.get(0);
			for(String key : map.keySet()){
				colsList.add(key);
			}
		}
		String filename = dbName + "-" + tableName + ".xls";
		response.setContentType("application/msexcel;charset=GBK");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(filename.getBytes("GBK"), "ISO-8859-1"));
		exportExcelPOI(response.getOutputStream(), list, colsList, colsList, tableName);
		return null;
	}

	private void exportExcelPOI(OutputStream os, List<Map<String, Object>> list, List<String> columnNames,
								List<String> columns, String sheetName) throws Exception {

		HSSFWorkbook wb = null;
		OutputStream fos = os;
		try{
			wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName);
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			HSSFCellStyle style = wb.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			HSSFFont font = wb.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			// 把字体应用到当前的样式
			style.setFont(font);

			// 生成并设置另一个样式
			HSSFCellStyle style2 = wb.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 生成另一个字体
			HSSFFont font2 = wb.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// 把字体应用到当前的样式
			style2.setFont(font2);

			// 创建行对象
			HSSFCell cell = null;
            /* 添加标题行 */
			HSSFRow row = null;
			row = sheet.createRow(0);
			row.setHeightInPoints(20);
			for (int i = 0; i < columnNames.size(); i++) {
				cell = row.createCell(i);
				cell.setCellValue(columnNames.get(i));
				cell.setCellStyle(style);
			}
			HashMap<String, Object> confListMap = new HashMap<String, Object>();
			// 添加已存在的数据
			String columnValue = "";
			for (int i = 0; i < list.size(); i++) {     // 循环 行
				HashMap map = (HashMap) list.get(i);
				row = sheet.createRow(i+1);
				row.setHeightInPoints(20);
				for (int j = 0; j < columns.size(); j++) {//循环列
					if (map.get(columns.get(j)) != null && !map.get(columns.get(j)).toString().equals("")) {
						columnValue = map.get(columns.get(j)).toString();
						cell = row.createCell(j);
						cell.setCellValue(map.get(columns.get(j)).toString());
						cell.setCellStyle(style2);
					} else {
						columnValue = map.get(columns.get(j))==null?"":map.get(columns.get(j)).toString();
						cell = row.createCell(j);
						cell.setCellValue(columnValue);
						cell.setCellStyle(style2);
					}
				}
			}
			wb.write(fos);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			fos.close();
		}
	}
	
	/**
	 * 添加目录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addDir")
	@ResponseBody
	public ActionResult addDir(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String dirName = request.getParameter("dirName");
		String dirDesc  = request.getParameter("dirDesc");
		String prtId = request.getParameter("nodeId");
		String prtType = request.getParameter("nodeType");
		ActionResult result = new ActionResult();
		Date now = new Date();
		MetaCore mc = new MetaCore();
		mc.setBizDomain("core.BasePackage");
		mc.setName(dirName);
		mc.setChName(dirName);
		mc.setPrtId(prtId);
		mc.setCmt(dirDesc);
		mc.setCrtDate(now);
		mc.setLastUpdate(now);
		mc.setBizDomain(prtType);
		mc.setCrtUser("admin");
		mc.setMetaMdl("core.BasePackage");
		mc.setNamespace(prtId);
		mc.setType("pkg");
		try{
			metaService.addMetaCore(mc);
			result.setResult("succ");
			result.setMsg("添加成功");
		}catch(Exception e){
			result.setResult("fail");
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 删除目录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteDir")
	@ResponseBody
	public ActionResult deleteDir(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String nodeId = request.getParameter("nodeId");
		ActionResult result = new ActionResult();
		try{
			metaService.deletePrjNode(nodeId);
			result.setResult("succ");
			result.setMsg("节点删除成功");
		}catch(Exception e){
			result.setResult("fail");
			result.setMsg(e.getMessage());
		}
		return result;
	}

}
