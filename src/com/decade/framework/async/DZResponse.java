package com.decade.framework.async;



/**
 * @description: 抽象数据模型
 * @author: Decade
 * @date: 2013-5-17
 * 
 */
public abstract class DZResponse implements DZiResponse{

	// 服务器报错时返回的错误描述
	private String _message;
	// 返回数据状态值
	private int _code;

	// 返回数据状态
	private String _state;

	public String getMessage() {
		return _message;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public int getCode() {
		return _code;
	}

	public void setCode(int code) {
		_code = code;
	}

	public String getState() {
		return _state;
	}

	public void setState(String state) {
		_state = state;
	}

}
