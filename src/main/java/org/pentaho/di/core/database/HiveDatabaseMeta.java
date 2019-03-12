/*
 * Copyright (c) 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * Data Integration.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 */

package org.pentaho.di.core.database;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.row.ValueMetaInterface;

/**
 * Contains Hive specific information through static final members
 * Copy from MySQL
 */

public class HiveDatabaseMeta extends BaseDatabaseMeta implements DatabaseInterface {
	@Override
	public int[] getAccessTypeList() {
		return new int[] { DatabaseMeta.TYPE_ACCESS_NATIVE };
	}

	@Override
	public int getDefaultDatabasePort() {
		if (getAccessType() == DatabaseMeta.TYPE_ACCESS_NATIVE)
			return 10000;
		return -1;
	}

	@Override
	public String getLimitClause(int nrRows) {
		return " LIMIT " + nrRows;
	}

	/**
	 * Returns the minimal SQL to launch in order to determine the layout of the
	 * resultset for a given database table
	 * 
	 * @param tableName
	 *            The name of the table to determine the layout for
	 * @return The SQL to launch.
	 */
	@Override
	public String getSQLQueryFields(String tableName) {
		tableName = tableName.replaceAll("\"", "");
		return "SELECT * FROM " + tableName + " LIMIT 0"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String getSQLTableExists(String tablename) {
		return getSQLQueryFields(tablename);
	}
	
	public String getSchemaTableCombination(String schema_name, String table_part)
	{
		return schema_name+"."+table_part;
	}

	@Override
	public String getSQLColumnExists(String columnname, String tablename) {
		return getSQLQueryColumnFields(columnname, tablename);
	}

	public String getSQLQueryColumnFields(String columnname, String tableName) {
		return "SELECT " + columnname + " FROM " + tableName + " LIMIT 0"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * @see org.pentaho.di.core.database.DatabaseInterface#getNotFoundTK(boolean)
	 */
	// TODO
	@Override
	public int getNotFoundTK(boolean use_autoinc) {
		if (supportsAutoInc() && use_autoinc) {
			return 1;
		}
		return super.getNotFoundTK(use_autoinc);
	}

	@Override
	public String getDriverClass() {
		return "org.apache.hadoop.hive.jdbc.HiveDriver";
	}

	@Override
	public String getURL(String hostname, String port, String databaseName) {
		if (Const.isEmpty(port)) {
			return "jdbc:hive://" + hostname + ":" + getDefaultDatabasePort() + "/" + databaseName;
		} else {
			return "jdbc:hive://" + hostname + ":" + port + "/" + databaseName;
		}
	}

	/**
	 * @return true if the database supports transactions.
	 */
	@Override
	public boolean supportsTransactions() {
		return false;
	}

	/**
	 * @return true if the database supports bitmap indexes
	 */
	@Override
	public boolean supportsBitmapIndex() {
		return false;
	}

	/**
	 * @return true if the database supports views
	 */
	@Override
	public boolean supportsViews() {
		return false;
	}

	/**
	 * @return true if the database supports synonyms
	 */
	@Override
	public boolean supportsSynonyms() {
		return false;
	}

	/**
	 * Generates the SQL statement to add a column to the specified table
	 * 
	 * @param tablename
	 *            The table to add
	 * @param v
	 *            The column defined as a value
	 * @param tk
	 *            the name of the technical key field
	 * @param use_autoinc
	 *            whether or not this field uses auto increment
	 * @param pk
	 *            the name of the primary key field
	 * @param semicolon
	 *            whether or not to add a semi-colon behind the statement.
	 * @return the SQL statement to add a column to the specified table
	 */
	@Override
	public String getAddColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon) {
		return "ALTER TABLE " + tablename + " ADD " + getFieldDefinition(v, tk, pk, use_autoinc, true, false);
	}

	/**
	 * Generates the SQL statement to modify a column in the specified table
	 * 
	 * @param tablename
	 *            The table to add
	 * @param v
	 *            The column defined as a value
	 * @param tk
	 *            the name of the technical key field
	 * @param use_autoinc
	 *            whether or not this field uses auto increment
	 * @param pk
	 *            the name of the primary key field
	 * @param semicolon
	 *            whether or not to add a semi-colon behind the statement.
	 * @return the SQL statement to modify a column in the specified table
	 */
	//TODO
	@Override
	public String getModifyColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon) {
		return "ALTER TABLE " + tablename + " MODIFY " + getFieldDefinition(v, tk, pk, use_autoinc, true, false);
	}

	//TODO
	@Override
	public String getFieldDefinition(ValueMetaInterface v, String tk, String pk, boolean use_autoinc, boolean add_fieldname, boolean add_cr) {
		String retval = "";

		String fieldname = v.getName();
		int length = v.getLength();
		int precision = v.getPrecision();

		if (add_fieldname) {
			retval = retval + fieldname + " ";
		}

		int type = v.getType();
		switch (type) {
		case 4:
			retval = retval + "BOOLEAN";
			break;
		case 3:
			retval = retval + "STRING";
			break;
		case 2:
			retval = retval + "STRING";
			break;
		case 1:
		case 5:
		case 6:
			if (precision == 0) {
				if (length > 9) {
					if (length < 19) {
						retval = retval + "BIGINT";
					} else {
						retval = retval + "FLOAT";
					}
				} else {
					retval = retval + "INT";
				}

			} else if (length > 15) {
				retval = retval + "FLOAT";
			} else {
				retval = retval + "DOUBLE";
			}

			break;
		}

		return retval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pentaho.di.core.database.DatabaseInterface#getReservedWords()
	 */
	//TODO
	@Override
	public String[] getReservedWords() {
		return new String[] { "ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE",
				"CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONNECTION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS",
				"CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND",
				"DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL",
				"EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FLOAT", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "GOTO",
				"GRANT", "GROUP", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT",
				"INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "KEY", "KEYS", "KILL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT",
				"LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCATE", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MATCH", "MEDIUMBLOB", "MEDIUMINT",
				"MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL", "NUMERIC", "ON", "OPTIMIZE",
				"OPTION", "OPTIONALLY", "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "POSITION", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "READ", "READS", "REAL", "REFERENCES",
				"REGEXP", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT",
				"SENSITIVE", "SEPARATOR", "SET", "SHOW", "SMALLINT", "SONAME", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT",
				"SQL_CALC_FOUND_ROWS", "SQL_SMALL_RESULT", "SSL", "STARTING", "STRAIGHT_JOIN", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING",
				"TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES",
				"VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "WHEN", "WHERE", "WHILE", "WITH", "WRITE", "XOR", "YEAR_MONTH", "ZEROFILL" };
	}

	@Override
	public String[] getUsedLibraries() {
		return new String[] { "hive-jdbc-0.9.0-cdh4.1.2.jar" };
	}
}
