package sites.cn.com.jiehun.bj.submit;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import httpClient.BrowsePageRunner;
import httpClient.reply.FloorReply;
import httpClient.reply.ReplyPolicy;
import sites.cn.com.jiehun.bj.common.ReplyConst;
import sites.cn.com.jiehun.bj.forum.ParseFormInputParamHandler;

public class AutoGrabFloorRunner extends AutoReplyRunner {

	private List<NameValuePair> replyParamNameValList = new ArrayList<NameValuePair>();

	private int preReplyCount = 5;
	
	private int[] floors = null;

	public AutoGrabFloorRunner(String loginUser, String keyStr,
			ReplyPolicy replyPolicy, AutoReplyConfig autoReplyConfig) {
		super(loginUser, keyStr, replyPolicy, autoReplyConfig);
	}
	
	public void setFloors(int[] floors){
		this.floors = floors;
	}
	
	protected int refleshCurFloorCount() {
		this.replyParamNameValList.clear();
		BrowsePageRunner httpGetReplyPage = new BrowsePageRunner(httpClient,
				httpContext, replyPageURL);
		ParseFormInputParamHandler formInputParamHandler = new ParseFormInputParamHandler(
				this.replyParamNameValList, this.replyPolicy);
		httpGetReplyPage.setReponseHandler(formInputParamHandler);
		httpGetReplyPage.run();
		return formInputParamHandler.getCurFloorCount();
	}

	protected void doReply() throws InterruptedException{
		for(int i = 0; i < floors.length; i++){
			int reachFloor = floors[i] - preReplyCount;
			int curFloor = refleshCurFloorCount();
			while(curFloor < reachFloor){
				System.out.println("current floor: " + curFloor);
				curFloor = refleshCurFloorCount();
			}
			System.out.println("µ½´ïÂ¥²ã: " + curFloor);
			if(curFloor < floors[i]){
				int floorMinus = floors[i] - curFloor;
				if(floorMinus > 2){
					Thread.sleep(100 * (floorMinus - 2));
				}
				for(int j = 0; j < this.replyPolicy.replyContentCount(); j++){
					int curReplyMaxFloor = curFloor + preReplyCount;
					FloorReply floorReply = this.replyPolicy.getReplyContent(j);
					int replyFloor = floorReply.getFloorIndex();
					if((replyFloor >= curFloor) && (replyFloor <= curReplyMaxFloor)){
						
					}
				}
			}
			
			
		}
	}
}
