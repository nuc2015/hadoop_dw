package api;

import org.apache.hadoop.conf.Configuration;
import org.apache.sqoop.Sqoop;
import org.apache.sqoop.hive.HiveConfig;
import org.apache.sqoop.tool.SqoopTool;
import org.apache.sqoop.util.OptionsFileUtil;

public class SqoopTest {
    public static void main(String[] args) throws Exception {
        System.out.println(" begin test sqoop");
        String[] argument = new String[] {
                "--connect","jdbc:mysql://localhost:3306/hadoop_dw",
                "--username","root",
                "--password","root",
                "--table","user",
                "--null-string","'\\\\N'",
                "--null-non-string","'\\\\N'",
                "--target-dir", "/windows/hadoop_dw/user",
                "--delete-target-dir",
                "--num-mappers","1",
                "--hive-import",
                "--hive-database", "hadoop_dw",
                "--hive-table", "user",
                "--hive-drop-import-delims",
                "--create-hive-table",
                "--hive-overwrite",
                "--fields-terminated-by", "\\|"
        };
        String[] expandArguments = null;
        try {
            expandArguments = OptionsFileUtil.expandArguments(args);
        } catch (Exception e){
            System.err.println(e.getMessage());
            System.err.println("Try 'sqoop help' for usage.");
        }
        SqoopTool sqoopTool = SqoopTool.getTool("import");
        Configuration conf= new Configuration();
        conf.set("fs.defaultFS","hdfs://master.hadoop:9000");
        Configuration loadPlugins = SqoopTool.loadPlugins(conf);
        Configuration hive=HiveConfig.getHiveConf(conf);
        Sqoop sqoop = new Sqoop((com.cloudera.sqoop.tool.SqoopTool) sqoopTool, loadPlugins);
        int res = Sqoop.runSqoop(sqoop, expandArguments);
        System.out.println(res);
        if (res == 0){
            System.out.println("成功");
        }
        System.out.println("执行sqoop结束");
    }
}



