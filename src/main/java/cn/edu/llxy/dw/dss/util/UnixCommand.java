package cn.edu.llxy.dw.dss.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class UnixCommand {
	private static Logger log = Logger.getLogger(UnixCommand.class);
	private final static Integer UnixCommandThreadNum = 100;
	private  ScheduledExecutorService executor;
	
	private  String wcpath=null;
	public UnixCommand(){
		executor = new ScheduledThreadPoolExecutor(UnixCommandThreadNum);
			try {
				wcpath = new CfgUtils().getProperty("wc.path");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public String WCCommand(File file) throws Exception {
		if (!file.exists())
			throw new Exception("file("+file.getPath()+") not exists;");
		if(file.isDirectory())
			throw new Exception("file("+file.getPath()+") is a Directory;");

		if(file.length()<1)
			return "0";

		String filepath = file.getPath();
		String command = "wc -l " + filepath;
		String result = this.runtimeExce(command);
		log.info("run wc result:" + result);
		if (result.equals("-1"))
			throw new Exception("file("+file.getPath()+") wc error;");

		String lineNum = result.trim().split("[ ]")[0];
		if (lineNum == null || StringUtils.isEmpty(lineNum.trim())) {
			log.info("run wc is empty, do it again.");
			result = this.runtimeExce(command);
			log.info("run wc result:" + result);
			if (result.equals("-1"))
				throw new Exception("file("+file.getPath()+") wc error;");

			 lineNum = result.trim().split("[ ]")[0];
			if (lineNum == null || StringUtils.isEmpty(lineNum.trim())) {
				log.info("run wc is empty, useApacheExec.");
				lineNum = execWCsh(file);
				log.info("run wc result:" + lineNum);
			}
		}
		lineNum = lineNum.trim();
		if(lineNum.isEmpty())
			throw new Exception("file("+file.getPath()+") wc is empty;");
		if(!StringUtil.isNumeric(lineNum))
			throw new Exception("file("+file.getPath()+") wc is not numeric;");
		Long line = Long.parseLong(lineNum);
		if(line<1)
			throw new Exception("file("+file.getPath()+") wc is 0;");

		return lineNum;

	}
	private String execWCsh(File file) throws Exception{
		String shStr = "PATH="+wcpath+"\n"
				+ " export PATH \n"
				+ " wc -l " +file.getAbsolutePath();
		File shFile = new File(file.getParentFile(),file.getName()+".sh");
		if(shFile.exists())
			shFile.delete();
		try{
			FileUtil.WriteContentToFile(shFile,shStr);
			FileUtil.makeFileExecutable(shFile);

			CommandLine commandLine = new CommandLine("sh");
			commandLine.addArgument(shFile.getAbsolutePath());
			ByteArrayOutputStream outstream=new ByteArrayOutputStream();
			PumpStreamHandler executestreamhandler=new PumpStreamHandler(outstream);
			DefaultExecutor executor=new DefaultExecutor();
			executor.setWorkingDirectory(file.getParentFile());
			executor.setStreamHandler(executestreamhandler);
			String outmsg = null;
			executor.execute(commandLine);
			outmsg = outstream.toString().trim();
			outstream.close();
			return outmsg.split("[ ]")[0];
		}catch(Exception e){
			e.printStackTrace();
			throw e;
			//return "wc is wrong!";
		}finally{
			if(shFile.exists())
				shFile.delete();
		}

	}

	public  String runtimeExce(String comm) {
		if(wcpath!=null){
			 String[] envp = new String[1];
			 envp[0]= ("PATH="+wcpath);
			 return runtimeExce(comm, envp, null);
		}else
			return runtimeExce(comm, null, null);
	}

	public  String sevenZipCommand(String command, String[] envp, File dir) {
		return runtimeExce(command, envp, dir);
	}

	private String runtimeExce(String comm, String[] envp, File dir) {
		String text = "-1";
		Process proc = null;
		StringWriter outputStr = new StringWriter();
		StringWriter errputStr = new StringWriter();
		try {
			log.debug("runtimeExe comm>>>>" + comm);
			Runtime runtime = Runtime.getRuntime();
			proc = runtime.exec(comm, envp, dir);

			Thread errorGobbler = constructThread(proc.getErrorStream(), "Error", errputStr);
			Thread outputGobbler = constructThread(proc.getInputStream(), "Output", outputStr);

			executor.execute(errorGobbler);
			executor.execute(outputGobbler);

			log.debug("before proc.waitFor();");
			proc.waitFor();
			log.debug("after proc.waitFor();");

			int i = 0;
			text = outputStr.toString();
			while ((outputGobbler.isAlive() || errorGobbler.isAlive() || StringUtils.isEmpty(outputStr.toString())) && i < 6000) {
				Thread.sleep(100);
				text = outputStr.toString();
				i++;
			}
			log.debug("runtimeExce---out-->" + text);

			if (StringUtils.isNotEmpty(errputStr.toString())) {
				log.error(errputStr.toString());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("[runtimeExce]exception:", e);
		} finally {
			if (proc != null) {
				proc.destroy();
			}
		}
		return text;
	}

	private static Thread constructThread(InputStream is, String type, StringWriter output) {
		return new StreamGobbler(is, type, output);
	}

	/**
	 * 执行导出脚本
	 * @param tmpScriptFile
	 * @return
	 * @throws CheckedException
	 * @throws IOException
	 * @throws ExecuteException
	 */
	public static String executeScript(File scriptFile,File workDir, String charsetName) throws ExecuteException, IOException {
		CommandLine commandLine = new CommandLine("sh");
		commandLine.addArgument(scriptFile.getAbsolutePath());
		ByteArrayOutputStream outstream=new ByteArrayOutputStream();
		PumpStreamHandler executestreamhandler=new PumpStreamHandler(outstream);
		DefaultExecutor executor=new DefaultExecutor();
		executor.setWorkingDirectory(workDir);
		executor.setStreamHandler(executestreamhandler);
		String outmsg = null;
		executor.execute(commandLine);
		outmsg = StringUtils.isEmpty(charsetName) ? outstream.toString().trim() : outstream.toString(charsetName).trim();
		outstream.close();
		return outmsg;
	}

}
