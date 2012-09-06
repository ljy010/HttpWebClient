package core.formSubmit;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

public class FormSubmitExecutor {
	
	private String formActionURL = null;
	
	private HttpClient httpClient = null;
	
	private FormSubmitExecutor(HttpClient httpClient, String formActionURL){
		this.formActionURL = formActionURL;
		this.httpClient = httpClient;
	}
	
	public void submit(){
		HttpPost httpPost = new HttpPost(formActionURL);
		try{
			httpClient.
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			httpPost.releaseConnection();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
