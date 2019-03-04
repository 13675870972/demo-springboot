package com.example.demospringboot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.CookieGenerator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpUtil2 {

	private static int connectTimeOut = 5000;
	private static int readTimeOut = 30000;

	/**
	 * http get方式发送消息
	 * 
	 * @param reqUrl
	 *            请求url地址
	 * @param parameters
	 * @param recvEncoding
	 * @return
	 */
	public static String doGet(String reqUrl, Map<String, String> parameters,
			String recvEncoding) {
		HttpURLConnection url_con;
		String responseContent;
		url_con = null;
		responseContent = null;
		BufferedReader rd = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<Entry<String, String>> iter = parameters.entrySet()
					.iterator(); iter.hasNext(); params.append("&")) {
				Entry<String, String> element = (Entry<String, String>) iter
						.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(),
						"utf-8"));
			}

			if (params.length() > 0)
				params = params.deleteCharAt(params.length() - 1);
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			url_con.setConnectTimeout(connectTimeOut);
			url_con.setReadTimeout(readTimeOut);
			url_con.setDoOutput(true);
			byte b[] = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			rd = new BufferedReader(new InputStreamReader(url_con.getInputStream(),
					"utf-8"));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			for (; tempLine != null; tempLine = rd.readLine()) {
				temp.append(tempLine);
				temp.append(crlf);
			}
			responseContent = temp.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (url_con != null){
				url_con.disconnect();
			}
			try {
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
		}
		return responseContent;
	}

	/**
	 * http get方式发送消息
	 *
	 * @param reqUrl
	 * @param recvEncoding
	 * @return
	 */
	public static String doGet(String reqUrl, String recvEncoding) {
		HttpURLConnection url_con;
		String responseContent;
		url_con = null;
		responseContent = null;
		BufferedReader rd = null;
		try {
			StringBuffer params = new StringBuffer();
			String queryUrl = reqUrl;
			int paramIndex = reqUrl.indexOf("?");
			if (paramIndex > 0) {
				queryUrl = reqUrl.substring(0, paramIndex);
				String parameters = reqUrl.substring(paramIndex + 1, reqUrl
						.length());
				String paramArray[] = parameters.split("&");
				for (int i = 0; i < paramArray.length; i++) {
					String string = paramArray[i];
					int index = string.indexOf("=");
					if (index > 0) {
						String parameter = string.substring(0, index);
						String value = string.substring(index + 1, string
								.length());
						params.append(parameter);
						params.append("=");
						params.append(URLEncoder.encode(value, recvEncoding));
						params.append("&");
					}
				}

				params = params.deleteCharAt(params.length() - 1);
			}
			URL url = new URL(queryUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			url_con.setConnectTimeout(connectTimeOut);
			url_con.setReadTimeout(readTimeOut);
			url_con.setDoOutput(true);
			byte b[] = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			rd = new BufferedReader(new InputStreamReader(url_con.getInputStream(),
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			for (; tempLine != null; tempLine = rd.readLine()) {
				temp.append(tempLine);
				temp.append(crlf);
			}

			responseContent = temp.toString();
		} catch (IOException e) {
			log.error("发送失败", e);
		} finally {
			if (url_con != null){
				url_con.disconnect();
			}
			try {
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
		}
		return responseContent;
	}

	/**
	 * http post方式发送消息
	 *
	 * @param reqUrl
	 * @param parameters
	 * @param recvEncoding
	 * @return
	 */
	public static String doPost(String reqUrl, Map<String, String> parameters,
			String recvEncoding) {
		HttpURLConnection url_con;
		String responseContent;
		url_con = null;
		responseContent = null;
		BufferedReader rd = null;
		try {
			if(reqUrl.indexOf("https") != -1){
				trustAllHttpsCertificates();
				((HttpsURLConnection) url_con).setDefaultHostnameVerifier(hv);
			}
			StringBuffer params = new StringBuffer();
			for (Iterator<Entry<String, String>> iter = parameters.entrySet()
					.iterator(); iter.hasNext(); params.append("&")) {
				Entry<String, String> element = (Entry<String, String>) iter
						.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(),
						recvEncoding));
			}

			if (params.length() > 0)
				params = params.deleteCharAt(params.length() - 1);
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setConnectTimeout(connectTimeOut);
			url_con.setReadTimeout(readTimeOut);
			url_con.setDoOutput(true);
			byte b[] = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			rd = new BufferedReader(new InputStreamReader(url_con.getInputStream(),
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			for (; tempLine != null; tempLine = rd.readLine()) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
			}

			responseContent = tempStr.toString();
		} catch (Exception e) {
			log.error("发送失败！", e);
		} finally {
			if (url_con != null){
				url_con.disconnect();
			}
			try {
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
		}
		return responseContent;
	}

	public static String doPost(String reqUrl, String xmlParameters,
			String recvEncoding) {
		HttpURLConnection url_con;
		String responseContent;
		url_con = null;
		responseContent = null;
		BufferedReader rd = null;
		try {
			if(reqUrl.indexOf("https") != -1){
				trustAllHttpsCertificates();
				((HttpsURLConnection) url_con).setDefaultHostnameVerifier(hv);
			}
			StringBuffer params = new StringBuffer(xmlParameters);
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setConnectTimeout(connectTimeOut);
			url_con.setReadTimeout(readTimeOut);
			url_con.setDoOutput(true);
			byte b[] = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			rd = new BufferedReader(new InputStreamReader(url_con.getInputStream(),
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			for (; tempLine != null; tempLine = rd.readLine()) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
			}

			responseContent = tempStr.toString();
		} catch (Exception e) {
			log.error("发送失败！", e);
		} finally {
			if (url_con != null){
				url_con.disconnect();
			}
			try {
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
		}
		return responseContent;
	}

	public static void put(HttpSession session, String key, Object value) {
		session.setAttribute(key, value);
	}

	public static Object get(HttpSession session, String key) {
		return session.getAttribute(key);
	}

	public static void remove(HttpSession session, String key) {
		session.removeAttribute(key);
	}

	public static void clear(HttpSession session) {
		session.invalidate();
	}

	// Cookie op.
	public static String get(HttpServletRequest req, String key) {
		Cookie[] cookies = req.getCookies();
		if (null == cookies)
			return null;
		for (Cookie c : cookies)
			if (c.getName().equals(key))
				return c.getValue();
		return null;
	}

	public static void put(HttpServletResponse resp, String[] keys, String[] values) {
		CookieGenerator g = new CookieGenerator();
		g.setCookiePath("/");
		g.setCookieMaxAge(86400);// 一天时间
		for (int i = 0; i < keys.length; i++) {
			g.setCookieName(keys[i]);
			g.addCookie(resp, values[i]);
		}
	}

	public static void put(HttpServletResponse resp, String[] domains, String[] keys, String[] values) {
		CookieGenerator g = new CookieGenerator();
		g.setCookiePath("/");
		g.setCookieMaxAge(86400);// 一天时间
		for (String domain : domains) {
			g.setCookieDomain(domain);
			for (int i = 0; i < keys.length; i++) {
				g.setCookieName(keys[i]);
				g.addCookie(resp, values[i]);
			}
		}
	}

	public static void remove(HttpServletResponse resp, String[] keys) {
		disableCookie(resp);
		/*
		CookieGenerator g = new CookieGenerator();
		g.setCookiePath("/");

		g.setCookieMaxAge(86400);// 一天时间
		for (int i = 0; i < keys.length; i++) {
			g.setCookieName(keys[i]);
			g.removeCookie(resp);
		}
		*/
	}

	public static void remove(HttpServletResponse resp, String[] domains, String[] keys) {
		disableCookie(resp);
		/*
		CookieGenerator g = new CookieGenerator();
		g.setCookiePath("/");

		for (String domain : domains) {
			g.setCookieDomain(domain);
			for (String key : keys) {
				g.setCookieName(key);
				g.removeCookie(resp);
			}
		}
		*/
	}

	public static void disableCookie(HttpServletResponse resp) {
		CookieGenerator g = new CookieGenerator();
		g.setCookiePath("/");

		//HttpContext.SessionKey.LOGINING_ACTIVE.toString()
		g.setCookieName("active");
		g.addCookie(resp, "1");
	}



	//以下两段忽略证书信任问题
	static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                               + session.getPeerHost());
            return true;
        }
    };

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());  
    }  
  
    static class miTM implements javax.net.ssl.TrustManager,  
            javax.net.ssl.X509TrustManager {  
        @Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;  
        }  
  
        public boolean isServerTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        public boolean isClientTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        @Override
		public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }  
  
        @Override
		public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }  
    }  
	
}
