package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpParams {

	private List<HttpParam> formParams = new ArrayList<HttpParam>();
	private Map<String, Integer> formMap = new HashMap<String, Integer>();
	
	public void addOrUpdateHttpFormParam(String paramName, String paramVal){
		if(formMap.containsKey(paramName)){
			Integer index = formMap.get(paramName);
			HttpParam httpFormParam = formParams.get(index);
			httpFormParam.setParamVal(paramVal);
		}else{
			HttpParam httpFormParam = new HttpParam(paramName, paramVal);
			formParams.add(httpFormParam);
			formMap.put(paramName, formParams.size() - 1);
		}
	}
	
	public void addOrUpdateHttpFormParam(HttpParam httpParam){
		if(httpParam != null){
			addOrUpdateHttpFormParam(httpParam.getParamName(), httpParam.getParamVal());
		}
	}
	
	public int getParamCount(){
		return formParams.size();
	}
	
	public HttpParam getHttpParam(int index){
		return formParams.get(index);
	}
	
    public String getHttpParamVal(String httpParamName){
    	if(formMap.containsKey(httpParamName)){
    		Integer index = formMap.get(httpParamName);
			HttpParam httpFormParam = formParams.get(index);
			return httpFormParam.getParamVal();
    	}else{
    		throw new RuntimeException("没有找到相应的Form参数!");
    	}
    }
	
	public List<NameValuePair> transToNameValuePairs(){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for(int i = 0; i < formParams.size(); i++){
			HttpParam httpFormParam = formParams.get(i);
			NameValuePair nameValPair = new BasicNameValuePair(httpFormParam.getParamName(), httpFormParam.getParamVal());
			nameValuePairs.add(nameValPair);
		}
		return nameValuePairs;
	}
	
	public boolean checkIsValid(){
		for(HttpParam httpParam : formParams){
			if(httpParam.getParamVal().equals("")){
				throw new RuntimeException(httpParam.getParamName() + "的值没有取到!");
			}
		}
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
