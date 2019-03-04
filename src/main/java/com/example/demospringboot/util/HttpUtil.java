package com.example.demospringboot.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author haoxz11
 */
public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private final static int SOCK_TIMEOUT = 5000;
	private final static int CONN_TIMEOUT = 5000;
	private final static String DEFAULT_CHARSET = "UTF-8";
	private final static RequestConfig config = RequestConfig.custom().setSocketTimeout(SOCK_TIMEOUT)
			.setConnectTimeout(CONN_TIMEOUT).build();

	public static Response post(CloseableHttpClient client, String url, HttpEntity entity, String charset) throws Exception {
		HttpPost method = new HttpPost(url);
		method.setConfig(config);
		try {
			if (entity != null) {
				method.setEntity(entity);
			}
			if (charset == null) {
				charset = DEFAULT_CHARSET;
			}
			HttpResponse response = client.execute(method);
			Response result = wrapResponse(response);
			return result;
		} catch (Exception e) {
			logger.error("#http client post error with reason : {}", e.getMessage());
			throw e;
		} finally {
			method.releaseConnection();
			try {
				client.close();
			} catch (IOException e) {
				logger.error("#http client close post connection error with reason : {}#", e.getMessage());
			}
		}
	}

	/**
	 * post a 'form like' data to http server with given url
	 *
	 * @param url       http url
	 * @param paramsMap key:value form data
	 * @return Response{code,body}
	 * @throws Exception wrap any exception to HttpException :IOException
	 *                   SocketException
	 */
	public static Response post(String url, Map<String, String> paramsMap) throws Exception {
		return post(url, paramsMap, DEFAULT_CHARSET);
	}

	/**
	 * post a 'form like' data to http server with given url
	 *
	 * @param url       http url
	 * @param paramsMap key:value form data
	 * @return Response{code,body}
	 * @throws Exception wrap any exception to HttpException :IOException
	 *                   SocketException
	 */
	public static Response post(String url, Map<String, String> paramsMap, String charset) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>(paramsMap.size());
		Set<String> keySet = paramsMap.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
		}
		return post(HttpClients.createDefault(), url, new UrlEncodedFormEntity(nvps, charset), charset);
	}

	/**
	 * Post json entity to http server with given url
	 *
	 * @param url  http url
	 * @param json json format data
	 * @return Response{code,body}
	 * @throws Exception wrap any exception to HttpException eg:IOException
	 *                   SocketException
	 */
	public static Response post(String url, String json) throws Exception {
		return post(url, json, DEFAULT_CHARSET);
	}

	/**
	 * Post json entity to http server with given url
	 *
	 * @param url  http url
	 * @param json json format data
	 * @return Response{code,body}
	 * @throws Exception wrap any exception to HttpException eg:IOException
	 *                   SocketException
	 */
	public static Response post(String url, String json, String charset) throws Exception {
		StringEntity entity = new StringEntity(json, charset);
		entity.setContentType("application/json");
		return post(HttpClients.createDefault(), url, entity, charset);
	}

	/**
	 * do http get without parameters
	 *
	 * @param url http url
	 * @return Response{code,body}
	 * @throws Exception wrap any exception to HttpException eg:IOException
	 *                   SocketException
	 */
	public static Response get(String url) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		try {
			HttpResponse response = client.execute(method);
			return wrapResponse(response);
		} catch (Exception e) {
			logger.error("http get request error with reason : {}", e.getMessage());
			throw e;
		} finally {
			method.releaseConnection();
			try {
				client.close();
			} catch (IOException e) {
				logger.error("close http  connection  with reason : {}", e.getMessage());
			}
		}
	}

	/**
	 * append current timestamp to url with give name
	 *
	 * @param url           http url
	 * @param timestampName parameter name
	 * @return string
	 */
	public static String appendTimestamp(String url, String timestampName) {
		if (url.endsWith("/")) {
			return url;
		}
		if (url.contains("?")) {
			url += "&" + timestampName + "=" + System.currentTimeMillis();
		} else {
			url += "?" + timestampName + "=" + System.currentTimeMillis();
		}
		return url;
	}

	private static Response wrapResponse(HttpResponse response) throws IOException {
		int code = response.getStatusLine().getStatusCode();
		String body = EntityUtils.toString(response.getEntity());
		return new Response(code, body);
	}

	public static class Response {
		public Response(int code, String content) {
			this.code = code;
			this.content = content;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		int code;
		String content;
	}
}
