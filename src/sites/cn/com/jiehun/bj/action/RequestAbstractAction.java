package sites.cn.com.jiehun.bj.action;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

public abstract class RequestAbstractAction<T> implements RequestionAction<T> {

	private HttpClient httpClient;
	
	private ResponseHandler<T> responseHandler;
	
	private String requestURL = null;
	
	protected ResponseHandler<T> getResponseHandler(){
		return this.responseHandler;
	}
	
	public RequestAbstractAction(HttpClient httpClient, ResponseHandler<T> responseHandler){
		this.httpClient = httpClient;
		this.responseHandler = responseHandler;
	}
	
	public RequestAbstractAction(HttpClient httpClient, String requestURL, ResponseHandler<T> responseHandler){
		this(httpClient, responseHandler);
		this.requestURL = requestURL;
	}
	
	public void setRequestURL(String url){
		this.requestURL = url;
	}
	
	public String getRequestURL(){
		return this.requestURL;
	}

	@Override
	public HttpClient getHttpClient() {
		return this.httpClient;
	}

}
