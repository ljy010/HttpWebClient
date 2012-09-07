package sites.cn.com.jiehun.bj.scene.responseHandler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import core.common.HttpResponseUtils;

import sites.cn.com.jiehun.bj.scene.ReplyState;

public class PrintReplyResponseHandler implements ResponseHandler<ReplyState> {

	private ReplyState replyState = null;
	
	public PrintReplyResponseHandler(ReplyState replyState){
		this.replyState = replyState;
	}
	
	@Override
	public ReplyState handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		HttpResponseUtils.printResponse(response);
		return this.replyState;
	}

}
