package com.decade.framework.async;

import java.io.IOException;

import org.json.JSONException;

import android.text.TextUtils;

import com.decade.framework.cache.DZJsonFileCache;
import com.decade.framework.kit.DZEncryptTool;
import com.decade.framework.kit.DZLog;
import com.decade.framework.network.DZNetStatusDefine;
import com.decade.framework.network.DZNetWorkeUtility;
import com.decade.framework.network.DZRequestParams;

/**
 * @description: 缓存策略，不适用缓存
 * @author: Decade
 * @date: 2013-5-31
 * 
 */
public class DZUseCache implements DZiCacheType {

	private long _cacheIndate;
	private String _cacheName;

	public DZUseCache(String cacheName, long cacheIndate) {
		_cacheName = cacheName;
		_cacheIndate = cacheIndate;
	}
	
	public DZiResponse processCache(final DZBaseAsyncTask<?, ?> ayncTask,
			String url, DZRequestParams params, int httpType)
			throws IOException, Exception {
		final String cacheResult = getDataFromDiskCache(ayncTask, url, params);
		if (cacheResult == null) { // 如果没有缓存，下载数据，存储。
			DZLog.d(getClass(), "Without cache");
			return requestDataAndSave(ayncTask, url, params, httpType);
		} else { // 如果有缓存先显示缓存
			DZiResponse cacheRs = ayncTask.doJsonParse(cacheResult);
			ayncTask.senddata(DZNetStatusDefine.SHOW_CACHE, cacheRs);
			if (DZJsonFileCache.getJsonFileCache().isExpiredCache( // 如果缓存过期
					_cacheName, _cacheIndate)) {
				DZLog.d(getClass(), "Cache expires");
				ayncTask.senddata(DZNetStatusDefine.SHOW_LOADING);
				DZiResponse result = requestDataAndSave(ayncTask, url, params,
						httpType);
				ayncTask.senddata(DZNetStatusDefine.CLOSE_LOADING);
				return result;
			} else {
				ayncTask.senddata(DZNetStatusDefine.S_CACHE_NO_EXPIRED);
			}
		}

		return null;
	}
	
	public DZiResponse processCache(DZBaseAsyncTask<?, ?> ayncTask, String url,
			String params, int httpType) throws IOException, Exception {
		final String cacheResult = getDataFromDiskCache(ayncTask, url, params);
		if (cacheResult == null) { // 如果没有缓存，下载数据，存储。
			DZLog.d(getClass(), "Without cache");
			return requestDataAndSave(ayncTask, url, params, httpType);
		} else { // 如果有缓存先显示缓存
			DZiResponse cacheRs = ayncTask.doJsonParse(cacheResult);
			ayncTask.senddata(DZNetStatusDefine.SHOW_CACHE, cacheRs);
			if (DZJsonFileCache.getJsonFileCache().isExpiredCache( // 如果缓存过期
					_cacheName, _cacheIndate)) {
				DZLog.d(getClass(), "Cache expires");
				ayncTask.senddata(DZNetStatusDefine.SHOW_LOADING);
				DZiResponse result = requestDataAndSave(ayncTask, url, params,
						httpType);
				ayncTask.senddata(DZNetStatusDefine.CLOSE_LOADING);
				return result;
			} else {
				ayncTask.senddata(DZNetStatusDefine.S_CACHE_NO_EXPIRED);
			}
		}

		return null;
	}

	/**
	 * 请求数据并存储
	 * 
	 * @param ayncTask
	 * @param url
	 * @param params
	 * @param httpType
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public DZiResponse requestDataAndSave(DZBaseAsyncTask<?, ?> ayncTask,
			String url, DZRequestParams params, int httpType)
			throws IOException, Exception {
		String pResult = ayncTask.requestData(url, params, httpType);
		return saveData(ayncTask, pResult);
	}

	public DZiResponse saveData(DZBaseAsyncTask<?, ?> ayncTask, String result)
			throws Exception {
		DZiResponse response = ayncTask.doJsonParse(result);
		ayncTask.senddata(DZNetStatusDefine.S_COMPLETE);
		ayncTask.saveCache(result, _cacheName);
		return response;
	}

	public DZiResponse requestDataAndSave(DZBaseAsyncTask<?, ?> ayncTask,
			String url, String params, int httpType) throws IOException,
			Exception {
		String pResult = ayncTask.requestData(url, params, httpType);
		return saveData(ayncTask, pResult);
	}

	/**
	 * 从磁盘缓存获取数据
	 * 
	 * @param url
	 * @param params
	 * @return 没有缓存返回 null
	 */
	public String getDataFromDiskCache(DZBaseAsyncTask<?, ?> ayncTask,
			String url, DZRequestParams params) {
		if (TextUtils.isEmpty(_cacheName)) {
			_cacheName = DZNetWorkeUtility.getUrlWithQueryString(url, params);
		}
		return getDataFromDiskCacheByName(ayncTask);
	}

	public String getDataFromDiskCache(DZBaseAsyncTask<?, ?> ayncTask,
			String url, String params) {
		if (TextUtils.isEmpty(_cacheName)) {
			_cacheName = url + params;
		}
		return getDataFromDiskCacheByName(ayncTask);
	}

	public String getDataFromDiskCacheByName(DZBaseAsyncTask<?, ?> ayncTask) {
		String cacheResult = DZJsonFileCache.getJsonFileCache().selectCache(
				_cacheName);
		if (!TextUtils.isEmpty(cacheResult)) {
			if (ayncTask.getTaskParams().isEncrypt()) {
				String text = DZEncryptTool.getInstance().decrypt(
						DZEncryptTool.KEY, cacheResult);
				return text;
			}
			return cacheResult;
		}
		return null;
	}

	public long getCacheIndate() {
		return _cacheIndate;
	}

	public void setCacheIndate(long cacheIndate) {
		_cacheIndate = cacheIndate;
	}

	public String getCacheName() {
		return _cacheName;
	}

	public void setCacheName(String cacheName) {
		_cacheName = cacheName;
	}
	
}
