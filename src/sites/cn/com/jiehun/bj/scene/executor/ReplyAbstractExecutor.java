package sites.cn.com.jiehun.bj.scene.executor;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import sites.cn.com.jiehun.bj.action.LoginRequestAction;
import sites.cn.com.jiehun.bj.scene.LoginScene;
import sites.cn.com.jiehun.bj.scene.LoginState;
import sites.cn.com.jiehun.bj.scene.ReplyScene;
import sites.cn.com.jiehun.bj.scene.ReplyState;
import sites.cn.com.jiehun.bj.scene.responseHandler.LoginResponseHanlder;
import core.HttpClientFactory;

public abstract class ReplyAbstractExecutor implements ReplyScene, LoginScene {

	private String loginUser = null; 
	
	private HttpClient httpClient = null;
	
	private LoginState loginState = null;
	
	private ResponseHandler<LoginState> loginResponseHandler = null;
	
	public ReplyAbstractExecutor(String loginUser){
		this.loginUser = loginUser;
		httpClient = HttpClientFactory.createHttpClient();
		loginState = new LoginState();
	}
	
	@Override
	public String getLoginUser() {
		return loginUser;
	}
	
	protected ResponseHandler<LoginState> createLoginResponseHandler(){
		if(loginResponseHandler == null){
			loginResponseHandler = new LoginResponseHanlder(loginState);
		}
		return loginResponseHandler;
	}
	
	protected HttpClient getHttpClient(){
		return httpClient;
	}

	@Override
	public LoginState login() {
		ResponseHandler<LoginState> loginResponseHandler = createLoginResponseHandler();
		LoginRequestAction<LoginState> loginRequest = new LoginRequestAction<LoginState>(httpClient, loginResponseHandler, loginUser);
		return loginRequest.execute();
	}

	@Override
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	
	protected abstract ReplyState doActionAfterLogin();

	@Override
	public ReplyState reply() {
		LoginState loginState = login();
		if(loginState.isLogined()){
			return doActionAfterLogin();
		}else{
			System.out.println("µÇÂ¼Ê§°Ü!");
		}
		return null;
	}

}
