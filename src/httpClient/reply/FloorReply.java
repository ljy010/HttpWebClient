package httpClient.reply;

public class FloorReply {
	
	private int floorIndex;
	
	private String reply;
	
	public FloorReply(int floorIndex, String reply){
		this.floorIndex = floorIndex;
		this.reply = reply;
	}

	public int getFloorIndex() {
		return floorIndex;
	}

	public void setFloorIndex(int floorIndex) {
		this.floorIndex = floorIndex;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
