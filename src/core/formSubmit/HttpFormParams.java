package core.formSubmit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpFormParams {

	private List<HttpFormParam> formParams = new ArrayList<HttpFormParam>();
	private Map<String, Integer> formMap = new HashMap<String, Integer>();
	
	public void addOrUpdateHttpFormParam(String paramName, String paramVal){
		if(formMap.containsKey(paramName)){
			Integer index = formMap.get(paramName);
			HttpFormParam httpFormParam = formParams.get(index);
			httpFormParam.setParamVal(paramVal);
		}else{
			HttpFormParam httpFormParam = new HttpFormParam(paramName, paramVal);
			formParams.add(httpFormParam);
			formMap.put(paramName, formParams.size() - 1);
		}
	}
	
	public List<NameValuePair> transToNameValuePairs(){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for(int i = 0; i < formParams.size(); i++){
			HttpFormParam httpFormParam = formParams.get(i);
			NameValuePair nameValPair = new BasicNameValuePair(httpFormParam.getParamName(), httpFormParam.getParamVal());
			nameValuePairs.add(nameValPair);
		}
		return nameValuePairs;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
