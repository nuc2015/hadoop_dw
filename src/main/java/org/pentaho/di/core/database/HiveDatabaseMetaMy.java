package org.pentaho.di.core.database;

import java.sql.Driver;

import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
import org.pentaho.di.core.row.ValueMetaInterface;

@DatabaseMetaPlugin(type = "HIVE", typeDescription = "Hadoop Hive")
public class HiveDatabaseMetaMy extends BaseDatabaseMeta implements DatabaseInterface {
	private static final String JAR_FILE = "hive-jdbc-0.9.0-cdh4.1.2.jar";
	protected Integer driverMajorVersion;
	protected Integer driverMinorVersion;

	public HiveDatabaseMetaMy() throws Throwable {
	}

	HiveDatabaseMetaMy(int majorVersion, int minorVersion) throws Throwable {
		this.driverMajorVersion = Integer.valueOf(majorVersion);
		this.driverMinorVersion = Integer.valueOf(minorVersion);
	}

	@Override
	public int[] getAccessTypeList() {
		return new int[] { 0 };
	}

	@Override
	public String getAddColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean useAutoinc,
			String pk, boolean semicolon) {
		return "ALTER TABLE " + tablename + " ADD " + getFieldDefinition(v, tk, pk, useAutoinc, true, false);
	}

	@Override
	public String getDriverClass() {
		return "org.apache.hadoop.hive.jdbc.HiveDriver";
	}

	@Override
	public String getFieldDefinition(ValueMetaInterface v, String tk, String pk, boolean useAutoinc,
			boolean addFieldname, boolean addCr) {
		String retval = "";

		String fieldname = v.getName();
		int length = v.getLength();
		int precision = v.getPrecision();

		if (addFieldname) {
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

	@Override
	public String getModifyColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean useAutoinc,
			String pk, boolean semicolon) {
		return "ALTER TABLE " + tablename + " MODIFY " + getFieldDefinition(v, tk, pk, useAutoinc, true, false);
	}

	@Override
	public String getURL(String hostname, String port, String databaseName) throws KettleDatabaseException {
		return "jdbc:hive://" + hostname + ":" + port + "/" + databaseName;
	}

	@Override
	public String[] getUsedLibraries() {
		return new String[] { JAR_FILE };
	}

	@Override
	public String getSelectCountStatement(String tableName) {
		return "select count(1) from " + tableName;
	}

	@Override
	public String generateColumnAlias(int columnIndex, String suggestedName) {
		if (isDriverVersion(0, 6)) {
			return suggestedName;
		}

		return "_col" + String.valueOf(columnIndex);
	}

	protected synchronized void initDriverInfo() {
		Integer majorVersion = Integer.valueOf(0);
		Integer minorVersion = Integer.valueOf(0);
		try {
			Class driverClass = Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver");
			if (driverClass != null) {
				Driver driver = (Driver) driverClass.getConstructor(new Class[0]).newInstance(new Object[0]);
				majorVersion = Integer.valueOf(driver.getMajorVersion());
				minorVersion = Integer.valueOf(driver.getMinorVersion());
			}
		} catch (Exception e) {
		}
		this.driverMajorVersion = majorVersion;
		this.driverMinorVersion = minorVersion;
	}

	protected boolean isDriverVersion(int majorVersion, int minorVersion) {
		if (this.driverMajorVersion == null) {
			initDriverInfo();
		}

		if (majorVersion < this.driverMajorVersion.intValue()) {
			return true;
		}
		if (majorVersion == this.driverMajorVersion.intValue()) {
			if (minorVersion <= this.driverMinorVersion.intValue()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String getStartQuote() {
		return "";
	}

	@Override
	public String getEndQuote() {
		return "";
	}
}