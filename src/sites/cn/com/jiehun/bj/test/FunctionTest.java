package sites.cn.com.jiehun.bj.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import core.HttpClientFactory;
import core.HttpParams;
import core.common.HttpResponseUtils;

import sites.cn.com.jiehun.bj.action.LoginRequestAction;
import sites.cn.com.jiehun.bj.action.ParseRequestAction;
import sites.cn.com.jiehun.bj.action.ReplyRequestAction;
import sites.cn.com.jiehun.bj.dataConst.ForumConst;
import sites.cn.com.jiehun.bj.scene.LoginState;
import sites.cn.com.jiehun.bj.scene.ParseResult;
import sites.cn.com.jiehun.bj.scene.ReplyState;
import sites.cn.com.jiehun.bj.scene.executor.ExecutorConfig;
import sites.cn.com.jiehun.bj.scene.executor.FastestGrabFloorReplyExecutor;
import sites.cn.com.jiehun.bj.scene.responseHandler.LoginResponseHanlder;
import sites.cn.com.jiehun.bj.scene.responseHandler.ParseByTitleKeyWordReponseHandler;
import sites.cn.com.jiehun.bj.scene.responseHandler.ParseFormParamHandler;
import sites.cn.com.jiehun.bj.scene.responseHandler.PrintReplyResponseHandler;

public class FunctionTest {
	
	private HttpClient httpClient = null;
	
	private ParseResult parseResult = new ParseResult();
	
	private ResponseHandler<ParseResult> parseResponseHandler = null;
	
	private ParseRequestAction<ParseResult> parseRequest = null;
	
	private ResponseHandler<HttpParams> parseFormParamHandler = null;
	
    protected HttpParams formReplyParams = new HttpParams();
    
    private ParseRequestAction<HttpParams> parseRequestAction = null;
    
    private ResponseHandler<ReplyState> replyResponseHandler = null;
    
    private ReplyRequestAction<ReplyState> replyRequestAction = null;
	
	protected Map<String, String> formPropMap = null;
	
	protected Map<String, String> formParamValMap = null;
	
	public FunctionTest(){
		httpClient = HttpClientFactory.createHttpClient();
		
		initFormParseMap();
		initFormParamValMap();
	}
	
	protected void initFormParseMap(){
		if(formPropMap == null){
			formPropMap = new HashMap<String, String>();
			formPropMap.put("action", "/bbs/topic/_addpost");
			formPropMap.put("method", "post");
			formPropMap.put("id", "postForm");
		}
	}
	
	protected void initFormParamValMap(){
		if(formParamValMap == null){
			formParamValMap = new HashMap<String, String>();
			formParamValMap.put(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_TOPIC_UID, "");
			formParamValMap.put(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_TOPIC_ID, "");
			formParamValMap.put(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_POST_UID, "");
			formParamValMap.put(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_TOPIC_CREATE_TIME, "");
			formParamValMap.put(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_TOPIC_TITLE, "");
			formParamValMap.put(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_CITY_HOST, "");
			formParamValMap.put(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_POST_TOTAL, "");
			formParamValMap.put(ForumConst.FORM_REPLY_INPUT_NAME, "");
		}
	}

	public void login(String loginUser){
		LoginState loginState = new LoginState();
		ResponseHandler<LoginState> loginResponseHandler = new LoginResponseHanlder(loginState);
		LoginRequestAction<LoginState> loginRequest = new LoginRequestAction<LoginState>(httpClient, loginResponseHandler, loginUser);
		loginState = loginRequest.execute();
	}
	
	public ParseResult parseURL(String keyStr, String mainParsePage){
		if(parseResponseHandler == null){
			parseResponseHandler = new ParseByTitleKeyWordReponseHandler(keyStr, parseResult);
		}
		if(parseRequest == null){
			parseRequest = new ParseRequestAction<ParseResult>(httpClient, parseResult.getResult(),  parseResponseHandler);
			parseRequest.setRequestURL(mainParsePage);
		}
		parseResult = parseRequest.execute();
		return parseResult;
//		System.out.println(parseResult.getResult());
	}
	
	private ResponseHandler<HttpParams> createParseFormResponseHandler(){
		return new ParseFormParamHandler(this.formReplyParams, formParamValMap, formPropMap);
	}
	
	public HttpParams getFormParams(String pageUrl, String replyContent){
		if(parseResponseHandler == null){
			parseFormParamHandler = createParseFormResponseHandler();
		}
		if(parseRequestAction == null){
			parseRequestAction = new ParseRequestAction<HttpParams>(this.httpClient, pageUrl, parseFormParamHandler);
		}
		formReplyParams = parseRequestAction.execute();
		formReplyParams.addOrUpdateHttpFormParam(ForumConst.FORM_REPLY_INPUT_NAME, replyContent);
		return formReplyParams;
	}
	
	public ReplyState singleReply(HttpParams httpParams){
		httpParams.checkIsValid();
		if(replyResponseHandler == null){
			ReplyState replyState =  new ReplyState();
			replyResponseHandler = new PrintReplyResponseHandler(replyState);
		}
		if(replyRequestAction == null){
			replyRequestAction = new ReplyRequestAction<ReplyState>(this.httpClient, replyResponseHandler, httpParams, ForumConst.REPLY_POST_ACTION_URL, null);
		}
		return replyRequestAction.execute();
	}
	
	public ReplyState sigleReply(String pageUrl, String replyContent){
		HttpParams httpParams = getFormParams(pageUrl, replyContent);
		if(replyResponseHandler == null){
			ReplyState replyState =  new ReplyState();
			replyResponseHandler = new PrintReplyResponseHandler(replyState);
		}
		if(replyRequestAction == null){
			replyRequestAction = new ReplyRequestAction<ReplyState>(this.httpClient, replyResponseHandler, httpParams, ForumConst.REPLY_POST_ACTION_URL, null);
		}
		return replyRequestAction.execute();
	}
	
	
	public void reply(String loginUser, String pageUrl, String replyContent){
		ExecutorConfig config = new ExecutorConfig();
		ParseResult parseResult = new ParseResult();
		parseResult.setResult(pageUrl);
		FastestGrabFloorReplyExecutor grabFirstFloorExecutor = new FastestGrabFloorReplyExecutor(loginUser,
				                                                                                 "",
				                                                                                 config,
				                                                                                 replyContent,
				                                                                                 parseResult);
		grabFirstFloorExecutor.reply();
	}
	
	public void testHttpGet(){
		HttpGet get = new HttpGet(ForumConst.PARSE_PAGE_URL);
		try{
			ParseResult parseResult = new ParseResult();
			ResponseHandler<ParseResult> parseResponseHandler = new ParseByTitleKeyWordReponseHandler("9月9日签退", parseResult);
			parseResult = httpClient.execute(get, parseResponseHandler);
//			HttpResponseUtils.printResponse(response);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			get.releaseConnection();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FunctionTest functionTest = new FunctionTest();
		functionTest.reply("ljy", "http://bj.jiehun.com.cn/bbs/topic/46899.html", "签到签到了哈");
//		functionTest.login("dr");
//		functionTest.parseURL("9月9日签退", ForumConst.PARSE_PAGE_URL);
//		functionTest.testHttpGet();
	}

}
