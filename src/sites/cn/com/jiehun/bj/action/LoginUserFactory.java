package sites.cn.com.jiehun.bj.action;

import sites.cn.com.jiehun.bj.dataConst.UserConst;
import core.HttpParam;

public class LoginUserFactory {
	
	private static final HttpParam USER_LJY = new HttpParam(UserConst.PARAM_VAL_LOGIN_USER_NAME_LJY, UserConst.PARAM_VAL_LOGIN_PSW_LJY);
	
	private static final HttpParam USER_DR = new HttpParam(UserConst.PARAM_VAL_LOGIN_USER_NAME_DR, UserConst.PARAM_VAL_LOGIN_PSW_DR);
	
	public static HttpParam getUser(String user){
		if(user.trim().toLowerCase().equals("ljy")){
			return USER_LJY;
		}else if(user.trim().toLowerCase().equals("dr")){
			return USER_DR;
		}
		else{
			return USER_LJY;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
