package sites.cn.com.jiehun.bj.submit;

import httpClient.BrowseConst;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import sites.cn.com.jiehun.bj.forum.LoginRunner;
import sites.cn.com.jiehun.bj.forum.ParseURLHandler;
import sites.cn.com.jiehun.bj.forum.ReplyRunner;

public class AutoReplyRunner implements Runnable {
	
	private String loginUser = null;
	
	private String replyContent = null;
	
	private String keyStr = null;
	
	private AutoReplyConfig replyConfig = null;
	
	public AutoReplyRunner(String loginUser, String keyStr, String replyContent, AutoReplyConfig autoReplyConfig){
		this.loginUser = loginUser;
		this.replyContent = replyContent;
		this.keyStr = keyStr;
		if(autoReplyConfig == null){
			replyConfig = new AutoReplyConfig();
		}else{
			replyConfig = autoReplyConfig;
		}
	}

	@Override
	public void run() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		try{
			httpContext.setAttribute(BrowseConst.CONTEXT_LOGIN_USER, loginUser);
			boolean isLogin = false;
			httpContext.setAttribute(BrowseConst.CONTEXT_IS_LOGINED, isLogin);
			
			LoginRunner login = null;
			while(!isLogin){
				login = new LoginRunner(httpClient, httpContext);
				login.run();
				isLogin = (Boolean)httpContext.getAttribute(BrowseConst.CONTEXT_IS_LOGINED);
				if(!isLogin){
					Thread.sleep(replyConfig.getLoginInterval());
				}	
			}
			
		    String replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
		    ParseURLHandler parseURLHandler = null; 
		    while((replyPageURL == null) || ("".equals(replyPageURL))){
		    	parseURLHandler = new ParseURLHandler(httpContext, keyStr, null);
		    	parseURLHandler.handle(null);
		    	replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
		    	if((replyPageURL == null) || ("".equals(replyPageURL))){
		    		Thread.sleep(replyConfig.getParseInterval());
		    	}
		    }
		    
		    Thread.sleep(replyConfig.getReplyPostInterval());
		    ReplyRunner reply = new ReplyRunner(httpClient, httpContext, replyPageURL);
		    reply.run();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
