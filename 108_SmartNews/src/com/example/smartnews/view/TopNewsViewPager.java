package com.example.smartnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TopNewsViewPager extends ViewPager {

	// 触摸事件时按下的位置
	private int startX;
	private int startY;

	public TopNewsViewPager(Context context) {
		super(context);
	}

	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 用户右滑，而且已在第一个页面，父控件则拦截 用户左滑，而且已在最后一个页面，父控件则拦截 用户上下滑动，父控件拦截，因为下面是ListView
	 **/
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);// 刚按下的时候，不要拦截，为了保证ACTION_MOVE调用

			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getRawX();
			int endY = (int) ev.getRawY();

			// 判断当前是水平滑动还是竖直滑动
			if (Math.abs(endX - startX) > Math.abs(endY - startY)) {
				if (endX - startX > 0) {
					// 表示向右滑动，如果是第一个页面，要拦截
					if (getCurrentItem() == 0) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}// 不再做不拦截的条件处理是因为down操作里每次都会处理
				} else {
					// 表示向左滑动，如果是最后一个页面，要拦截
					if (getCurrentItem() == (getAdapter().getCount() - 1)) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
			} else {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}
}
