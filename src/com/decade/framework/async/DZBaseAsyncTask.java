package com.decade.framework.async;

import java.io.IOException;

import android.content.Context;
import android.text.TextUtils;

import com.decade.framework.DZApplication;
import com.decade.framework.async.core.DZCoreAsyncTask;
import com.decade.framework.cache.DZJsonFileCache;
import com.decade.framework.kit.DZEncryptTool;
import com.decade.framework.kit.DZLog;
import com.decade.framework.network.DZNetStatusDefine;
import com.decade.framework.network.DZNetWorkeUtility;
import com.decade.framework.network.DZRequestParams;

/**
 * @description: 网络请求抽象基类
 * @author: Decade
 * @date: 2013-5-20
 * 
 */
public abstract class DZBaseAsyncTask<Params, Progress> extends
		DZCoreAsyncTask<Params, Object, DZiResponse> {
	private int _statusCode = DZNetStatusDefine.S_DEFAULT;
	protected static final int HTTP_POST = 10;
	protected static final int HTTP_GET = 11;
	private DZAsyncTaskParams _taskParams;
	private Context _context;

	public DZBaseAsyncTask(Context context, DZAsyncTaskParams taskParams) {
		super();
		_context = context;
		_taskParams = taskParams;
	}

	protected DZiCacheType getCacheType() {
		if (_taskParams != null) {
			return _taskParams.getCacheType();
		}
		return null;
	}

	protected DZiAsyncTaskCallback getEvent() {
		if (_taskParams != null) {
			return _taskParams.getEvent();
		}
		return null;
	}

	public DZAsyncTaskParams getTaskParams() {
		return _taskParams;
	}

	public DZiResponse getParse() {
		if (_taskParams != null) {
			return _taskParams.getParse();
		}
		return null;
	}

	public void setTaskParams(DZAsyncTaskParams taskParams) {
		_taskParams = taskParams;
	}

	@Override
	protected DZiResponse doInBackground(Params... params) {
		return null;
	}

	protected String requestData(String url, DZRequestParams params,
			int httpType) throws IOException {
		if (DZApplication.getApp() != null) {
			if (DZApplication.getApp().isNetStatus()) {
				if (httpType == HTTP_POST) {
					return DZNetWorkeUtility.requestHttpPost(url, params);

				} else if (httpType == HTTP_GET) {
					return DZNetWorkeUtility.requestHttpGet(url, params);
				}
			} else {
				senddata(DZNetStatusDefine.S_NET_DISCONNECTED);
			}
		} else {
			DZLog.e(getClass(), "DCApplication not initialize");
		}
		return null;
	}

	protected String requestData(String url, String params, int httpType)
			throws IOException {
		if (DZApplication.getApp() != null) {
			if (DZApplication.getApp().isNetStatus()) {
				if (httpType == HTTP_POST) {
					return DZNetWorkeUtility.requestHttpPost(url, params);
				} else if (httpType == HTTP_GET) {
					return DZNetWorkeUtility.requestHttpGet(url, params);
				}
			} else {
				senddata(DZNetStatusDefine.S_NET_DISCONNECTED);
			}
		} else {
			DZLog.e(getClass(), "DCApplication not initialize");
		}
		return null;
	}

	protected void saveCache(String response_Text, String urlCache) {
		if (!TextUtils.isEmpty(response_Text)) {
			if (_taskParams.isEncrypt()) {
				String text = DZEncryptTool.getInstance().encrypt(
						DZEncryptTool.KEY, response_Text);
				DZJsonFileCache.getJsonFileCache().saveCache(text, urlCache);
			} else {
				DZJsonFileCache.getJsonFileCache().saveCache(response_Text,
						urlCache);
			}
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (_taskParams != null) {
			onStart(_taskParams.isOpenPrompt(), _taskParams.getPromptContent());
		}
	}

	@Override
	protected void onPostExecute(DZiResponse result) {
		requestComplete(result);
	}

	public void senddata(Object... values) {
		publishProgress(values);
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		Integer code = (Integer) values[0];
		switch (code) {
		case DZNetStatusDefine.SHOW_CACHE:
			DZLog.d(getClass(), "show cache");
			DZiResponse response = (DZiResponse) values[1];
			_statusCode = DZNetStatusDefine.S_COMPLETE;
			requestComplete(response);
			break;
		case DZNetStatusDefine.SHOW_LOADING:
			DZLog.d(getClass(), "show loading");
			openTopLoadView();
			break;
		case DZNetStatusDefine.CLOSE_LOADING:
			DZLog.d(getClass(), "close loading");
			closeTopLoadView();
			break;
		case DZNetStatusDefine.S_CACHE_NO_EXPIRED:
			_statusCode = DZNetStatusDefine.S_CACHE_NO_EXPIRED;
			DZLog.i(getClass(), "Cache has not expired !");
			break;
		case DZNetStatusDefine.S_NET_DISCONNECTED:
			DZLog.e(getClass(), "network disconnected");
			_statusCode = DZNetStatusDefine.S_NET_DISCONNECTED;
			netDisconnected();
			break;
		case DZNetStatusDefine.S_COMPLETE:
			DZLog.d(getClass(), "complete");
			if (_statusCode != DZNetStatusDefine.S_NET_DISCONNECTED) {
				_statusCode = DZNetStatusDefine.S_COMPLETE;
			}
			break;
		case DZNetStatusDefine.S_JSON_PARSE_ERROR:
			DZLog.e(getClass(), "json parse error");
			_statusCode = DZNetStatusDefine.S_JSON_PARSE_ERROR;
			onJsonPaserError((String) values[1]);
			closeTopLoadView();
			break;
		case DZNetStatusDefine.S_NETWORK_ERROR:
			DZLog.e(getClass(), "network error");
			if (_statusCode != DZNetStatusDefine.S_NET_DISCONNECTED) {
				_statusCode = DZNetStatusDefine.S_NETWORK_ERROR;
				onServerRequestError((String) values[1]);
			}
			closeTopLoadView();
			break;
		default:
			break;
		}

	}

	private void requestComplete(DZiResponse result) {
		DZLog.d(getClass(), "requestComplete" + _statusCode);
		if (_taskParams != null) {
			onFinish(_taskParams.isClosePrompt());
		}
		if (_statusCode == DZNetStatusDefine.S_COMPLETE) {
			onComplete(result);
		}
	}

	protected DZiResponse doJsonParse(String response_text) throws Exception {
		DZiResponse response = null;
		if (getParse() != null) {
			try {
				response = getParse().paser(response_text);
			} catch (Exception e) {
				throw new Exception("json_parse_error", e);
			}
		}
		return response;
	}

	public void openTopLoadView() {
		if (getEvent() != null) {
			getEvent().openTopLoadView(_taskParams.getRequestCode());
		}
	}

	public void closeTopLoadView() {

		if (getEvent() != null) {
			getEvent().closeTopLoadView(_taskParams.getRequestCode());
		}
	}

	protected void onServerRequestError(String message) {
		if (getEvent() != null) {
			getEvent().onServerResponseError(message,
					_taskParams.getRequestCode());
		}
	}

	protected void onJsonPaserError(String message) {
		if (getEvent() != null) {
			getEvent().onJsonPaserError(message, _taskParams.getRequestCode());
		}
	}

	protected void netDisconnected() {
		if (getEvent() != null) {
			getEvent().onNetDisconnected(_taskParams.getRequestCode());
		}
	}

	protected void onComplete(DZiResponse _response) {
		if (getEvent() != null) {
			if (_taskParams != null) {
				getEvent().onComplete(_response, _taskParams.getRequestCode());
			}
		}
	}

	/**
	 * @preserve
	 * @return
	 */
	public Context getContext() {
		return _context;
	}

	/**
	 * @preserve
	 * @param openPrompt
	 */
	protected void onStart(boolean openPrompt, String content) {
	}

	/**
	 * @preserve
	 * @param closePrompt
	 */
	protected void onFinish(boolean closePrompt) {
	}

}
