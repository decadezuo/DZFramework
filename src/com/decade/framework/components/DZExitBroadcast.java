package com.decade.framework.components;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @description:
 * @author: Decade
 * @date: 2013-5-7
 * 
 */
public class DZExitBroadcast extends BroadcastReceiver {

	private static DZExitBroadcast _receiver = new DZExitBroadcast();
	private static IntentFilter _intentFilter;

	public static void registerReceiver(Context context) {
		_intentFilter = new IntentFilter();
		_intentFilter.addAction(DZBroadcaseDefine.EXIT_APP);
		context.registerReceiver(_receiver, _intentFilter);
	}

	public static void unregisterReceiver(Context context) {
		if (_receiver != null) {
			context.unregisterReceiver(_receiver);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		((Activity) context).finish();
	}
}
