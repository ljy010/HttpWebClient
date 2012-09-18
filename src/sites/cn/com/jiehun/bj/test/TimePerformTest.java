package sites.cn.com.jiehun.bj.test;

import sites.cn.com.jiehun.bj.dataConst.ForumConst;
import core.HttpParams;

public class TimePerformTest {
	
	private static FunctionTest functionTest = new FunctionTest();
	
	/**
	 * 登录所用的时间
	 * @param loginUser
	 * @return
	 */
	public static long testLoginTime(String loginUser){
	    long start = System.currentTimeMillis();
	    functionTest.login(loginUser);
	    long end = System.currentTimeMillis();
	    long time = end - start;
	    System.out.println(time);
	    return time;
	}
	
	/**
	 * 使用Get请求解析出URL所用的时间
	 * @param keyWord
	 * @param parseMainURL
	 * @return
	 */
	public static long testSingleParseURLTime(String keyWord, String parseMainURL){
		long start = System.currentTimeMillis();
		functionTest.parseURL(keyWord, parseMainURL);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println(time);
		return time;
	}
	
	/**
	 * 
	 * 解析一次form参数所用的时间
	 */
	public static long testSingleParseFormParam(String pageURL, String replyContent, HttpParams httpParams){
		long start = System.currentTimeMillis();
		httpParams = functionTest.getFormParams(pageURL, replyContent);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println(time);
		return time;
	}
	/**
	 * 一个完整的请求，包括登录，解析参数，提交请求
	 * 并计算登录后，到提交请求成功所用的时间
	 * @param loginUser
	 * @param replyPageURL
	 * @param replyContent
	 * @return
	 */
	public static long testReply(String loginUser, String replyPageURL, String replyContent){
		functionTest.login(loginUser);
		long start = System.currentTimeMillis();
		HttpParams httpParams = functionTest.getFormParams(replyPageURL, replyContent);
		httpParams.addOrUpdateHttpFormParam(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_POST_TOTAL, "10");
		testSingleRely(httpParams);
		long end = System.currentTimeMillis();
		long replyTotalTime = end - start;
		System.out.println(replyTotalTime);
		return replyTotalTime;
	}
	/**
	 * 提交一个请求的时间
	 * @param httpParams
	 * @return
	 */
	public static long testSingleRely(HttpParams httpParams){
		long start = System.currentTimeMillis();
		functionTest.singleReply(httpParams);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println(time);
		return time;
	}
	
	/**
	 * 解析form参数的平均时间
	 * @param loginUser
	 * @param pageURL
	 * @param replyContent
	 * @return
	 */
	public static long testParseFormParam(String loginUser, String pageURL, String replyContent){
		functionTest.login(loginUser);
		long avgTime = 0;
		int count = 20;
		long parseFormTime = 0;
		HttpParams httpParams = new HttpParams();
		try{
			for(int i = 0; i < count; i++){
				parseFormTime = testSingleParseFormParam(pageURL, replyContent, httpParams);
				if(httpParams.checkIsValid()){
					avgTime += parseFormTime;					
				}
				Thread.sleep(500);
			}
			System.out.println("parseFormParam avg time:" + avgTime / count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return avgTime / count;
		
		
	}
	
	/**
	 * 解析URL地址的平均时间
	 * @param keyWord
	 * @param parseMainURL
	 * @return
	 */
	public static long testAvgParseURLTime(String keyWord, String parseMainURL){
		int count = 20;
		long avgTime = 0;
		try{
			for(int i = 0; i < count; i++){
				avgTime += testSingleParseURLTime(keyWord, parseMainURL);
				Thread.sleep(500);
			}
			System.out.println("parseURL avg time:" + avgTime / count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return avgTime / count;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		testLoginTime("ljy");
//		testParseFormParam("ljy", "http://bj.jiehun.com.cn/bbs/topic/47216.html", "签退啦签退啦哈");
//		testReply("ljy", "http://bj.jiehun.com.cn/bbs/topic/46899.html", "签到啦签到啦哈");
	}

}
