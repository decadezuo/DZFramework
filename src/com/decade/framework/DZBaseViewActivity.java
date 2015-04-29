package com.decade.framework;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.decade.framework.kit.DZLog;

/**
 * @description 使用框架的Activity 必须继承此基础类，此基类负责和子View进行交互。
 * @author Decade
 * @date 2013-4-23
 * 
 */
public abstract class DZBaseViewActivity extends DZActivity {
	private DZBaseView _curryView;
	// view堆栈
	private SparseArray<DZBaseView> _allViews;
	private List<Integer> _viewsStack;
	private DZiViewFactory _viewFactory;

	public abstract ViewGroup getRootView();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_allViews = new SparseArray<DZBaseView>();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (_curryView != null) {
			_curryView.onActivityResult(requestCode, resultCode, data);
		}
	}

	final void setViewInto(DZBaseView view, Object data, boolean cacheOpen,
			boolean previousPage, boolean animation) {
		if (_curryView != null) {
			if (animation) {
				_curryView.exitViewAnimation();
			}
			_curryView.pause();
			_curryView.close();
			if (previousPage) {
				_curryView.destroy();
			}
		}
		_curryView = view;
		if (_curryView != null) {
			initTitleAndBottomView();
			_curryView.start(data);
			_curryView.addToRoot(cacheOpen);
			if (animation) {
				_curryView.enterViewAnimation();
			}
			if (!previousPage) {
				_curryView.open();
			}
			_curryView.resume();
		}
	}

	private final void initTitleAndBottomView() {
		_curryView.topView(getTopView());
		_curryView.bottomView(getBottomView());
		setTopViewVisibility(View.VISIBLE);// 初始化TopView为可见
		setBottomViewVisibility(View.VISIBLE);// 初始化ButtomView为可见
	}

	// 获取一个VIEW
	final DZBaseView getView(int viewId) {
		DZBaseView view;
		if (_allViews.indexOfKey(viewId) >= 0) {
			// 加入栈顶
			addStack(viewId);
			view = _allViews.get(viewId);
		} else {
			view = createViewFromFactory(viewId);
			if (view != null) {
				_allViews.put(viewId, view);
				// 加入栈顶
				addStack(viewId);
			}
		}
		return view;
	}

	// 获取一个view 但是不会将其Id加入_viewStack中
	private final DZBaseView getViewById(int viewId) {
		DZBaseView view;
		if (_allViews.indexOfKey(viewId) >= 0) {
			view = _allViews.get(viewId);
		} else {
			view = createViewFromFactory(viewId);
			if (view != null) {
				_allViews.put(viewId, view);
			}
		}
		return view;
	}

	private DZBaseView createViewFromFactory(int viewId) {

		if (_viewFactory != null) {
			return _viewFactory.createView(this, viewId, getRootView());
		} else {
			DZLog.e(getClass(), "View factory not initialize !");
		}
		return null;
	}

	// 获得最后一个VIEWID
	private final int getLastViewId() {
		if (_viewsStack == null) {
			_viewsStack = new ArrayList<Integer>();
		}
		int count = _viewsStack.size() - 1;
		if (count >= 0) {
			return _viewsStack.get(count);
		}
		return -1;
	}

	// 返回上一个视图
	protected final void preViewLast(boolean animation) {

		finishView();
		int viewId = getLastViewId();
		if (viewId != -1) {
			DZBaseView view = getViewById(viewId);
			setViewInto(view, null, true, true, animation);
		}
	}

	// 移除最后一个视图的id
	protected final void finishView() {
		final int view_id = getLastViewId();
		boolean find = false;
		if (_viewsStack.size() > 0) {
			_viewsStack.remove(_viewsStack.size() - 1);
		}
		if (view_id != -1) {
			int size = _viewsStack.size();
			for (int i = 0; i < size; i++) {
				if (_viewsStack.get(i) == view_id) {
					find = true;
				}
			}
			if (!find) {
				_allViews.remove(view_id);
			}
		}

	}

	private final void addStack(int viewId) {
		if (_viewsStack == null) {
			_viewsStack = new ArrayList<Integer>();
		}
		int size = _viewsStack.size();
		if (size == 0) {
			_viewsStack.add(viewId);
		} else {
			for (int i = 0; i < size; i++) {
				if (_viewsStack.get(i) == viewId) {
					// for (int j = size - 1; j >= i; j--) { //删除相同id之间的id.
					// _viewsStack.remove(j);
					// }
					_viewsStack.remove(i);
					break;
				}
			}
			_viewsStack.add(viewId);
		}

	}

	public void startPage(int viewId, Object data, boolean openCache) {
		DZBaseView view = getView(viewId);
		setViewInto(view, data, openCache, false, false);
	}

	public void startPage(int viewId, Object data, boolean openCache,
			boolean animation) {
		DZBaseView view = getView(viewId);
		setViewInto(view, data, openCache, false, animation);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (_curryView != null) {
			if (_curryView.onOptionsItemSelected(item)) {
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (_curryView != null) {
			if (_curryView.onPrepareOptionsMenu(menu)) {
				return true;
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (_curryView != null) {
			if (_curryView.onCreateOptionsMenu(menu)) {
				return true;
			}
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (_curryView != null) {
			if (_curryView.onContextItemSelected(item)) {
				return true;
			}
		}
		return super.onContextItemSelected(item);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (_curryView != null) {
			_curryView.onCreateContextMenu(menu, v, menuInfo);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (_curryView != null) {
			if (_curryView.onKeyDown(keyCode, event)) {
				return true;
			} else {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (_curryView != null) {
			if (_curryView.onKeyUp(keyCode, event)) {
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (_curryView != null) {
			if (_curryView.onTouchEvent(event)) {
				return true;
			}
		}
		return super.onTouchEvent(event);
	}

	public void resetAllButtomIcon() {

	}

	protected final void dispenseData(Object args, int code) {
		if (_allViews != null && _curryView != null) {
			if (_curryView.doingReceiveDataListener(args, code)) {
				return;
			} else {
				int size = _allViews.size();
				for (int i = 0; i < size; i++) {
					DZBaseView item = _allViews.valueAt(i);
					if (item.doingReceiveDataListener(args, code)) {
						break;
					}
				}
			}
		}
	}

	private final void closeAllView() {
		if (_allViews != null) {
			int size = _allViews.size();
			for (int i = 0; i < size; i++) {
				DZBaseView view = _allViews.valueAt(i);
				view.pause();
				view.close();
				view.destroy();
			}
			_allViews.clear();
		}

	}

	protected final DZBaseView getCurrentView() {
		return _curryView;
	}

	protected void setViewFactory(DZiViewFactory viewFactory) {
		_viewFactory = viewFactory;
	}

	@Override
	public void finish() {
		super.finish();
		closeAllView();
	}

}
