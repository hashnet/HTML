package com.hashnet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoginLogoutTest {
	private static boolean isProxyEnabled = true;
	private static final String PROXY_IP = "10.1.100.1";
	private static final int PROXY_PORT = 8080;
	private static final int TIME_OUT = 5000;
	private static final String LOGIN_URL = "https://holdmycourt.com/reserve2/reserve_signin.php";
	private static final String LOGOUT_URL = "https://holdmycourt.com/reserve2/reserve_signin.php?logout=1&dir=rahrc";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0";
	private static final Map<String, String> HEADERS;
	
	static {
		HEADERS = new HashMap<String, String>();
		HEADERS.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		//HEADERS.put("Referer", "https://holdmycourt.com/reserve2/reserve_signin.php?dir=rahrc");
		HEADERS.put("Connection", "keep-alive");
	}
	
	private static Document data;
	private static Map<String, String> cookies;
	
	public static void main(String[] args) throws IOException {
		if(login(LOGIN_URL)) {
			System.out.println("Login Successful.");
			System.out.println("Data:");
			System.out.println(data);
			
			System.out.print("\n\n\n");
			if(logout(LOGOUT_URL)) {
				System.out.println("Logout Successful.");
				System.out.println("Data:");
				System.out.println(data);
			} else {
				System.out.println("Logout Unsuccessful");
			}
		} else {
			System.out.println("Login Unsuccessful");
		}

	
	}
	
	private static boolean login(String url) throws IOException {
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("dir", "rahrc");
		postData.put("date", "");
		postData.put("hour", "");
		postData.put("court", "");
		postData.put("email", "maidul.hasan@gmail.com");
		postData.put("password", "deyuxu");
		postData.put("signin", "Sign In");
		
		Connection conn = Jsoup.connect(url)
				.userAgent(USER_AGENT)
				.headers(HEADERS)
				.method(Connection.Method.POST)
				.timeout(TIME_OUT)
				.followRedirects(true)
				.data(postData);
		if(isProxyEnabled) {
			conn.proxy(PROXY_IP, PROXY_PORT);
		}
		Connection.Response response = conn.execute();
		
		if(response.statusCode() == 200) {
			data = response.parse();
		} else {
			return false;
		}
	
		if(response.hasCookie("reserve_rahrc")) {
			cookies = response.cookies();
		} else {
			return false;
		}
		
		return true;
	}
	
	private static boolean logout(String url) throws IOException {
		if(!cookies.containsKey("reserve_rahrc")) {
			return false;
		}
		
		Connection conn = Jsoup.connect(url)
				.userAgent(USER_AGENT)
				.headers(HEADERS)
				.method(Connection.Method.GET)
				.timeout(TIME_OUT)
				.followRedirects(true)
				.cookies(cookies);
		if(isProxyEnabled) {
			conn.proxy(PROXY_IP, PROXY_PORT);
		}
		Connection.Response response = conn.execute();
		
		if(response.statusCode() == 200) {
			data = response.parse();
		} else {
			return false;
		}
		
		return true;
	}
}
