package sites.cn.com.jiehun.bj.common;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class LoginConst {

	public static String PARAM_NAME_LOGIN_USER = "user_name";
	
	public static String PARAM_NAME_LOGIN_PSW = "psw";
	
	public static String PARAM_VAL_LOGIN_USER_NAME_LJY = "13240006799";
	
	public static String PARAM_VAL_LOGIN_PSW_LJY = "610fda4f5c7b4154eaf629499d48d7fd";
	
	public static String PARAM_VAL_LOGIN_USER_NAME_DR = "18610318624";

	public static String PARAM_VAL_LOGIN_PSW_DR = "388338eba7454b2922e5a677c3807542";
	
	public static String FORUM_LOGIN_ACTION_URL = "http://bj.jiehun.com.cn/accounts/_login";
	
	public static String FORUM_LOGIN_URL = "http://bj.jiehun.com.cn/accounts/login?u=%2Fbbs%2F";
	
	public static NameValuePair LOGIN_USER_NAME_LJY = new BasicNameValuePair(PARAM_NAME_LOGIN_USER, PARAM_VAL_LOGIN_USER_NAME_LJY);
	
	public static NameValuePair LOGIN_USER_PSW_LJY = new BasicNameValuePair(PARAM_NAME_LOGIN_PSW, PARAM_VAL_LOGIN_PSW_LJY);
	
	public static NameValuePair LOGIN_USER_NAME_DR = new BasicNameValuePair(PARAM_NAME_LOGIN_USER, PARAM_VAL_LOGIN_USER_NAME_DR);
	
	public static NameValuePair LOGIN_USER_PSW_DR = new BasicNameValuePair(PARAM_NAME_LOGIN_PSW, PARAM_VAL_LOGIN_PSW_DR);
	
}
