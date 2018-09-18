package com.tanjin.framework.base.wechat.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.tanjin.framework.base.common.utils.FileUtil;


public class HttpReqestUtil {

	private String defaultContentEncoding;

	public HttpReqestUtil() {
		this.defaultContentEncoding = Charset.defaultCharset().name();
	}

	public HttpResponseData sendGet(String urlString) throws IOException {
		return send(urlString, "GET", null, null);
	}

	public HttpResponseData sendGet(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "GET", params, null);
	}

	public HttpResponseData sendGet(String urlString, Map<String, String> params, Map<String, String> propertys)
			throws IOException {
		return send(urlString, "GET", params, propertys);
	}

	public HttpResponseData sendPost(String urlString) throws IOException {
		return send(urlString, "POST", null, null);
	}

	public HttpResponseData sendPost(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "POST", params, null);
	}

	public HttpResponseData sendPost(String urlString, String params) throws IOException {
		HttpURLConnection urlConnection = null;
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();

		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);

		OutputStream output = urlConnection.getOutputStream();
		output.write(params.getBytes("UTF-8"));
		output.flush();
		output.close();
		return makeContent(urlString, urlConnection);
	}

	public HttpResponseData sendPost(String urlString, Map<String, String> params, Map<String, String> propertys)
			throws IOException {
		return send(urlString, "POST", params, propertys);
	}

	private HttpResponseData send(String urlString, String method, Map<String, String> parameters,
			Map<String, String> propertys) throws IOException {
		HttpURLConnection urlConnection = null;
		Iterator localIterator;
		String key;
		if ((method.equalsIgnoreCase("GET")) && (parameters != null)) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (localIterator = parameters.keySet().iterator(); localIterator.hasNext();) {
				key = (String) localIterator.next();
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append((String) parameters.get(key));
				i++;
			}
			urlString = urlString + param;
		}
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();

		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);

		if (propertys != null) {
			for (String key1 : propertys.keySet()) {
				urlConnection.addRequestProperty(key1, (String) propertys.get(key1));
			}
		}
		if ((method.equalsIgnoreCase("POST")) && (parameters != null)) {
			StringBuffer param = new StringBuffer();
			for (String key2 : parameters.keySet()) {
				param.append("&");
				param.append(key2).append("=").append((String) parameters.get(key2));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}

		return makeContent(urlString, urlConnection);
	}

	private HttpResponseData makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
		HttpResponseData httpResponser = new HttpResponseData();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			httpResponser.contentCollection = new Vector();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

			String ecod = urlConnection.getContentEncoding();
			if (ecod == null) {
				ecod = this.defaultContentEncoding;
			}
			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();

			httpResponser.content = new String(temp.toString().getBytes(), ecod);
			httpResponser.contentEncoding = ecod;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}

	public String getDefaultContentEncoding() {
		return this.defaultContentEncoding;
	}

	public void setDefaultContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}
	
	public HttpResponseData getImage(String urlString) throws Exception {
		HttpURLConnection urlConnection = null;
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestProperty("Accept-Charset", "utf-8");
		urlConnection.setRequestProperty("Accept", "application/x-www-form-urlencoded");
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		
		HttpResponseData httpResponser = new HttpResponseData();
		try {
			InputStream in = urlConnection.getInputStream();
			//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
			int count = in.available();
			byte[] fileByte =FileUtil.InputStreamToBytes(in);;
	        in.close(); 
			httpResponser.contentCollection = new Vector();
			String ecod = urlConnection.getContentEncoding();
			if (ecod == null) {
				ecod = this.defaultContentEncoding;
			}
			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();
			httpResponser.contentByte = fileByte;
			//httpResponser.content = new String(temp.toString().getBytes(), ecod);
			httpResponser.contentEncoding = ecod;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();
			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}
	
	public static void main(String[] args){
		String savePath="D:\\test\\";
    	String fileName="testHead1.jpg";
    	 // 构造URL  
    	// 打开连接  
    	InputStream is = null;
    	HttpReqestUtil httpRequester = new HttpReqestUtil();
		try {
			HttpResponseData hr = httpRequester.getImage("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFg8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyYTFndmtfT1RiRl8xMDAwMDAwNzkAAgRei_RYAwQAAAAA");
			is = new ByteArrayInputStream(hr.getContentByte());
			FileUtil.InputStreamTOFile(is, savePath+fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(is!=null){
					is.close();
				}
			}catch(Exception e){
			}
		}
	}
}
