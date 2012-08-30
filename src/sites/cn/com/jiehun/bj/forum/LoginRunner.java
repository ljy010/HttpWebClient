package sites.cn.com.jiehun.bj.forum;

import httpClient.BrowseConst;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import sites.cn.com.jiehun.bj.common.LoginConst;

public class LoginRunner extends PostRunner {
	
	private HttpClient httpClient;
	
	private HttpContext httpContext;
	
	private String user;
	
	public LoginRunner(HttpClient httpClient, HttpContext httpContext){
		this.httpClient = httpClient;
		this.httpContext = httpContext;
		this.user = (String)httpContext.getAttribute(BrowseConst.CONTEXT_LOGIN_USER);
	}
	

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		HttpPost post = new HttpPost(LoginConst.FORUM_LOGIN_ACTION_URL);
		try {
			initHttpHeader(post);
			java.util.List<NameValuePair> formParams = LoginUserFactory.getLoginUserParam(user);
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(formParams, "UTF-8");
			post.setEntity(formEntiry);
			HttpResponse response = httpClient.execute(post, httpContext);
			HttpEntity responseEntry = response.getEntity();
			System.out.println(EntityUtils.toString(responseEntry));
			System.out.println(".....logined");
			httpContext.setAttribute(BrowseConst.CONTEXT_IS_LOGINED, true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			post.releaseConnection();
			long end = System.currentTimeMillis();
			System.out.println(end - start);
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(BrowseConst.CONTEXT_LOGIN_USER, "ljy");
		LoginRunner login = new LoginRunner(httpClient, httpContext);
		login.run();
	}

}
