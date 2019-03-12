package api;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlJDBC {
    public static  String DBDRIVER="org.gjt.mm.mysql.Driver";
    public static  String DBURL="jdbc:mysql://localhost:3306/";
    public static  String DBUSER="root";
    public static  String DBPASS="240637";

    private static Connection conn = null;

    public static Connection getConn(String dbName){
        try{
            Class.forName(DBDRIVER);
            conn = DriverManager.getConnection(DBURL+dbName,DBUSER,DBPASS);
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
