package core.entity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

public class HttpPostEntity<T> extends HttpAbstractEntity<T> {

	public HttpPostEntity(String targetURL) {
		super(targetURL);
	}

	@Override
	protected HttpUriRequest doCreateHttpRequest(String url) {
		return new HttpPost(url);
	}

	@Override
	public void releaseURIRequest() {
		((HttpPost)uriRequest).releaseConnection();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
