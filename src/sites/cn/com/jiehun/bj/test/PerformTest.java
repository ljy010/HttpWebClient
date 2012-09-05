package sites.cn.com.jiehun.bj.test;

import httpClient.BrowseConst;
import httpClient.BrowsePageRunner;

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

public class PerformTest {
	
	public static long timeTest(Runnable runner){
		long start = System.currentTimeMillis();
		runner.run();
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println(time);
		return time;
	}
	
	public static void testParsePublicedTextTime() throws Exception{
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter(CookieSpecPNames.SINGLE_COOKIE_HEADER, true);
		HttpContext httpContext = new BasicHttpContext();
		String replyPageURL = null;
		String keyStr = "8月30日签到";
	    ParseURLHandler parseURLHandler = new ParseURLHandler(httpContext, keyStr); 
	    int time = 20;
	    int index = 0;
	    int totalTime = 0;
	    BrowsePageRunner browsePageRunner = null;
	    while(index < time){
	    	browsePageRunner = new BrowsePageRunner(httpClient, httpContext, ReplyConst.PARSE_PAGE_URL);
	    	browsePageRunner.setReponseHandler(parseURLHandler);
	    	browsePageRunner.run();
	    	totalTime += timeTest(browsePageRunner);
	    	replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
	    	if((replyPageURL == null) || ("".equals(replyPageURL))){
	    		Thread.sleep(500);
	    	}
	    	index++;
	    }
	    System.out.println("avg time: " + (totalTime / time));
	}
	
	public static void testParseUnPublicTextTime() throws InterruptedException{
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter(CookieSpecPNames.SINGLE_COOKIE_HEADER, true);
		HttpContext httpContext = new BasicHttpContext();
		String replyPageURL = null;
		String keyStr = "9月1日签到";
	    ParseURLHandler parseURLHandler = new ParseURLHandler(httpContext, keyStr); 
	    BrowsePageRunner browsePageRunner = null;
	    while((replyPageURL == null) || ("".equals(replyPageURL))){
	    	browsePageRunner = new BrowsePageRunner(httpClient, httpContext, ReplyConst.PARSE_PAGE_URL);
	    	browsePageRunner.setReponseHandler(parseURLHandler);
	    	browsePageRunner.run();
	    	timeTest(browsePageRunner);
	    	replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
	    	if((replyPageURL == null) || ("".equals(replyPageURL))){
	    		Thread.sleep(500);
	    	}
	    }
	}

	public static void testLoginTime(){
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter(CookieSpecPNames.SINGLE_COOKIE_HEADER, true);
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(BrowseConst.CONTEXT_LOGIN_USER, "ljy");
		boolean isLogin = false;
		httpContext.setAttribute(BrowseConst.CONTEXT_IS_LOGINED, isLogin);

		LoginRunner login = new LoginRunner(httpClient, httpContext);
		
		timeTest(login);
			
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		testLoginTime();
//		testParseUnPublicTextTime();
		testParsePublicedTextTime();
	}

}
