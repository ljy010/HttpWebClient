package sites.cn.com.jiehun.bj.action;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import core.HttpHeaders;
import core.entity.HttpEntity;
import core.entity.HttpGetEntity;

public class ParseRequestAction<T> extends RequestAbstractAction<T> {

	public ParseRequestAction(HttpClient httpClient,
			ResponseHandler<T> responseHandler) {
		super(httpClient, responseHandler);
	}

	@Override
	public T execute() {
		if((getRequestURL() == null) || (!"".equals(getRequestURL()))){
			throw new RuntimeException("�����ַ����Ϊ��!");
		}
		
		HttpHeaders defaultHttpHeader = HttpHeaderFactory.getDefaultHeader();
		HttpEntity<T> httpEntity = new HttpGetEntity<T>(getRequestURL());
		httpEntity.setHttpHeaders(defaultHttpHeader);
		
		return httpEntity.execute(getHttpClient(), getResponseHandler());
	}

}