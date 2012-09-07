package core.entity;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;

import core.HttpHeader;
import core.HttpHeaders;
import core.HttpParam;
import core.HttpParams;

public abstract class HttpAbstractEntity<T> implements HttpEntity<T> {
	
	protected HttpHeaders headers = new HttpHeaders();
	
	protected HttpParams httpParams = new HttpParams();

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
	
	protected abstract HttpUriRequest doCreateHttpRequest(String url);
	
	public HttpUriRequest createHttpRequest(String url){
		HttpUriRequest request = doCreateHttpRequest(url);
		requestMethod = request.getMethod().trim().toUpperCase();
		return request;
	}
	
	protected String buildHttpGetParamPartURI(){
		try{
			URIBuilder uriBuilder = new URIBuilder();
			for(int i = 0; i < httpParams.getParamCount(); i++){
				HttpParam httpParam = httpParams.getHttpParam(i);
				uriBuilder.setParameter(httpParam.getParamName(), httpParam.getParamVal());
			}
			return uriBuilder.build().toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	protected String getRequestMethod(){
		return uriRequest.getMethod().trim().toUpperCase();
	}
	
	protected void initHttpPostParams(){
		if((requestMethod != null) && (requestMethod.equals("POST"))){
			try{
				HttpPost httpPost = (HttpPost) uriRequest;
				List<NameValuePair> nameValPair = httpParams.transToNameValuePairs();
				UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(nameValPair, urlEncoded);
				httpPost.setEntity(formEntiry);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.headers = httpHeaders;
	}
	
	public void setHttpParams(HttpParams httpParams){
		this.httpParams = httpParams;
	}
	
	@Override
	public void addHeader(HttpHeader httpHeader) {
		if(headers == null){
			throw new RuntimeException("httpHeaders不能为空!");
		}
		if(httpHeader != null){
			headers.addOrUpdateHeader(httpHeader.getHeaderName(), httpHeader.getHeaderVal());
		}
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
	
	private void processorURI(){
		if((requestMethod != null) && (requestMethod.equals("GET"))){
			String paramStr = buildHttpGetParamPartURI();
			target = target + paramStr;
		}
	}

	@Override
	public T execute(HttpClient httpClient, ResponseHandler<T> responseHandler){
		processorURI();
		HttpUriRequest uriRequest = createHttpRequest(target);
		try{
			initHttpHeaders();
			initHttpPostParams();
			return httpClient.execute(uriRequest, responseHandler);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseURIRequest();
		}
		return null;
	}
	

}
