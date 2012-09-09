package core.entity;

import org.apache.http.client.methods.HttpUriRequest;

import core.HttpHeader;
import core.HttpHeaders;
import core.HttpParam;
import core.HttpParams;

public abstract class HttpAbstractEntity<T> implements HttpEntity<T> {
	
	protected HttpParams httpParams = new HttpParams();
	
	protected HttpHeaders headers = new HttpHeaders();

	protected HttpUriRequest uriRequest = null;
	
	protected String requestMethod = null;

	protected String target = null;
	
	protected String urlEncoded = "UTF-8";
	
	public HttpAbstractEntity(String targetURL){
		this.target = targetURL;
	}
	
	public HttpAbstractEntity(String targetURL, String urlEncoded){
		this(targetURL);
		this.urlEncoded = urlEncoded;
	}
	
	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.headers = httpHeaders;
	}
	
	public void addHeader(HttpHeader httpHeader) {
		if(headers == null){
			throw new RuntimeException("httpHeaders不能为空!");
		}
		if(httpHeader != null){
			headers.addOrUpdateHeader(httpHeader.getHeaderName(), httpHeader.getHeaderVal());
		}
	}	
	
	protected abstract HttpUriRequest doCreateHttpRequest(String url);
	
	public HttpUriRequest createHttpRequest(String url){
		HttpUriRequest request = doCreateHttpRequest(url);
		requestMethod = request.getMethod().trim().toUpperCase();
		return request;
	}
	
	protected void initHttpHeaders(){
		if(headers == null){
			throw new RuntimeException("httpHeaders不能为空!");
		}
		if(uriRequest == null){
			throw new RuntimeException("URIRequest不能为空!");
		}
		for(int i = 0; i < headers.getHeaderCount(); i++){
			HttpHeader httpHeader = headers.getHeader(i);
			uriRequest.addHeader(httpHeader.getHeaderName(), httpHeader.getHeaderVal());
		}
	}
	
	
	protected String getRequestMethod(){
		return uriRequest.getMethod().trim().toUpperCase();
	}

	public void setHttpParams(HttpParams httpParams){
		this.httpParams = httpParams;
	}
	
	@Override
	public void addParam(HttpParam httpParam) {
		if(httpParams == null){
			throw new RuntimeException("httpParams不能为空!");
		}
		if(httpParam != null){
			httpParams.addOrUpdateHttpFormParam(httpParam.getParamName(), httpParam.getParamVal());
		}
	}

}
