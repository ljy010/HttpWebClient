package core.entity;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

import core.HttpHeader;
import core.HttpHeaders;
import core.HttpParam;
import core.HttpParams;

public interface HttpEntity<T> {

	T execute(HttpClient httpClient, ResponseHandler<T> responseHandler);
	
	HttpUriRequest createHttpRequest(String url);
	
	void releaseURIRequest();
	
	void setHttpHeaders(HttpHeaders httpHeaders);
	
	void setHttpParams(HttpParams httpParams); 
	
	void addHeader(HttpHeader httpHeader);
	
	void addParam(HttpParam httpParam);
	
	

}
