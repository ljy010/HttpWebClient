package httpClient.reply;

import java.util.ArrayList;
import java.util.List;


public class GrabFloorDiffReplyPolicy implements ReplyPolicy {
	
	private FloorReply defaultReply = null;
	
	private List<FloorReply> replys = new ArrayList<FloorReply>();

	@Override
	public void addReply(FloorReply floorReply) {
		replys.add(floorReply);
	}

	@Override
	public FloorReply getDefaultReply() {
		return defaultReply;
	}

	@Override
	public FloorReply getReplyContent(int index) {
		return replys.get(index);
	}

	@Override
	public int replyContentCount() {
		return replys.size();
	}

	@Override
	public void setDefaultReply(FloorReply defaultReply) {
		this.defaultReply = defaultReply;
	}
	
}
