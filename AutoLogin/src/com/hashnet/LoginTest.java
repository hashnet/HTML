package com.hashnet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoginTest {
	private static final int TIME_OUT = 5000;
	private static final String LOGIN_URL = "https://holdmycourt.com/reserve2/reserve_signin.php";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0";
	private static final Map<String, String> HEADERS;
	private static final Map<String, String> POST_DATA;
	static {
		HEADERS = new HashMap<String, String>();
		HEADERS.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		HEADERS.put("Referer", "https://holdmycourt.com/reserve2/reserve_signin.php?dir=rahrc");
		HEADERS.put("Connection", "keep-alive");
		
		POST_DATA = new HashMap<String, String>();
		POST_DATA.put("dir", "rahrc");
		POST_DATA.put("date", "");
		POST_DATA.put("hour", "");
		POST_DATA.put("court", "");
		POST_DATA.put("email", "maidul.hasan@gmail.com");
		POST_DATA.put("password", "deyuxu");
		POST_DATA.put("signin", "Sign In");
	}
	
	public static void main(String[] args) throws IOException {
		
		Connection conn = Jsoup.connect(LOGIN_URL)
				.userAgent(USER_AGENT)
				.headers(HEADERS)
				.method(Connection.Method.POST)
				.timeout(TIME_OUT)
				.followRedirects(true)
				.data(POST_DATA);
		
		Connection.Response response = conn.execute();
	
		System.out.println(response.parse());
		
		System.out.println(response.headers());
		
		System.out.println(response.statusCode());
		
		System.out.println(response.statusMessage());
		
		System.out.println(response.hasCookie("reserve_rahrc"));
		
		System.out.println(response.cookies());

	
	}
}
