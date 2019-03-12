package cn.edu.llxy.dw.dss.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface EtlDataBaseInterface {

	/**
	 * set一个数据库连接
	 * 
	 * @param connection
	 */
	public void setConnection(Connection connection);

	/**
	 * 获得一个数据库连接
	 * 
	 * @return
	 */
	public Connection getConnection();

	/**
	 * 设置一个最大查询数，resultSet返回的数据条数
	 * 
	 * @param rows
	 */
	public void setQueryLimit(int rows);

	/**
	 * 获得一个用来插入数据的 PreparedStatement
	 * 
	 * @return
	 */
	public PreparedStatement getPrepStatementInsert();

	/**
	 * 获得一个用来LookUp数据的PreparedStatement
	 * 
	 * @return
	 */
	public PreparedStatement getPrepStatementLookup();

	/**
	 * 获得一个用来Update数据的 PreparedStatement
	 * 
	 * @return
	 */
	public PreparedStatement getPrepStatementUpdate();

	/**
	 * 
	 * 打开一个connection 连接
	 * 
	 * @throws Exception
	 */
	public void connect() throws Exception;
	
	public void connect(String partitionId) throws Exception;

	/**
	 * 
	 * 关闭connection 连接，只关闭连接，不清理 ResultSet，statment
	 * 
	 * @throws Exception
	 * 
	 */
	public void closeConnectionOnly() throws Exception;

	/**
	 * 
	 * 关闭connection，理 清理 ResultSet，statment
	 * 
	 */
	public void disconnect();

	/**
	 * 停止一个正在执行的 数据库查询
	 * 
	 * @throws Exception
	 */
	public void cancelQuery() throws Exception;

	/**
	 * 
	 * 取消一个 statment的执行
	 * 
	 * @param statement
	 * @throws Exception
	 * 
	 */
	public void cancelStatement(Statement statement) throws Exception;

	/**
	 * 设置一个 commit提交的size
	 * 
	 * @param commsize
	 *            The number of rows to wait before doing a commit on the
	 *            connection.
	 */
	public void setCommit(int commsize);

	/**
	 * 设置connection是否自动提交
	 * 
	 * @param useAutoCommit
	 * @throws Exception
	 */
	public void setAutoCommit(boolean useAutoCommit) throws Exception;

	/**
	 * 
	 * 执行数据库提交操作，如果数据类型支持的话
	 * 
	 */
	public void commit() throws Exception;

	/**
	 * 
	 * 执行数据库提交操作，如果数据类型支持的话,设置是否需要等待上一个事务的结束
	 * 
	 * @param force
	 *            ，true需要等待事务结束，如果失败进行回滚
	 * @throws Exception
	 * 
	 */
	public void commit(boolean force) throws Exception;

	/**
	 * 
	 * 执行数据库回滚操作，如果数据库类型支持的话
	 * 
	 * @throws Exception
	 * 
	 */
	public void rollback() throws Exception;

	/**
	 * 
	 * 执行数据库回滚操作，如果数据库类型支持的话,设置是否需要等待上一个事务的结束
	 * 
	 * @param force
	 *            true，需要等待事务结束
	 * @throws Exception
	 * 
	 */
	public void rollback(boolean force) throws Exception;

	/**
	 * 返回一个可以执行的Prepare statement，不需要keys Prepare a statement to be executed on
	 * the database. (does not return generated keys)
	 * 
	 * @param sql
	 *            The SQL to be prepared
	 * @return The PreparedStatement object.
	 * @throws Exception
	 */
	public PreparedStatement prepareSQL(String sql) throws Exception;

	/**
	 * 返回一个可以执行的Prepare statement，需要keys Prepare a statement to be executed on
	 * the database.
	 * 
	 * @param sql
	 *            The SQL to be prepared
	 * @param returnKeys
	 *            set to true if you want to return generated keys from an
	 *            insert statement
	 * @return The PreparedStatement object.
	 * @throws Exception
	 */
	public PreparedStatement prepareSQL(String sql, boolean returnKeys)
			throws Exception;

	/**
	 * 关闭 lookup 的 PreparedStatement pstmt
	 * 
	 * @throws Exception
	 */
	public void closeLookup() throws Exception;

	/**
	 * 关闭一个指定的 PreparedStatement
	 * 
	 * @param ps
	 * @throws Exception
	 */
	public void closePreparedStatement(PreparedStatement ps) throws Exception;

	/**
	 * 
	 * 关闭inset 的 PreparedStatement prepStatementInsert
	 * 
	 * @throws Exception
	 * 
	 */
	public void closeInsert() throws Exception;

	/**
	 * 
	 * 关闭 update 的 PreparedStatement prepStatementUpdate
	 * 
	 * @throws Exception
	 */
	public void closeUpdate() throws Exception;

	/**
	 * 从序列里面获得下一个序列号
	 * 
	 * @param sequenceName
	 * @param keyfield
	 * @return
	 * @throws Exception
	 */
	public Long getNextSequenceValue(String sequenceName, String keyfield)
			throws Exception;

	/**
	 * 从序列里面获得下一个序列号，包含schema
	 * 
	 * @param schemaName
	 * @param sequenceName
	 * @param keyfield
	 * @return
	 * @throws Exception
	 */
	public Long getNextSequenceValue(String schemaName, String sequenceName,
                                     String keyfield) throws Exception;

	/**
	 * insert 对象自己的 PreparedStatement prepStatementInsert
	 * 
	 * @throws Exception
	 */
	public void insertRow() throws Exception;

	/**
	 * insert 对象自己的 PreparedStatement prepStatementInsert
	 * 
	 * @param batch
	 *            设置批处理
	 * @throws Exception
	 */
	public void insertRow(boolean batch) throws Exception;

	/**
	 * update 一行 数据 到数据库中，使用 PreparedStatement prepStatementUpdate
	 * 
	 * @throws Exception
	 */
	public void updateRow() throws Exception;

	/**
	 * insert 一行 数据 到数据库中，使用 Prepared Statement
	 * 
	 * @param ps
	 * @throws Exception
	 *             传入的 ps
	 */
	public void insertRow(PreparedStatement ps) throws Exception;

	/**
	 * 插入一行数据到database中，使用传入的prepared statement Insert a row into the database
	 * using a prepared statement that has all values set.
	 * 
	 * @param ps
	 *            The prepared statement
	 * @param batch
	 *            True if you want to use batch inserts (size = commit size)
	 * @return true if the rows are safe: if batch of rows was sent to the
	 *         database OR if a commit was done.
	 * @throws Exception
	 */
	public boolean insertRow(PreparedStatement ps, boolean batch)
			throws Exception;

	/**
	 * 插入一行数据到database中，使用传入的prepared statement，可以设置是否使用 handleCommit Insert a
	 * row into the database using a prepared statement that has all values set.
	 * 
	 * @param ps
	 *            The prepared statement
	 * @param batch
	 *            True if you want to use batch inserts (size = commit size)
	 * @param handleCommit
	 *            True if you want to handle the commit here after the commit
	 *            size (False e.g. in case the step handles this, see
	 *            TableOutput)
	 * @return true if the rows are safe: if batch of rows was sent to the
	 *         database OR if a commit was done.
	 * @throws Exception
	 */
	public boolean insertRow(PreparedStatement ps, boolean batch,
                             boolean handleCommit) throws Exception;

	/**
	 * 清理一个 perpare statment 中的数据
	 * 
	 * @param preparedStatement
	 * @throws Exception
	 */
	public void clearBatch(PreparedStatement preparedStatement)
			throws Exception;

	/**
	 * 执行一个 自己的 insert pepare statement完成后设置 prepStatementInsert 为空
	 * 
	 * @param batch
	 * @throws Exception
	 */
	public void insertFinished(boolean batch) throws Exception;

	/**
	 * 检查这个 perpared statemnent 是否为空，并执行不为空的 ps Close the prepared statement of
	 * the insert statement.
	 * 
	 * @param ps
	 *            The prepared statement to empty and close.
	 * @param batch
	 *            true if you are using batch processing
	 * @param psBatchCounter
	 *            The number of rows on the batch queue
	 * @throws KettleException
	 */
	public void emptyAndCommit(PreparedStatement ps, boolean batch,
                               int batchCounter) throws Exception;

	/**
	 * 暂时先返回 true false 也可以返回执行成功，失败的条数和 sql 内容 Execute an SQL statement on the
	 * database connection (has to be open)
	 * 
	 * @param sql
	 *            The SQL to execute
	 * @return a Result object indicating the number of lines read, deleted,
	 *         inserted, updated, ...
	 * @throws KettleException
	 *             in case anything goes wrong.
	 */

	public boolean execStatement(String sql) throws Exception;

	
	/**
	 * 返回sql 内容 Execute an SQL statement on the
	 * database connection (has to be open)
	 * 
	 * @param sql
	 *            The SQL to execute
	 * @return a Result object indicating the number of lines read, deleted,
	 *         inserted, updated, ...
	 * @throws KettleException
	 *             in case anything goes wrong.
	 */

	public ResultSet execStatementResultSet(String sql) throws Exception;

	
	/**
	 * 执行一组 sql 暂时先返回 true false 也可以返回执行成功，失败的条数和 sql 内容 Execute a series of SQL
	 * statements, separated by ;
	 * 
	 * We are already connected...
	 * 
	 * Multiple statements have to be split into parts We use the ";" to
	 * separate statements...
	 * 
	 * We keep the results in Result object from Jobs
	 * 
	 * @param script
	 *            The SQL script to be execute
	 * @throws Exception
	 *             In case an error occurs
	 * @return A result with counts of the number or records updates, inserted,
	 *         deleted or read.
	 */
	public boolean execStatements(String script) throws Exception;

	/**
	 * 执行一个sql查询，打开返回Resultset 对象
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ResultSet openQuery(String sql) throws Exception;

	/**
	 * 检查当前用户schema下表是否存在
	 * 
	 * @param tablename
	 * @return
	 * @throws Exception
	 */
	public boolean checkTableExists(String tablename) throws Exception;

	/**
	 * 检查当前用户schema下表中的列是否存在
	 * 
	 * @param columnname
	 * @param tablename
	 * @return
	 * @throws Exception
	 */
	public boolean checkColumnExists(String columnname, String tablename)
			throws Exception;

	/**
	 * 检查当前用户schema下序列是否存在
	 * 
	 * @param sequenceName
	 * @return
	 * @throws Exception
	 */
	public boolean checkSequenceExists(String sequenceName) throws Exception;

	/**
	 * 检查schema下面的序列是否存在
	 * 
	 * @param schemaName
	 * @param sequenceName
	 * @return
	 * @throws Exception
	 */
	public boolean checkSequenceExists(String schemaName, String sequenceName)
			throws Exception;

	/**
	 * 检查table中是这些索引字段
	 * 
	 * @param tableName
	 * @param idx_fields
	 * @return
	 * @throws Exception
	 */
	public boolean checkIndexExists(String tableName, String idx_fields[])
			throws Exception;

	/**
	 * 检查schmea中表的索引字段
	 * 
	 * @param schemaName
	 * @param tableName
	 * @param idx_fields
	 * @return
	 * @throws Exception
	 */
	public boolean checkIndexExists(String schemaName, String tableName,
                                    String idx_fields[]) throws Exception;

	/**
	 * 生成创建表索引的sql
	 * 
	 * @param tablename
	 *            表名
	 * @param indexname
	 *            索引名
	 * @param idx_fields
	 *            索引字段
	 * @param tk
	 * @param unique
	 * @param bitmap
	 * @param semi_colon
	 * @return
	 */
	public String getCreateIndexStatement(String tablename, String indexname,
                                          String idx_fields[], boolean tk, boolean unique, boolean bitmap,
                                          boolean semi_colon);

	/**
	 * 生成创建表索引的sql
	 * 
	 * @param schemaname
	 *            schema名
	 * @param tablename
	 *            表名
	 * @param indexname
	 *            索引名
	 * @param idx_fields
	 *            所以字段
	 * @param tk
	 * @param unique
	 * @param bitmap
	 * @param semi_colon
	 * @return
	 */
	public String getCreateIndexStatement(String schemaname, String tablename,
                                          String indexname, String idx_fields[], boolean tk, boolean unique,
                                          boolean bitmap, boolean semi_colon);

	/**
	 * 生成创建序列的sql
	 * 
	 * @param sequence
	 *            序列名
	 * @param start_at
	 *            起始值
	 * @param increment_by
	 *            步长
	 * @param max_value
	 *            最大值
	 * @param semi_colon
	 * @return
	 */
	public String getCreateSequenceStatement(String sequence, long start_at,
                                             long increment_by, long max_value, boolean semi_colon);

	/**
	 * 生成创建序列的sql
	 * 
	 * @param sequence
	 * @param start_at
	 * @param increment_by
	 * @param max_value
	 * @param semi_colon
	 * @return
	 */
	public String getCreateSequenceStatement(String sequence, String start_at,
                                             String increment_by, String max_value, boolean semi_colon);

	/**
	 * 生成创建序列的sql
	 * 
	 * @param schemaName
	 *            schema 名称
	 * @param sequence
	 * @param start_at
	 * @param increment_by
	 * @param max_value
	 * @param semi_colon
	 * @return
	 */
	public String getCreateSequenceStatement(String schemaName,
                                             String sequence, long start_at, long increment_by, long max_value,
                                             boolean semi_colon);

	/**
	 * 生成创建序列的sql
	 * 
	 * @param schemaName
	 *            schema 名称
	 * @param sequenceName
	 * @param start_at
	 * @param increment_by
	 * @param max_value
	 * @param semi_colon
	 * @return
	 */
	public String getCreateSequenceStatement(String schemaName,
                                             String sequenceName, String start_at, String increment_by,
                                             String max_value, boolean semi_colon);

	/**
	 * 关闭一个 resultset 对象的查询
	 * 
	 * @param res
	 * @throws Exception
	 */
	public void closeQuery(ResultSet res) throws Exception;

	/**
	 * 将光标移动到此 ResultSet 对象的给定行编号
	 * 
	 * @param rs
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public boolean absolute(ResultSet rs, int position) throws Exception;

	/**
	 * 按相对行数（或正或负）移动光标
	 * 
	 * @param rs
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public boolean relative(ResultSet rs, int rows) throws Exception;

	/**
	 * 将光标移动到此 ResultSet 对象的末尾，正好位于最后一行之后
	 * 
	 * @param rs
	 * @throws Exception
	 */
	public void afterLast(ResultSet rs) throws Exception;

	/**
	 * 将光标移动到此 ResultSet 对象的第一行
	 * 
	 * @param rs
	 * @throws Exception
	 */
	public void first(ResultSet rs) throws Exception;

	/**
	 * 打印 SQLException 异常的 msg ，包括 异常的 sql语句
	 * 
	 * @param ex
	 */
	public void printSQLException(SQLException ex);

	/**
	 * 设置Looup PreparedStatement prepStatementLookup 从数据库表中lookup
	 * 
	 * @param table
	 * @param codes
	 * @param condition
	 * @param gets
	 * @param rename
	 * @param orderby
	 * @throws Exception
	 */
	public void setLookup(String table, String codes[], String condition[],
                          String gets[], String rename[], String orderby) throws Exception;

	/**
	 * 设置Looup PreparedStatement prepStatementLookup 从数据库表中lookup
	 * 
	 * @param schema
	 * @param table
	 * @param codes
	 * @param condition
	 * @param gets
	 * @param rename
	 * @param orderby
	 * @throws Exception
	 */
	public void setLookup(String schema, String table, String codes[],
                          String condition[], String gets[], String rename[], String orderby)
			throws Exception;

	/**
	 * 设置Looup PreparedStatement prepStatementLookup 从数据库表中lookup
	 * 
	 * @param tableName
	 * @param codes
	 * @param condition
	 * @param gets
	 * @param rename
	 * @param orderby
	 * @param checkForMultipleResults
	 * @throws Exception
	 */
	public void setLookup(String tableName, String codes[], String condition[],
                          String gets[], String rename[], String orderby,
                          boolean checkForMultipleResults) throws Exception;

	/**
	 * 设置Looup PreparedStatement prepStatementLookup 从数据库表中lookup
	 * 
	 * @param schemaName
	 * @param tableName
	 * @param codes
	 * @param condition
	 * @param gets
	 * @param rename
	 * @param orderby
	 * @param checkForMultipleResults
	 * @throws Exception
	 */
	public void setLookup(String schemaName, String tableName, String codes[],
                          String condition[], String gets[], String rename[], String orderby,
                          boolean checkForMultipleResults) throws Exception;

	/**
	 * 设置Looup PreparedStatement prepStatementLookup 从数据库表中lookup
	 * 
	 * @param table
	 * @param codes
	 * @param condition
	 * @param sets
	 * @return
	 */
	public boolean prepareUpdate(String table, String codes[],
                                 String condition[], String sets[]);

	/**
	 * 设置update的 PreparedStatement prepStatementUpdate 从数据库表中lookup
	 * 
	 * @param schemaName
	 * @param tableName
	 * @param codes
	 * @param condition
	 * @param sets
	 * @return
	 */
	public boolean prepareUpdate(String schemaName, String tableName,
                                 String codes[], String condition[], String sets[]);

	/**
	 * 设置update的 PreparedStatement prepStatementUpdate，从数据库表中lookup Prepare a
	 * delete statement by giving it the tablename, fields and conditions to
	 * work with.
	 * 
	 * @param table
	 *            The table-name to delete in
	 * @param codes
	 * @param condition
	 * @return true when everything went OK, false when something went wrong.
	 */
	public boolean prepareDelete(String table, String codes[],
                                 String condition[]);

	/**
	 * 设置update的 PreparedStatement prepStatementUpdate，从数据库表中lookup Prepare a
	 * delete statement by giving it the tablename, fields and conditions to
	 * work with.
	 * 
	 * @param schemaName
	 *            the schema-name to delete in
	 * @param tableName
	 *            The table-name to delete in
	 * @param codes
	 * @param condition
	 * @return true when everything went OK, false when something went wrong.
	 */
	public boolean prepareDelete(String schemaName, String tableName,
                                 String codes[], String condition[]);

	/**
	 * 从数据库查找对应的 存储过程，并设置对应的 prepared statement
	 * 
	 * @param proc
	 *            过程名
	 * @param arg
	 *            参数
	 * @param argdir
	 *            参数 是 OUT or INOUT
	 * @param argtype
	 *            参数类型 ，输入的应该是 ValueMetaInterface.TYPE_NUMBER 等
	 * @param returnvalue
	 *            返回值
	 * @param returntype
	 *            返回值类型
	 * @throws Exception
	 */
	public void setProcLookup(String proc, String arg[], String argdir[],
                              int argtype[], String returnvalue, int returntype) throws Exception;

	/**
	 * 
	 * 执行 truncate table
	 * 
	 * @param tablename
	 * @throws Exception
	 */
	public void truncateTable(String tablename) throws Exception;

	/**
	 * 
	 * 执行 truncate table 包含 schema名称
	 * 
	 * @param schema
	 * @param tablename
	 * @throws Exception
	 */
	public void truncateTable(String schema, String tablename) throws Exception;

	/**
	 * 
	 * 获得一个预编译 SQL中参数的个数 insert into table ('?','?'); 上面SQL的参数个数为2
	 * 
	 * @param sql
	 * @return
	 */
	public int countParameters(String sql);

	/**
	 * 
	 * 返回 datameta的名称
	 * 
	 * @return
	 */
	public String toString();

	/**
	 * 
	 * 获得当前DB用户下的所有表类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getTableTypes() throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的视表（table）），不包含schema名
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getTablenames() throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的视表（table），设置名称中包含schema名
	 * 
	 * @param includeSchema
	 * @return
	 * @throws Exception
	 */
	public String[] getTablenames(boolean includeSchema) throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的表（table），名称中是否包含schema名，可以传入schema
	 * 
	 * @param schemanamein
	 * @param includeSchema
	 * @return
	 * @throws Exception
	 */
	public String[] getTablenames(String schemanamein, boolean includeSchema)
			throws Exception;
	
	/**
	 * 
	 * 获得当前DB用户下所有的表（table），名称中是否包含schema名，可以传入schema
	 * 
	 * @param schemanamein
	 * @param includeSchema
	 * @param tableNamePattern   
	 * @return
	 * @throws Exception
	 */
	public String[] getTablenames(String schemanamein, String tableNamePattern, boolean includeSchema)
			throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的视图（view），不包含schema名
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getViews() throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的视图（view），设置名称中包含schema名
	 * 
	 * @param includeSchema
	 * @return
	 * @throws Exception
	 */
	public String[] getViews(boolean includeSchema) throws Exception;
	
	/**
	 * 获得当前DB用户下所有的视图（view），名称中是否包含schema名，以及Schema名称规则
	 * @param schemanamein
	 * @param tableNamePattern
	 * @param includeSchema
	 * @return
	 * @throws Exception
	 */
	public String[] getViews(String schemanamein, String tableNamePattern, boolean includeSchema)
			throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的视图（view），名称中是否包含schema名，可以传入schema
	 * 
	 * @param schemanamein
	 * @param includeSchema
	 * @return
	 * @throws Exception
	 */
	public String[] getViews(String schemanamein, boolean includeSchema)
			throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的Synonyms，不包含schema名
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getSynonyms() throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的Synonyms，设置名称中包含schema名
	 * 
	 * @param includeSchema
	 * @return
	 * @throws Exception
	 */
	public String[] getSynonyms(boolean includeSchema) throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的Synonyms，名称中是否包含schema名，可以传入schema
	 * 
	 * @param schemanamein
	 * @param includeSchema
	 * @return
	 * @throws Exception
	 */
	public String[] getSynonyms(String schemanamein, boolean includeSchema)
			throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的schema
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getSchemas() throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的Catalogs
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getCatalogs() throws Exception;

	/**
	 * 
	 * 获得当前DB用户下所有的存储过程
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getProcedures() throws Exception;

	/**
	 * 
	 * 获得当前DB用户指定schema下所有存储过程的参数信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ProcedureInfoVo> getProcedureColumns(String schemaPattern,
                                                     String procedureNamePattern) throws Exception;

	/**
	 * 
	 * 当前DB是否是自动提交
	 * 
	 * @return
	 */
	public boolean isAutoCommit();

	/**
	 * 
	 * 加锁一组表
	 * 
	 * @param tableNames
	 * @throws Exception
	 */
	public void lockTables(String tableNames[]) throws Exception;

	/**
	 * 
	 * 解锁一组表
	 * 
	 * @param tableNames
	 * @throws Exception
	 */
	public void unlockTables(String tableNames[]) throws Exception;

	/**
	 * 关闭存储过程调用的statamet
	 * 
	 * @throws Exception
	 * 
	 */
	public void closeProcedureStatement() throws Exception;

	/**
	 * 
	 * 生成一个 truncate 表的SQL语句，表名中包含 schema
	 * 
	 * @param schema
	 * @param tablename
	 * @return
	 * @throws Exception
	 */
	public String getDDLTruncateTable(String schema, String tablename)
			throws Exception;

	/**
	 * 
	 * 获得一个无名称的保存点 保存点的表示形式，保存点是可以从 Connection.rollback 方法引用的当前事务中的点。
	 * 将事务回滚到保存点时，在该保存点之后所作的全部更改都将被撤消。 保存点可以是命名的，也可以是未命名的。 未命名的保存点由基础数据源生成的 ID
	 * 来标识。
	 * 
	 * @return
	 * @throws Exception
	 */
	public Savepoint setSavepoint() throws Exception;

	/**
	 * 
	 * 获得一个保存点，设置这个保存点的名称
	 * 
	 * @param savePointName
	 * @return
	 * @throws Exception
	 */
	public Savepoint setSavepoint(String savePointName) throws Exception;

	/**
	 * 
	 * 释放这个保存点
	 * 
	 * @param savepoint
	 * @throws Exception
	 */
	public void releaseSavepoint(Savepoint savepoint) throws Exception;

	/**
	 * 
	 * 从保存点回滚数据
	 * 
	 * @param savepoint
	 * @throws Exception
	 */
	public void rollback(Savepoint savepoint) throws Exception;

	/**
	 * 
	 * 获得这个表的主键列名
	 * 
	 * @param tablename
	 * @return
	 * @throws Exception
	 */
	public String[] getPrimaryKeyColumnNames(String tablename) throws Exception;

	/**
	 * 
	 * 获得数据库中所有的序列名称
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getSequences() throws Exception;
	
	/**
	 * 格式化sql
	 * @param sbsql
	 * @return
	 */
	public String stripCR(String sbsql);

	public String callProcedure(String arg[], String argdir[], int argtype[],
                                String resultname, int resulttype) throws Exception;

	public List<FieldInfoVo> getTableFields(String tablename) throws Exception;
	
	public List<FieldInfoVo> getQuerySqlFields(String sql, String schema) throws Exception;

	public String[] getReservedWords();

	public String testConnection();

	public String getInsertStatement(String schema, String tablename,
                                     RowModel rowmodel);

	public String getInsertStatement(String tablename, RowModel rowmodel);

	public void setValues(RowModel rowmodel, RowData rowdata,
                          PreparedStatement ps) throws Exception;
	public List<Object[]> getRows(String sql, int limit) throws
	 Exception;
	
	public List<Map<String, Object>> getMapRows(String sql, int limit) throws
	 Exception;
	// public Object[] getLookup() throws KettleException

	// public String getDDL(String tablename, RowMetaInterface fields) throws
	// KettleException
	// public String getCreateTableStatement(String tableName, RowMetaInterface
	// fields, String tk, boolean use_autoinc, String pk, boolean semicolon)
	// public String getAlterTableStatement(String tableName, RowMetaInterface
	// fields, String tk, boolean use_autoinc, String pk, boolean semicolon)
	// throws KettleException

	// public String getSQLOutput(String schemaName, String tableName,
	// RowMetaInterface fields, Object[] r,String dateFormat) throws
	// KettleException

	// public String getDDLCreationTable(String tableName, RowMetaInterface
	// fields)
	// public RowMetaAndData callProcedure(String arg[], String argdir[], int
	// argtype[],String resultname, int resulttype) throws KettleException
	public Object[] getRow(ResultSet rs) throws Exception;// 对返回的数据进行格式化到
	// rowmodle模型

	/**
	 * Prepare inserting values into a table, using the fields & values in a Row
	 * 
	 * @param rowMeta
	 *            The row metadata to determine which values need to be inserted
	 * @param table
	 *            The name of the table in which we want to insert rows
	 * @throws KettleException
	 *             if something went wrong.
	 */
	// public void prepareInsert(Row rowMeta, String tableName) throws
	// Exception;
	
	public List<Object[]> getRowsWithHeader(String sql, int limit) throws Exception;
}
