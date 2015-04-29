package com.decade.framework.async;

import java.io.IOException;

import android.content.Context;

import com.decade.framework.network.DZNetStatusDefine;
import com.decade.framework.network.DZRequestParams;

/**
 * @description: Http请求发送数据
 * @author: Decade
 * @date: 2013-5-6
 * 
 */
public abstract class DZHttpAsyncTask<Params, Progress> extends
		DZBaseAsyncTask<Params, Progress> {

	/**
	 * @param taskParams
	 */
	public DZHttpAsyncTask(Context context, DZAsyncTaskParams taskParams) {
		super(context, taskParams);
	}

	protected DZiResponse doRequestFromHttp(String url, DZRequestParams params,int httpType) {
		try {
			DZiResponse result = getCacheType().processCache(this, url, params,
					httpType);
			return result;
		} catch (IOException e) {
			senddata(DZNetStatusDefine.S_NETWORK_ERROR,e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			if(e.getMessage().equals("json_parse_error")){
				senddata(DZNetStatusDefine.S_JSON_PARSE_ERROR,e.getMessage());
			}
			e.printStackTrace();
		}
		return null;
	}
	
	protected DZiResponse doRequestFromHttp(String url, String params,int httpType) {
		try {
			DZiResponse result = getCacheType().processCache(this, url, params,
					httpType);
			return result;
		} catch (IOException e) {
			senddata(DZNetStatusDefine.S_NETWORK_ERROR,e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			if(e.getMessage().equals("json_parse_error")){
				senddata(DZNetStatusDefine.S_JSON_PARSE_ERROR,e.getMessage());
			}
			e.printStackTrace();
		}
		return null;
	}

}
