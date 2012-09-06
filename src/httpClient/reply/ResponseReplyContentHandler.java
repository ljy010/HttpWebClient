package httpClient.reply;

public interface ResponseReplyContentHandler {

	String getReplyContent(int curFloorCount, ReplyPolicy replyPolicy);
}
