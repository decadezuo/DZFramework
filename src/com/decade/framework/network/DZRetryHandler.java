package com.decade.framework.network;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;

import javax.net.ssl.SSLException;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import com.decade.framework.kit.DZLog;

/**
 * 
 * @description: 重试请求
 * @author: Decade
 * @date: 2013-5-23
 * 
 */

public class DZRetryHandler implements HttpRequestRetryHandler {
	private static HashSet<Class<?>> _exceptionWhitelist = new HashSet<Class<?>>();
	private static HashSet<Class<?>> _exceptionBlacklist = new HashSet<Class<?>>();

	static {
		_exceptionWhitelist.add(NoHttpResponseException.class);
		_exceptionWhitelist.add(UnknownHostException.class);
		_exceptionWhitelist.add(SocketException.class);
		_exceptionBlacklist.add(SSLException.class);
	}

	private final int maxRetries;

	public DZRetryHandler(int maxRetries) {
		this.maxRetries = maxRetries;
	}


	public boolean retryRequest(IOException exception, int executionCount,
			HttpContext context) {
		DZLog.e(getClass(), "retryRequest and retryCount is "
				+ executionCount);
		if (executionCount > maxRetries) {
			return false;
		}
		if (isInList(_exceptionBlacklist, exception)) {
			return false;
		}
		if (isInList(_exceptionWhitelist, exception)) {
			return true;
		}
		return false;
	}

	protected boolean isInList(HashSet<Class<?>> list, Throwable error) {
		Iterator<Class<?>> itr = list.iterator();
		while (itr.hasNext()) {
			if (itr.next().isInstance(error)) {
				return true;
			}
		}
		return false;
	}

	public static void removeExceptionFromWhitelist(Class<?> exceptClass) {
		_exceptionWhitelist.remove(exceptClass);
	}

	public static void addExceptionToWhitelist(Class<?> exceptClass) {
		_exceptionWhitelist.add(exceptClass);
	}

	public static void removeExceptionFromBlacklist(Class<?> exceptClass) {
		_exceptionBlacklist.remove(exceptClass);
	}

	public static void addExceptionToBlacklist(Class<?> exceptClass) {
		_exceptionBlacklist.add(exceptClass);
	}
	
}