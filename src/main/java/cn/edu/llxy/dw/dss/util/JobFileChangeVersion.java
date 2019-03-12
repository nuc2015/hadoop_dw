package cn.edu.llxy.dw.dss.util;

import java.io.File;
import java.util.Date;
import java.util.List;

import cn.edu.llxy.dw.dss.cfg.Const;


public class JobFileChangeVersion {
	private String BAKCDATAROOT;//= ConfigFacade.getProperty("back.data.root");
	private String DATAROOT ;//= ConfigFacade.getProperty("data.root");
	
	public static final String JOB_UPDATE="update";
	public static final String JOB_CREATE="create";
	public static final String JOB_RENAME="rename";
	public static final String JOB_DELETE="delete";
	
	private static final String VERSION_SEPARATOR="_";
	private static final String SEPARATOR=".";
	private static final String VERSIONNUMBER_SEPARATOR="@";
	
	public JobFileChangeVersion(String datdir,String bakdatdir){
		this.DATAROOT=datdir;
		this.BAKCDATAROOT=bakdatdir;
	}
	
	public  void FileCreate(File file,File srcfile){
		try {
			File createfile=FileUtil.ReplaceBasePath(DATAROOT, BAKCDATAROOT, file);
			FileUtil.checkDirAndCreate(createfile.getParent());
			File baseversion=new File(createfile.getParent(),createfile.getName()+VERSION_SEPARATOR+JOB_CREATE+VERSIONNUMBER_SEPARATOR+"0.0");
			if(!baseversion.exists()){
				baseversion.createNewFile();
				if(srcfile!=null)
					FileUtil.copyFile(srcfile, baseversion);
			}
			else
				FileChange(file,JOB_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void FileRename(File srcfile,File tagfile){
		FileChange(srcfile, JOB_RENAME);
		FileCreate(tagfile,srcfile);
	}
	
	public  void FileUpdate(File srcfile){
		FileChange(srcfile, JOB_UPDATE);
	}
	
	public  void FileDelete(File srcfile){
		
		FileChange(srcfile, JOB_DELETE);
	}
	
	private  void FileChange(File file,String changetype){
		try {
			File oldfile=FileUtil.ReplaceBasePath(DATAROOT, BAKCDATAROOT, file);
			List<File> vesionfiles=FileUtil.FileListForWildcardFileFilter(oldfile.getParentFile(), oldfile.getName()+VERSION_SEPARATOR+"*", false);
			if(Const.isEmpty(vesionfiles))
				FileCreate(file, file);
			else{
				int lastverion=0;
				for (File historyfile : vesionfiles) {
					String versionStr=historyfile.getName().substring(historyfile.getName().lastIndexOf(VERSIONNUMBER_SEPARATOR)+1);
					int versionNr=NumberUtil.toInt(versionStr.substring(0,versionStr.lastIndexOf(".")));
					if(versionNr>lastverion)
						lastverion=versionNr;
				}
				File newversion=new File(oldfile.getParent(),oldfile.getName()+VERSION_SEPARATOR+getTimestamp()+SEPARATOR+changetype+VERSIONNUMBER_SEPARATOR+(lastverion+1)+".0");
				FileUtil.copyFile(file, newversion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private  String getTimestamp(){
		return DateFormatUtil.formatDateShort14(new Date());
	}
}
