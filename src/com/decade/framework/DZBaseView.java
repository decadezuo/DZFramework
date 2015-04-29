package com.decade.framework;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.decade.framework.annotation.DZViewProcessor;

/**
 * @description 所有子view的父类，此基类负责和主Activity进行交互。
 * @author Decade
 * @date 2013-4-23
 * 
 */

public abstract class DZBaseView {
	private ViewGroup _root;
	private LayoutInflater _inflater;
	private View _view;
	private DZBaseViewActivity _parent;
	private DZiReceiveData _receiveData = null;
	private LayoutParams _viewParams = new LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

	/**
	 * 1.开始启动，初始化数据
	 * 
	 * @param agrs
	 *            从上个页面传过来的值
	 * 
	 */
	protected abstract void start(Object agrs);

	/**
	 * 2.创建视图
	 * 
	 * @param mInflater
	 * @return 返回自定义view用于显示
	 */
	protected abstract View create();

	/**
	 * 3.打开视图，如果视图没被销毁，下次进入视图将不会执行此方法。
	 */
	protected abstract void open();

	/**
	 * 4.恢复视图 ，视图没被销毁，下次打开视图将执行此方法。<br>
	 * 按返回键不会执行此方法
	 */
	protected abstract void resume();

	/**
	 * 5.中止，退出视图时执行
	 */
	protected abstract void pause();

	/**
	 * 6.关闭，退出视图时执行
	 */
	protected abstract void close();

	/**
	 * 7.销毁，视图被销毁时执行
	 */
	protected abstract void destroy();

	public DZBaseView(ViewGroup root, DZBaseViewActivity parent) {
		_root = root;
		_parent = parent;
		_inflater = LayoutInflater.from(parent);
	}

	protected final void setReceiveDataListener(DZiReceiveData listener) {
		_receiveData = listener;
	}

	protected final boolean doingReceiveDataListener(Object args, int code) {
		if (_receiveData != null) {
			return _receiveData.onReceiveData(args, code);
		}
		return false;
	}

	protected void removeReceiveDataListener() {
		_receiveData = null;
	}

	protected void addToTopCenterView(View view) {
		_parent.addToTopCenterView(view);
	}

	/**
	 * 添加顶部标题View
	 * 
	 * @param resId
	 *            标题文字资源
	 * @param color
	 * @param sizeId
	 *            标题字体大小，单位为 sp
	 */
	protected final void addTopTitle(int textResId, int color, float size) {
		_parent.addTopTitle(textResId, color, size);
	}

	/**
	 * 添加顶部标题View
	 * 
	 * @param text
	 *            标题文字
	 * @param color
	 * @param sizeId
	 *            标题字体大小，单位为 sp
	 */
	protected final void addTopTitle(String text, int color, float size) {
		_parent.addTopTitle(text, color, size);
	}

	final void removeAllToTopCenterView() {
		_parent.removeAllToTopCenterView();
	}

	/**
	 * 设置顶部标题文字，只有在标题view被创建后设置才有效。
	 * 
	 * @param title
	 */
	public final void setTopTitleText(String title) {
		_parent.setTopTitleText(title);
	}

	/**
	 * 设置顶部标题文字，只有在标题View被创建后设置才有效。
	 * 
	 * @param resId
	 *            资源ID
	 */
	public void setTopTitleText(int resId) {
		_parent.setTopTitleText(resId);
	}

	/**
	 * 设置顶部标题栏左边View文字
	 * 
	 * @param text
	 */
	protected final void setTopLeftViewText(String text) {
		_parent.setTopLeftViewText(text);
	}

	/**
	 * 设置顶部标题栏左边View文字
	 * 
	 * @param resId
	 */
	protected final void setTopLeftViewText(int resId) {
		_parent.setTopLeftViewText(resId);

	}

	protected final void setTopLeftSecondViewText(String text) {
		_parent.setTopLeftSecondViewText(text);
	}

	protected final void setTopLeftSecondViewText(int resId) {
		_parent.setTopLeftSecondViewText(resId);

	}

	protected final void setTopLeftThirdViewText(String text) {
		_parent.setTopLeftThirdViewText(text);
	}

	protected final void setTopLeftThirdViewText(int resId) {
		_parent.setTopLeftThirdViewText(resId);

	}

	protected final void setTopRightViewText(int resId) {
		_parent.setTopRightViewText(resId);
	}

	/**
	 * 设置顶部标题栏右边View文字
	 * 
	 * @param text
	 */
	protected final void setTopRightViewText(String text) {
		_parent.setTopRightViewText(text);

	}

	protected final void setTopRightSecondViewText(int resId) {
		_parent.setTopRightSecondViewText(resId);
	}

	protected final void setTopRightSecondViewText(String text) {
		_parent.setTopRightSecondViewText(text);
	}

	protected final void setTopRightThirdViewText(int resId) {
		_parent.setTopRightThirdViewText(resId);
	}

	protected final void setTopRightThirdViewText(String text) {
		_parent.setTopRightThirdViewText(text);
	}

	protected final void setTopLeftViewBackground(int resid) {
		_parent.setTopLeftViewBackground(resid);
	}

	protected final void setTopLeftSecondViewBackground(int resid) {
		_parent.setTopLeftSecondViewBackground(resid);
	}

	protected final void setTopLeftThirdViewBackground(int resid) {
		_parent.setTopLeftThirdViewBackground(resid);
	}

	protected final void setTopRightViewBackground(int resid) {
		_parent.setTopRightViewBackground(resid);
	}

	protected final void setTopRightSecondViewBackground(int resid) {
		_parent.setTopRightSecondViewBackground(resid);
	}

	protected final void setTopRightThirdViewBackground(int resid) {
		_parent.setTopRightThirdViewBackground(resid);
	}

	protected final void setTopLeftViewVisibility(int visibility) {
		_parent.setTopLeftViewVisibility(visibility);
	}

	protected final void setTopLeftSecondViewVisibility(int visibility) {
		_parent.setTopLeftSecondViewVisibility(visibility);
	}

	protected final void setTopLeftThirdViewVisibility(int visibility) {
		_parent.setTopLeftThirdViewVisibility(visibility);
	}

	protected final void setTopLeftSecondViewTextColor(int color) {
		_parent.setTopLeftSecondViewTextColor(color);
	}

	protected final void setTopLeftThirdViewTextColor(int color) {
		_parent.setTopLeftThirdViewTextColor(color);
	}

	protected final void setTopRightSecondViewTextColor(int color) {
		_parent.setTopRightSecondViewTextColor(color);
	}

	protected final void setTopRightThirdViewTextColor(int color) {
		_parent.setTopRightThirdViewTextColor(color);
	}

	/**
	 * 设置顶部标题栏左边View点击事件
	 * 
	 * @param l
	 */
	protected final void setTopLeftAction(OnClickListener listener) {
		_parent.setTopLeftAction(listener);
	}

	protected final void setTopLeftSecondAction(OnClickListener listener) {
		_parent.setTopLeftSecondAction(listener);
	}

	protected final void setTopLeftThirdAction(OnClickListener listener) {
		_parent.setTopLeftThirdAction(listener);
	}

	protected final void setTopRightAction(OnClickListener listener) {
		_parent.setTopRightAction(listener);
	}

	protected final void setTopRightSecondAction(OnClickListener listener) {
		_parent.setTopRightSecondAction(listener);
	}

	protected final void setTopRightThirdAction(OnClickListener listener) {
		_parent.setTopRightThirdAction(listener);
	}

	protected final void setTopRightViewVisibility(int visibility) {
		_parent.setTopRightViewVisibility(visibility);
	}

	protected final void setTopRightSecondViewVisibility(int visibility) {
		_parent.setTopRightSecondViewVisibility(visibility);
	}

	protected final void setTopRightThirdViewVisibility(int visibility) {
		_parent.setTopRightThirdViewVisibility(visibility);
	}

	protected final void setTopLeftViewEnabled(Boolean enabled) {
		_parent.setTopLeftViewEnabled(enabled);
	}

	protected final void setTopLeftSecondViewEnabled(Boolean enabled) {
		_parent.setTopLeftSecondViewEnabled(enabled);
	}

	protected final void setTopLeftThirdViewEnabled(Boolean enabled) {
		_parent.setTopLeftThirdViewEnabled(enabled);
	}

	protected final void setTopRightViewEnabled(Boolean enabled) {
		_parent.setTopRightViewEnabled(enabled);
	}

	protected final void setTopRightSecondViewEnabled(Boolean enabled) {
		_parent.setTopRightSecondViewEnabled(enabled);
	}

	protected final void setTopRightThirdViewEnabled(Boolean enabled) {
		_parent.setTopRightThirdViewEnabled(enabled);
	}

	protected final void setBottomViewVisibility(int visibility) {
		_parent.setBottomViewVisibility(visibility);
	}

	protected final void setBottomItemSelected(int index) {
		_parent.setBottomItemSelected(index);
	}

	/**
	 * 设置顶部标题栏是否可见
	 * 
	 * @param visibility
	 */
	protected final void setTopViewVisibility(int visibility) {
		_parent.setTopViewVisibility(visibility);
	}

	protected final void resetLoading() {
		closeLoading();
	}

	/**
	 * 顶栏 (可用来重新自定义顶栏View)
	 */
	public void topView(View view) {
	}

	/**
	 * 底栏(可用来重新自定义底栏View)
	 * 
	 * @param view
	 */
	public void bottomView(View view) {
	}

	protected final View findViewById(int viewId) {
		return _view.findViewById(viewId);
	}

	/**
	 * 跳转到指定页面
	 * 
	 * @param viewId
	 * @param data
	 *            传递数据，可在下个页面ready方法中获取此数据。
	 * @param openCache
	 *            是否使用缓存
	 */
	public final void startPage(int viewId, Object data, boolean openCache) {
		DZBaseView view = _parent.getView(viewId);
		startPage(view, data, openCache, false);
	}

	/**
	 * 跳转到指定页面
	 * 
	 * @param viewId
	 * @param data
	 *            传递数据，可在下个页面ready方法中获取此数据。
	 * @param openCache
	 *            是否使用缓存
	 * @param animation
	 *            过度动画
	 */
	public final void startPage(int viewId, Object data, boolean openCache,
			boolean animation) {
		DZBaseView view = _parent.getView(viewId);
		startPage(view, data, openCache, animation);
	}

	public final void startPage(DZBaseView view, Object data,
			boolean openCache, boolean animation) {
		_parent.setViewInto(view, data, openCache, false, animation);
	}

	protected final void addToRoot(boolean openCache) {
		if (_view == null) {
			_view = create();
		} else {
			if (!openCache) {
				_view = create();
			}/*
			 * else{ if(!verify() || _view == null){ _view = getView(mInflater);
			 * } }
			 */
		}
		if (_root != null && _view != null) {
			_root.removeAllViews();
			_root.addView(_view, _viewParams);
		}
	}

	protected final View inflateView(int resource, ViewGroup root) {
		View view = _inflater.inflate(resource, root);
		bindView(view);
		return view;
	}

	/**
	 * 框架自带view注入在此绑定<br/>
	 * PS: 此方法会在{@link #inflateView(int, ViewGroup)}方法获取view 后被调用
	 * 
	 * @param view
	 *            {@link #create(LayoutInflater)} 方法返回的view
	 */
	private final void bindView(View view) {
		DZViewProcessor.bind(view, this);
	}

	/**
	 * view 进入展示动画
	 */
	public final void enterViewAnimation() {
		Animation animation = getEnterViewAnimation();
		startViewAnimation(animation);
	}

	/**
	 * view 退出动画
	 */
	public final void exitViewAnimation() {
		Animation animation = getExitViewAnimation();
		startViewAnimation(animation);
	}

	public Animation getEnterViewAnimation() {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
				1.0f, DZApplication.getApp().getWorkSpaceWidth(), 0.0f);
		scaleAnimation.setDuration(500);

		TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f,
				DZApplication.getApp().getWorkSpaceHeight(), 0.0f);
		animation.setDuration(500);

		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(animation);
		return animationSet;
	}

	public Animation getExitViewAnimation() {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
				0.0f, DZApplication.getApp().getWorkSpaceWidth(), 0.0f);
		scaleAnimation.setDuration(500);
		TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f,
				DZApplication.getApp().getWorkSpaceHeight());
		animation.setDuration(500);
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(animation);
		return animationSet;
	}

	/**
	 * 执行动画
	 * 
	 * @param animation
	 */
	private final void startViewAnimation(Animation animation) {
		if (_view != null) {
			_view.startAnimation(animation);
		}
	}

	/**
	 * 返回上一个页面
	 */
	public void preViewLastPage(boolean animation) {
		_parent.preViewLast(animation);
	}

	/**
	 * 返回上一个页面
	 */
	public void preViewLastPage() {
		_parent.preViewLast(false);
	}

	/*
	 * 关闭当前页面 PS: 如果关闭后跳转页面，请先关闭后跳转。
	 */
	public void finishCurrentView() {
		_parent.finishView();
	}

	protected void startActivity(Intent intent) {
		_parent.startActivity(intent);
	}

	protected void startActivityforResult(Intent intent, int requestCode) {
		_parent.startActivityForResult(intent, requestCode);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	public final View getTopView() {
		return _parent.getTopView();
	}

	protected final View getBottomView() {
		return _parent.getBottomView();
	}
	
	public TextView getUnreadView() {
		return _parent.getUnreadView();
	}

	public final Context getContext() {
		return _parent;
	}

	public final DZBaseViewActivity getParent() {
		return _parent;
	}

	/**
	 * 获得当前View PS: 必须在create方法完成后调用
	 * 
	 * @return
	 */
	protected final View getCurrentView() {
		return _view;
	}

	protected boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	protected final boolean doOnTouchEvent(MotionEvent event) {
		return _parent.onTouchEvent(event);
	}

	/**
	 * 创建上下文菜单
	 * 
	 * @param menu
	 * @param v
	 * @param menuInfo
	 */
	protected void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

	}

	/**
	 * 响应上下问菜单
	 * 
	 * @param item
	 * @return
	 */
	public boolean onContextItemSelected(MenuItem item) {
		return false;
	}

	/**
	 * 创建选项菜单
	 * 
	 * @param menu
	 * @return
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	/**
	 * 改变选项菜单
	 * 
	 * @param menu
	 * @return
	 */
	public boolean onPrepareOptionsMenu(Menu menu) {
		return false;
	}

	/**
	 * 选项菜单事件处理
	 * 
	 * @param item
	 * @return
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	/**
	 * 打开选项菜
	 * 
	 * @param
	 * @return
	 */
	protected final void openOptionsMenu() {
		_parent.openOptionsMenu();
	}

	protected final void openContextMenu(View view) {
		_parent.openContextMenu(view);
	}

	protected final MenuInflater getMenuInflater() {
		return _parent.getMenuInflater();
	}

	protected final void registerForContextMenu(View view) {
		_parent.registerForContextMenu(view);
	}

	public final void openLoading() {
		_parent.openLoading();
	}

	public final void closeLoading() {
		_parent.closeLoading();
	}

	public final boolean isLoading() {
		return _parent.isLoading();
	}

	protected final void resetAllButtomIcon() {
		_parent.resetAllButtomIcon();
	}

	public final String getString(int resId) {
		return _parent.getString(resId);
	}

	protected final String[] getStringArray(int resid) {
		return getResources().getStringArray(resid);
	}

	public final String getString(String resName) {
		int id = getResources().getIdentifier(resName, "string",
				_parent.getPackageName());
		return _parent.getString(id);
	}

	protected final int getStringID(String resName) {
		int id = getResources().getIdentifier(resName, "string",
				_parent.getPackageName());
		return id;
	}

	public final Drawable getDrawableById(int id) {
		return _parent.getDrawableById(id);
	}

	public final int getColorById(int resId) {
		return _parent.getColorById(resId);
	}

	public final Resources getResources() {
		return _parent.getResources();
	}

	public final int getIdentifier(String name, String defType,
			String defPackage) {
		return getResources().getIdentifier(name, defType, defPackage);
	}

	public final String getPackageName() {
		return _parent.getPackageName();
	}

	public final int getDimensionPixelOffset(int resId) {
		return getResources().getDimensionPixelOffset(resId);
	}

	public final float getDimensionById(int id) {
		return _parent.getDimensionById(id);
	}

	protected final LayoutInflater getInflater() {
		return _inflater;
	}

	protected final void finish() {
		_parent.finish();
	}
}
