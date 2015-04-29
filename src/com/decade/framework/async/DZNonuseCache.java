package com.decade.framework.async;

import java.io.IOException;

import com.decade.framework.network.DZNetStatusDefine;
import com.decade.framework.network.DZRequestParams;

/**
 * @description: 缓存策略，不使用缓存
 * @author: Decade
 * @date: 2013-5-31
 * 
 */
public class DZNonuseCache implements DZiCacheType {

	public DZiResponse processCache(DZBaseAsyncTask<?, ?> ayncTask, String url,
			DZRequestParams params, int httpType)
			throws IOException, Exception {
		DZiResponse response = ayncTask.doJsonParse(ayncTask.requestData(url, params, httpType));
		ayncTask.senddata(DZNetStatusDefine.S_COMPLETE);
		return response;
	}

	public DZiResponse processCache(DZBaseAsyncTask<?, ?> ayncTask, String url,
			String params, int httpType) throws IOException, Exception {
		DZiResponse response = ayncTask.doJsonParse(ayncTask.requestData(url, params, httpType));
		ayncTask.senddata(DZNetStatusDefine.S_COMPLETE);
		return response;
	}

}
