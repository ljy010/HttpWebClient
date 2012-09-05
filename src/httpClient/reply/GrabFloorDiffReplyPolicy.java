package httpClient.reply;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class GrabFloorDiffReplyPolicy implements ReplyPolicy {
	
	private Map<Integer, String> replyMap = null;
	
	private String defaultReply;
	
	public GrabFloorDiffReplyPolicy(String defaultReply){
		replyMap = new TreeMap<Integer, String>(new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
			
		});
		setDefaultReply(defaultReply);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReplyContent(int curFloorIndex) {
		String reply = replyMap.get(curFloorIndex);
		if((reply == null) || ("".equals(reply))){
			return getDefaultReply();
		}
		else{
			return reply;
		}
	}

	@Override
	public int replyContentCount() {
		return replyMap.size();
	}

	@Override
	public void setReply(int floorIndex, String reply) {
		replyMap.put(floorIndex, reply);
	}

	@Override
	public String getDefaultReply() {
		return this.defaultReply;
	}

	@Override
	public void setDefaultReply(String reply) {
		this.defaultReply = reply;
	}

}
