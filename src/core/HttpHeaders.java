package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
	
	private List<HttpHeader> headers = new ArrayList<HttpHeader>();
	
	private Map<String, Integer> map = new HashMap<String, Integer>();
	
	public void addOrUpdateHeader(String headerName, String headerVal){
		if(map.containsKey(headerName)){
			Integer index = map.get(headerName);
			HttpHeader httpHeader = headers.get(index);
			httpHeader.setHeaderVal(headerVal);
		}else{
			HttpHeader httpHeader = new HttpHeader(headerName, headerVal);
			headers.add(httpHeader);
			map.put(headerName, headers.size() - 1);
		}
	}
	
	public int getHeaderCount(){
		return headers.size();
	}
	
	public HttpHeader getHeader(int index){
		return headers.get(index);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
