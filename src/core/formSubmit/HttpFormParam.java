package core.formSubmit;

public class HttpFormParam {

	private String paramName;
	
	private String paramVal;
	
	public HttpFormParam(String paramName, String paramVal){
		this.paramName = paramName;
		this.paramVal = paramVal;
	}
	
	
	public String getParamName() {
		return paramName;
	}


	public void setParamName(String paramName) {
		this.paramName = paramName;
	}


	public String getParamVal() {
		return paramVal;
	}


	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
