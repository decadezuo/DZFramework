package com.decade.framework;

import android.view.View;
import android.widget.TextView;

/**
 * @description: 底部视图接口
 * @author: Decade
 * @date: 2013-5-2
 * 
 */
public interface DZiBottomView {
	public View getBottomView();
	public void setItemSelected(int index);
	public TextView getUnreadView();
}
