package com.example.smartnews.baseclass.menudetailpager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.smartnews.baseclass.BaseMenuDetailPager;

//组图详情页
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

	public PhotoMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView text = new TextView(mActivity);
		text.setText("菜单详情页-组图");
		text.setTextColor(Color.RED);
		text.setGravity(Gravity.CENTER);
		text.setTextSize(25);
		return text;
	}

}
