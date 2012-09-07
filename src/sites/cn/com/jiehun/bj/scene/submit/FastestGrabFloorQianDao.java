package sites.cn.com.jiehun.bj.scene.submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;
import core.common.DateUtils;

public class FastestGrabFloorQianDao{

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		String replyContent = DateUtils.getCurrentMonthDay() + "Ç©µ½";
		ExecutorConfig executorConfig = new ExecutorConfig();
		FastestGrabFloor fastestGrabFloor = new FastestGrabFloor("ljy", executorConfig, replyContent);
		
		executorService.submit(fastestGrabFloor);
		
		executorService.shutdown();
	}

}
