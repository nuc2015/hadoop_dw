package cn.edu.llxy.dw.dss.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

//import org.apache.tools.ant.util.FileUtils;




import de.schlichtherle.truezip.file.TArchiveDetector;
import de.schlichtherle.truezip.file.TConfig;
import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileInputStream;
import de.schlichtherle.truezip.file.TFileOutputStream;
import de.schlichtherle.truezip.file.TFileReader;
import de.schlichtherle.truezip.file.TVFS;
import de.schlichtherle.truezip.fs.archive.zip.JarDriver;
//import de.schlichtherle.truezip.fs.archive.zip.JarDriver;
import de.schlichtherle.truezip.socket.sl.IOPoolLocator;

/**
 * 
 */
public class JarUtil {
	
	/**
	 * @param jar	jar文件中需要修改的文件名全路径
	 * @param map	需要修改的键值对
	 * @throws IOException
	 */
	public static void updateJar(File jar, Map<String, String> map) throws IOException{
		
		TConfig.get().setArchiveDetector(new TArchiveDetector("jar", new JarDriver(IOPoolLocator.SINGLETON)));
		TFile entry = new TFile(jar);
		
		updateOther(entry, map);
		TVFS.umount();
	}
	
	/**
	 * @param jar
	 *            jar文件全路径
	 * @param fileName
	 *            需要修改的文件名
	 * @param map
	 *            需要修改的键值对
	 * @throws IOException
	 */
	public static void updateJar(File jar, String fileName, Map<String, String> map) throws IOException{
		TConfig.get().setArchiveDetector(new TArchiveDetector("jar", new JarDriver(IOPoolLocator.SINGLETON)));
		update(jar, fileName, map);
		TVFS.umount();
	}
	
	private static void update(File jar, String fileName, Map<String, String> map) throws IOException {
		TFile entry = new TFile(jar);
		if (entry.isDirectory()) {
			for (TFile member : entry.listFiles())
				update(member, fileName, map);
		} else if (entry.isFile()) {
			if (entry.getName().equals(fileName)) {
				if (fileName.endsWith(".properties")) {
					updateProperties(entry, map);
				} else {
					updateOther(entry, map);
				}
			} else {// else is special file or non-existent
			}
		}
	}

	private static void updateProperties(TFile file, Map<String, String> map) throws IOException {
		Properties properties = new Properties();
		InputStream in = new TFileInputStream(file);
		try {
			properties.load(in);
		} finally {
			in.close();
		}

		for (String key : map.keySet()) {
			properties.setProperty(key, map.get(key));// [your updates here]
		}

		OutputStream out = new TFileOutputStream(file);
		try {
			properties.store(out, "updated");
		} finally {
			out.close();
		}
	}

	private static void updateOther(TFile file, Map<String, String> map) {
		Reader reader = null;
		OutputStream out = null;
		try {
			reader = new TFileReader(file);
			String context = FileUtil.readFully(reader);
			context = StringUtil.substitute(context, map);
			out = new TFileOutputStream(file);
			out.write(context.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
