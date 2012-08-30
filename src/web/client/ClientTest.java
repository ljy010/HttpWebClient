package web.client;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://bj.jiehun.com.cn/bbs/topic/27567.html");
		try {
//			HttpContext context = new BasicHttpContext();
//			HttpResponse response = client.execute(get, context);
//			CookieOrigin cookieOrgin = (CookieOrigin)context.getAttribute(ClientContext.COOKIE_ORIGIN);
//			System.out.println(cookieOrgin);
//			
//			CookieSpec cookieSpec = (CookieSpec)context.getAttribute(ClientContext.COOKIE_SPEC);
//			System.out.println(cookieSpec);
			
			CookieStore cookieStore = client.getCookieStore();
			//System.out.println(cookieStore);
			
			for(int i = 0; i < cookieStore.getCookies().size(); i++){
				Cookie cookie = cookieStore.getCookies().get(i);
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}
		}finally{
			get.releaseConnection();
		}

	}

}
