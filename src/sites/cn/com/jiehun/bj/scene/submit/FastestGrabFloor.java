package sites.cn.com.jiehun.bj.scene.submit;

import core.common.DateUtils;
import sites.cn.com.jiehun.bj.scene.ReplyScene;
import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;
import sites.cn.com.jiehun.bj.scene.executor.FastestGrabFloorReplyExecutor;

public class FastestGrabFloor extends GrabFloorAbstract {

	private String replyContent;
	
	private String parseKeyWord;
	
	
	public FastestGrabFloor(String loginUser, ExecutorConfig executorConfig, String parseKeyWord, String replyContent) {
		super(loginUser, executorConfig);
		this.replyContent = replyContent;
		this.parseKeyWord = parseKeyWord;
	}

	
	public String getReplyContent() {
		return replyContent;
	}



	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}



	@Override
	public void grabFloor() {
		ReplyScene fgre = new FastestGrabFloorReplyExecutor(this.login_user,
				this.mainParsePage, executorConfig, parseKeyWord, getReplyContent());
		fgre.reply();
	}
	
	public static void main(String[] args) {
		String parseKeyWord = DateUtils.getCurrentMonthDay() + "签到";
		
		String replyContent = "签到了签到了哈";
		
		ExecutorConfig executorConfig = new ExecutorConfig();
		
		FastestGrabFloor fastestGrabFloor = new FastestGrabFloor("ljy", executorConfig, parseKeyWord, replyContent);
		fastestGrabFloor.run();
	}


}
