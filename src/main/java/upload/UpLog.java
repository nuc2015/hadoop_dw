package upload;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;

/**
 * @author 刘卫卫
 * 2018年8月21日上午9:33:10
 */
public class UpLog extends TimerTask {

	@Override
	public void run() {
		try {
			//将该文件夹下的文件暂时移动到特temp中转文件夹
			File logDir = new File("D:\\Hadoop\\log");
			//中转文件夹
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
			String tempDirName = format.format(new Date());
			File tempDir = new File("D:\\Hadoop\\temp\\"+tempDirName+"_log");
			//如果文件不存在就创建
			if(!tempDir.exists()) {
				tempDir.mkdirs();
			}
			//过滤出以log.开头的文件
			File[] listFiles = logDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					if(pathname.getName().startsWith("log")) {
						return true;
					}else {
						return false;
					}
				}
			});
			//将文件移动到指定目录
			for (File file : listFiles) {
				file.renameTo(new File(tempDir+"\\"+file.getName()));
			}
			//将此目录下的文件上传到hdfs上
			FileSystem fsClient = FileSystem.get(new URI("hdfs://master.hadoop:9000/"), new Configuration(), "root");
			if(!fsClient.exists(new Path("/log/"+tempDir.getName()))) {
				fsClient.mkdirs(new Path("/log/"+tempDir.getName()), new FsPermission((short) 777));
			}
			File[] files = tempDir.listFiles();
			for (File file : files) {
				fsClient.copyFromLocalFile(new Path(file.getPath()), new Path("/log/"+tempDir.getName()+"/"+file.getName()));
			}
			fsClient.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
