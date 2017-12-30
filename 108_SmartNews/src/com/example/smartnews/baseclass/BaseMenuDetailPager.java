package com.example.smartnews.baseclass;

import android.app.Activity;
import android.view.View;

//菜单详情页
public abstract class BaseMenuDetailPager {

	protected Activity mActivity;
	protected View mRootView;
	
	public BaseMenuDetailPager(Activity activity) {
		mActivity = activity;
		mRootView = initView();
	}
	
	public abstract View initView();
	
	public void initData() {

	}
	
	public View getRootView() {
		return mRootView;
	}
}
