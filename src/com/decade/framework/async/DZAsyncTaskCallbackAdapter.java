package com.decade.framework.async;

/**
 * @description: 网络请求回调接口适配器
 * @author: Decade
 * @date: 2013-9-16
 * 
 */
public class DZAsyncTaskCallbackAdapter implements DZiAsyncTaskCallback {
	public void onJsonPaserError(String message, int requestCode) {}
	public void onNetDisconnected(int requestCode) {}
	public void onServerResponseError(String message, int requestCode) {}
	public void openTopLoadView(int requestCode) {}
	public void closeTopLoadView(int requestCode) {}
	public void onComplete(DZiResponse response, int requestCode) {}

}
