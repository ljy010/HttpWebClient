package sites.cn.com.jiehun.bj.scene;

import httpClient.reply.DefaultReplyPolicy;
import httpClient.reply.ReplyPolicy;
import sites.cn.com.jiehun.bj.submit.AutoReplyConfig;
import sites.cn.com.jiehun.bj.submit.AutoReplyRunner;
import common.DateUtils;

public class QianDao implements Runnable{
	
	private String keyStr = DateUtils.getCurrentMonthDay() + "签到";
	
//	private String keyStr = "8月30日签到";
	
	private String replyStr = "签到了签到了哈";
	
	private AutoReplyConfig replyConfig = new AutoReplyConfig();
	
	private String loginUser = "ljy";
	
	public QianDao(String user){
		this.loginUser = user;
	}

	@Override
	public void run() {
		ReplyPolicy defaultReplyPolicy = new DefaultReplyPolicy(replyStr);
		AutoReplyRunner autoReply = new AutoReplyRunner(loginUser, keyStr, defaultReplyPolicy, replyConfig);
		autoReply.run();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QianDao qianDao = new QianDao("ljy");
		qianDao.run();
	}

}
