package org.pentaho.di.core.database;

import java.sql.Driver;

import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
import org.pentaho.di.core.row.ValueMetaInterface;

@DatabaseMetaPlugin(type = "MONGODB", typeDescription = "MongoDB")
public class MongoDBDatabaseMeta extends BaseDatabaseMeta implements
		DatabaseInterface {
	protected static final String JAR_FILE = "mongodb_unityjdbc_full.jar";
	protected static final String DRIVER_CLASS_NAME = "mongodb.jdbc.MongoDriver";

	protected Integer driverMajorVersion;
	protected Integer driverMinorVersion;

	@Override
	public String getFieldDefinition(ValueMetaInterface v, String tk,
			String pk, boolean use_autoinc, boolean add_fieldname,
			boolean add_cr) {

	    String retval = "";

	    String fieldname = v.getName();
	    int length = v.getLength();
	    int precision = v.getPrecision();

	    if ( add_fieldname ) {
	      retval += fieldname + " ";
	    }

	    int type = v.getType();
	    switch ( type ) {

	      case ValueMetaInterface.TYPE_BOOLEAN:
	        retval += "BOOLEAN";
	        break;

	      //  Hive does not support DATE until 0.12
	      case ValueMetaInterface.TYPE_DATE:
	          retval += "DATE";
	          break;

	      // Hive does not support DATE until 0.8
	      case ValueMetaInterface.TYPE_TIMESTAMP:
	          retval += "TIMESTAMP";
	        break;

	      case ValueMetaInterface.TYPE_STRING:
	        retval += "STRING";
	        break;

	      case ValueMetaInterface.TYPE_NUMBER:
	      case ValueMetaInterface.TYPE_INTEGER:
	      case ValueMetaInterface.TYPE_BIGNUMBER:
	        // Integer values...
	        if ( precision == 0 ) {
	          if ( length > 9 ) {
	            if ( length < 19 ) {
	              // can hold signed values between -9223372036854775808 and 9223372036854775807
	              // 18 significant digits
	              retval += "BIGINT";
	            } else {
	              retval += "FLOAT";
	            }
	          } else {
	            retval += "INT";
	          }
	        } else {
	          // Floating point values...
	          if ( length > 15 ) {
	            retval += "FLOAT";
	          } else {
	            // A double-precision floating-point number is accurate to approximately 15 decimal places.
	            // http://mysql.mirrors-r-us.net/doc/refman/5.1/en/numeric-type-overview.html
	            retval += "DOUBLE";
	          }
	        }
	        break;
	    }

	    return retval;
	  }

	@Override
	public String getDriverClass() {
		return DRIVER_CLASS_NAME;
	}

	@Override
	public String getURL(String hostname, String port, String databaseName)
			throws KettleDatabaseException {
		return "jdbc:mongo://" + hostname + ":" + port + "/" + databaseName;
	}

	@Override
	public String getAddColumnStatement(String tablename, ValueMetaInterface v,
			String tk, boolean use_autoinc, String pk, boolean semicolon) {
		//return "ALTER TABLE " + tablename + " ADD " + getFieldDefinition( v, tk, pk, use_autoinc, true, false );
		return null;
	}

	@Override
	public String getModifyColumnStatement(String tablename,
			ValueMetaInterface v, String tk, boolean use_autoinc, String pk,
			boolean semicolon) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getTruncateTableStatement(String tableName) {
		return "drop TABLE " + tableName;
	}

	@Override
	public String[] getUsedLibraries() {
		return new String[] { JAR_FILE };
	}

	protected synchronized void initDriverInfo() {
		Integer majorVersion = 0;
		Integer minorVersion = 0;
		try {
			// Load the driver version number
			Class<?> driverClass = Class.forName(DRIVER_CLASS_NAME); //$NON-NLS-1$
			if (driverClass != null) {
				Driver driver = (Driver) driverClass.getConstructor()
						.newInstance();
				majorVersion = driver.getMajorVersion();
				minorVersion = driver.getMinorVersion();
			}
		} catch (Exception e) {
			// Failed to load the driver version, leave at the defaults
		}
		driverMajorVersion = majorVersion;
		driverMinorVersion = minorVersion;
	}

	@Override
	public int[] getAccessTypeList() {
		return new int[] { DatabaseMeta.TYPE_ACCESS_NATIVE };
	}

	@Override
	public String getSelectCountStatement(String tableName) {
		return "select count(1) from " + tableName;
	}
}
