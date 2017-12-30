package com.example.smartnews.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.smartnews.R;
import com.example.smartnews.config.KeyConfig;
import com.example.smartnews.utils.PrefUtils;

public class GuideActivity extends Activity {
	private ViewPager vp_guide;
	private Button btn_start;
	private LinearLayout ll_dot_group;

	private static final int[] mImagesId = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	private List<ImageView> mImageViewList;
	private int distance;// 两个圆点间的距离
	private View view_red;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);

		vp_guide = (ViewPager) findViewById(R.id.vp_guide);

		btn_start = (Button) findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PrefUtils.setBoolean(GuideActivity.this,
						KeyConfig.MSPREF_IS_FIRST_LAUNCH, false);
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});

		ll_dot_group = (LinearLayout) findViewById(R.id.ll_dot_group);
		view_red = (View) findViewById(R.id.view_red);

		mImageViewList = new ArrayList<ImageView>();
		for (int i = 0; i < mImagesId.length; i++) {

			// 初始化背景图片
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImagesId[i]);
			mImageViewList.add(image);

			// 初始化圆点
			View dot = new View(this);
			dot.setBackgroundResource(R.drawable.shape_point_gray);
			// LayoutParams用于设置view的长宽等属性
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					12, 12);
			if (i > 0) {
				params.leftMargin = 8;
			}
			dot.setLayoutParams(params);
			ll_dot_group.addView(dot);
		}

		// 为了获取宽高，利用控件布局完成后的回调
		// 利用android工具hierarchyviewer.bat查看视图树以及每个视图的onMeasue，onLayout，onDraw执行时间
		// 还可以查看其它app的界面布局概况
		ll_dot_group.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						// 调试发现，此方法会多次被调用，
						// 为了避免重复执行，则在执行过一次后去除监听
						Log.e("GuideActivity", "Layout 结束");

						distance = ll_dot_group.getChildAt(1).getLeft()
								- ll_dot_group.getChildAt(0).getLeft();
						ll_dot_group.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						
					}
				});
	}

	private void initData() {
		MyAdapter adapter = new MyAdapter();
		vp_guide.setAdapter(adapter);
		MyOnPageChangeListener listener = new MyOnPageChangeListener();
		vp_guide.setOnPageChangeListener(listener);
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mImageViewList.get(position));
			return mImageViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			Log.e("GuideActivity", "当前位置： " + position + "；百分比："
					+ positionOffset + "；移动距离" + positionOffsetPixels);
			int len = (int) (distance * positionOffset) + position * distance;
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_red
					.getLayoutParams();
			params.leftMargin = len;

			view_red.setLayoutParams(params);
		}

		@Override
		public void onPageSelected(int position) {
			if (position == mImageViewList.size() - 1) {
				btn_start.setVisibility(View.VISIBLE);
			} else {
				btn_start.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}

	}
}
