package sites.cn.com.jiehun.bj.forum;

import org.apache.http.client.methods.HttpPost;

public abstract class PostRunner implements Runnable {

	protected void initHttpHeader(HttpPost httpPost){
		httpPost.addHeader("host", "bj.jiehun.com.cn");
		httpPost.addHeader("connection", "keep-alive");
		httpPost.addHeader("origin", "http://bj.jiehun.com.cn");
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
	}
}
