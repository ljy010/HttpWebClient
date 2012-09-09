package sites.cn.com.jiehun.bj.action;

import sites.cn.com.jiehun.bj.dataConst.ForumConst;
import sites.cn.com.jiehun.bj.dataConst.UserConst;
import core.HttpParam;
import core.HttpParams;

public class LoginUserFactory {
	
	private static final HttpParams USER_LJY = new HttpParams();
	static{
		USER_LJY.addOrUpdateHttpFormParam(ForumConst.FORM_PARAM_NAME_LOGIN_USER, UserConst.PARAM_VAL_LOGIN_USER_NAME_LJY);
		USER_LJY.addOrUpdateHttpFormParam(ForumConst.FORM_PARAM_NAME_LOGIN_PSW, UserConst.PARAM_VAL_LOGIN_PSW_LJY);
	}
	
	private static final HttpParams USER_DR = new HttpParams();
	static{
		USER_DR.addOrUpdateHttpFormParam(ForumConst.FORM_PARAM_NAME_LOGIN_USER, UserConst.PARAM_VAL_LOGIN_USER_NAME_DR);
		USER_DR.addOrUpdateHttpFormParam(ForumConst.FORM_PARAM_NAME_LOGIN_PSW, UserConst.PARAM_VAL_LOGIN_PSW_DR);
	}
	
//	private static final HttpParam USER_LJY = new HttpParam(UserConst.PARAM_VAL_LOGIN_USER_NAME_LJY, UserConst.PARAM_VAL_LOGIN_PSW_LJY);
//	
//	private static final HttpParam USER_DR = new HttpParam(UserConst.PARAM_VAL_LOGIN_USER_NAME_DR, UserConst.PARAM_VAL_LOGIN_PSW_DR);
	
	public static HttpParams getUser(String user){
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
