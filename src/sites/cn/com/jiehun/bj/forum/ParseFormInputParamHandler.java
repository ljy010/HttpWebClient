package sites.cn.com.jiehun.bj.forum;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import sites.cn.com.jiehun.bj.common.ReplyConst;

import common.HttpHtmlParserUtils;

import httpClient.AfterResponseHandler;

public class ParseFormInputParamHandler implements AfterResponseHandler {
	
	private List<NameValuePair> formInputParams = null;
	
	private String replyContent = null;
	
	private Map<String, String> replyParamMap = new HashMap<String, String>();
	
	protected void initParamPair(){
		replyParamMap.clear();
		
		replyParamMap.put("topic_uid", "");
		replyParamMap.put("topic_id", "");
		replyParamMap.put("post_uid", "");
		replyParamMap.put("topic_creat_time", "");
		replyParamMap.put("topic_title", "");
		replyParamMap.put("city_host", "");
		replyParamMap.put("post_total", "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_NAME, replyContent);
	}
	
	public ParseFormInputParamHandler(List<NameValuePair> formInputParams, String replyContent){
		this.formInputParams = formInputParams;
		this.replyContent = replyContent;
		initParamPair();
	}

	@Override
	public void handle(HttpResponse response) {
		try {
			String content = EntityUtils.toString(response.getEntity());
			Parser httpParser = new Parser(content);
			
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
						formInputParams.add(new BasicNameValuePair(attrName, value));
					}
				}
			}
			replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_NAME, replyContent);
			formInputParams.add(new BasicNameValuePair("rr_content", replyContent));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
