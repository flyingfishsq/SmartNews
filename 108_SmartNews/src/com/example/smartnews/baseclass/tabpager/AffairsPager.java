package com.example.smartnews.baseclass.tabpager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.smartnews.baseclass.BaseTabPager;

public class AffairsPager extends BaseTabPager {

	public AffairsPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initView() {
		super.initView();
	}

	@Override
	public void initData() {
		tv_title.setText("政务");
		setSlidingMenuEnable(true);
		
		TextView text = new TextView(mActivity);
		text.setText("政务中心");
		text.setTextColor(Color.RED);
		text.setGravity(Gravity.CENTER);
		text.setTextSize(25);
		fl_content.addView(text);
	}
}
