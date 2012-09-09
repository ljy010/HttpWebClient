package core.entity;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
	
	protected void initHttpPostParams(){
		try{
			HttpPost httpPost = (HttpPost) uriRequest;
			List<NameValuePair> nameValPair = httpParams.transToNameValuePairs();
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(nameValPair, urlEncoded);
			httpPost.setEntity(formEntiry);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public T execute(HttpClient httpClient, ResponseHandler<T> responseHandler){
		uriRequest = createHttpRequest(target);
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
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
