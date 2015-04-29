package com.decade.framework.network;

/**
 * 
 * @description: 网络请求状态码
 * @author: Decade
 * @date: 2013-5-17
 */
public class DZNetStatusDefine {
	public static final int S_DEFAULT = -1;
	public static final int S_NETWORK_ERROR = -404;
	public static final int S_JSON_PARSE_ERROR = -405;
	public static final int S_COMPLETE = 200;
	public static final int S_CACHE_NO_EXPIRED = 5;
	public static final int S_NET_DISCONNECTED = 6;
	public static final int SHOW_CACHE = 1;
	public static final int SHOW_LOADING = 2;
	public static final int CLOSE_LOADING = 3;

}
