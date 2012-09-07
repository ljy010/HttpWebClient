package sites.cn.com.jiehun.bj.scene.executor;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ResponseHandler;

import sites.cn.com.jiehun.bj.action.ParseRequestAction;
import sites.cn.com.jiehun.bj.action.ReplyRequestAction;
import sites.cn.com.jiehun.bj.dataConst.ForumConst;
import sites.cn.com.jiehun.bj.scene.ParseResult;
import sites.cn.com.jiehun.bj.scene.ParseScene;
import sites.cn.com.jiehun.bj.scene.ReplyState;
import sites.cn.com.jiehun.bj.scene.responseHandler.ParseByTitleKeyWordReponseHandler;
import sites.cn.com.jiehun.bj.scene.responseHandler.ParseFormParamHandler;
import sites.cn.com.jiehun.bj.scene.responseHandler.PrintReplyResponseHandler;
import core.HttpParams;

public abstract class AfterParsingReplyExecutor extends ReplyAbstractExecutor implements
		Runnable, ParseScene {

	protected ExecutorConfig config;
	
	protected ParseResult parseResult = null;
	
	protected String parsePageURL = null;
	
	protected ResponseHandler<ParseResult> parseResponseHandler = null;
	
	protected ResponseHandler<HttpParams> parseFormParamsHandler = null;
	
	protected String parseKeyWord = null;
	
	protected ReplyState replyState = null;
	
	protected HttpParams formReplyParams = null;
	
	protected Map<String, String> formPropMap = null;
	
	protected Map<String, String> formParamValMap = null;
	
	public AfterParsingReplyExecutor(String loginUser, 
			                         String parsePageURL, 
			                         ExecutorConfig executorConfig) {
		super(loginUser);
		this.parsePageURL = parsePageURL;
		this.config = executorConfig;
		this.parseResult = new ParseResult();
		this.replyState = new ReplyState();
		this.formReplyParams = new HttpParams();
		initFormParseMap();
		initFormParamValMap();
	}
	
	public AfterParsingReplyExecutor(String loginUser, 
			                         String parsePageURL, 
			                         ExecutorConfig executorConfig, 
			                         ParseResult parseResult){
		this(loginUser, parsePageURL, executorConfig);
		this.parseResult = parseResult;
	}
	
	protected boolean checkParseResult(){
		return (this.parseResult.getResult() != null) && (!"".equals(this.parseResult.getResult()));
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
	
	protected ResponseHandler<HttpParams> createParseFormResponseHandler(String url){
		if((url == null) || ("".equals(url))){
			throw new RuntimeException("解析form参数地址不能为空!");
		}
		if(parseFormParamsHandler == null){
			parseFormParamsHandler = new ParseFormParamHandler(this.formReplyParams, formParamValMap, formPropMap);
		}
		return parseFormParamsHandler;
	}
	
	
	protected ResponseHandler<ParseResult> createParseResponseHandler(){
		if((parseKeyWord == null) || ("".equals(parseKeyWord))){
			throw new RuntimeException("解析关键字不能为空!");
		}
		if(parseResponseHandler == null){
			parseResponseHandler = new ParseByTitleKeyWordReponseHandler(getParseKeyWord(), this.parseResult);
		}
		return parseResponseHandler;
	}
	
	protected boolean isParsed(){
		boolean isParsed = checkParseResult();
		try{
			ResponseHandler<ParseResult> parseResponseHandler = createParseResponseHandler();
			ParseRequestAction<ParseResult> parseRequest = new ParseRequestAction<ParseResult>(getHttpClient(),  parseResponseHandler);
			parseRequest.setRequestURL(this.parsePageURL);
			
			while(!isParsed){
				parseResult = parseRequest.execute();
				isParsed = checkParseResult();
				if(!isParsed){
					Thread.sleep(config.getParseInterval());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isParsed;
	}
	
	protected boolean checkHttpParams(HttpParams httpParams){
		String postCount = httpParams.getHttpParamVal(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_POST_TOTAL);
		return ("".equals(postCount)) ? false : true;
	}
	
	protected HttpParams createReplyHttpParams(){
		ResponseHandler<HttpParams> parseFormParamHandler = createParseFormResponseHandler(this.parseResult.getResult());
		ParseRequestAction<HttpParams> parseRequestAction = new ParseRequestAction<HttpParams>(getHttpClient(), parseFormParamHandler);
		return parseRequestAction.execute();
	}
	
	protected abstract String getReplyContent();
	
	protected ReplyState doReply(){
		try{
			if(!checkParseResult()){
				throw new RuntimeException("回复帖子地址不能为空!");
			}
			HttpParams httpParams = createReplyHttpParams();
			if(checkHttpParams(httpParams)){
				String replyContent = getReplyContent();
				httpParams.addOrUpdateHttpFormParam(ForumConst.FORM_REPLY_INPUT_NAME, replyContent);
				ResponseHandler<ReplyState> replyResponseHandler = new PrintReplyResponseHandler(this.replyState);
				ReplyRequestAction<ReplyState> replyRequestAction = new ReplyRequestAction<ReplyState>(getHttpClient(), replyResponseHandler, httpParams, this.parseResult.getResult());
				return replyRequestAction.execute();
			}else{
				throw new RuntimeException("检验form参数不对!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return replyState;
	}

	@Override
	protected ReplyState doActionAfterLogin() {
		try{
			if(isParsed()){
				Thread.sleep(config.getReplyIntegerval());
				return doReply();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.replyState;
	}

	@Override
	public void run() {
		reply();
	}

	@Override
	public ParseResult parsePage() {
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Thread.sleep(-1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getParseKeyWord() {
		return parseKeyWord;
	}

	public void setParseKeyWord(String parseKeyWord) {
		this.parseKeyWord = parseKeyWord;
	}

}
