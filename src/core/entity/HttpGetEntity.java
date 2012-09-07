package core.entity;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import core.HttpParam;

public class HttpGetEntity<T> extends HttpAbstractEntity<T> {

	public HttpGetEntity(String targetURL) {
		super(targetURL);
	}


	@Override
	public void releaseURIRequest() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpGetEntity<String> getEntity = new HttpGetEntity<String>("http://www.aaa.com");
		getEntity.addParam(new HttpParam("a", "b"));
		System.out.println(getEntity.buildHttpGetParamPartURI());
	}

	@Override
	protected HttpUriRequest doCreateHttpRequest(String url) {
		return new HttpGet(url);
	}

}
