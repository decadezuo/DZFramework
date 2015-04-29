package com.decade.framework;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.decade.framework.network.DZNetCheck;

/**
 * @description: 应用程序基类
 * @author: Decade
 * @date: 2013-5-17
 * 
 */

public abstract class DZApplication extends Application {
	private static DZApplication _app;
	private int _workSpaceWidth = 480;
	private int _workSpaceHeight = 800;
	private float _density;
	private boolean _netStatus = false;

	public static DZApplication getApp() {
		return _app;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initApp();
	}

	public void initApp() {
		_app = this;
		_netStatus = DZNetCheck.checkNet(this);
	}

	public int getWorkSpaceWidth() {
		return _workSpaceWidth;
	}

	public void setWorkSpaceWidth(int workSpaceWidth) {
		_workSpaceWidth = workSpaceWidth;
	}

	public int getWorkSpaceHeight() {
		return _workSpaceHeight;
	}

	public void setWorkSpaceHeight(int workSpaceHeight) {
		_workSpaceHeight = workSpaceHeight;
	}

	public float getDensity() {
		return _density;
	}

	public void setDensity(float density) {
		_density = density;
	}

	public boolean isNetStatus() {
		return _netStatus;
	}

	public void setNetStatus(boolean netStatus) {
		_netStatus = netStatus;
	}

	public static Context getAppContext() {
		DZApplication app = getApp();
		return app.getApplicationContext();
	}

	public Resources getAppResources() {
		DZApplication app = getApp();
		return app.getResources();
	}

	public String getAppString(int resId) {
		Resources RES = getAppResources();
		return RES.getString(resId);
	}

	public static int getAppStringName(String name) {
		return getResId(name, "string");
	}

	private static int getResId(String name, String resType) {
		return getAppContext().getResources().getIdentifier(name, resType,
				getAppContext().getPackageName());
	}
}
