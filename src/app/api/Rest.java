package app.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class Rest {

	private static String 				server;
	private static HttpParams 			httpParameters;
	private static DefaultHttpClient 	httpClient;

	private static final String URL = "http://www.jumpyjosh.com/ds/rest/api";
	private static final String LocalhostURL = "http://192.168.43.51:8080/DiaryS/rest/api";

	public static void setup() {
		Rest.server = LocalhostURL;
		httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				10000);
		HttpConnectionParams.setSoTimeout(httpParameters, 20000);
		httpClient = new DefaultHttpClient(httpParameters);
	}

	private static String getBase() {
		return server;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public static String get(String url) {
		String result = "";
		try {
			HttpGet getRequest = new HttpGet(getBase() + url);
			getRequest.setHeader("accept", "application/json");
			//getRequest.setHeader("accept","text/plain");
			HttpResponse response = httpClient.execute(getRequest);
			result = getResult(response).toString();
		} catch (Exception e) {
			Log.v("Donate","ASYNC ERROR" + e.getMessage());
		}
		Log.v("Donate","ASYNC ERROR" + result);

		return result;
	}
	
	
	public static String getAll(String name) {
		String result = "";
//		Log.v("namenamename",name);

		try {
			HttpGet getRequest = new HttpGet(getBase() + "/getallDiary/"+name);
			Log.v("namenamename",getBase() + "/getallDiary/"+name);

			getRequest.setHeader("accept", "application/json");
			//getRequest.setHeader("accept","text/plain");
			HttpResponse response = httpClient.execute(getRequest);
			result = getResult(response).toString();
		} catch (Exception e) {
			Log.v("Donate","ASYNC ERROR" + e.getMessage());
		}
		Log.v("Donate","ASYNC ERROR" + result);

		return result;
	}
	


	// /////////////////////////////////////////////////////////////////////////////////////////////
	public static String delete(String url) {
		String result = "";
		try {
			HttpDelete deleteRequest = new HttpDelete(getBase() + url);
			deleteRequest.setHeader("Content-type", "application/json");
			deleteRequest.setHeader("accept", "application/json");
			deleteRequest.setHeader("accept","text/plain");
			HttpResponse response = httpClient.execute(deleteRequest);

			result = getResult(response).toString();

		} catch (Exception e) {
			System.out.println("DELETE RESULT - " + result + "ERROR - "
					+ e.getMessage());
		}
		return result;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////
	public static String put(String url, String json) {
		String result = "";
		try {
			String strRequest = getBase() + url;
			HttpPut putRequest = new HttpPut(strRequest);
			putRequest.setHeader("Content-type", "application/json");
			putRequest.setHeader("accept", "application/json");
			putRequest.setHeader("accept","text/plain");
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");

			putRequest.setEntity(s);

			HttpResponse response = httpClient.execute(putRequest);
			result = getResult(response).toString();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	
	public static String login(String json) {

		String result = "";
		String url = "/login";

		try {
			String strRequest = getBase() + url;
			HttpPost postRequest = new HttpPost(strRequest);
			postRequest.setHeader("Content-type", "application/json");
			postRequest.setHeader("accept", "application/json");
			postRequest.setHeader("accept","text/plain");
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			postRequest.setEntity(s);
			HttpResponse response = httpClient.execute(postRequest);
			result = getResult(response).toString();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	   	Log.v("resultresultresultresult", result);

		return result;

	}
	
	public static String register(String json) {
	   	Log.v("444", json);

	   	String result = "";
		String url = "/register";
		try {
			String strRequest = getBase() + url;
		   	Log.v("resultresultresultresult", strRequest);

			HttpPost postRequest = new HttpPost(strRequest);
			postRequest.setHeader("Content-type", "application/json");
			postRequest.setHeader("accept", "application/json");
			postRequest.setHeader("accept","text/plain");
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			postRequest.setEntity(s);
			HttpResponse response = httpClient.execute(postRequest);
			result = getResult(response).toString();
		   	Log.v("resultresultresultresult", result);


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	   	Log.v("resultresultresultresult", result);
		return result;
	}
	
	public static String addDiary(String json) {
	   	Log.v("444", json);

	   	String result = "";
		String url = "/newdiary";
		try {
			String strRequest = getBase() + url;
		   	Log.v("resultresultresultresult", strRequest);

			HttpPost postRequest = new HttpPost(strRequest);
			postRequest.setHeader("Content-type", "application/json");
			postRequest.setHeader("accept", "application/json");
			postRequest.setHeader("accept","text/plain");
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			postRequest.setEntity(s);
			HttpResponse response = httpClient.execute(postRequest);
			result = getResult(response).toString();
		   	Log.v("resultresultresultresult", result);


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	   	Log.v("resultresultresultresult", result);
		return result;
	}
	

	// /////////////////////////////////////////////////////////////////////////////////////////////
	public static String post(String url, String json) {
		String result = "";
		try {
			String strRequest = getBase() + url;
			HttpPost postRequest = new HttpPost(strRequest);
			postRequest.setHeader("Content-type", "application/json");
			postRequest.setHeader("accept", "application/json");
			postRequest.setHeader("accept","text/plain");
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			postRequest.setEntity(s);
			HttpResponse response = httpClient.execute(postRequest);
			result = getResult(response).toString();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	private static StringBuilder getResult(HttpResponse response)
			throws IllegalStateException, IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())), 1024);
		String output;
		while ((output = br.readLine()) != null)
			result.append(output);

		return result;
	}

	public static void shutDown() {
//		httpClient.getConnectionManager().shutdown();
	}
}