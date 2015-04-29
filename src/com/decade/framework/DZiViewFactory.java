package com.decade.framework;

import android.view.ViewGroup;

/**
 * @description: View抽象工厂接口
 * @author: Decade
 * @date: 2013-5-17
 */
public interface DZiViewFactory {
	public DZBaseView createView(DZBaseViewActivity parent,int viewId, ViewGroup root);
}
