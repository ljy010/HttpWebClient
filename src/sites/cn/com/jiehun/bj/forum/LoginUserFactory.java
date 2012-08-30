package sites.cn.com.jiehun.bj.forum;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import sites.cn.com.jiehun.bj.common.LoginConst;

public class LoginUserFactory {
	
	private static List<NameValuePair> LOGIN_USER_LJY = new ArrayList<NameValuePair>();
	
	private static List<NameValuePair> LOGIN_USER_DR = new ArrayList<NameValuePair>();
	
	static{
		LOGIN_USER_LJY.add(LoginConst.LOGIN_USER_NAME_LJY);
		LOGIN_USER_LJY.add(LoginConst.LOGIN_USER_PSW_LJY);
	}
	
	static{
		LOGIN_USER_DR.add(LoginConst.LOGIN_USER_NAME_DR);
		LOGIN_USER_DR.add(LoginConst.LOGIN_USER_PSW_DR);
	}
	
	public static List<NameValuePair> getLoginUserParam(String user){
		if("ljy".equals(user)){
			return LOGIN_USER_LJY;
		}else if("dr".equals(user)){
			return LOGIN_USER_DR;
		}
		else{
			return LOGIN_USER_LJY;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
