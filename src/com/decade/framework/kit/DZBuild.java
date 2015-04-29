package com.decade.framework.kit;

/**
 * @description: 
 * @author: Decade
 * @date: 2013-6-4
 * 
 */
public class DZBuild {
	private static boolean _debug = true;
	private static boolean _inject = false;
	
	/**
	 * 设置调试状态，为true会打印日志
	 * @param debug
	 */
	public static void setDebugMode(boolean debug){
		
		_debug = debug;
	}
	
	public static boolean isDebugMode(){
		return _debug;
	}
	
	public static void setInject(boolean inject){
		
		_inject = inject;
	}
	
	public static boolean isInject(){
		return _inject;
	}
}
