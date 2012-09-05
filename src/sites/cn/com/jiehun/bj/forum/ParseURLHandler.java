package sites.cn.com.jiehun.bj.forum;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
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
import org.htmlparser.util.ParserException;

import sites.cn.com.jiehun.bj.common.ReplyConst;

import httpClient.AfterResponseHandler;
import httpClient.BrowseConst;

public class ParseURLHandler implements AfterResponseHandler {
	
	private HttpContext httpContext;
	
	private String keyStr = null;
	
	public ParseURLHandler(HttpContext httpContext, String keyStr){
		this.httpContext = httpContext;
		this.keyStr = keyStr;
		
	}

	@Override
	public void handle(HttpResponse response) {
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
					if(context.indexOf(keyStr) != -1){
						url = l.getLink();
						break;
					}
				}
			}
			if(url != null){
				url = ReplyConst.HOST + url;
				System.out.println(url);
			    httpContext.setAttribute(BrowseConst.CONTEXT_BROWSE_ATTRIBUTE_URL, url);	
			}
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		HttpContext httpContext = new BasicHttpContext();
		ParseURLHandler parseURLHandler = new ParseURLHandler(httpContext, "8ÔÂ30ÈÕÇ©µ½");
		parseURLHandler.handle(null);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
