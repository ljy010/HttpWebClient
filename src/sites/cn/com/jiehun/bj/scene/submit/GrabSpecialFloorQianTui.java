package sites.cn.com.jiehun.bj.scene.submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.common.DateUtils;

import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;

public class GrabSpecialFloorQianTui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();

		String parseKeyWord = DateUtils.getCurrentMonthDay() + "Ç©ÍË";

		String replyContent = "ÇÀÂ¥À²ÇÀÂ¥À²¹þ¹þ";
		
		ExecutorConfig executorConfig = new ExecutorConfig();
		executorConfig.setReplyIntegerval(0);
		
		int[] floors = {2500};
		
		GrabSpecialFloor grabSpecialFloor = new GrabSpecialFloor("ljy", executorConfig, floors, parseKeyWord, replyContent);

		executorService.submit(grabSpecialFloor);

		executorService.shutdown();
	}

}
