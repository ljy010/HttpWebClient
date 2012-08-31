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
	
	private AfterResponseHandler reponseHandler = null;
	
	public void setReponseHandler(AfterResponseHandler reponseHandler) {
		this.reponseHandler = reponseHandler;
	}

	public BrowsePageRunner(HttpClient httpClient, HttpContext httpContext, String url){
		this.httpClient = httpClient;
		this.httpContext = httpContext;
		this.url = url;
	}

	@Override
	public void run() {
		if((this.url == null) || ("".equals(this.url))){
			return;
		}
		HttpGet httpGet = new HttpGet(this.url);
		try{
			HttpResponse response = httpClient.execute(httpGet, httpContext);
			if(reponseHandler != null){
				reponseHandler.handle(response);
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
