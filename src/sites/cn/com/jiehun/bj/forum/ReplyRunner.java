package sites.cn.com.jiehun.bj.forum;

import httpClient.BrowseConst;

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
	
	private String replyContent;
	
	private String replyPageURL;
	
	private Map<String, String> replyParamMap = new HashMap<String, String>();
	
	private List<NameValuePair> replyParamNameValList = new ArrayList<NameValuePair>();
	
	protected void initParamPair(){
		replyParamNameValList.clear();
		replyParamMap.clear();
		replyParamMap.put("topic_uid", "");
		replyParamMap.put("topic_id", "");
		replyParamMap.put("post_uid", "");
		replyParamMap.put("topic_creat_time", "");
		replyParamMap.put("topic_title", "");
		replyParamMap.put("city_host", "");
		replyParamMap.put("post_total", "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_NAME, "");
	}
	
	public ReplyRunner(HttpClient httpClient, HttpContext httpContext, String replyContent){
		this.httpClient = httpClient;
		this.httpContext = httpContext;
		this.replyContent = replyContent;
		this.replyPageURL = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
		initParamPair();
	}
	
	protected void initHttpHeader(HttpPost httpPost){
		super.initHttpHeader(httpPost);
		httpPost.addHeader("Referer", replyPageURL);
		httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
		httpPost.addHeader("Cache-Control", "no-cache");
		httpPost.addHeader("Accept", "*/*");
	}
	
	
	
	private void initReplyParamValue(){
		Parser httpParser = new Parser();
		try {
			httpParser.setURL(replyPageURL);
			
			Map<String, String> formMap = new HashMap<String, String>();
			formMap.put("action", "/bbs/topic/_addpost");
			formMap.put("method", "post");
			formMap.put("id", "postForm");
			
			NodeList nodes = HttpHtmlParserUtils.getHiddenInputNodeListByForm(httpParser, formMap);
			for(int i = 0; i < nodes.size(); i++){
				Node node = nodes.elementAt(i);
				if(node instanceof InputTag){
					InputTag inputTag = (InputTag)node;
//					System.out.println(inputTag.toHtml());
					String  attrName = inputTag.getAttribute("name");
					if(replyParamMap.containsKey(attrName)){
						String value = inputTag.getAttribute("value");
						replyParamMap.put(attrName, value);
						replyParamNameValList.add(new BasicNameValuePair(attrName, value));
					}
				}
			}
			replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_NAME, replyContent);
			replyParamNameValList.add(new BasicNameValuePair("rr_content", replyContent));
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
