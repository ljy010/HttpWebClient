package sites.cn.com.jiehun.bj.scene.submit;

import core.common.DateUtils;
import sites.cn.com.jiehun.bj.scene.ReplyScene;
import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;
import sites.cn.com.jiehun.bj.scene.executor.GrabSpecialFloorReplyExecutor;

public class GrabSpecialFloor extends GrabFloorAbstract {

	private int[] floors = null;
	
	private String replyContent = null;
	
	private String parseKeyWord = null;
	
	public GrabSpecialFloor(String loginUser, ExecutorConfig executorConfig, int[] floors, String parseKeyWord, String replyContent) {
		super(loginUser, executorConfig);
		this.floors = floors;
		this.replyContent = replyContent;
		this.parseKeyWord = parseKeyWord;
	}

	@Override
	public void grabFloor() {
		ReplyScene grabSpecialFloorExecutor = new GrabSpecialFloorReplyExecutor(this.login_user,
				                                                                this.mainParsePage,
				                                                                executorConfig,
				                                                                floors,
				                                                                this.parseKeyWord,
				                                                                this.replyContent);
		grabSpecialFloorExecutor.reply();
	}
	
	public static void main(String[] args) {
		
		String parseKeyWord = DateUtils.getCurrentMonthDay() + "Ç©µ½";
		
		String replyContent = "ÇÀÂ¥À²ÇÀÂ¥À²¹þ¹þ";
		
		ExecutorConfig executorConfig = new ExecutorConfig();
		
		int[] floors = {};
		
		GrabSpecialFloor grabSpecialFloor = new GrabSpecialFloor("ljy", executorConfig, floors, parseKeyWord, replyContent);
		grabSpecialFloor.run();
	}


}
