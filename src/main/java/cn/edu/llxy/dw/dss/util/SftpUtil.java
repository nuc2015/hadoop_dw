package cn.edu.llxy.dw.dss.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpUtil {
	private String hostName;
	private Integer port;
	private String userName;
	private String password;

	public SftpUtil(String hostName, Integer port, String userName, String password) {
		super();
		this.hostName = hostName;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	public String testSftp() {
		String res = "";
		Session session = null;
		try {
			session = getSession();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			if (channel.isConnected()) {
				res = "success";
			} else {
				res = "failure";
			}
			channel.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
			res = e.getMessage();
		} finally {
			this.closeSession(session);
		}
		return res;
	}

	public void uploadFile(String local, String remote) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Channel channel = session.openChannel("sftp");
			channel.connect();

			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.put(local, remote);

			sftpChannel.exit();
			channel.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
			throw new Exception(e.getCause());
		} catch (SftpException e) {
			e.printStackTrace();
			throw new Exception(e.getCause());
		} finally {
			this.closeSession(session);
		}
	}

	public void downloadFile(String remote, String local) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Channel channel = session.openChannel("sftp");
			channel.connect();

			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.get(remote, local);

			sftpChannel.exit();
			channel.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
			throw new Exception(e.getCause());
		} catch (SftpException e) {
			e.printStackTrace();
			throw new Exception(e.getCause());
		} finally {
			this.closeSession(session);
		}
	}

	public void rmDir(String remote) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Channel channel = session.openChannel("sftp");
			channel.connect();

			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.rmdir(remote);

			sftpChannel.exit();
			channel.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
			throw new Exception(e.getCause());
		} catch (SftpException e) {
			e.printStackTrace();
			throw new Exception(e.getCause());
		} finally {
			this.closeSession(session);
		}
	}

	public void rmFile(String remote) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Channel channel = session.openChannel("sftp");
			channel.connect();

			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.rm(remote);

			sftpChannel.exit();
			channel.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
			throw new Exception(e.getCause());
		} catch (SftpException e) {
			e.printStackTrace();
			throw new Exception(e.getCause());
		} finally {
			this.closeSession(session);
		}
	}
	
	private Session getSession() throws JSchException {
		Session session;
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(this.userName, this.hostName, this.port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.setTimeout(10 * 1000);
			session.connect();
			return session;
		} catch (JSchException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void closeSession(Session session) {
		if (session != null) {
			session.disconnect();
		}
	}

}
