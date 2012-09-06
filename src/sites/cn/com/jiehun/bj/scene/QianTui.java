package sites.cn.com.jiehun.bj.scene;

import httpClient.reply.DefaultReplyPolicy;
import httpClient.reply.ReplyPolicy;
import sites.cn.com.jiehun.bj.submit.AutoReplyConfig;
import sites.cn.com.jiehun.bj.submit.AutoReplyRunner;

import common.DateUtils;

public class QianTui implements Runnable {
    private String keyStr = DateUtils.getCurrentMonthDay() + "签退";
	
//	private String keyStr = "8月30日签到";
	
	private String replyStr = "签退了签退了哈";
	
	private AutoReplyConfig replyConfig = new AutoReplyConfig();
	
	private String loginUser = "ljy";

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
		QianTui qianTui = new QianTui();
		qianTui.run();
	}

}
