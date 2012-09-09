package core.entity;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;

import core.HttpParam;

public class HttpGetEntity<T> extends HttpAbstractEntity<T> {

	public HttpGetEntity(String targetURL) {
		super(targetURL);
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
	
	@Override
	protected HttpUriRequest doCreateHttpRequest(String url) {
		return new HttpGet(url);
	}
	
	
	protected void processorURI(){
		String paramStr = buildHttpGetParamPartURI();
		target = target + paramStr;
	}
	
	@Override
	public void releaseURIRequest() {
		((HttpGet)uriRequest).releaseConnection();
	}
	
	@Override
	public T execute(HttpClient httpClient, ResponseHandler<T> responseHandler){
		processorURI();
		uriRequest = createHttpRequest(target);
		try{
			initHttpHeaders();
			return httpClient.execute(uriRequest, responseHandler);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseURIRequest();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpGetEntity<String> getEntity = new HttpGetEntity<String>("http://www.aaa.com");
		getEntity.addParam(new HttpParam("a", "b"));
		System.out.println(getEntity.buildHttpGetParamPartURI());
	}

}
