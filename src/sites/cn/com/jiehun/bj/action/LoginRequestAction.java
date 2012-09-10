package sites.cn.com.jiehun.bj.action;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import sites.cn.com.jiehun.bj.dataConst.ForumConst;

import core.HttpHeaders;
import core.HttpParams;
import core.entity.HttpEntity;
import core.entity.HttpPostEntity;

public class LoginRequestAction<T> extends RequestAbstractAction<T> {

	private String loginUser = "ljy";
	
	public LoginRequestAction(HttpClient httpClient, ResponseHandler<T> responseHandler, String loginUser) {
		super(httpClient, ForumConst.FORUM_LOGIN_ACTION_URL, responseHandler);
		this.loginUser = loginUser;
	}
	
	@Override
	public T execute() {
		if((getRequestURL() == null) || ("".equals(getRequestURL()))){
			throw new RuntimeException("请求地址不能为空!");
		}
		HttpHeaders loginHttpHeader = HttpHeaderFactory.getDefaultGetHeader();
		HttpParams httpParams = LoginUserFactory.getUser(this.loginUser);
		
		HttpEntity<T> httpEntity = new HttpPostEntity<T>(getRequestURL());
		httpEntity.setHttpHeaders(loginHttpHeader);
		httpEntity.setHttpParams(httpParams);
		
		return httpEntity.execute(getHttpClient(), getResponseHandler());
	}

}
