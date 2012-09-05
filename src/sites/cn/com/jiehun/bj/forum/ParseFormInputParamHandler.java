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
import httpClient.RefleshFloorCount;
import httpClient.reply.ReplyPolicy;

public class ParseFormInputParamHandler implements RefleshFloorCount,AfterResponseHandler {
	
	protected List<NameValuePair> formInputParams = null;
	
	protected ReplyPolicy replyPolicy = null;
	
	protected Map<String, String> replyParamMap = new HashMap<String, String>();
	
	protected void initParamPair(){
		replyParamMap.clear();
		
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_PARAM_NAME_TOPIC_UID, "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_PARAM_NAME_TOPIC_ID, "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_PARAM_NAME_POST_UID, "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_PARAM_NAME_TOPIC_CREATE_TIME, "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_PARAM_NAME_TOPIC_TITLE, "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_PARAM_NAME_CITY_HOST, "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_PARAM_NAME_POST_TOTAL, "");
		replyParamMap.put(ReplyConst.FORM_REPLY_INPUT_NAME, "");
	}
	
	public ParseFormInputParamHandler(List<NameValuePair> formInputParams, ReplyPolicy replyPolicy){
		this.formInputParams = formInputParams;
		this.replyPolicy = replyPolicy;
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
			int floorCount = getCurFloorCount();
			String replyContent = replyPolicy.getReplyContent(floorCount);
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
	
	public String getParamVal(String paramName){
		return replyParamMap.get(paramName);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCurFloorCount() {
		String floorCountStr = getParamVal(ReplyConst.FORM_REPLY_INPUT_PARAM_NAME_POST_TOTAL);
		if((floorCountStr != null) && (!"".equals(floorCountStr))){
			return Integer.valueOf(floorCountStr) + 1;	
		}
		else{
			throw new RuntimeException("没有找到帖子数!");
		}
		
	}

}
