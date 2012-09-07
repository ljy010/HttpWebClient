package sites.cn.com.jiehun.bj.scene.responseHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;

import core.HttpParams;
import core.common.HttpHtmlParserUtils;

public class ParseFormParamHandler implements ResponseHandler<HttpParams> {

	private HttpParams httpParams = null;
	
	private Map<String, String> paramNameValPairMap = null;
	
	private Map<String, String> formPropMap = null;
	
	public ParseFormParamHandler(HttpParams httpParams, Map<String, String> paramNameValMap, Map<String, String> formPropMap){
		this.httpParams = httpParams;
		this.paramNameValPairMap = paramNameValMap;
		this.formPropMap = formPropMap;
	}
	
	@Override
	public HttpParams handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		try{
			String content = EntityUtils.toString(response.getEntity());
			Parser httpParser = new Parser(content);
		
			NodeList nodes = HttpHtmlParserUtils.getHiddenInputNodeListByForm(httpParser, formPropMap);
			for(int i = 0; i < nodes.size(); i++){
				Node node = nodes.elementAt(i);
				if(node instanceof InputTag){
					InputTag inputTag = (InputTag)node;
//					System.out.println(inputTag.toHtml());
					String  attrName = inputTag.getAttribute("name");
					if(paramNameValPairMap.containsKey(attrName)){
						String value = inputTag.getAttribute("value");
						paramNameValPairMap.put(attrName, value);
						httpParams.addOrUpdateHttpFormParam(attrName, value);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return httpParams;
	}

}
