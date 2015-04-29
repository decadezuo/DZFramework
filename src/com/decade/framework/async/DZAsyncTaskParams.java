package com.decade.framework.async;

/**
 * @description:
 * @author: Decade
 * @date: 2013-5-6
 * 
 */
public class DZAsyncTaskParams {
	private static final int DEFAULT_REQUESTCODE = -10000;
	private DZiAsyncTaskCallback _event;
	private DZiResponse _parse;
	private int _requestCode = DEFAULT_REQUESTCODE;
	private DZiCacheType _cacheType;;
	private boolean _openPrompt;
	private boolean _closePrompt;
	private String _promptContent;
	private boolean _encrypt;

	public DZAsyncTaskParams(DZiAsyncTaskCallback event, DZiResponse parse,
			DZiCacheType cacheType, int requestCode) {
		_event = event;
		_parse = parse;
		_cacheType = cacheType;
		_requestCode = requestCode;
		init(cacheType);
	}

	public void init(DZiCacheType cacheType) {
		if (cacheType == null) {
			_cacheType = new DZNonuseCache();
		}
		setOpenPrompt(true);
		setClosePrompt(true);
		setEncrypt(false);
	}

	public DZiAsyncTaskCallback getEvent() {
		return _event;
	}

	public void setEvents(DZiAsyncTaskCallback event) {
		_event = event;
	}

	public DZiResponse getParse() {
		return _parse;
	}

	public void setParse(DZiResponse parse) {
		_parse = parse;
	}

	public int getRequestCode() {
		return _requestCode;
	}

	public void setRequestCode(int requestCode) {
		_requestCode = requestCode;
	}

	public DZiCacheType getCacheType() {
		return _cacheType;
	}

	public void setCacheType(DZiCacheType cacheType) {
		_cacheType = cacheType;
	}

	public boolean isOpenPrompt() {
		return _openPrompt;
	}

	public void setOpenPrompt(boolean openPrompt) {
		_openPrompt = openPrompt;
	}

	public boolean isClosePrompt() {
		return _closePrompt;
	}

	public void setClosePrompt(boolean closePrompt) {
		_closePrompt = closePrompt;
	}

	public String getPromptContent() {
		return _promptContent;
	}

	public void setPromptContent(String promptContent) {
		_promptContent = promptContent;
	}

	public boolean isEncrypt() {
		return _encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		_encrypt = encrypt;
	}

}
