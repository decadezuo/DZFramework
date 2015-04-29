package com.decade.framework.async;

import java.io.IOException;

import com.decade.framework.network.DZRequestParams;

/**
 * @description:
 * @author: Decade
 * @date: 2013-5-31
 * 
 */
public interface DZiCacheType {
	public DZiResponse processCache( DZBaseAsyncTask<?, ?> ayncTask, String url,
			DZRequestParams params, int httpType)
			throws IOException, Exception;
	
	public DZiResponse processCache( DZBaseAsyncTask<?, ?> ayncTask, String url,
			String params, int httpType)
			throws IOException, Exception;
	
}
