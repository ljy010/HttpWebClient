package sites.cn.com.jiehun.bj.forum;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
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
import org.htmlparser.util.ParserException;

import sites.cn.com.jiehun.bj.common.ReplyConst;

import httpClient.AfterResponseHandler;
import httpClient.BrowseConst;

public class ParseURLHandler implements AfterResponseHandler {
	
	private HttpContext httpContext;
	
	private String pageURL = ReplyConst.PARSE_PAGE_URL; 
	
	private String keyStr = null;
	
	public ParseURLHandler(HttpContext httpContext, String keyStr, String pageURL){
		this.httpContext = httpContext;
		this.keyStr = keyStr;
		if((pageURL != null) && (!"".equals(pageURL))){
			this.pageURL = pageURL;	
		}
	}

	@Override
	public void handle(HttpResponse response) {
		if((pageURL == null) || ("".equals(pageURL))){
			System.out.println("没有要处理的URL!");
			return;
		}
		Parser httpParser = new Parser();
		try {
			httpParser.setURL(pageURL);
			
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
					if(context.indexOf(keyStr) != -1){
						url = l.getLink();
						break;
					}
				}
			}
			if(url != null){
				System.out.println(url);
			    httpContext.setAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL, url);	
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		HttpContext httpContext = new BasicHttpContext();
		ParseURLHandler parseURLHandler = new ParseURLHandler(httpContext, "8月30日签到", null);
		parseURLHandler.handle(null);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
