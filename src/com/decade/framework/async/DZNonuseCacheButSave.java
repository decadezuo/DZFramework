package com.decade.framework.async;

import java.io.IOException;

import android.text.TextUtils;

import com.decade.framework.network.DZNetStatusDefine;
import com.decade.framework.network.DZNetWorkeUtility;
import com.decade.framework.network.DZRequestParams;

/**
 * @description: 缓存策略，不使用缓存但是存储
 * @author: Decade
 * @date: 2013-5-31
 * 
 */
public class DZNonuseCacheButSave implements DZiCacheType {

	public String _cacheName;

	public DZNonuseCacheButSave(String cacheName) {
		_cacheName = cacheName;
	}

	public DZiResponse processCache(DZBaseAsyncTask<?, ?> ayncTask, String url,
			DZRequestParams params, int httpType) throws IOException,
			Exception {
		String responseResult = ayncTask.requestData(url, params, httpType);
		if (TextUtils.isEmpty(_cacheName)) {
			_cacheName = DZNetWorkeUtility.getUrlWithQueryString(url, params);
		}
		DZiResponse result = ayncTask.doJsonParse(responseResult);
		ayncTask.senddata(DZNetStatusDefine.S_COMPLETE);
		ayncTask.saveCache(responseResult, _cacheName);
		return result;
	}


	public DZiResponse processCache(DZBaseAsyncTask<?, ?> ayncTask, String url,
			String params, int httpType) throws IOException, Exception {
		String responseResult = ayncTask.requestData(url, params, httpType);
		if (TextUtils.isEmpty(_cacheName)) {
			_cacheName = url + params;
		}
		DZiResponse result = ayncTask.doJsonParse(responseResult);
		ayncTask.senddata(DZNetStatusDefine.S_COMPLETE);
		ayncTask.saveCache(responseResult, _cacheName);
		return result;
	}

}
