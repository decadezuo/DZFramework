/*
 * @(#)UIBindUtil.java		       Project:com.sinaapp.msdxblog.androidkit
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.decade.framework.DZBaseView;
import com.decade.framework.kit.DZLog;

/**
 * @description: 注解处理类 (Modification AndroidKit)
 * @author: Decade
 * @date: 2013-5-28
 */
public class DZViewProcessor {

	/**
	 * 绑定添加了注解的控件，及其事件方法。<br/>
	 * 注意：它必须在activity调用了setContentView之后调用。
	 * 
	 * @param activity
	 */
	public static void bind(Activity activity) {
		Class<?> cl = activity.getClass();
		bindViews(activity, cl);
		bindMethods(activity, cl);
	}

	/**
	 * 该方法用来代替Activity.setContentView(layoutResID)的调用，并自动绑定控件及事件。
	 * 
	 * @param activity
	 *            进行绑定的Activity。
	 * @param layoutResID
	 *            Activity的布局资源的ID。
	 */
	public static void bind(Activity activity, int layoutResID) {
		activity.setContentView(layoutResID);
		bind(activity);
	}

	/**
	 * 对object中添加注解的变量进行控件绑定
	 * 
	 * @param rootView
	 * @param object
	 *            添加注解的变量所属的对象
	 */
	public static void bind(View root, Object object) {
		bindViews(root, object);
		bindMethods(root, object);
	}

	/**
	 * 根据成员变量的注解进行绑定
	 * 
	 * @param activity
	 * @param cl
	 * @throws IllegalAccessException
	 */
	private static void bindViews(Activity activity, Class<?> cl) {
		try {
			for (Field field : cl.getDeclaredFields()) {
				DZInjectView av = field.getAnnotation(DZInjectView.class);
				if (av != null) {
					field.setAccessible(true);

					if (av.id() == -1) {
						int resId = activity.getResources().getIdentifier(
								field.getName(), "id",
								activity.getPackageName());
						setView(field, activity, resId);
					} else {
						setView(field, activity, av.id());
					}

					setEventListener(activity, field, av);
				}
			}
		} catch (Exception e) {
			DZLog.w(DZViewProcessor.class, e);
		}
	}

	private static void bindViews(View rootView, Object object) {
		Class<? extends Object> cl = object.getClass();
		try {
			for (Field field : cl.getDeclaredFields()) {
				DZInjectView av = field.getAnnotation(DZInjectView.class);
				if (av != null) {
					field.setAccessible(true);
					if (av.id() == -1) {
						int resId = ((DZBaseView) object).getIdentifier(
								field.getName(), "id",
								((DZBaseView) object).getPackageName());
						setView(field, object, rootView, resId);
					} else {
						setView(field, object, rootView, av.id());
					}
					setEventListener(object, field, av);

				}
			}
		} catch (Exception e) {
			DZLog.w(DZViewProcessor.class, e);
		}
	}

	private static void bindMethods(View rootView, Object object) {
		Class<? extends Object> cl = object.getClass();

		for (Method method : cl.getDeclaredMethods()) {
			DZInjectClick oc = method.getAnnotation(DZInjectClick.class);
			if (oc != null) {
				DZInjectEventListener listener = new DZInjectEventListener(
						object).setmClick(method.getName());
				int ids[] = oc.id();
				for (int id : ids) {
					rootView.findViewById(id).setOnClickListener(listener);
				}
			}
		}
	}

	/**
	 * 根据成员方法的注解进行绑定
	 * 
	 * @param activity
	 * @param cl
	 */
	private static void bindMethods(Activity activity, Class<?> cl) {
		for (Method method : cl.getDeclaredMethods()) {
			DZInjectClick oc = method.getAnnotation(DZInjectClick.class);
			if (oc != null) {
				DZInjectEventListener listener = new DZInjectEventListener(
						activity).setmClick(method.getName());
				int ids[] = oc.id();
				for (int id : ids) {
					activity.findViewById(id).setOnClickListener(listener);
				}
			}
		}
	}

	/**
	 * 设置事件的监听器。
	 * 
	 * @param object
	 *            context对象。
	 * @param field
	 *            要设置的控件。
	 * @param av
	 *            AndroidView注解信息。
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static void setEventListener(Object object, Field field,
			DZInjectView av) throws IllegalArgumentException,
			IllegalAccessException {
		Object obj = field.get(object);
		DZInjectEventListener l = new DZInjectEventListener(object);

		if (obj instanceof View) {
			View view = (View) obj;

			String click = av.onClick();
			if (!TextUtils.isEmpty(click)) {
				view.setOnClickListener(l.setmClick(click));
			}

			String createContextMenu = av.onCreateContextMenu();
			if (!TextUtils.isEmpty(createContextMenu)) {
				view.setOnCreateContextMenuListener(l
						.setmCreateContextMenu(createContextMenu));
			}

			String focusChange = av.onFocusChange();
			if (!TextUtils.isEmpty(focusChange)) {
				view.setOnFocusChangeListener(l.setmFocusChange(focusChange));
			}

			String key = av.onKey();
			if (!TextUtils.isEmpty(key)) {
				view.setOnKeyListener(l.setmKey(key));
			}

			String longClick = av.onLongClick();
			if (!TextUtils.isEmpty(longClick)) {
				view.setOnLongClickListener(l.setmLongClick(longClick));
			}

			String touch = av.onTouch();
			if (!TextUtils.isEmpty(touch)) {
				view.setOnTouchListener(l.setmTouth(touch));
			}
		}

		if (obj instanceof AdapterView<?>) {
			AdapterView<?> view = (AdapterView<?>) obj;
			String itemClick = av.onItemClick();
			if (!TextUtils.isEmpty(itemClick)) {
				view.setOnItemClickListener(l.setmItemClick(itemClick));
			}

			String itemLongClick = av.onItemLongClick();
			if (!TextUtils.isEmpty(itemLongClick)) {
				view.setOnItemLongClickListener(l
						.setmItemLongClick(itemLongClick));
			}

			DZInjectItemSelect itemSelect = av.onItemSelect();
			if (!TextUtils.isEmpty(itemSelect.onItemSelected())) {
				view.setOnItemSelectedListener(l.setmItemSelected(
						itemSelect.onItemSelected()).setmNothingSelected(
						itemSelect.onNothingSelected()));
			}
		}
	}

	/**
	 * 对控件赋值。
	 * 
	 * @param view
	 *            要进行赋值的控件。
	 * @param activity
	 *            activity对象。
	 * @param id
	 *            控件的ID。
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static void setView(Field view, Activity activity, int id)
			throws IllegalArgumentException, IllegalAccessException {
		view.set(activity, activity.findViewById(id));
	}

	/**
	 * 对控件赋值
	 * 
	 * @param view
	 *            要进行赋值的控件
	 * @param object
	 *            控件所属的对象
	 * @param rootView
	 *            能找到该控件的view
	 * @param id
	 *            控件的id
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static void setView(Field view, Object object, View rootView, int id)
			throws IllegalArgumentException, IllegalAccessException {
		view.set(object, rootView.findViewById(id));
	}

}
