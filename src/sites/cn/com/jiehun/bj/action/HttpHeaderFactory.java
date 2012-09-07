package sites.cn.com.jiehun.bj.action;

import core.HttpHeaders;

public class HttpHeaderFactory {
	
	private static HttpHeaders DefaultHeader = null;
	
	private static HttpHeaders DefaultForumHeader = null;
	
	public static HttpHeaders getDefaultHeader(){
		if(DefaultHeader == null){
			DefaultHeader = new HttpHeaders();
		    DefaultHeader.addOrUpdateHeader("host", "bj.jiehun.com.cn");	
		    DefaultHeader.addOrUpdateHeader("connection", "keep-alive");	
		    DefaultHeader.addOrUpdateHeader("origin", "http://bj.jiehun.com.cn");	
		    DefaultHeader.addOrUpdateHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");	
		    DefaultHeader.addOrUpdateHeader("Content-type", "application/x-www-form-urlencoded");	
		    DefaultHeader.addOrUpdateHeader("Accept-Encoding", "gzip,deflate,sdch");	
		    DefaultHeader.addOrUpdateHeader("Accept-Language", "zh-CN,zh;q=0.8");	
		    DefaultHeader.addOrUpdateHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");	
		}
		return DefaultHeader;
	}

	public static HttpHeaders getForumPostHeaders(){
		if(DefaultForumHeader == null){
			DefaultForumHeader = new HttpHeaders();
			DefaultForumHeader.addOrUpdateHeader("host", "bj.jiehun.com.cn");
			DefaultForumHeader.addOrUpdateHeader("connection", "keep-alive");
			DefaultForumHeader.addOrUpdateHeader("origin", "http://bj.jiehun.com.cn");
			DefaultForumHeader.addOrUpdateHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
			DefaultForumHeader.addOrUpdateHeader("Content-type", "application/x-www-form-urlencoded");
			DefaultForumHeader.addOrUpdateHeader("Accept-Encoding", "gzip,deflate,sdch");
			DefaultForumHeader.addOrUpdateHeader("Accept-Language", "zh-CN,zh;q=0.8");
			DefaultForumHeader.addOrUpdateHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			DefaultForumHeader.addOrUpdateHeader("X-Requested-With", "XMLHttpRequest");
			DefaultForumHeader.addOrUpdateHeader("Cache-Control", "no-cache");
			DefaultForumHeader.addOrUpdateHeader("Accept", "*/*"); 	
		}
		return DefaultForumHeader;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
