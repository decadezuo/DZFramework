package com.decade.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.decade.framework.annotation.DZViewProcessor;
import com.decade.framework.components.DZBroadcaseDefine;
import com.decade.framework.kit.DZBuild;

/**
 * @description: 整个框架基础Activity，此Activity继承至FragmentActivity
 * @author: Decade
 * @date: 2013-5-2
 */

public abstract class DZActivity extends FragmentActivity {

	private DZiTopView _topViewLoader;
	private DZiBottomView _bottomViewLoader;
	private TextView _title_tv;
	private boolean _isLoading = false;
	private DCDispenseDataBroadcast _dispenseData = new DCDispenseDataBroadcast();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 注册分发消息广播
	 */
	protected final void registerDispenseData() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(DZBroadcaseDefine.BROADCASE_DISPENSE_MESSAGE);
		registerReceiver(_dispenseData, intentFilter);
	}

	/**
	 * @param dispenseData
	 */
	protected final void unregisterDispenseData() {
		if (_dispenseData != null) {
			unregisterReceiver(_dispenseData);
		}
	}

	/**
	 * @description: 数据发放接收器
	 * @author: Decade
	 * @date: 2013-9-16
	 * 
	 */
	public class DCDispenseDataBroadcast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int where = intent.getIntExtra("where", -1);
			int code = intent.getIntExtra("code", -1);
			Object object = intent.getSerializableExtra("args");
			if (where == DZSendDataDefine.SEND_TO_VIEW) {
				dispenseData(object, code);
			} else if (where == DZSendDataDefine.SEND_TO_ACTIVITY) {
				onReceiveData(object, code);
			} else if (where == DZSendDataDefine.SEND_TO_ALL) {
				onReceiveData(object, code);
				dispenseData(object, code);
			}
		}
	}

	/**
	 * @param args
	 */
	protected void dispenseData(Object args, int code) {
	}

	/**
	 * 当前Activity注册为数据分发监听者者时，数据更新后会调用此方法。
	 * 
	 * @param object
	 */
	protected void onReceiveData(Object object, int code) {
	}

	protected final void sendExitBroadcast() {
		Intent intent = new Intent();
		intent.setAction(DZBroadcaseDefine.EXIT_APP);
		sendBroadcast(intent);
	}

	public void setTopView(DZiTopView topViewLoader) {
		_topViewLoader = topViewLoader;
	}

	public void setBottomView(DZiBottomView bottomViewLoader) {
		_bottomViewLoader = bottomViewLoader;
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initContentView();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		initContentView();
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initContentView();
	}

	private void initContentView() {
		setTopView(getTopViewLoader());
		setBottomView(getBottomViewLoader());
		if(DZBuild.isInject()){
			DZViewProcessor.bind(this);
		}
	}

	protected abstract DZiTopView getTopViewLoader();

	protected abstract DZiBottomView getBottomViewLoader();

	protected View getTopView() {
		return _topViewLoader != null ? _topViewLoader.getTopView() : null;
	}

	protected View getTopLeftView() {
		return _topViewLoader != null ? _topViewLoader.getTopLeftView() : null;
	}

	protected View getTopLeftSideView() {
		return _topViewLoader != null ? _topViewLoader.getTopLeftSideView()
				: null;
	}

	protected View getTopLeftSecondView() {
		return _topViewLoader != null ? _topViewLoader.getTopLeftSecondView()
				: null;
	}

	protected View getTopLeftThirdView() {
		return _topViewLoader != null ? _topViewLoader.getTopLeftThirdView()
				: null;
	}

	protected View getTopRightView() {
		return _topViewLoader != null ? _topViewLoader.getTopRightView() : null;
	}

	protected View getTopRightSecondView() {
		return _topViewLoader != null ? _topViewLoader.getTopRightSecondView()
				: null;
	}

	protected View getTopRightThirdView() {
		return _topViewLoader != null ? _topViewLoader.getTopRightThirdView()
				: null;
	}

	protected View getTopLoadView() {
		return _topViewLoader != null ? _topViewLoader.getTopLoadView() : null;
	}

	protected View getTopCenterView() {
		return _topViewLoader != null ? _topViewLoader.getTopCenterView()
				: null;
	}

	public View getBottomView() {
		return _bottomViewLoader != null ? _bottomViewLoader.getBottomView()
				: null;
	}

	public TextView getUnreadView() {
		return _bottomViewLoader != null ? _bottomViewLoader.getUnreadView()
				: null;
	}

	/**
	 * 设置左边View文字
	 * 
	 * @param text
	 */
	public final void setTopLeftViewText(String text) {
		TextView left = (TextView) getTopLeftView();
		if (left != null) {
			left.setText(text);
		}
	}

	public final void setTopLeftViewText(int resId) {
		TextView left = (TextView) getTopLeftView();
		if (left != null) {
			left.setText(resId);
		}
	}

	public final void setTopLeftSecondViewText(String text) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setText(text);
		}
	}

	public final void setTopLeftSecondViewText(int resId) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setText(resId);
		}
	}

	public final void setTopLeftThirdViewText(String text) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setText(text);
		}
	}

	public final void setTopLeftThirdViewText(int resId) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setText(resId);
		}
	}

	/**
	 * 设置右边Viwe文字
	 * 
	 * @param text
	 */
	public final void setTopRightViewText(String text) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setText(text);
		}
	}

	public final void setTopRightViewText(int resId) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setText(resId);
		}
	}

	public final void setTopRightSecondViewText(String text) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setText(text);
		}
	}

	public final void setTopRightSecondViewText(int resId) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setText(resId);
		}
	}

	public final void setTopRightThirdViewText(String text) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setText(text);
		}
	}

	public final void setTopRightThirdViewText(int resId) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setText(resId);
		}
	}

	/**
	 * 添加顶部标题View
	 * 
	 * @param resId
	 *            文字资源
	 * @param color
	 * @param size
	 *            标题字体大小，单位为 sp
	 */
	public final void addTopTitle(int textResId, int color, float size) {
		createTitleView(color, size);
		if (textResId != 0) {
			_title_tv.setText(textResId);
		}
		addTopTitleView();
	}

	/**
	 * 添加顶部标题View
	 * 
	 * @param text
	 *            文字资源
	 * @param color
	 * @param size
	 *            标题字体大小资源，单位为 sp
	 */
	public final void addTopTitle(String text, int color, float size) {
		createTitleView(color, size);
		if (!TextUtils.isEmpty(text)) {
			_title_tv.setText(text);
		}
		addTopTitleView();
	}

	private final void addTopTitleView() {
		removeAllToTopCenterView();
		addToTopCenterView(_title_tv);
	}

	private final void createTitleView(int color, float size) {
		_title_tv = new TextView(this);
		_title_tv.setTextSize(size);
		_title_tv.setTextColor(color);
	}

	/**
	 * 设置顶部标题文字，只有在标题view被创建后设置才有效。
	 * 
	 * @param title
	 */
	public final void setTopTitleText(String title) {
		if (_title_tv != null) {
			_title_tv.setText(title);
		}
	}

	/**
	 * 设置顶部标题文字，只有在标题View被创建后设置才有效。
	 * 
	 * @param resId
	 *            资源ID
	 */
	public final void setTopTitleText(int resId) {
		if (_title_tv != null) {
			_title_tv.setText(resId);
		}
	}

	public TextView getTitleView() {
		return _title_tv;
	}

	/**
	 * 添加view 到顶部中间标题处
	 * 
	 * @param view
	 */
	public final void addToTopCenterView(View view) {
		ViewGroup center = (ViewGroup) getTopCenterView();
		if (center != null) {
			center.addView(view);
		}
	}

	public final void removeAllToTopCenterView() {
		ViewGroup center = (ViewGroup) getTopCenterView();
		if (center != null) {
			center.removeAllViews();
		}
	}

	/**
	 * 设置左边View点击事件
	 * 
	 * @param listener
	 */
	public final void setTopLeftSideAction(OnClickListener listener) {
		View leftSide = getTopLeftSideView();
		if (leftSide != null) {
			leftSide.setOnClickListener(listener);
		}
	}

	public final void setTopLeftAction(OnClickListener listener) {
		TextView left = (TextView) getTopLeftView();
		if (left != null) {
			left.setOnClickListener(listener);
		}
	}

	public final void setTopLeftSecondAction(OnClickListener listener) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setOnClickListener(listener);
		}
	}

	public final void setTopLeftThirdAction(OnClickListener listener) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setOnClickListener(listener);
		}
	}

	/**
	 * 设置右边View点击事件
	 * 
	 * @param listener
	 */
	public final void setTopRightAction(OnClickListener listener) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setOnClickListener(listener);
		}
	}

	public final void setTopRightSecondAction(OnClickListener listener) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setOnClickListener(listener);
		}
	}

	public final void setTopRightThirdAction(OnClickListener listener) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setOnClickListener(listener);
		}
	}

	/**
	 * 设置顶部View 背景
	 * 
	 * @param resid
	 */
	public final void setTopViewBackground(int resid) {
		View top = getTopView();
		if (top != null) {
			top.setBackgroundResource(resid);
		}
	}

	public final void setTopViewBackgroundColor(int color) {
		View top = getTopView();
		if (top != null) {
			top.setBackgroundColor(color);
		}
	}

	/**
	 * 设置左边View背景
	 * 
	 * @param resid
	 */

	public final void setTopLeftViewBackground(int resid) {
		TextView left = (TextView) getTopLeftView();
		if (left != null) {
			left.setBackgroundResource(resid);
		}
	}

	public final void setTopLeftSecondViewBackground(int resid) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setBackgroundResource(resid);
		}
	}

	public final void setTopLeftThirdViewBackground(int resid) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setBackgroundResource(resid);
		}
	}

	/**
	 * 设置右边View背景
	 * 
	 * @param resid
	 */
	public final void setTopRightViewBackground(int resid) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setBackgroundResource(resid);
		}
	}

	public final void setTopRightSecondViewBackground(int resid) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setBackgroundResource(resid);
		}
	}

	public final void setTopRightThirdViewBackground(int resid) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setBackgroundResource(resid);
		}
	}

	/**
	 * 设置左边Viwe文字颜色
	 * 
	 * @param color
	 */
	public final void setTopLeftSecondViewTextColor(int color) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setTextColor(color);
		}
	}

	public final void setTopLeftThirdViewTextColor(int color) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setTextColor(color);
		}
	}

	/**
	 * 设置右边View文字颜色
	 * 
	 * @param color
	 */

	public final void setTopRightViewTextColor(int color) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setTextColor(color);
		}
	}

	public final void setTopRightSecondViewTextColor(int color) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setTextColor(color);
		}
	}

	public final void setTopRightThirdViewTextColor(int color) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setTextColor(color);
		}
	}

	/**
	 * 设置左边View文字大小
	 */

	public final void setTopLeftThirdViewTextSize(float size) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setTextSize(size);
		}
	}

	/**
	 * 设置右边View文字大小
	 */

	public final void setTopRightViewTextSize(float size) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setTextSize(size);
		}
	}

	public final void setTopRightSecondViewTextSize(float size) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setTextSize(size);
		}
	}

	public final void setTopRightThirdViewTextSize(float size) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setTextSize(size);
		}
	}

	/**
	 * 设置左边按钮标签
	 */
	public final void setTopLeftViewTag(Object tag) {
		TextView left = (TextView) getTopLeftView();
		if (left != null) {
			left.setTag(tag);
		}
	}

	public final void setTopLeftSecondViewTag(Object tag) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setTag(tag);
		}
	}

	public final void setTopLeftThirdViewTag(Object tag) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setTag(tag);
		}
	}

	/**
	 * 设置右边按钮标签
	 */

	public final void setTopRightViewTag(Object tag) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setTag(tag);
		}
	}

	public final void setTopRightSecondViewTag(Object tag) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setTag(tag);
		}
	}

	public final void setTopRightThirdViewTag(Object tag) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setTag(tag);
		}
	}

	/**
	 * 设置左边按钮Padding
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public final void setTopLeftViewPadding(int left, int top, int right,
			int bottom) {
		TextView leftView = (TextView) getTopLeftView();
		if (leftView != null) {
			leftView.setPadding(left, top, right, bottom);
		}
	}

	public final void setTopLeftSecondViewPadding(int left, int top, int right,
			int bottom) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setPadding(left, top, right, bottom);
		}
	}

	public final void setTopLeftThirdViewPadding(int left, int top, int right,
			int bottom) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setPadding(left, top, right, bottom);
		}
	}

	/**
	 * 设置右边按钮Padding
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public final void setTopRightViewPadding(int left, int top, int right,
			int bottom) {
		TextView rightView = (TextView) getTopRightView();
		if (rightView != null) {
			rightView.setPadding(left, top, right, bottom);
		}
	}

	public final void setTopRightSecondViewPadding(int left, int top,
			int right, int bottom) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setPadding(left, top, right, bottom);
		}
	}

	public final void setTopRightThirdViewPadding(int left, int top, int right,
			int bottom) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setPadding(left, top, right, bottom);
		}
	}

	/**
	 * 设置左边按钮激活性
	 * 
	 * @param enabled
	 */
	public final void setTopLeftViewEnabled(boolean enabled) {
		TextView left = (TextView) getTopLeftView();
		if (left != null) {
			left.setEnabled(enabled);
		}
	}

	public final void setTopLeftSecondViewEnabled(boolean enabled) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setEnabled(enabled);
		}
	}

	public final void setTopLeftThirdViewEnabled(boolean enabled) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setEnabled(enabled);
		}
	}

	/**
	 * 设置右边按钮激活性
	 * 
	 * @param enabled
	 */

	public final void setTopRightViewEnabled(boolean enabled) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setEnabled(enabled);
		}
	}

	public final void setTopRightSecondViewEnabled(boolean enabled) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setEnabled(enabled);
		}
	}

	public final void setTopRightThirdViewEnabled(boolean enabled) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setEnabled(enabled);
		}
	}

	/**
	 * 设置顶部View可见性
	 * 
	 * @param visibility
	 */
	public final void setTopViewVisibility(int visibility) {
		View top = getTopView();
		if (top != null) {
			top.setVisibility(visibility);
		}
	}

	/**
	 * 设置左边View可见性
	 * 
	 * @param visibility
	 */
	public final void setTopLeftViewVisibility(int visibility) {
		TextView left = (TextView) getTopLeftView();
		if (left != null) {
			left.setVisibility(visibility);
		}
	}

	public final void setTopLeftSecondViewVisibility(int visibility) {
		TextView leftSecond = (TextView) getTopLeftSecondView();
		if (leftSecond != null) {
			leftSecond.setVisibility(visibility);
		}
	}

	public final void setTopLeftThirdViewVisibility(int visibility) {
		TextView leftThird = (TextView) getTopLeftThirdView();
		if (leftThird != null) {
			leftThird.setVisibility(visibility);
		}
	}

	/**
	 * 设置右边View可见性
	 * 
	 * @param visibility
	 */
	public final void setTopRightViewVisibility(int visibility) {
		TextView right = (TextView) getTopRightView();
		if (right != null) {
			right.setVisibility(visibility);
		}
	}

	public final void setTopRightSecondViewVisibility(int visibility) {
		TextView rightSecond = (TextView) getTopRightSecondView();
		if (rightSecond != null) {
			rightSecond.setVisibility(visibility);
		}
	}

	public final void setTopRightThirdViewVisibility(int visibility) {
		TextView rightThird = (TextView) getTopRightThirdView();
		if (rightThird != null) {
			rightThird.setVisibility(visibility);
		}
	}

	/**
	 * 设置底部View可见性
	 * 
	 * @param visibility
	 */
	protected final void setBottomViewVisibility(int visibility) {
		View bottom = getBottomView();
		if (bottom != null) {
			bottom.setVisibility(visibility);
		}
	}

	/**
	 * 设置底部按钮被选中
	 * 
	 * @param index
	 */
	protected final void setBottomItemSelected(int index) {
		_bottomViewLoader.setItemSelected(index);
	}

	public final void openLoading() {
		if (getTopLoadView() != null) {
			getTopLoadView().setVisibility(View.VISIBLE);
		}
		_isLoading = true;
	}

	public final void closeLoading() {
		if (getTopLoadView() != null) {
			getTopLoadView().setVisibility(View.GONE);
		}
		_isLoading = false;
	}

	public final boolean isLoading() {
		return _isLoading;
	}

	public final float getDimensionById(int resId) {
		return getResources().getDimension(resId);
	}

	public final Drawable getDrawableById(int resId) {
		return getResources().getDrawable(resId);
	}

	public final int getColorById(int resId) {
		return getResources().getColor(resId);
	}
}
