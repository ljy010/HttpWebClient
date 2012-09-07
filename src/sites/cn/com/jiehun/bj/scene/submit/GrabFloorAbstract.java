package sites.cn.com.jiehun.bj.scene.submit;

import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;

public abstract class GrabFloorAbstract implements GrabFloor, Runnable {

	protected String login_user = "ljy";
	
	protected ExecutorConfig executorConfig;
	
	protected String mainParsePage = "http://bj.jiehun.com.cn/bbs/65/164";
	
	public void setMainParsePage(String url){
		this.mainParsePage = url;
	}
	
	public GrabFloorAbstract(String loginUser, ExecutorConfig executorConfig){
		this.login_user = loginUser;
		this.executorConfig = executorConfig;
	}
	

	@Override
	public void run() {
		grabFloor();
	}

}
