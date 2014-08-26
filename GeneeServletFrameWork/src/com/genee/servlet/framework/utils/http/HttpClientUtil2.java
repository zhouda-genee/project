package com.genee.servlet.framework.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil2 {
	
	public static String[] post(String url, Map<String, String> params, Map<String, String> headers) {
		// post 执行体
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		HttpPost post = postForm(url, params, headers);

		String[] body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String[] get(String url, Map<String, String> headers) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String[] body = null;

		HttpGet get = new HttpGet(url);
		
		// 设置header
		if (headers != null){
			Set<String> keySet = headers.keySet();
			for (String key : keySet) {
				get.setHeader(key, headers.get(key));
			}
		}
		
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String[] invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String[] body = paseResponse(response);

		return body;
	}

	private static String[] paseResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String[] responseBody = new String[2];
		try {
			responseBody[0] = String.valueOf(response.getStatusLine().getStatusCode());
			responseBody[1] = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseBody;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings("deprecation")
	private static HttpPost postForm(String url, Map<String, String> params, Map<String, String> headers) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		// 设置参数
		if (params != null){
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
			}
		}
		
		// 设置header
		if (headers != null){
			Set<String> keySet = headers.keySet();
			for (String key : keySet) {
				httpost.setHeader(key, headers.get(key));
			}
		}

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "周达");
		params.put("password", "1234567");

//		String xml = HttpClientUtil.post("www.baidu.com", params);

	}

}
