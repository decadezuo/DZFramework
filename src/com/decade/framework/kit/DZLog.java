package com.decade.framework.kit;

import android.util.Log;

/**
 * @description 调试信息输出
 * @author Decade
 * @date 2013-4-22
 * 
 */
public class DZLog {

	public static void output(String msg) {
		if (DZBuild.isDebugMode() && msg != null) {
			System.out.println(msg);
		}
	}

	public static void output(int msg) {
		if (DZBuild.isDebugMode()) {
			System.out.println(msg);
		}
	}

	public static void i(Class<?> cls, String msg) {
		if (DZBuild.isDebugMode() && msg != null) {
			Log.i(getClassName(cls), msg);
		}
	}

	public static void i(Class<?> cls, int msg) {
		i(cls, msg + "");
	}

	public static void i(Class<?> cls, float msg) {
		i(cls, msg + "");
	}

	public static void i(Class<?> cls, double msg) {
		i(cls, msg + "");
	}

	public static void i(Class<?> cls, long msg) {
		i(cls, msg + "");
	}

	public static void i(Class<?> cls, String msg, Throwable tr) {
		if (DZBuild.isDebugMode() && msg != null) {
			Log.i(getClassName(cls), msg, tr);
		}
	}

	public static void e(Class<?> cls, String msg) {
		if (DZBuild.isDebugMode() && msg != null) {
			Log.e(getClassName(cls), msg);
		}
	}

	public static void e(Class<?> cls, int msg) {
		e(cls, msg + "");
	}

	public static void e(Class<?> cls, float msg) {
		e(cls, msg + "");
	}

	public static void e(Class<?> cls, double msg) {
		e(cls, msg + "");
	}

	public static void e(Class<?> cls, long msg) {
		e(cls, msg + "");
	}

	public static void e(Class<?> cls, String msg, Throwable tr) {
		if (DZBuild.isDebugMode() && msg != null) {
			Log.e(getClassName(cls), msg, tr);
		}
	}

	public static void d(Class<?> cls, String msg) {
		if (DZBuild.isDebugMode() && msg != null) {
			Log.d(getClassName(cls), msg);
		}
	}

	public static void d(Class<?> cls, int msg) {
		d(cls, msg + "");
	}

	public static void d(Class<?> cls, double msg) {
		d(cls, msg + "");
	}

	public static void d(Class<?> cls, float msg) {
		d(cls, msg + "");
	}

	public static void d(Class<?> cls, long msg) {
		d(cls, msg + "");
	}

	public static void d(Class<?> cls, String msg, Throwable tr) {
		if (DZBuild.isDebugMode() && msg != null) {
			Log.d(getClassName(cls), msg, tr);
		}
	}

	public static void w(Class<?> cls, String msg) {
		if (DZBuild.isDebugMode() && msg != null) {
			Log.w(getClassName(cls), msg);
		}
	}

	public static void w(Class<?> cls, int msg) {
		w(cls, msg + "");
	}

	public static void w(Class<?> cls, float msg) {
		w(cls, msg + "");
	}

	public static void w(Class<?> cls, double msg) {
		w(cls, msg + "");
	}

	public static void w(Class<?> cls, long msg) {
		w(cls, msg + "");
	}

	public static void w(Class<?> cls, String msg, Throwable tr) {
		if (DZBuild.isDebugMode() && msg != null) {
			Log.w(getClassName(cls), msg, tr);
		}
	}

	public static void w(Class<?> cls, Throwable tr) {
		if (DZBuild.isDebugMode()) {
			Log.w(getClassName(cls), tr);
		}
	}

	public static String getClassName(Class<?> cls) {
		return cls.getSimpleName();
	}

}
