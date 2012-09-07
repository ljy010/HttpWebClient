package core.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HttpHtmlParserUtils {
	
	public static NodeList getHiddenInputNodeListByForm(Parser parser, Map<String, String> formAttrMap) throws ParserException{
		NodeFilter formFilter = new TagNameFilter("form");
		
		NodeFilter[] attrFilters = new NodeFilter[formAttrMap.size()]; 
		
		Set<Entry<String, String>> attrMapSet = formAttrMap.entrySet();
		Iterator<Entry<String, String>> itr = attrMapSet.iterator();
		
		int index = 0;
		while(itr.hasNext()){
			Entry<String, String> entry = itr.next();
			NodeFilter attrFilter = new HasAttributeFilter(entry.getKey(), entry.getValue());
			attrFilters[index++] = attrFilter;
		}
		NodeFilter andFilter = new AndFilter(attrFilters);
		NodeFilter formAttrFilter = new AndFilter(formFilter, andFilter);
		
		NodeFilter inputFilter = new TagNameFilter("input");
		NodeFilter attrFilter = new HasAttributeFilter("type", "hidden");
		NodeFilter hiddenAndFilter = new AndFilter(inputFilter, attrFilter);
		
		NodeFilter parentFilter = new HasParentFilter(formAttrFilter);
		
		NodeFilter[] andFilters = new NodeFilter[2];
		andFilters[0] = hiddenAndFilter;
		andFilters[1] = parentFilter;
		
		
		NodeFilter inputAttrAndFilter = new AndFilter(andFilters);
		return parser.extractAllNodesThatMatch(inputAttrAndFilter);				
	}
	

	public static NodeList getNodeListByInputType(Parser parser, String inputType) throws ParserException{
		NodeFilter inputFilter = new TagNameFilter("input");
		NodeFilter attrFilter = new HasAttributeFilter("type", inputType);
		
		NodeFilter andFilter = new AndFilter(inputFilter, attrFilter);
		return parser.extractAllNodesThatMatch(andFilter);
	}
	
	public static String parseHiddenInputValueByName(Parser parser, String inputName){
		NodeFilter inputFilter = new TagNameFilter("input");
		
		NodeFilter typeAttrFilter = new HasAttributeFilter("type", "hidden");
		NodeFilter nameAttrFilter = new HasAttributeFilter("name", inputName);
		NodeFilter[] filters = new NodeFilter[2];
		filters[0] = typeAttrFilter;
		filters[1] = nameAttrFilter;
		NodeFilter andFilter = new AndFilter(filters);
		
		NodeFilter inputFilters = new AndFilter(inputFilter, andFilter);
		
		try {
			NodeList nodes = parser.extractAllNodesThatMatch(inputFilters);
			if((nodes != null) && (nodes.size() == 1)){
				if(nodes.elementAt(0) instanceof InputTag){
					InputTag inputTag = (InputTag) nodes.elementAt(0);
					return inputTag.getAttribute("value");
				}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String parseInputValueByName(Parser parser, String inputType, String inputName){
		try{
			NodeFilter[] filterList = new NodeFilter[2];
			NodeFilter inputfilter = new TagNameFilter("input");
			NodeFilter attrFilter = new HasAttributeFilter("type", inputType);
			filterList[0] = inputfilter;
			filterList[1] = attrFilter;
			AndFilter andFilter = new AndFilter(filterList);
			
			
			NodeList nodes = parser.parse(andFilter);
			if(nodes != null){
				for(int i = 0; i < nodes.size(); i++){
					Node node = nodes.elementAt(i);
					if(node instanceof InputTag){
						InputTag inputTag = (InputTag) node;
						if(inputTag.getAttribute("name").equals(inputName)){
							return inputTag.getAttribute("value");
						}
					}
					
				}
				return null;
			}
			else{
				return null;
			}	
		}catch (ParserException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static String readFile(String filePath){
		StringBuffer sb = new StringBuffer();
		try{
			File file = new File(filePath);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
			BufferedReader br = new BufferedReader(isr);
			String oneLine = "";
			while((oneLine = br.readLine()) != null){
				sb.append(oneLine);
				sb.append("/r/n");
			}
			br.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
