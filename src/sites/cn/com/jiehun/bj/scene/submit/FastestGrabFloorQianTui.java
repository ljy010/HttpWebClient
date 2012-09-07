package sites.cn.com.jiehun.bj.scene.submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.common.DateUtils;
import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;

public class FastestGrabFloorQianTui{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();

		String replyContent = DateUtils.getCurrentMonthDay() + "Ç©ÍË";
		ExecutorConfig executorConfig = new ExecutorConfig();
		FastestGrabFloor fastestGrabFloor = new FastestGrabFloor("ljy",
				executorConfig, replyContent);

		executorService.submit(fastestGrabFloor);

		executorService.shutdown();
	}

}
