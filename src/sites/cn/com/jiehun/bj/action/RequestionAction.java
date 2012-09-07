package sites.cn.com.jiehun.bj.action;

import org.apache.http.client.HttpClient;

public interface RequestionAction<T> {

	HttpClient getHttpClient();
	
	String getRequestURL();
	
	void setRequestURL(String url);
	
	T execute();
}
