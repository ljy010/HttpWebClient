package sites.cn.com.jiehun.bj.scene;

public interface LoginScene {

	public String getLoginUser();
	
	public void setLoginUser(String loginUser);
	
	public LoginState login();
}
