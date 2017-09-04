package examples.http.header;

public class HTTPHeader {

	public static final String SERVER_NAME = "Server: Midnight Server";
	
	public static final String RESPONSE_OK = "HTTP/1.1 200 OK";
	public static final String CONTENT_LENGTH = "Content-Length: ";
	public static final String CONTENT_TYPE = "Content-Type: text/html";
	
	private static final String NEW_LINE = "\r\n";
		
	public static String fakeResponse(String message) {	
		String response = "HTTP/1.1 200 OK"+NEW_LINE+
		"Host: localhost:8080"+NEW_LINE+
	     "Date: Mon, 26 Sep 2015 01:28:53 GMT"+NEW_LINE+"Server: Apache"+NEW_LINE+
	     "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT"+NEW_LINE+
	     "Vary: Accept-Encoding"+NEW_LINE+"Content-Type: text/html"+NEW_LINE+
	     "Accept-Ranges: bytes"+NEW_LINE+"Content-Length: "+message.getBytes().length+NEW_LINE
	     //"Accept-Ranges: bytes"+NEW_LINE+"Content-Length: 1"+NEW_LINE
	     +NEW_LINE+
	     message+NEW_LINE;
		
		return response;
	}
	
}
