package sites.cn.com.jiehun.bj.scene.responseHandler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import sites.cn.com.jiehun.bj.dataConst.ForumConst;
import sites.cn.com.jiehun.bj.scene.ParseResult;

public class ParseByTitleKeyWordReponseHandler implements ResponseHandler<ParseResult> {
	
	private String titleKeyWord;
	
	private ParseResult parseResult;
	
	public ParseByTitleKeyWordReponseHandler(String keyWord, ParseResult parseResult){
		this.titleKeyWord = keyWord;
		this.parseResult = parseResult;
	}

	@Override
	public ParseResult handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		try {
			String content = EntityUtils.toString(response.getEntity());
			Parser httpParser = new Parser(content);
			NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
			
			NodeFilter divParentFilter = new NodeClassFilter(Div.class);
			NodeFilter divParentAttrFilter = new HasAttributeFilter("class", "g-u slt nomal_img");
			NodeFilter divParentAndFilter = new AndFilter(divParentFilter, divParentAttrFilter);
		    
		    NodeFilter ppFilter = new NodeClassFilter(Div.class);
		    NodeFilter ppAttrFilter = new HasAttributeFilter("class", "title");
		    NodeFilter ppAndFilter = new AndFilter(ppFilter, ppAttrFilter);
		    
		    NodeFilter parentDivFilter = new AndFilter(ppAndFilter, new HasParentFilter(divParentAndFilter));
		    			    
		    NodeFilter linkAndFilter = new AndFilter(linkFilter, new HasParentFilter(parentDivFilter));
			
		    NodeList nodes = httpParser.extractAllNodesThatMatch(linkAndFilter);
		    String url = null;
			for(int i = 0; i < nodes.size(); i++){
				Node node = nodes.elementAt(i);
				if(node instanceof LinkTag){
					LinkTag l = (LinkTag)node;
					String context = l.getLinkText();
					if(context.indexOf(titleKeyWord) != -1){
						url = l.getLink();
						break;
					}
				}
			}
			if(url != null){
				url = ForumConst.HOST + url;
				System.out.println(url);
			    parseResult.setResult(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return parseResult;
	}

}
