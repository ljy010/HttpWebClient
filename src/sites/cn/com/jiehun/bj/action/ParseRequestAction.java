package sites.cn.com.jiehun.bj.action;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import sites.cn.com.jiehun.bj.dataConst.ForumConst;

import core.HttpHeaders;
import core.entity.HttpEntity;
import core.entity.HttpGetEntity;

public class ParseRequestAction<T> extends RequestAbstractAction<T> {
	
	private String referURL = ForumConst.FORUM_GET_REFER_URL;

	public ParseRequestAction(HttpClient httpClient, String requestURL,
			ResponseHandler<T> responseHandler) {
		super(httpClient, requestURL, responseHandler);
	}

	@Override
	public T execute() {
		if((getRequestURL() == null) || ("".equals(getRequestURL()))){
			throw new RuntimeException("请求地址不能为空!");
		}
		HttpHeaders httpHeaders = HttpHeaderFactory.getDefaultGetHeader();
		if((referURL != null) && (!"".equals(referURL))){
			httpHeaders.addOrUpdateHeader("Referer", referURL);
		}
		HttpEntity<T> httpEntity = new HttpGetEntity<T>(getRequestURL());
		httpEntity.setHttpHeaders(httpHeaders);
		
		return httpEntity.execute(getHttpClient(), getResponseHandler());
	}

}
