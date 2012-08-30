package httpClient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpContext;

public class BrowsePageRunner implements Runnable {
	
	private HttpClient httpClient;
	
	private HttpContext httpContext;
	
	private String url;
	
	public BrowsePageRunner(HttpClient httpClient, HttpContext httpContext){
		this.httpClient = httpClient;
		this.httpContext = httpContext;
		this.url = (String)httpContext.getAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL);
	}

	@Override
	public void run() {
		if((this.url == null) || ("".equals(this.url))){
			return;
		}
		HttpGet httpGet = new HttpGet(this.url);
		try{
			HttpResponse response = httpClient.execute(httpGet, httpContext);
			AfterResponseHandler responseHandler = (AfterResponseHandler)httpContext.getAttribute(BrowseConst.CONTEXT_AFTER_HANDLE);
			if(responseHandler != null){
				responseHandler.handle(response);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpGet.releaseConnection();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
