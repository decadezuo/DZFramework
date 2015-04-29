/*
 * @(#)OnEventListener.java		       Project:com.sinaapp.msdxblog.androidkit
 * Date:2012-11-15
 *
 * Copyright (c) 2011 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.decade.framework.annotation;

import java.lang.reflect.Method;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.decade.framework.kit.DZLog;

/**
 * @description: View 所有事件 (From AndroidKit)
 * @author: Decade
 * @date: 2013-5-28
 * @preserve all
 */
public final class DZInjectEventListener implements OnClickListener, OnItemClickListener,
		OnItemSelectedListener, OnItemLongClickListener, OnKeyListener,
		OnCreateContextMenuListener, OnFocusChangeListener, OnTouchListener, OnLongClickListener {
	private Object mObject;
	private String mTouth;
	private String mFocusChange;
	private String mCreateContextMenu;
	private String mKey;
	private String mItemLongClick;
	private String mItemSelected;
	private String mNothingSelected;
	private String mItemClick;
	private String mClick;
	private String mLongClick;

	public DZInjectEventListener(Object object) {
		super();
		this.mObject = object;
	}

	public boolean onTouch(View v, MotionEvent event) {
		return invokeMethod(mTouth, new Class<?>[] { View.class, MotionEvent.class }, new Object[] {
				v, event });
	}

	public void onFocusChange(View v, boolean hasFocus) {
		invokeMethod(mFocusChange, new Class<?>[] { View.class, boolean.class }, new Object[] { v,
				hasFocus });
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		invokeMethod(mCreateContextMenu, new Class<?>[] { ContextMenu.class, View.class,
				ContextMenuInfo.class }, new Object[] { menu, v, menuInfo });
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return invokeMethod(mKey, new Class<?>[] { View.class, int.class, KeyEvent.class },
				new Object[] { v, keyCode, event });
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		return invokeMethod(mItemLongClick, new Class<?>[] { AdapterView.class, View.class,
				int.class, long.class }, new Object[] { arg0, arg1, arg2, arg3 });
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		invokeMethod(mItemSelected, new Class<?>[] { AdapterView.class, View.class, int.class,
				long.class }, new Object[] { arg0, arg1, arg2, arg3 });
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		invokeMethod(mNothingSelected, new Class<?>[] { AdapterView.class }, new Object[] { arg0 });
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		invokeMethod(mItemClick, new Class<?>[] { AdapterView.class, View.class, int.class,
				long.class }, new Object[] { arg0, arg1, arg2, arg3 });
	}

	public void onClick(View v) {
		invokeMethod(mClick, new Class<?>[] { View.class }, new Object[] { v });
	}

	public boolean onLongClick(View v) {
		return invokeMethod(mLongClick, new Class<?>[] { View.class }, new Object[] { v });
	}

	private boolean invokeMethod(String methodName, Class<?>[] cls, Object[] params) {
		if (mObject == null) {
			return false;
		}
		try {
			Method method = mObject.getClass().getDeclaredMethod(methodName, cls);
			if (method != null) {
				method.invoke(mObject, params);
			} else {
				throw new Exception(String.format("error to get the method with name :%s", methodName));
			}
		} catch (Exception e) {
			DZLog.w(getClass(),e);
		}
		return false;
	}

	public DZInjectEventListener setmTouth(String mTouth) {
		this.mTouth = mTouth;
		return this;
	}

	public DZInjectEventListener setmFocusChange(String mFocusChange) {
		this.mFocusChange = mFocusChange;
		return this;
	}

	public DZInjectEventListener setmCreateContextMenu(String mCreateContextMenu) {
		this.mCreateContextMenu = mCreateContextMenu;
		return this;
	}

	public DZInjectEventListener setmKey(String mKey) {
		this.mKey = mKey;
		return this;
	}

	public DZInjectEventListener setmItemLongClick(String mItemLongClick) {
		this.mItemLongClick = mItemLongClick;
		return this;
	}

	public DZInjectEventListener setmItemSelected(String mItemSelected) {
		this.mItemSelected = mItemSelected;
		return this;
	}

	public DZInjectEventListener setmNothingSelected(String mNothingSelected) {
		this.mNothingSelected = mNothingSelected;
		return this;
	}

	public DZInjectEventListener setmItemClick(String mItemClick) {
		this.mItemClick = mItemClick;
		return this;
	}

	public DZInjectEventListener setmClick(String mClick) {
		this.mClick = mClick;
		return this;
	}

	public DZInjectEventListener setmLongClick(String mLongClick) {
		this.mLongClick = mLongClick;
		return this;
	}

}
