package sites.cn.com.jiehun.bj.action;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import core.HttpHeaders;
import core.HttpParams;
import core.entity.HttpEntity;
import core.entity.HttpPostEntity;

public class ReplyRequestAction<T> extends RequestAbstractAction<T> {

	private String referURL = null;
	
	private HttpParams httpParams = null;
	
	public ReplyRequestAction(HttpClient httpClient,
			ResponseHandler<T> responseHandler, HttpParams httpParams, String requestURL, String referURL) {
		super(httpClient, requestURL, responseHandler);
		this.referURL = referURL;
		this.httpParams = httpParams;
	}

	@Override
	public T execute() {
		if((getRequestURL() == null) || ("".equals(getRequestURL()))){
			throw new RuntimeException("请求地址不能为空!");
		}
		HttpHeaders httpHeaders = HttpHeaderFactory.getForumPostHeaders();
		if((referURL != null) && (!"".equals(referURL))){
			httpHeaders.addOrUpdateHeader("Referer", referURL);
		}
		
		HttpEntity<T> httpEntity = new HttpPostEntity<T>(getRequestURL());
		httpEntity.setHttpHeaders(httpHeaders);
		httpEntity.setHttpParams(httpParams);
		
		return httpEntity.execute(getHttpClient(), getResponseHandler());
	}

}
