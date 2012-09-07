package sites.cn.com.jiehun.bj.scene.responseHandler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import core.common.HttpResponseUtils;

import sites.cn.com.jiehun.bj.scene.LoginState;

public  class LoginResponseHanlder implements ResponseHandler<LoginState> {
	
	private LoginState loginState;
	
	public LoginResponseHanlder(LoginState loginState){
		this.loginState = loginState;
	}

	@Override
	public LoginState handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		HttpResponseUtils.printResponse(response);
		this.loginState.setLogined(true);
		return this.loginState;
	}

}
