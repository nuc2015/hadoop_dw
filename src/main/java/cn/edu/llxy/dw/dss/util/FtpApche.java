package cn.edu.llxy.dw.dss.util;

import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream; 
import java.io.OutputStream; 

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply; 

public class  FtpApche { 
	
	private static  boolean isSuccess = false; 
	
    private  FTPClient ftpClient = new FTPClient();  

//private static String encoding = System.getProperty("file.encoding"); 
		/**  	
		 *@param url:FTP服务器hostname  
		 * @param port:FTP服务器端口
		 *@param username:FTP登录账号
		 *@param password :FTP登录密码
		 *@param path:FTP服务器保存目录,如果是根目录则为“/”		
        */ 

 public  boolean connect(String url,int port,String username,String passWord,String path){ 
	  int replyCode = 0; 
	  try { 	
	   //设置编码格式,防止文件名称中出现中文乱码 
	   ftpClient.setControlEncoding("gbk"); 
	   //连接ftp服务器 
	   ftpClient.connect(url, port); 
	   replyCode = ftpClient.getReplyCode(); 
	   if(FTPReply.isPositiveCompletion(replyCode)){ 
		    //验证登录 
		    if(ftpClient.login(username, passWord)){ 
		     isSuccess = true; 
		    } 
	   }else{ 
	     ftpClient.disconnect(); 
	   } 
	   ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE); // 设置上传文件以二进制上传 
	   ftpClient.enterLocalPassiveMode();  //设置被动模式 
	   //切换到ftp服务器当前的工作目录 
	   boolean change= ftpClient.changeWorkingDirectory(path); 
	   if(!change){
		   System.out.println("ftp服务器目录不存在...");
		   return isSuccess; 
	   }
	  } catch (IOException e) { 
	   return isSuccess; 
	  } 
	  return isSuccess; 
	} 
       /** 
		 *@param filename:上传到FTP服务器上的文件名
		 *@param input:本地文件输入流
		 *@return 成功返回true，否则返回false
		 **/ 
	public  boolean uploadFile(String path, String filename, InputStream input) {
		      	
     try { 
    	 System.out.println("ftp正在上传文件......");
    	 ftpClient.enterLocalPassiveMode();//通知ftp server开通一个端口来传输数据,unix需要客户端需要调用此函数    	
        // 转移工作目录至指定目录下
         boolean change = ftpClient.changeWorkingDirectory(path);
         
         if(change){  		 
		        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		        
		        //生成缓存文件
		        String tmpFile=filename.replace(".txt", ".tmp");
		    	isSuccess = ftpClient.storeFile(new String(tmpFile), input);
		    
		            if (isSuccess) {
		             System.out.println("上传成功!"); 
		            }
		            
		          //将缓存文件修改回源文件
		           ftpClient.rename(tmpFile,filename);
		           
		            input.close();         
         }else{
        	 System.out.println("ftp服务器目录不存在...");
   		     return isSuccess; 
   	   }
    } catch (IOException e) {  
    	
        e.printStackTrace();
    }     
    return isSuccess;  

} 
	
	public  boolean downFile(String path, String fileName, OutputStream  os) {      	
	     try { 
	    	 System.out.println("ftp正在下载文件......");
	    	 ftpClient.enterLocalPassiveMode();//通知ftp server开通一个端口来传输数据,unix需要客户端需要调用此函数    	
	        // 转移工作目录至指定目录下
	         boolean change = ftpClient.changeWorkingDirectory(path);	         
	         if(change){  		 
		        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);			        
		        FTPFile[] ftpFiles = ftpClient.listFiles(); 
		        for(FTPFile f : ftpFiles){ 
		            if(f.getName().equals(fileName)){  
				        isSuccess = ftpClient.retrieveFile(f.getName(), os);  			    
			            if (isSuccess) {
			               System.out.println("下载成功!"); 
			            }			           
		            }
		        }
	         }else{
	        	 System.out.println("ftp服务器目录不存在...");
	   		     return isSuccess; 
	   	   }
	    } catch (IOException e) {  
	    	
	        e.printStackTrace();
	    }     
	    return isSuccess;  

	} 
	
	public InputStream downFile(String path, String fileName) {      	
	     try { 
	    	 System.out.println("ftp正在下载文件......");
	    	 ftpClient.enterLocalPassiveMode();//通知ftp server开通一个端口来传输数据,unix需要客户端需要调用此函数    	
	         // 转移工作目录至指定目录下
	         boolean change = ftpClient.changeWorkingDirectory(path);	         
	         if(change){  		 
		        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);			        
		        FTPFile[] ftpFiles = ftpClient.listFiles(); 
		        for(FTPFile f : ftpFiles){ 
		            if(f.getName().equals(fileName)){  
		            	InputStream bis = ftpClient.retrieveFileStream(f.getName());  
		            	return bis;
		            }
		        }
	         }
	    } catch (IOException e) {  	    	
	        e.printStackTrace();
	    }     
	    return null;  

	} 
	
	public boolean ftpUploadFile(String localFileDir,String ftpDatDir,String ftpIdnoDir) {		
		FileInputStream fis = null;
		boolean sucFlag = true;  
		try{ 
			ftpClient.enterLocalPassiveMode();
			// 上传FTP
			File localFile = new File(localFileDir);
			if (localFile.exists()) {
				File [] files = localFile.listFiles();
				int j=0;//记录文件传输个数
				
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					String fileName = file.getName();
					//生成缓存文件
					//String file1 = fileName.replace(".dat", "").replace(".txt", "");
			        String tmpFile=fileName.substring(0,fileName.length()-4)+".tmp";
						fis = new FileInputStream(file);
					boolean bool = false;
					if (fileName.endsWith("_001.txt") || fileName.endsWith("_001.dat")) {						
							bool = ftpClient.changeWorkingDirectory(ftpDatDir);	
						if (bool) {
								boolean a = ftpClient.storeFile(new String(tmpFile), fis);		
								if(a){
									ftpClient.rename(tmpFile,fileName);
									j++;
									System.out.println("FTP上传文件成功:"+fileName);
								}else{
									System.out.println("FTP上传文件失败:"+fileName);
									sucFlag=false;
								}							
								fis.close();			
						}
					}else{
						bool = ftpClient.changeWorkingDirectory(ftpIdnoDir);
						if (bool) {
							boolean a = ftpClient.storeFile(new String(tmpFile), fis);
							if(a){
								ftpClient.rename(tmpFile,fileName);
								j++;
								System.out.println("FTP上传文件成功:"+fileName);
							}else{
								System.out.println("FTP上传文件失败:"+fileName);
								sucFlag=false;
							}				
							fis.close();
						}
					}
				}
				System.out.println("FTP成功上传"+j+"个文件，上传失败"+(files.length-j)+"个文件"); 
			}
			}catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
				   sucFlag=false;
					e.printStackTrace();
			}catch (IOException e) {
				   sucFlag=false;
					// TODO Auto-generated catch block
					e.printStackTrace();
			}finally{
				this.ftpClosed();
			}
	    return sucFlag;
	}	
	
 
public  boolean ftpClosed(){	
	try {
		ftpClient.completePendingCommand();
	} catch (IOException e) {
		e.printStackTrace();
	}
	if (ftpClient.isConnected()) {
        try {
          ftpClient.logout();
          ftpClient.disconnect();
        } catch (IOException ioe) {
        	ioe.printStackTrace(); 
        }
	}
	return isSuccess;
}


/**
 * 将本地文件上传到FTP服务器上
 *
 */
public static void main(String[] args)  {
	
	//String  file_dir="e:/test/20130921_001~99.dat";
	 String  file_dir="e:/test/20130921_001.dat";
	try {	   	     		
	      int j=0;//记录上传成功文件的个数    	  
	      String [] file_len = file_dir.split("~");
	
	   	 FtpApche fa = new FtpApche();    	 
	   	 isSuccess = fa.connect("10.204.96.21", 21, "weblogic","web0608@@", "/data/zuoftp/");
	
		   	if(isSuccess){
		   	 if( 1 == file_len.length ){	   	
		   		//上传控制文件 
		      String localFile = file_len[0];    		 
		   	   File file = new File(localFile);
	    		 
	    		 if(file.exists()){
	    		        FileInputStream in = new FileInputStream(file);
	    		        boolean flag = fa.uploadFile("/data/zuoftp/", file.getName(), in);
	    		        System.out.println("FTP上传文件成功:"+localFile);
	    		       j= j+1;
	    		    	}else{
	    		    		System.out.println("FTP上传文件不存在："+localFile);
	    		    	}   
	    		//上传数据文件文件 
	    		localFile = file_len[0].replace(".dat", ".txt");    		 
		   	    file = new File(localFile);
		   		 if(file.exists()){
		   		        FileInputStream in = new FileInputStream(file);
		   		        boolean flag = fa.uploadFile("/data/zuoftp/", file.getName(), in);
		   		        System.out.println("FTP上传文件成功:"+localFile);
		   		        
		   		          j= j+1;
		   		        
		   		    	}else{
		    		    		System.out.println("FTP上传文件不存在："+localFile);
		    		    	}    	    		 
		   	       //常态化处理
		   	  }else if ( 2 == file_len.length ){
		   	 
					 for (int i = 0; i <= 99; i++){   		
			    			 
						 String	 localFile = file_len[0].substring(0,file_dir.lastIndexOf('_'))+"_"+ (i<10?"0"+i:i) +".txt";       				 
						 File file = new File( localFile );  
			   			  
			       		 if(file.exists()){        			 
			       		        FileInputStream in = new FileInputStream(file);
			       		        boolean flag = fa.uploadFile("/data/zuoftp/", file.getName(), in);
			       		        System.out.println("FTP上传文件成功:"+localFile);        
			       		        
			       		        j= j+1;
			       		    	}else{
			       		    		System.out.println("FTP上传文件不存在："+localFile);
			        		    	} 
			   		 }
		   	 }
 	
		   	fa.ftpClosed();
		   	System.out.println("FTP成功上传"+j+"个文件");
 	
	   	}else{
	   		System.out.println("FTP服务器连接失败...");		   		
	   	  }
	   	
 	} catch (Exception e) { 
      e.printStackTrace(); 
     }
 }  
   
}

