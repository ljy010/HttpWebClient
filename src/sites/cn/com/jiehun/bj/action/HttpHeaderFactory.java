package sites.cn.com.jiehun.bj.action;

import core.HttpHeaders;

public class HttpHeaderFactory {
	
	private static HttpHeaders DefaultGetHeader = null;
	
	private static HttpHeaders DefaultForumPostHeader = null;
	
	public static HttpHeaders getDefaultGetHeader(){
		if(DefaultGetHeader == null){
			DefaultGetHeader = new HttpHeaders();
			DefaultGetHeader.addOrUpdateHeader("host", "bj.jiehun.com.cn");	
			DefaultGetHeader.addOrUpdateHeader("connection", "keep-alive");		
			DefaultGetHeader.addOrUpdateHeader("Cache-Control", "max-age=0");
			DefaultGetHeader.addOrUpdateHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");	
			DefaultGetHeader.addOrUpdateHeader("Content-type", "application/x-www-form-urlencoded");	
			DefaultGetHeader.addOrUpdateHeader("Accept-Encoding", "gzip,deflate,sdch");	
			DefaultGetHeader.addOrUpdateHeader("Accept-Language", "zh-CN,zh;q=0.8");	
			DefaultGetHeader.addOrUpdateHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			DefaultGetHeader.addOrUpdateHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"); 	
		}
		return DefaultGetHeader;
	}

	public static HttpHeaders getForumPostHeaders(){
		if(DefaultForumPostHeader == null){
			DefaultForumPostHeader = new HttpHeaders();
			DefaultForumPostHeader.addOrUpdateHeader("host", "bj.jiehun.com.cn");
			DefaultForumPostHeader.addOrUpdateHeader("connection", "keep-alive");
			DefaultForumPostHeader.addOrUpdateHeader("origin", "http://bj.jiehun.com.cn");
			DefaultForumPostHeader.addOrUpdateHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
			DefaultForumPostHeader.addOrUpdateHeader("Content-type", "application/x-www-form-urlencoded");
			DefaultForumPostHeader.addOrUpdateHeader("Accept-Encoding", "gzip,deflate,sdch");
			DefaultForumPostHeader.addOrUpdateHeader("Accept-Language", "zh-CN,zh;q=0.8");
			DefaultForumPostHeader.addOrUpdateHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
			DefaultForumPostHeader.addOrUpdateHeader("X-Requested-With", "XMLHttpRequest");
			DefaultForumPostHeader.addOrUpdateHeader("Cache-Control", "no-cache");
			DefaultForumPostHeader.addOrUpdateHeader("Accept", "*/*"); 	
		}
		return DefaultForumPostHeader;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
