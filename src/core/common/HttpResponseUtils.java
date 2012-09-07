package core.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class HttpResponseUtils {

	public static void printResponse(HttpResponse response){
		try{
			HttpEntity responseEntry = response.getEntity();
			System.out.println(EntityUtils.toString(responseEntry));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
