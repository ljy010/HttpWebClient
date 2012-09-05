package sites.cn.com.jiehun.bj.forum;

import httpClient.BrowseConst;
import httpClient.BrowsePageRunner;
import httpClient.reply.ReplyPolicy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import sites.cn.com.jiehun.bj.common.ReplyConst;

import common.HttpHtmlParserUtils;

public class ReplyRunner extends PostRunner {
	
	private HttpClient httpClient;
	
	private HttpContext httpContext;
	
//	private String replyContent;
	
	private ReplyPolicy replyPolicy;
	
	private String replyPageURL;
	
	private List<NameValuePair> replyParamNameValList = new ArrayList<NameValuePair>();
	
	private Integer curFloorCount;
	
	
	public ReplyRunner(HttpClient httpClient, HttpContext httpContext, ReplyPolicy replyPolicy){
		this.httpClient = httpClient;
		this.httpContext = httpContext;
		this.replyPolicy = replyPolicy;
//		this.replyContent = replyContent;
		this.replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
	}
	
	protected void initHttpHeader(HttpPost httpPost){
		super.initHttpHeader(httpPost);
		httpPost.addHeader("Referer", replyPageURL);
		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
		httpPost.addHeader("Cache-Control", "no-cache");
		httpPost.addHeader("Accept", "*/*");
	}
	
	
	
	protected void initReplyParamValue(){
		BrowsePageRunner httpGetReplyPage = new BrowsePageRunner(httpClient, httpContext, replyPageURL);
		ParseFormInputParamHandler formInputParamHandler = new ParseFormInputParamHandler(this.replyParamNameValList, replyPolicy);
		httpGetReplyPage.setReponseHandler(formInputParamHandler);
		httpGetReplyPage.run();
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		if((replyPageURL == null) || ("".equals(replyPageURL))){
			throw new RuntimeException("没有要回复的帖子URL!");
		}
		HttpPost httpPost = new HttpPost(ReplyConst.REPLY_POST_ACTION_URL);
		try {
			initReplyParamValue();
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(replyParamNameValList, "UTF-8");
			httpPost.setEntity(formEntity);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			HttpEntity responseEntry = response.getEntity();
			System.out.println(EntityUtils.toString(responseEntry));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpPost.releaseConnection();
		}
	}

}
