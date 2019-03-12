package upload;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 刘卫卫 2018年8月20日下午5:06:37
 */
public class OptionLog {
	public static void main(String[] args) {
		Timer timer = new Timer();
		//上传日志
		timer.schedule(new UpLog(), new Date(), 10*1000);
		//清理日志
//		timer.schedule(new ClearLog(), new Date(), 2*60*1000);
	}
}
