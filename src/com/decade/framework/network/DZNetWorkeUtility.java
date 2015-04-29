package com.decade.framework.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.decade.framework.kit.DZLog;

/**
 * @description: HTTP请求
 * @author: Decade
 * @date: 2013-5-17
 */
public class DZNetWorkeUtility {
	private static final int TIMEOUT_CONNECTION = 1000 * 15;
	private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
	private static final String VERSION = "0.1.0";
	private static DZRetryHandler _retryHandler = new DZRetryHandler(3);

	public static String requestHttpPost(String url, String params)
			throws IOException {
		DZLog.d(DZNetWorkeUtility.class, "Post: " + url + params);
		return requestHttpPost(url, new StringEntity(params, HTTP.UTF_8));
	}

	public static String requestHttpGet(String url, String params)
			throws IOException {
		DZLog.d(DZNetWorkeUtility.class, "Get: " + url + params);
		return requestHttpGet(new HttpGet(params));
	}

	public static String requestHttpPost(String url, DZRequestParams params)
			throws IOException {
		DZLog.d(DZNetWorkeUtility.class,
				"Post: " + getUrlWithQueryString(url, params));
		return requestHttpPost(url, paramsToEntity(params));
	}

	private static String requestHttpPost(String url, HttpEntity entity)
			throws IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		return sendRequest(httpPost);
	}

	public static String requestHttpGet(String url, DZRequestParams params)
			throws IOException {
		DZLog.d(DZNetWorkeUtility.class,
				"Get: " + getUrlWithQueryString(url, params));
		return requestHttpGet(new HttpGet(getUrlWithQueryString(url, params)));
	}

	private static String requestHttpGet(HttpGet httpGet) throws IOException {
		return sendRequest(httpGet);
	}

	private static String sendRequest(final HttpRequestBase httpRequestBase)
			throws IOException {
		DefaultHttpClient httpClient = getHttpClient();
		try {
			String result = makeRequestWithRetries(httpClient, httpRequestBase);
			if (result != null && result.startsWith("\ufeff")) {
				result = result.substring(1);
			}
			DZLog.d(DZNetWorkeUtility.class, "Result = " + result);
			return result;
		} catch (IOException e) {
			throw e;
		} finally {
			abortConnection(httpRequestBase, httpClient);
		}
	}

	private static String makeRequestWithRetries(DefaultHttpClient httpClient,
			HttpRequestBase httpRequestBase) throws IOException {
		boolean retry = true;
		int executionCount = 1;
		while (retry) {
			try {
				String result = httpClient.execute(httpRequestBase,
						responseHandler);
				return result;
			} catch (IOException e) {
				retry = _retryHandler.retryRequest(e, ++executionCount,
						getHttpContext());
				if (!retry) {
					throw e;
				}
			}
		}
		return null;
	}

	public static DefaultHttpClient getHttpClient() {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_CONNECTION);

		HttpConnectionParams.setTcpNoDelay(httpParameters, true);
		HttpConnectionParams.setSocketBufferSize(httpParameters,
				DEFAULT_SOCKET_BUFFER_SIZE);

		HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUserAgent(httpParameters,
				String.format("decade-framework-httprequest)", VERSION));
		ClientConnectionManager ccm = getClientConnectionManager(httpParameters);
		DefaultHttpClient httpClient;
		if (ccm != null) {
			httpClient = new DefaultHttpClient(ccm, httpParameters);
		} else {
			httpClient = new DefaultHttpClient(httpParameters);
		}
		httpClient.setHttpRequestRetryHandler(_retryHandler);
		return httpClient;
	}

	private static ClientConnectionManager getClientConnectionManager(
			HttpParams params) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new DZSSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			return new ThreadSafeClientConnManager(params, registry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HttpContext getHttpContext() {
		return new SyncBasicHttpContext(new BasicHttpContext());
	}

	/**
	 * 释放HttpClient连接
	 * 
	 * @param hrb请求对象
	 * @param httpclientclient对象
	 */
	private static void abortConnection(final HttpRequestBase httpRequestBase,
			final HttpClient httpclient) {
		if (httpRequestBase != null) {
			httpRequestBase.abort();
		}
		if (httpclient != null) {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	 * 
	 * @param actionUrl
	 *            访问的服务器URL
	 * @param params
	 *            普通参数
	 * @param files
	 *            文件参数
	 * @throws IOException
	 */
	public static String upLoadPhoto(String actionUrl,
			Map<String, String> params, Map<String, File> files)
			throws IOException {
		String result = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setConnectTimeout(30 * 1000);
		conn.setReadTimeout(5 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 发送文件数据
		if (files != null) {
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				// name是post中传参的键 filename是文件的名称
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());
				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}

			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			// 得到响应码
			int res = conn.getResponseCode();
			if (res == 200) {
				Log.e("upload photo", "request success");

				InputStream input = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(input, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				result = br.readLine();
				DZLog.e(DZNetWorkeUtility.class, "result : " + result);
				input.close();
			}
			outStream.close();
			conn.disconnect();

		}
		return result;
	}

	/**
	 * 使用ResponseHandler接口处理响应 HttpClient使用ResponseHandler会自动管理连接的释放,
	 * 解决了对连接的释放管理
	 */
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public synchronized String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			DZLog.e(DZNetWorkeUtility.class, "StatusCode = "
					+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String charset = EntityUtils.getContentCharSet(entity) == null ? HTTP.UTF_8
							: EntityUtils.getContentCharSet(entity);
					return new String(EntityUtils.toByteArray(entity), charset);
				}
			} else {
				throw new IOException("("
						+ response.getStatusLine().getStatusCode() + ")");
			}
			return null;
		}
	};

	public static String getUrlWithQueryString(String url,
			DZRequestParams params) {
		if (params != null) {
			String paramString = params.getParamString();
			if (url.indexOf("?") == -1) {
				url += "?" + paramString;
			} else {
				url += paramString;
			}
		}

		return url;
	}

	private static HttpEntity paramsToEntity(DZRequestParams params) {
		HttpEntity entity = null;

		if (params != null) {
			entity = params.getEntity();
		}

		return entity;
	}

}
