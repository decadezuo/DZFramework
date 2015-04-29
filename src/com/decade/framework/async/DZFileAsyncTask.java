package com.decade.framework.async;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import com.decade.framework.network.DZNetStatusDefine;
import com.decade.framework.network.DZNetWorkeUtility;

/**
 * @description: 文件上传
 * @author: Decade
 * @date: 2013-5-6
 * 
 */
public abstract class DZFileAsyncTask<Params, Progress> extends
		DZBaseAsyncTask<Params, Progress> {

	/**
	 * @param taskParams
	 */
	public DZFileAsyncTask(Context context, DZAsyncTaskParams taskParams) {
		super(context, taskParams);
	}

	protected DZiResponse doUpload(String url, Map<String, String> params,
			Map<String, File> files) {
		DZiResponse result = null;
		try {
			String response_text;
			response_text = DZNetWorkeUtility.upLoadPhoto(url, params, files);
			if (!TextUtils.isEmpty(response_text)) {
				result = doJsonParse(response_text);
				senddata(DZNetStatusDefine.S_COMPLETE);
			}
		} catch (IOException e) {
			senddata(DZNetStatusDefine.S_NETWORK_ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			if (e.getMessage().equals("json_parse_error")) {
				senddata(DZNetStatusDefine.S_JSON_PARSE_ERROR, e.getMessage());
			}
			e.printStackTrace();
		}

		finally {
			params.clear();
			params = null;
		}
		return result;
	}
}
