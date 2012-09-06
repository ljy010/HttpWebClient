package httpClient.reply;

public class DefaultReplyPolicy implements ReplyPolicy {
	
	private FloorReply defaultReply;
	
	public DefaultReplyPolicy(String reply){
		defaultReply = new FloorReply(-1, reply);
	}

	@Override
	public void addReply(FloorReply floorReply) {
		return;
	}

	@Override
	public FloorReply getDefaultReply() {
		return defaultReply;
	}

	@Override
	public FloorReply getReplyContent(int index) {
		return getDefaultReply();
	}

	@Override
	public int replyContentCount() {
		return 1;
	}

	@Override
	public void setDefaultReply(FloorReply defaultReply) {
		this.defaultReply = defaultReply;
	}
	
}
