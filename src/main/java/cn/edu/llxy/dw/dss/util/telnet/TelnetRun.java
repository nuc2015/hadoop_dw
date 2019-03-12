package cn.edu.llxy.dw.dss.util.telnet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.telnet.TelnetClient;

public class TelnetRun {

	public void execTelnet(String ip, Integer port, String command) throws Exception {
		TelnetClient tc = new TelnetClient();
		try {
			tc.setConnectTimeout(5 * 1000);
			tc.connect(ip, port);
			OutputStream outstr = tc.getOutputStream();
			outstr.write((command + "\r").getBytes());
			outstr.flush();
		} catch (SocketException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				tc.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			tc = null;
		}
	}

}
