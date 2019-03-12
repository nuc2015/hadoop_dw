package create;

import org.apache.log4j.Logger;

/**
 * @author 刘卫卫
 * 2018年8月20日下午5:06:03
 */
public class CreateLog {
	public static void main(String[] args) throws Exception {
		Logger logger = Logger.getLogger(CreateLog.class);
		while(true) {
			logger.debug("");
		}
	}
}