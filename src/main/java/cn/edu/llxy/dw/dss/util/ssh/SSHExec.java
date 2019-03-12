package cn.edu.llxy.dw.dss.util.ssh;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import cn.edu.llxy.dw.dss.util.KeepAliveOutputStream;
import cn.edu.llxy.dw.dss.util.TeeOutputStream;

/**
 * Executes a command on a remote machine via ssh.
 */
public class SSHExec extends SSHBase {

	private static final int RETRY_INTERVAL = 500;

	private String command_dir=null;
	
	/** the command to execute via ssh */
	private String command = null;

	/** units are milliseconds, default is 0=infinite */
	private long maxwait = 0;

	/** for waiting for the command to finish */
	private Thread thread = null;

	private static final String TIMEOUT_MESSAGE = "Timeout period exceeded, connection dropped.";

	/**
	 * Constructor for SSHExecTask.
	 */
	public SSHExec() {
		super();
	}

	/**
	 * Sets the command to execute on the remote host.
	 * 
	 * @param command
	 *            The new command value
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * The connection can be dropped after a specified number of milliseconds.
	 * This is sometimes useful when a connection may be flaky. Default is 0,
	 * which means &quot;wait forever&quot;.
	 * 
	 * @param timeout
	 *            The new timeout value in seconds
	 */
	public void setTimeout(long timeout) {
		maxwait = timeout;
	}

	/**
	 * Execute the command on the remote host.
	 * 
	 * @exception BuildException
	 *                Most likely a network error or bad parameter.
	 */
	public OutputStream execute() throws Exception {
		if (getHost() == null) {
			throw new Exception("Host is required.");
		}
		if (getUserInfo().getName() == null) {
			throw new Exception("Username is required.");
		}
		if (getUserInfo().getKeyfile() == null && getUserInfo().getPassword() == null) {
			throw new Exception("Password or Keyfile is required.");
		}
		if (command == null) {
			throw new Exception("Command or commandResource is required.");
		}

		Session session = null;
		ByteArrayOutputStream out = null;
		try {
			session = openSession();
			/* called once */
			if(command_dir!=null){
//				executeCommand(session, command_dir);
				command = "cd " + command_dir + ";" + command;
			}
			if (command != null) {
				out = executeCommand(session, command);
			}
		} catch (JSchException e) {
			throw new Exception(e);
		} finally {
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
		return out;
	}

	private ByteArrayOutputStream executeCommand(Session session, String cmd) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TeeOutputStream tee = new TeeOutputStream(out, new KeepAliveOutputStream(System.out));

		try {
			final ChannelExec channel;
			session.setTimeout((int) maxwait);
			/* execute the command */
			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(cmd);
			channel.setOutputStream(tee);
			channel.setExtOutputStream(tee);
			channel.setPty(true);
			channel.connect();
			// wait for it to finish
			thread = new Thread() {
				public void run() {
					while (!channel.isClosed()) {
						if (thread == null) {
							return;
						}
						try {
							sleep(RETRY_INTERVAL);
						} catch (Exception e) {
							// ignored
						}
					}
				}
			};

			thread.start();
			thread.join(maxwait);

			if (thread.isAlive()) {
				// ran out of time
				thread = null;
				if (getFailonerror()) {
					throw new Exception(TIMEOUT_MESSAGE);
				}
			} else {
				// this is the wrong test if the remote OS is OpenVMS,
				// but there doesn't seem to be a way to detect it.
				int ec = channel.getExitStatus();
				if (ec != 0) {
					String msg = "Remote command failed with exit status " + ec;
					if (getFailonerror()) {
						throw new Exception(msg);
					}
				}
			}
		} catch (JSchException e) {
			if (e.getMessage().indexOf("session is down") >= 0) {
				if (getFailonerror()) {
					throw new Exception(TIMEOUT_MESSAGE, e);
				} 
			} else {
				if (getFailonerror()) {
					throw new Exception(e);
				}
			}
		} catch (Exception e) {
			if (getFailonerror()) {
				throw new Exception(e);
			}
		}
		return out;
	}

	public String getCommand_dir() {
		return command_dir;
	}

	public void setCommand_dir(String command_dir) {
		this.command_dir = command_dir;
	}

}
