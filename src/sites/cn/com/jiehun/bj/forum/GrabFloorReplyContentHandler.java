package sites.cn.com.jiehun.bj.forum;

import httpClient.reply.ReplyPolicy;
import httpClient.reply.ResponseReplyContentHandler;

public class GrabFloorReplyContentHandler implements
		ResponseReplyContentHandler {
	
	private int preFloorCount = 0;
	
	public GrabFloorReplyContentHandler(int preFloorCount){
		this.preFloorCount = preFloorCount;
	}

	@Override
	public String getReplyContent(int curFloorCount, ReplyPolicy replyPolicy) {
		// TODO Auto-generated method stub
		return null;
	}

}
