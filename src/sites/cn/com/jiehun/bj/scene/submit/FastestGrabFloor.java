package sites.cn.com.jiehun.bj.scene.submit;

import core.common.DateUtils;
import sites.cn.com.jiehun.bj.scene.ReplyScene;
import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;
import sites.cn.com.jiehun.bj.scene.executor.FastestGrabFloorReplyExecutor;

public class FastestGrabFloor extends GrabFloorAbstract {

	private String replyContent;
	
	
	public FastestGrabFloor(String loginUser, ExecutorConfig executorConfig, String replyContent) {
		super(loginUser, executorConfig);
		this.replyContent = replyContent;
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
				this.mainParsePage, executorConfig, getReplyContent());
		fgre.reply();
	}
	
	public static void main(String[] args) {
		String replyContent = DateUtils.getCurrentMonthDay() + "Ç©µ½";
		
		ExecutorConfig executorConfig = new ExecutorConfig();
		
		FastestGrabFloor fastestGrabFloor = new FastestGrabFloor("ljy", executorConfig, replyContent);
		fastestGrabFloor.run();
	}


}
