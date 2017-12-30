package com.example.smartnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

//不支持左右滑动操作的ViewPager
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}
	
	//重写触摸事件，什么都不做
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
	
	//表示事件是否拦截，返回false表示不拦截事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

}
