package com.example.smartnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

//十二个子页签的ViewPager
public class MenuDetailViewPager extends ViewPager {

	public MenuDetailViewPager(Context context) {
		super(context);
	}

	public MenuDetailViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (getCurrentItem() != 0) {
			// 当页面不是第一页时请求父控件及祖先控件不拦截触摸事件
			getParent().requestDisallowInterceptTouchEvent(true);
		} else {
			// 当页面是第一页时则拦截，让侧边栏可以滑动出来
			getParent().requestDisallowInterceptTouchEvent(false);
		}
		return super.dispatchTouchEvent(ev);
	}
}
