package sites.cn.com.jiehun.bj.scene.submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.common.DateUtils;
import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;

public class GrabSpecialFloorQianDao {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();

        String replyContent = "��¥����¥������";
		
		ExecutorConfig executorConfig = new ExecutorConfig();
		executorConfig.setReplyIntegerval(0);
		
		int[] floors = {555};
		
		GrabSpecialFloor grabSpecialFloor = new GrabSpecialFloor("ljy", executorConfig, floors, replyContent);

		executorService.submit(grabSpecialFloor);

		executorService.shutdown();
	}

}
