package sites.cn.com.jiehun.bj.test;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import core.common.HttpResponseUtils;

import sites.cn.com.jiehun.bj.action.LoginRequestAction;
import sites.cn.com.jiehun.bj.action.ParseRequestAction;
import sites.cn.com.jiehun.bj.dataConst.ForumConst;
import sites.cn.com.jiehun.bj.scene.LoginState;
import sites.cn.com.jiehun.bj.scene.ParseResult;
import sites.cn.com.jiehun.bj.scene.responseHandler.LoginResponseHanlder;
import sites.cn.com.jiehun.bj.scene.responseHandler.ParseByTitleKeyWordReponseHandler;

public class FunctionTest {
	
	private HttpClient httpClient = null;
	
	public FunctionTest(){
		httpClient = new DefaultHttpClient();
	}

	public void login(String loginUser){
		LoginState loginState = new LoginState();
		ResponseHandler<LoginState> loginResponseHandler = new LoginResponseHanlder(loginState);
		LoginRequestAction<LoginState> loginRequest = new LoginRequestAction<LoginState>(httpClient, loginResponseHandler, loginUser);
		loginState = loginRequest.execute();
	}
	
	public void parseURL(String keyStr, String mainParsePage){
		ParseResult parseResult = new ParseResult();
		ResponseHandler<ParseResult> parseResponseHandler = new ParseByTitleKeyWordReponseHandler(keyStr, parseResult);
		ParseRequestAction<ParseResult> parseRequest = new ParseRequestAction<ParseResult>(httpClient,  parseResponseHandler);
		parseRequest.setRequestURL(mainParsePage);
		parseResult = parseRequest.execute();
//		System.out.println(parseResult.getResult());
	}
	public void testHttpGet(){
		HttpGet get = new HttpGet(ForumConst.PARSE_PAGE_URL);
		try{
			ParseResult parseResult = new ParseResult();
			ResponseHandler<ParseResult> parseResponseHandler = new ParseByTitleKeyWordReponseHandler("9月7日签退", parseResult);
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
//		functionTest.login("dr");
		functionTest.parseURL("9月7日签退", ForumConst.PARSE_PAGE_URL);
//		functionTest.testHttpGet();
	}

}
