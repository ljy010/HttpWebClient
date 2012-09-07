package sites.cn.com.jiehun.bj.scene.executor;

import sites.cn.com.jiehun.bj.scene.ParseResult;

public class FastestGrabFloorReplyExecutor extends AfterParsingReplyExecutor {

	private String replyContent = null;
	
	public FastestGrabFloorReplyExecutor(String loginUser, 
			                             String parsePageURL,
			                             ExecutorConfig executorConfig, 
			                             String replyContent) {
		super(loginUser, parsePageURL, executorConfig);
		this.replyContent = replyContent;
	}
	
	public FastestGrabFloorReplyExecutor(String loginUser, 
                                         String parsePageURL,
                                         ExecutorConfig executorConfig, 
                                         String replyContent, ParseResult parseResult) {
        super(loginUser, parsePageURL, executorConfig, parseResult);
        this.replyContent = replyContent;
    }

	@Override
	protected String getReplyContent() {
		return replyContent;
	}

}
