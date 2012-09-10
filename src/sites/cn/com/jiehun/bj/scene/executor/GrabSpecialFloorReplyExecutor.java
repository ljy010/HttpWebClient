package sites.cn.com.jiehun.bj.scene.executor;

import sites.cn.com.jiehun.bj.dataConst.ForumConst;
import sites.cn.com.jiehun.bj.scene.ParseResult;
import core.HttpParams;

public class GrabSpecialFloorReplyExecutor extends AfterParsingReplyExecutor {

	private int[] floors = null;

	private int preReplyFloorCount = 5;

	private int immediaReplyFloorMinusCount = 2;

	private long floorMinusSleepInterval = 100;

	private String replyStr = null;
	
	private int currentFloorCount = 0;

	public GrabSpecialFloorReplyExecutor(String loginUser, 
			                             String parsePageURL,
			                             ExecutorConfig executorConfig, 
			                             int[] floors, 
			                             String parseKeyWord,
			                             String replyStr) {
		super(loginUser, parsePageURL, executorConfig, parseKeyWord);
		this.floors = floors;
		this.replyStr = replyStr;
	}

	public GrabSpecialFloorReplyExecutor(String loginUser, String parsePageURL,
			ExecutorConfig executorConfig, int[] floors, String replyStr,
			ParseResult parseResult) {
		super(loginUser, parsePageURL, executorConfig, parseResult);
		this.floors = floors;
		this.replyStr = replyStr;
	}

	private int extractFloorCount() {
		HttpParams httpParams = createReplyHttpParams();
		String floorCountStr = httpParams
				.getHttpParamVal(ForumConst.FORM_REPLY_INPUT_PARAM_NAME_POST_TOTAL);
		int floorCount = Integer.valueOf(floorCountStr) + 1;
		System.out.println("��ǰ¥��:" + floorCount);
		return floorCount;
	}

	protected boolean checkHttpParams(HttpParams httpParams) {
		try {
			if (super.checkHttpParams(httpParams)) {
				for (int replyFloorIndex : floors) {
					int preReplyBeginFloorIndex = replyFloorIndex
							- preReplyFloorCount;

					currentFloorCount = extractFloorCount();

					while ((currentFloorCount < preReplyBeginFloorIndex)
							&& (currentFloorCount > 0)) {
						currentFloorCount = extractFloorCount();
					}

					if ((replyFloorIndex > currentFloorCount)) {
						int minusFloor = replyFloorIndex - currentFloorCount;
						if (minusFloor > immediaReplyFloorMinusCount) {
							Thread.sleep(floorMinusSleepInterval * (minusFloor - immediaReplyFloorMinusCount));
						}
						return true;
					}

				}
				throw new RuntimeException("��Ҫ����¥�㶼��ռ��!!");

			} else {
				throw new RuntimeException("û�н���ȡ��¥������!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	protected String getReplyContent() {
		return replyStr;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
