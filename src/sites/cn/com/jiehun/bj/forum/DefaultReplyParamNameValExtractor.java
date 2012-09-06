package sites.cn.com.jiehun.bj.forum;

import httpClient.BrowseConst;
import httpClient.BrowsePageRunner;
import httpClient.reply.ReplyParamNameValHandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;

public class DefaultReplyParamNameValExtractor implements
		ReplyParamNameValHandler {
	
	private HttpClient httpClient;
	
	private HttpContext httpContext;
	
	private String replyContent;
	
	protected List<NameValuePair> paramNameValList = new ArrayList<NameValuePair>();
	
	public DefaultReplyParamNameValExtractor(HttpClient httpClient, HttpContext httpContext, String replyContent){
		this.httpClient = httpClient;
		this.httpContext = httpContext;
		this.replyContent = replyContent;
	}

	@Override
	public List<NameValuePair> getReplyParamNamValList() {
		paramNameValList.clear();
		String replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
		BrowsePageRunner httpGetReplyPage = new BrowsePageRunner(httpClient, httpContext, replyPageURL);
		ParseFormInputParamHandler formInputParamHandler = new ParseFormInputParamHandler(paramNameValList, replyContent);
		httpGetReplyPage.setReponseHandler(formInputParamHandler);
		httpGetReplyPage.run();
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
