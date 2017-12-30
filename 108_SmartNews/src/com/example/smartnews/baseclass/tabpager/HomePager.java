package com.example.smartnews.baseclass.tabpager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.smartnews.baseclass.BaseTabPager;

public class HomePager extends BaseTabPager {

	public HomePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initView() {
		super.initView();
	}

	@Override
	public void initData() {
		tv_title.setText("智慧北京");
		imb_menu.setVisibility(View.INVISIBLE);
		setSlidingMenuEnable(false);

		TextView text = new TextView(mActivity);
		text.setText("首页");
		text.setTextColor(Color.RED);
		text.setGravity(Gravity.CENTER);
		text.setTextSize(25);
		fl_content.addView(text);
	}
}
