package sites.cn.com.jiehun.bj.submit;

public class AutoReplyConfig {
	
	private long loginInterval = 500;
	
	private long parseInterval = 500;
	
	private long replyPostInterval = 4000;
	

	public long getLoginInterval() {
		return loginInterval;
	}



	public void setLoginInterval(long loginInterval) {
		this.loginInterval = loginInterval;
	}



	public long getParseInterval() {
		return parseInterval;
	}



	public void setParseInterval(long parseInterval) {
		this.parseInterval = parseInterval;
	}



	public long getReplyPostInterval() {
		return replyPostInterval;
	}



	public void setReplyPostInterval(long replyPostInterval) {
		this.replyPostInterval = replyPostInterval;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
