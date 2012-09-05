package httpClient.reply;

public class DefaultReplyPolicy implements ReplyPolicy {
	
	private String reply;
	
	public DefaultReplyPolicy(String reply){
		this.reply = reply;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReplyContent(int curFloorIndex) {
		return getDefaultReply();
	}

	@Override
	public int replyContentCount() {
		return 1;
	}

	@Override
	public void setReply(int floorIndex, String reply) {
		setDefaultReply(reply);
	}

	@Override
	public String getDefaultReply() {
		return this.reply;
	}

	@Override
	public void setDefaultReply(String reply) {
		this.reply = reply;
	}


}
