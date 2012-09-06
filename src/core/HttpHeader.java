package core;

public class HttpHeader {
	
	private String headerName;
	
	private String headerVal;
	
	
	public HttpHeader(String headerName, String headerVal){
		this.headerName = headerName;
		this.headerVal = headerVal;
	}

	public String getHeaderName() {
		return headerName;
	}



	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}



	public String getHeaderVal() {
		return headerVal;
	}



	public void setHeaderVal(String headerVal) {
		this.headerVal = headerVal;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
