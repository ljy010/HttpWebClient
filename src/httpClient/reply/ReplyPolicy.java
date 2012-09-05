package httpClient.reply;

public interface ReplyPolicy {

	public FloorReply getReplyContent(int index);
	
	public int replyContentCount();
	
	public void setReply(int floorIndex, String reply);
	
	public void setDefaultReply(String reply);
	
	public String getDefaultReply();
}
