package cn.edu.llxy.dw.dss.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.log4j.Logger;

class StreamGobbler extends Thread {
	private static Logger log = Logger.getLogger(StreamGobbler.class);
	InputStream is;
	String type;
	StringWriter output;

	StreamGobbler(InputStream is, String type, StringWriter output) {
		this.is = is;
		this.type = type;
		this.output = output;
	}

	public void run() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = null;

			log.debug("before while readLine");
			while ((line = br.readLine()) != null) {
				log.debug("line:" + line);
				if (type.equals("Error"))
					output.write(line + "\n");
				else
					output.write(line);
			}
			if (output != null)
				output.flush();

			log.debug("StreamGobbler run end");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
