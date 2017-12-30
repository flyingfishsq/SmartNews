package com.example.smartnews.baseclass.menudetailpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartnews.R;
import com.example.smartnews.activity.MainActivity;
import com.example.smartnews.baseclass.BaseMenuDetailPager;
import com.example.smartnews.baseclass.tabdetailpager.TabDetailPager;
import com.example.smartnews.bean.NewsData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

public class NewsMenuDetailPager extends BaseMenuDetailPager {

	private ViewPager vp_news_detail;

	private List<TabDetailPager> mPagerList;

	// 十二个页签数据
	private List<NewsTabData> mNewsTabData;

	// ViewPager指针
	private TabPageIndicator tab_pager_indicator;

	public NewsMenuDetailPager(Activity activity, List<NewsTabData> children) {
		super(activity);
		mNewsTabData = children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.layout_news_menu_detail,
				null);

		ViewUtils.inject(this, view);

		vp_news_detail = (ViewPager) view.findViewById(R.id.vp_news_detail);

		tab_pager_indicator = (TabPageIndicator) view
				.findViewById(R.id.tab_pager_indicator);

		// 当ViewPager和Indicator绑定后，滑动监听要设置给Indicator，而不是ViewPager
		// 这个特点要注意，因为设置给ViewPager无效
		// 设置滑动监听
		tab_pager_indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				MainActivity mainUI = (MainActivity) mActivity;
				SlidingMenu slidingMenu = mainUI.getSlidingMenu();
				if (position == 0) {
					slidingMenu
							.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				} else {
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		return view;
	}

	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();
		// 初始化页签数据
		for (int i = 0; i < mNewsTabData.size(); i++) {
			TabDetailPager tabDetailPager = new TabDetailPager(mActivity,
					mNewsTabData.get(i));
			mPagerList.add(tabDetailPager);
		}
		vp_news_detail.setAdapter(new MenuDetailAdapter());
		tab_pager_indicator.setViewPager(vp_news_detail);
	}

	class MenuDetailAdapter extends PagerAdapter {

		// 重写此方法，返回页面标题，用于ViewPagerIndicator的页签显示
		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTabData.get(position).title;
		}

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mPagerList.get(position);
			container.addView(pager.getRootView());
			pager.initData();
			return pager.getRootView();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	// 利用xUtils给ImageButton加上监听事件
	// 也可以在布局文件的onClick属性中直接指定触发的方法
	@OnClick(R.id.ib_next)
	public void nextPager(View view) {
		int currentItem = vp_news_detail.getCurrentItem();
		if (currentItem < (mPagerList.size() - 1)) {
			vp_news_detail.setCurrentItem(++currentItem);
		}
	}
}
