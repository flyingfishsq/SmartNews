package com.example.smartnews.baseclass;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartnews.R;
import com.example.smartnews.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class BaseTabPager {

	protected Activity mActivity;
	protected View mRootView;

	protected FrameLayout fl_content;
	protected TextView tv_title;
	protected ImageButton imb_menu;// 菜单按钮

	public BaseTabPager(Activity activity) {
		mActivity = activity;
		initView();
	}

	public void initView() {
		mRootView = View.inflate(mActivity, R.layout.layout_base_pager, null);
		tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
		fl_content = (FrameLayout) mRootView.findViewById(R.id.fl_content);
		imb_menu = (ImageButton) mRootView.findViewById(R.id.imb_menu);
		imb_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleSlidingMenu();
			}
		});
	}

	public void initData() {

	}

	public View getRootView() {
		return mRootView;
	}

	// 设置在某个界面是否可滑动出现SlidingMenu
	protected void setSlidingMenuEnable(boolean enable) {
		// 通过对activity的观察，知道这个activity就是mainActivity
		MainActivity act = (MainActivity) mActivity;
		SlidingMenu slidingMenu = act.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	
	protected void toggleSlidingMenu() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();//切换状态，显示时隐藏
	}
}
