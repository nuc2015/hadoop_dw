package cn.edu.llxy.dw.dss.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.llxy.dw.dss.vo.cfg.EtlManDbResourceVo;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.util.StringUtil;

public class EtlDataBase implements EtlDataBaseInterface {

	private DatabaseMeta dbmeta;
	private Database db;

	public EtlDataBase(String name, String type, String access, String host, String db, String port, String user, String pass) {
		if(port.equals("-1"))
			host = null;

		this.dbmeta = new DatabaseMeta(name, type, access, host, db, port, user, pass,"","","","");	
		this.db = new Database(null, dbmeta);
	}

	public EtlDataBase(EtlManDbResourceVo dbResource) {
		String name=dbResource.getDbName();
		String sname=dbResource.getDbSname();
		String tns=dbResource.getDbTns();
		String url = dbResource.getDbUrl();
		String type =dbResource.getDbType();
		String access=dbResource.getDbConType();
		String host=dbResource.getDbHost();
		String db=dbResource.getDbName();
		String port=dbResource.getDbPort();
		String user=dbResource.getDbUser();
		String pass=dbResource.getDbPassword();
		
		if(port.equals("-1"))
			host = null;
		if("SID".equalsIgnoreCase(access)){
			access="NATIVE";
		}else if("ServerName".equalsIgnoreCase(access)){
			access="NATIVE";
			name = "/"+sname;
		}else if("TNS".equalsIgnoreCase(access)){
			access="NATIVE";
			port = "-1";
			host = null;
			name = tns;
		}else if("URL".equalsIgnoreCase(access)){
			access="NATIVE";
			port = "-1";
			host = null;
			name = url;
		}

		this.dbmeta = new DatabaseMeta(name, type, access, host, db, port, user, pass,dbResource.getKrb5ConfigPath(), 
				dbResource.getHadoopHomeDir(), dbResource.getKeytabPath(), dbResource.getPrincipal());
		this.db = new Database(null, dbmeta);	
	}


	@Override
	public void setConnection(Connection connection) {
		db.setConnection(connection);
	}

	@Override
	public Connection getConnection() {
		return db.getConnection();
	}

	@Override
	public void setQueryLimit(int rows) {
		db.setQueryLimit(rows);

	}

	@Override
	public PreparedStatement getPrepStatementInsert() {
		return db.getPrepStatementInsert();
	}

	@Override
	public PreparedStatement getPrepStatementLookup() {
		return db.getPrepStatementLookup();
	}

	@Override
	public PreparedStatement getPrepStatementUpdate() {
		return db.getPrepStatementUpdate();
	}

	@Override
	public void connect() throws Exception {
		db.connect();
	}
	
	@Override
	public void connect(String partitionId) throws Exception {
		db.connect(partitionId);
	}

	@Override
	public void closeConnectionOnly() throws Exception {
		db.closeConnectionOnly();
	}

	@Override
	public void disconnect() {
		db.disconnect();
	}

	@Override
	public void cancelQuery() throws Exception {
		db.cancelQuery();
	}

	@Override
	public void cancelStatement(Statement statement) throws Exception {
		db.cancelStatement(statement);
	}

	@Override
	public void setCommit(int commsize) {
		db.setCommit(commsize);
	}

	@Override
	public void setAutoCommit(boolean useAutoCommit) throws Exception {
		db.setAutoCommit(useAutoCommit);
	}

	@Override
	public void commit() throws Exception {
		db.commit();
	}

	@Override
	public void commit(boolean force) throws Exception {
		db.commit(force);
	}

	@Override
	public void rollback() throws Exception {
		db.rollback();
	}

	@Override
	public void rollback(boolean force) throws Exception {
		db.rollback(force);
	}

	@Override
	public PreparedStatement prepareSQL(String sql) throws Exception {
		return db.prepareSQL(sql);
	}

	@Override
	public PreparedStatement prepareSQL(String sql, boolean returnKeys) throws Exception {
		return db.prepareSQL(sql, returnKeys);
	}

	@Override
	public void closeLookup() throws Exception {
		db.closeLookup();
	}

	@Override
	public void closePreparedStatement(PreparedStatement ps) throws Exception {
		db.closePreparedStatement(ps);
	}

	@Override
	public void closeInsert() throws Exception {
		db.closeInsert();
	}

	@Override
	public void closeUpdate() throws Exception {
		db.closeUpdate();
	}

	@Override
	public Long getNextSequenceValue(String sequenceName, String keyfield) throws Exception {
		return db.getNextSequenceValue(sequenceName, keyfield);
	}

	@Override
	public Long getNextSequenceValue(String schemaName, String sequenceName, String keyfield) throws Exception {
		return db.getNextSequenceValue(schemaName, sequenceName, keyfield);
	}

	@Override
	public void insertRow() throws Exception {
		db.insertRow();
	}

	@Override
	public void insertRow(boolean batch) throws Exception {
		db.insertRow(batch);
	}

	@Override
	public void updateRow() throws Exception {
		db.updateRow();
	}

	@Override
	public void insertRow(PreparedStatement ps) throws Exception {
		db.insertRow(ps);
	}

	@Override
	public boolean insertRow(PreparedStatement ps, boolean batch) throws Exception {
		return db.insertRow(ps, batch);
	}

	@Override
	public boolean insertRow(PreparedStatement ps, boolean batch, boolean handleCommit) throws Exception {
		return db.insertRow(ps, batch, handleCommit);
	}

	@Override
	public void clearBatch(PreparedStatement preparedStatement) throws Exception {
		db.clearBatch(preparedStatement);
	}

	@Override
	public void insertFinished(boolean batch) throws Exception {
		db.insertFinished(batch);
	}

	@Override
	public void emptyAndCommit(PreparedStatement ps, boolean batch, int batchCounter) throws Exception {
		db.emptyAndCommit(ps, batch, batchCounter);
	}

	@Override
	public boolean execStatement(String sql) throws Exception {
		return db.execStatement(sql).getResult();
	}

	@Override
	public ResultSet execStatementResultSet(String sql) throws Exception {
		return db.execStatementResultSet(sql);
	}

	@Override
	public boolean execStatements(String script) throws Exception {
		return db.execStatements(script).getResult();
	}

	@Override
	public ResultSet openQuery(String sql) throws Exception {
		RowMeta params=new RowMeta();
		return db.openQuery(sql, params, null);
	}

	@Override
	public boolean checkTableExists(String tablename) throws Exception {
		return db.checkTableExists(tablename);
	}

	@Override
	public boolean checkColumnExists(String columnname, String tablename) throws Exception {
		return db.checkColumnExists(columnname, tablename);
	}

	@Override
	public boolean checkSequenceExists(String sequenceName) throws Exception {
		return db.checkSequenceExists(sequenceName);
	}

	@Override
	public boolean checkSequenceExists(String schemaName, String sequenceName) throws Exception {
		return db.checkSequenceExists(schemaName, sequenceName);
	}

	@Override
	public boolean checkIndexExists(String tableName, String[] idx_fields) throws Exception {
		return db.checkIndexExists(tableName, idx_fields);
	}

	@Override
	public boolean checkIndexExists(String schemaName, String tableName, String[] idx_fields) throws Exception {
		return db.checkIndexExists(schemaName, tableName, idx_fields);
	}

	@Override
	public String getCreateIndexStatement(String tablename, String indexname, String[] idx_fields, boolean tk, boolean unique, boolean bitmap, boolean semi_colon) {
		return db.getCreateIndexStatement(tablename, indexname, idx_fields, tk, unique, bitmap, semi_colon);
	}

	@Override
	public String getCreateIndexStatement(String schemaname, String tablename, String indexname, String[] idx_fields, boolean tk, boolean unique, boolean bitmap, boolean semi_colon) {
		return db.getCreateIndexStatement(schemaname, tablename, indexname, idx_fields, tk, unique, bitmap, semi_colon);
	}

	@Override
	public String getCreateSequenceStatement(String sequence, long start_at, long increment_by, long max_value, boolean semi_colon) {
		return db.getCreateSequenceStatement(sequence, start_at, increment_by, max_value, semi_colon);
	}

	@Override
	public String getCreateSequenceStatement(String sequence, String start_at, String increment_by, String max_value, boolean semi_colon) {
		return db.getCreateSequenceStatement(sequence, start_at, increment_by, max_value, semi_colon);
	}

	@Override
	public String getCreateSequenceStatement(String schemaName, String sequence, long start_at, long increment_by, long max_value, boolean semi_colon) {
		return db.getCreateSequenceStatement(schemaName, sequence, start_at, increment_by, max_value, semi_colon);
	}

	@Override
	public String getCreateSequenceStatement(String schemaName, String sequenceName, String start_at, String increment_by, String max_value, boolean semi_colon) {
		return db.getCreateSequenceStatement(schemaName, sequenceName, start_at, increment_by, max_value, semi_colon);
	}

	@Override
	public void closeQuery(ResultSet res) throws Exception {
		db.closeQuery(res);
	}

	@Override
	public boolean absolute(ResultSet rs, int position) throws Exception {
		return db.absolute(rs, position);
	}

	@Override
	public boolean relative(ResultSet rs, int rows) throws Exception {
		return db.relative(rs, rows);
	}

	@Override
	public void afterLast(ResultSet rs) throws Exception {
		db.afterLast(rs);
	}

	@Override
	public void first(ResultSet rs) throws Exception {
		db.first(rs);
	}

	@Override
	public void printSQLException(SQLException ex) {
		db.printSQLException(ex);
	}

	@Override
	public void setLookup(String table, String[] codes, String[] condition, String[] gets, String[] rename, String orderby) throws Exception {
		db.setLookup(table, codes, condition, gets, rename, orderby);
	}

	@Override
	public void setLookup(String schema, String table, String[] codes, String[] condition, String[] gets, String[] rename, String orderby) throws Exception {
		db.setLookup(schema, table, codes, condition, gets, rename, orderby);
	}

	@Override
	public void setLookup(String tableName, String[] codes, String[] condition, String[] gets, String[] rename, String orderby, boolean checkForMultipleResults) throws Exception {
		db.setLookup(tableName, codes, condition, gets, rename, orderby, checkForMultipleResults);
	}

	@Override
	public void setLookup(String schemaName, String tableName, String[] codes, String[] condition, String[] gets, String[] rename, String orderby, boolean checkForMultipleResults) throws Exception {
		db.setLookup(schemaName, tableName, codes, condition, gets, rename, orderby, checkForMultipleResults);
	}

	@Override
	public boolean prepareUpdate(String table, String[] codes, String[] condition, String[] sets) {
		return db.prepareUpdate(table, codes, condition, sets);
	}

	@Override
	public boolean prepareUpdate(String schemaName, String tableName, String[] codes, String[] condition, String[] sets) {
		return db.prepareUpdate(schemaName, tableName, codes, condition, sets);
	}

	@Override
	public boolean prepareDelete(String table, String[] codes, String[] condition) {
		return db.prepareDelete(table, codes, condition);
	}

	@Override
	public boolean prepareDelete(String schemaName, String tableName, String[] codes, String[] condition) {
		return db.prepareDelete(schemaName, tableName, codes, condition);
	}

	@Override
	public void setProcLookup(String proc, String[] arg, String[] argdir, int[] argtype, String returnvalue, int returntype) throws Exception {
		db.setProcLookup(proc, arg, argdir, argtype, returnvalue, returntype);

		RowMetaInterface rowMeta = new RowMeta();// 参数列表

		int argnrs[] = new int[arg.length];
		Object[] objs = new Object[arg.length];
		for (int i = 0; i < arg.length; i++) {
			argnrs[i] = i;// 初始化参数位置，在此只为满足setProcValues方法，无其它意义（因参数默认为顺序，所以argnrs只需要顺序赋值）
			ValueMetaInterface valueMetaTarget = new ValueMeta(arg[i], argtype[i]);
			ValueMetaInterface valueMetaSrc = new ValueMeta(null, ValueMetaInterface.TYPE_STRING);
			objs[i]=valueMetaTarget.convertData(valueMetaSrc, arg[i]);
			rowMeta.addValueMeta(valueMetaTarget);//初始化参数列表
		}

		boolean result = StringUtil.isEmpty(returnvalue) ? false : true;// 是否有返回值

		db.setProcValues(rowMeta, objs, argnrs, argdir, result);
	}

	@Override
	public void truncateTable(String tablename) throws Exception {
		db.truncateTable(tablename);
	}

	@Override
	public void truncateTable(String schema, String tablename) throws Exception {
		db.truncateTable(schema, tablename);
	}

	@Override
	public int countParameters(String sql) {
		return db.countParameters(sql);
	}

	@Override
	public String[] getTableTypes() throws Exception {
		return db.getTableTypes();
	}

	@Override
	public String[] getTablenames() throws Exception {
		return db.getTablenames();
	}

	@Override
	public String[] getTablenames(boolean includeSchema) throws Exception {
		return db.getTablenames(includeSchema);
	}

	@Override
	public String[] getTablenames(String schemanamein, boolean includeSchema) throws Exception {
		return db.getTablenames(schemanamein, includeSchema);
	}
	
	@Override
	public String[] getTablenames(String schemanamein, String tableNamePattern, boolean includeSchema)
			throws Exception{
		return db.getTablenames(schemanamein, tableNamePattern, includeSchema);
	}

	@Override
	public String[] getViews() throws Exception {
		return db.getViews();
	}
	
	@Override
	public String[] getViews(String schemanamein, String tableNamePattern, boolean includeSchema)
			throws Exception{
		return db.getViews(schemanamein, tableNamePattern,  includeSchema);
	}
			
	@Override
	public String[] getViews(boolean includeSchema) throws Exception {
		return db.getViews(includeSchema);
	}

	@Override
	public String[] getViews(String schemanamein, boolean includeSchema) throws Exception {
		return db.getViews(schemanamein, includeSchema);
	}

	@Override
	public String[] getSynonyms() throws Exception {
		return db.getSynonyms();
	}

	@Override
	public String[] getSynonyms(boolean includeSchema) throws Exception {
		return db.getSynonyms(includeSchema);
	}

	@Override
	public String[] getSynonyms(String schemanamein, boolean includeSchema) throws Exception {
		return db.getSynonyms(schemanamein, includeSchema);
	}

	@Override
	public String[] getSchemas() throws Exception {
		return db.getSchemas();
	}

	@Override
	public String[] getCatalogs() throws Exception {
		return db.getCatalogs();
	}

	@Override
	public String[] getProcedures() throws Exception {
		return db.getProcedures();
	}

	@Override
	public List<ProcedureInfoVo> getProcedureColumns(String schemaPattern, String procedureNamePattern) throws Exception{
		ProcedureInfoVo[] infos = db.getProcedureColumns(schemaPattern, procedureNamePattern);
		List<ProcedureInfoVo> columnList = new ArrayList<ProcedureInfoVo>();
		for(ProcedureInfoVo vo : infos){
			if(!StringUtil.isEmpty(vo.getColumnName()))
				columnList.add(vo);
		}
		return columnList;
	}

	@Override
	public boolean isAutoCommit() {
		return db.isAutoCommit();
	}

	@Override
	public void lockTables(String[] tableNames) throws Exception {
		db.lockTables(tableNames);
	}

	@Override
	public void unlockTables(String[] tableNames) throws Exception {
		db.unlockTables(tableNames);
	}

	@Override
	public void closeProcedureStatement() throws Exception {
		db.closeProcedureStatement();
	}

	@Override
	public String getDDLTruncateTable(String schema, String tablename) throws Exception {
		return db.getDDLTruncateTable(schema, tablename);
	}

	@Override
	public Savepoint setSavepoint() throws Exception {
		return db.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String savePointName) throws Exception {
		return db.setSavepoint(savePointName);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws Exception {
		db.releaseSavepoint(savepoint);
	}

	@Override
	public void rollback(Savepoint savepoint) throws Exception {
		db.rollback(savepoint);
	}

	@Override
	public String[] getPrimaryKeyColumnNames(String tablename) throws Exception {
		return db.getPrimaryKeyColumnNames(tablename);
	}

	@Override
	public String[] getSequences() throws Exception {
		return db.getSequences();
	}

	@Override
	public String callProcedure(String[] arg, String[] argdir, int[] argtype, String resultname, int resulttype) throws Exception {
		return db.callProcedure(arg, argdir, argtype, resultname, resulttype).toString();

	}
	
	private String getOriginalColumnTypeDesc(String originalColumnTypeName, int length, int precision){
		if(length <= 0 && precision <= 0){
			return originalColumnTypeName;
		}else{
			String typeDesc = originalColumnTypeName;
			if(length >0){
				typeDesc += "(" + length;
				if(precision > 0){
					typeDesc += "," + precision ;
				}
				typeDesc += ")";
			}
			return typeDesc;
		}
	}
	
	@Override
	public List<FieldInfoVo> getQuerySqlFields(String sql, String schema) throws Exception {
		RowMetaInterface rowmetainterface=db.getQueryFields(sql, false);
		 List<FieldInfoVo> fieldlist=new ArrayList<FieldInfoVo>();
		 List<ValueMetaInterface> valuemetalist=rowmetainterface.getValueMetaList();
		 int index=1;
		 
		 String clmName = null;
		 for (ValueMetaInterface valueMeta : valuemetalist) {
			 clmName = valueMeta.getName();
			 if(clmName.indexOf(".") > 0){
				 clmName = clmName.substring(clmName.indexOf(".") + 1);
			 }
			 
			 FieldInfoVo fieldInfoVo = new FieldInfoVo(index++,clmName, valueMeta.getTypeDesc(),
					 valueMeta.getType(), valueMeta.getLength(), valueMeta.getPrecision(),valueMeta.getComments(), valueMeta.getOriginalColumnTypeName()
					 ,valueMeta.getOriginalColumnTypeName());
			 fieldInfoVo.setTableName(valueMeta.getTableName());
			 fieldlist.add(
					 fieldInfoVo
					 );
		 }
		 
		 DatabaseMetaData dbmd=db.getConnection().getMetaData();
		 
		 ResultSet pkRs = dbmd.getPrimaryKeys(null, schema, fieldlist.get(0).getTableName());
		 
		 List<String> pkColumns = new ArrayList();
		 while(pkRs.next()){
			pkColumns.add(pkRs.getString("COLUMN_NAME").toLowerCase());
		 }
		 
		 for(FieldInfoVo meta:fieldlist){
			 if(pkColumns.contains(meta.getName().toLowerCase())){
				 meta.setPk(true);
			 }
		 }
		 
		return fieldlist;
	}
	
	@Override
	public List<FieldInfoVo> getTableFields(String tablename) throws Exception {
		 RowMetaInterface rowmetainterface=db.getTableFields(tablename);
		 List<FieldInfoVo> fieldlist=new ArrayList<FieldInfoVo>();
		 List<ValueMetaInterface> valuemetalist=rowmetainterface.getValueMetaList();
		 int index=1;
		 
		 String clmName = null;
		 for (ValueMetaInterface valueMeta : valuemetalist) {
			 clmName = valueMeta.getName();
			 if(clmName.indexOf(".") > 0){
				 clmName = clmName.substring(clmName.indexOf(".") + 1);
			 }
			 
			 FieldInfoVo fieldInfoVo = new FieldInfoVo(index++,clmName, valueMeta.getTypeDesc(),
					 valueMeta.getType(), valueMeta.getLength(), valueMeta.getPrecision(),valueMeta.getComments(), valueMeta.getOriginalColumnTypeName()
					 ,valueMeta.getOriginalColumnTypeName());
			 fieldInfoVo.setTableName(valueMeta.getTableName());
			 fieldlist.add(
					 fieldInfoVo
					 );
		 }
		 
		 DatabaseMetaData dbmd=db.getConnection().getMetaData();
			
		 ResultSet rs = dbmd.getColumns(null, "%", tablename, "%");
		 String remarks;
		 while(rs.next()){
			 clmName = rs.getString("COLUMN_NAME").toUpperCase();
			 for(FieldInfoVo vo:fieldlist){
				 if(vo.getName().toUpperCase().equals(clmName)){
					 remarks = rs.getString("REMARKS");
					 if(remarks != null && !remarks.equals(""))
						 vo.setComments(remarks);
				 }
			 }
		 }
		return fieldlist;
	}

	@Override
	public String[] getReservedWords() {
		return dbmeta.getReservedWords();
	}

	@Override
	public String testConnection() {
		return dbmeta.testConnection();
	}

	@Override
	public String getInsertStatement(String schema, String tablename,RowModel rowmodel) {
		return db.getInsertStatement(schema, tablename,ConvertModelUtil.ConvertRowModeToRMI(rowmodel));
	}

	@Override
	public String getInsertStatement(String tablename, RowModel rowmodel) {
		return db.getInsertStatement( tablename,ConvertModelUtil.ConvertRowModeToRMI(rowmodel));
	}

	@Override
	public void setValues(RowModel rowmodel, RowData rowdata,PreparedStatement ps) throws Exception {
			db.setValues(ConvertModelUtil.ConvertRowModeToRMI(rowmodel), ConvertModelUtil.ConvertRowDataToDataArray(rowdata), ps);
	}

	@Override
	public String stripCR(String sbsql) {
		return dbmeta.stripCR(sbsql);
	}

	@Override
	public List<Object[]> getRows(String sql, int limit) throws Exception {
		return db.getRows(sql, limit);
	}
	
	@Override
	public List<Map<String, Object>> getMapRows(String sql, int limit) throws Exception {
		return db.getMapRows(sql, limit);
	}

	@Override
	public Object[] getRow(ResultSet rs) throws Exception {
		return db.getRow(rs);
	}

	@Override
	public List<Object[]> getRowsWithHeader(String sql, int limit)
			throws Exception {
		return db.getRowsWithHeader(sql, limit);
	}
}
