package sites.cn.com.jiehun.bj.submit;

import httpClient.BrowseConst;
import httpClient.BrowsePageRunner;
import httpClient.reply.ReplyParamNameValHandler;
import httpClient.reply.ReplyPolicy;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.params.CookieSpecPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import sites.cn.com.jiehun.bj.common.ReplyConst;
import sites.cn.com.jiehun.bj.forum.LoginRunner;
import sites.cn.com.jiehun.bj.forum.ParseURLHandler;
import sites.cn.com.jiehun.bj.forum.ReplyRunner;

/**
 * 针对社区公告做签到签退回复
 * @author linjy
 *
 */
public class AutoReplyRunner implements Runnable {
	
	protected String loginUser = null;
	
	protected String replyContent = null;
	
	protected ReplyPolicy replyPolicy = null;
	
	protected String keyStr = null;
	
	protected AutoReplyConfig replyConfig = null;
	
	protected HttpClient httpClient = null;
	
	protected HttpContext httpContext = null;
	
	protected String replyPageURL = null;
	
	protected ReplyParamNameValHandler replyParamNameValHandler = null;
	
	public String getReplyPageURL() {
		return replyPageURL;
	}

	public void setReplyPageURL(String replyPageURL) {
		this.replyPageURL = replyPageURL;
	}

	protected boolean isNeedLogin(){
		return true;
	}
	
	protected void initHttpContext(){
		if(httpContext == null){
			httpContext = new BasicHttpContext();
			httpContext.setAttribute(BrowseConst.CONTEXT_LOGIN_USER, loginUser);
			httpContext.setAttribute(BrowseConst.CONTEXT_IS_LOGINED, false);
		}
	}
	
	private void initHttpClient(){
		if(httpClient == null){
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.getParams().setParameter(CookieSpecPNames.SINGLE_COOKIE_HEADER, true);
		}
	}
	
	public AutoReplyRunner(String loginUser, String keyStr, ReplyPolicy replyPolicy, AutoReplyConfig autoReplyConfig){
		this.loginUser = loginUser;
//		this.replyContent = replyContent;
		this.replyPolicy = replyPolicy;
		this.keyStr = keyStr;
		if(autoReplyConfig == null){
			replyConfig = new AutoReplyConfig();
		}else{
			replyConfig = autoReplyConfig;
		}
		initHttpClient();
		initHttpContext();
	}
	
	protected void doLogin() throws InterruptedException{
		boolean isLogined = (Boolean)httpContext.getAttribute(BrowseConst.CONTEXT_IS_LOGINED);
		LoginRunner login = new LoginRunner(httpClient, httpContext);
		while(!isLogined){
			login.run();
			isLogined = (Boolean)httpContext.getAttribute(BrowseConst.CONTEXT_IS_LOGINED);
			if(!isLogined){
				Thread.sleep(replyConfig.getLoginInterval());
			}	
		}
	}
	
    
	
	public void setReplyParamNameValHandler(
			ReplyParamNameValHandler replyParamNameValHandler) {
		this.replyParamNameValHandler = replyParamNameValHandler;
	}

	protected void doReply() throws InterruptedException{
		Thread.sleep(replyConfig.getReplyPostInterval());
	    ReplyRunner reply = new ReplyRunner(httpClient, httpContext, this.replyParamNameValHandler);
	    reply.run();
	}

	protected void doParse() throws InterruptedException{
	    replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
	    ParseURLHandler parseURLHandler = new ParseURLHandler(httpContext, keyStr); 
	    BrowsePageRunner browsePageRunner = new BrowsePageRunner(httpClient, httpContext, ReplyConst.PARSE_PAGE_URL);;
	    browsePageRunner.setReponseHandler(parseURLHandler);
	    while((replyPageURL == null) || ("".equals(replyPageURL))){
	    	browsePageRunner.run();
	    	replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
	    	if((replyPageURL == null) || ("".equals(replyPageURL))){
	    		Thread.sleep(replyConfig.getParseInterval());
	    	}
	    }
	}
	@Override
	public void run() {
		try{
			if(isNeedLogin()){
				doLogin();
			}
			
			doParse();
		    
		    doReply();
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
