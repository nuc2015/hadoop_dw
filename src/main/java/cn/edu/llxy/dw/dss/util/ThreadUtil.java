package cn.edu.llxy.dw.dss.util;

import org.apache.log4j.Logger;

public class ThreadUtil {
	private static Logger logger = Logger.getLogger(ThreadUtil.class);
	
	private ThreadUtil(){}
	
	public static void sleepQuietly(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			logger.error("Thread sleep exception:",e);
		}
	}
}
