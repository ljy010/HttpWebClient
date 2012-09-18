package sites.cn.com.jiehun.bj.test;

import sites.cn.com.jiehun.bj.dataConst.ForumConst;
import core.HttpParams;

public class TimePerformTest {
	
	private static FunctionTest functionTest = new FunctionTest();
	
	/**
	 * ��¼���õ�ʱ��
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
	 * ʹ��Get���������URL���õ�ʱ��
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
	 * ����һ��form�������õ�ʱ��
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
	 * һ�����������󣬰�����¼�������������ύ����
	 * �������¼�󣬵��ύ����ɹ����õ�ʱ��
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
	 * �ύһ�������ʱ��
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
	 * ����form������ƽ��ʱ��
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
	 * ����URL��ַ��ƽ��ʱ��
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
//		testParseFormParam("ljy", "http://bj.jiehun.com.cn/bbs/topic/47216.html", "ǩ����ǩ������");
//		testReply("ljy", "http://bj.jiehun.com.cn/bbs/topic/46899.html", "ǩ����ǩ������");
	}

}
