package sites.cn.com.jiehun.bj.scene;

import sites.cn.com.jiehun.bj.submit.AutoReplyConfig;
import sites.cn.com.jiehun.bj.submit.AutoReplyRunner;
import common.DateUtils;

public class QianDao implements Runnable{
	
	private String keyStr = DateUtils.getCurrentMonthDay() + "ǩ��";
	
//	private String keyStr = "8��30��ǩ��";
	
	private String replyStr = "ǩ����ǩ���˹�";
	
	private AutoReplyConfig replyConfig = new AutoReplyConfig();
	
	private String loginUser = "ljy";
	
	public QianDao(String user){
		this.loginUser = user;
	}

	@Override
	public void run() {
		AutoReplyRunner autoReply = new AutoReplyRunner(loginUser, keyStr, replyStr, replyConfig);
		autoReply.run();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        
		QianDao qianDao = new QianDao("ljy");
		//qianDao.run();
	}

}
