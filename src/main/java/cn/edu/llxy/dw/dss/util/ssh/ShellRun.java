package cn.edu.llxy.dw.dss.util.ssh;

import java.io.ByteArrayOutputStream;

public class ShellRun {
	public String execShell(String exedir, String command, String ip, Integer port, String userName, String password) throws Exception {
		ByteArrayOutputStream out = null;
		SSHExec sshexe = new SSHExec();
		sshexe.setCommand_dir(exedir);
		sshexe.setCommand(command);
		sshexe.setHost(ip);
		sshexe.setPort(port);
		sshexe.setUsername(userName);
		sshexe.setPassword(password);
		sshexe.setFailonerror(false);
		sshexe.setTrust(true);
		sshexe.setVerbose(false);
		out = (ByteArrayOutputStream) sshexe.execute();
		return out.toString();
	}
	public static void main(String[] args){
		ShellRun shr = new ShellRun();
		try {
/*			shr.execShell("/dss/gt/etl/etl32/install_home/etlweb", "./start.sh", "172.21.0.131", 22, "etl", "etl");
			//shr.execShell("/dss/gt/etl/etl32/install_home/etlweb", "./stop.sh", "172.21.0.131", 22, "etl", "etl");
			//shr.execShell("/dss/gt/etl/etl32/install_home/etlweb", "cd /dss/gt/etl/etl32/install_home/etlweb;rm -rf *", "172.21.0.131", 22, "etl", "etl");
			shr.execShell("/dss/gt/etl/etl32/install_home/etlrun", "./start.sh", "172.21.0.131", 22, "etl", "etl");		
			//shr.execShell("/dss/gt/etl/etl32/install_home/etlrun", "./stop.sh", "172.21.0.131", 22, "etl", "etl");
			//shr.execShell("/dss/gt/etl/etl32/install_home/etlrun", "cd /dss/gt/etl/etl32/install_home/etlrun;rm -rf *", "172.21.0.131", 22, "etl", "etl");

			
			//shr.execShell("/dss/gt/dqm/dqm3", "./start.sh", "172.21.0.112", 22, "dqm", "dqm");
			//shr.execShell("/dss/gt/dqm/dqm3", "./stop.sh", "172.21.0.112", 22, "dqm", "dqm");
			//shr.execShell("/dss/gt/dqm/dqm3", "cd /dss/gt/dqm/dqm3;rm -rf *", "172.21.0.112", 22, "dqm", "dqm");
		
			//shr.execShell("/etl/etl/web", "./osgistart.sh", "192.168.100.112", 22, "etl", "etl");
			//System.out.println(shr.execShell("/dss/gt/etl", "df -m", "172.21.0.131", 22, "etl", "etl"));*/
//			String aaa=shr.execShell("/root/scripts", "echo \"root1234\" |sudo -S ./createuser.sh one two", "134.32.50.18", 22, "monitor", "Umon.@123");
			String aaa=shr.execShell("", "sudo /root/scripts/cu.sh cpuser cpuser", "192.168.109.130", 22, "root", "root");
//			System.out.println(aaa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
