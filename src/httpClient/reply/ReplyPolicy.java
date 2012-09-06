package httpClient.reply;

public interface ReplyPolicy {

	public FloorReply getReplyContent(int index);
	
	public int replyContentCount();
	
	public void addReply(FloorReply floorReply);
	
	public void setDefaultReply(FloorReply defaultReply);
	
	public FloorReply getDefaultReply();
}
