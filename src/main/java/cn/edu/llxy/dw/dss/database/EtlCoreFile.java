package cn.edu.llxy.dw.dss.database;

import java.util.Date;


public class EtlCoreFile {
	
	private String filekey;
	private String filename;
	private String filepath;
	private long filesize;
	private long filelinesum;
	private String filedate;
	private String plugname;
	private String type;
	private Date filetime;
	
	public String getFilekey() {
		return filekey;
	}
	public void setFilekey(String filekey) {
		this.filekey = filekey;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public long getFilelinesum() {
		return filelinesum;
	}
	public void setFilelinesum(long filelinesum) {
		this.filelinesum = filelinesum;
	}
	public String getFiledate() {
		return filedate;
	}
	public void setFiledate(String filedate) {
		this.filedate = filedate;
	}
	public String getPlugname() {
		return plugname;
	}
	public void setPlugname(String plugname) {
		this.plugname = plugname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getFiletime() {
		return filetime;
	}
	public void setFiletime(Date filetime) {
		this.filetime = filetime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((filedate == null) ? 0 : filedate.hashCode());
		result = prime * result + ((filekey == null) ? 0 : filekey.hashCode());
		result = prime * result + (int) (filelinesum ^ (filelinesum >>> 32));
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		result = prime * result
				+ ((filepath == null) ? 0 : filepath.hashCode());
		result = prime * result + (int) (filesize ^ (filesize >>> 32));
		result = prime * result
				+ ((filetime == null) ? 0 : filetime.hashCode());
		result = prime * result
				+ ((plugname == null) ? 0 : plugname.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EtlCoreFile other = (EtlCoreFile) obj;
		if (filedate == null) {
			if (other.filedate != null)
				return false;
		} else if (!filedate.equals(other.filedate))
			return false;
		if (filekey == null) {
			if (other.filekey != null)
				return false;
		} else if (!filekey.equals(other.filekey))
			return false;
		if (filelinesum != other.filelinesum)
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (filepath == null) {
			if (other.filepath != null)
				return false;
		} else if (!filepath.equals(other.filepath))
			return false;
		if (filesize != other.filesize)
			return false;
		if (filetime == null) {
			if (other.filetime != null)
				return false;
		} else if (!filetime.equals(other.filetime))
			return false;
		if (plugname == null) {
			if (other.plugname != null)
				return false;
		} else if (!plugname.equals(other.plugname))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
