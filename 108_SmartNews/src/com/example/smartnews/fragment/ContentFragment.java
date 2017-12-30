package com.example.smartnews.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.smartnews.R;
import com.example.smartnews.baseclass.BaseTabPager;
import com.example.smartnews.baseclass.tabpager.AffairsPager;
import com.example.smartnews.baseclass.tabpager.HomePager;
import com.example.smartnews.baseclass.tabpager.NewsCenterPager;
import com.example.smartnews.baseclass.tabpager.ServicePager;
import com.example.smartnews.baseclass.tabpager.SettingPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ContentFragment extends BaseFragment {

	@ViewInject(R.id.rg_radio)
	private RadioGroup rg_radio;

	@ViewInject(R.id.vp_content)
	private ViewPager vp_content;

	private List<BaseTabPager> mPagerList;

	@Override
	public View initView() {
		View view = View.inflate(activity, R.layout.fragment_content, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		rg_radio.check(R.id.rb_home);// 为radioGroup设置默认勾选项
		mPagerList = new ArrayList<BaseTabPager>();

		mPagerList.add(new HomePager(activity));
		mPagerList.add(new NewsCenterPager(activity));
		mPagerList.add(new ServicePager(activity));
		mPagerList.add(new AffairsPager(activity));
		mPagerList.add(new SettingPager(activity));

		ContentPagerAdapter adapter = new ContentPagerAdapter();
		vp_content.setAdapter(adapter);

		rg_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					// 切换到响应页面，去掉切换页面的动画
					vp_content.setCurrentItem(0, false);
					break;
				case R.id.rb_news:
					vp_content.setCurrentItem(1, false);
					break;
				case R.id.rb_service:
					vp_content.setCurrentItem(2, false);
					break;
				case R.id.rb_govern:
					vp_content.setCurrentItem(3, false);
					break;
				case R.id.rb_setting:
					vp_content.setCurrentItem(4, false);
					break;
				default:
					break;
				}
			}
		});
		
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				BaseTabPager pager = mPagerList.get(position);
				pager.initData();
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		//如上只在页面选中时做数据加载，会造成第一个页面初始化不正确（因为此时没有页面被选中）
		//因此在这里强制调用第一个页面的加载
		mPagerList.get(0).initData();
	}

	class ContentPagerAdapter extends PagerAdapter {

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
			BaseTabPager pager = mPagerList.get(position);
			container.addView(pager.getRootView());
			
			// 在这里调用每个ViewPager的initData()方法
			// 修改bug：由于ViewPager的预加载pager的作用，
			// 在这里initData()，会造成setSlidingMenuEnable(bool)方法执行的问题：
			// 不是按照预想的打开或关闭：A页面为打开，B页面为关闭，执行加载时，
			// 会把A，B的slidingMenu拖动都打开
			// 所以不可以在这里initData()
			
			//另外，在预加载里执行initData()会去网络请求下一页面的数据，
			//而下一页面不一定会被关注，因此浪费了流量
			//因此改为在viewPager的OnPageChangeListener里调用
//			pager.initData();
			
			return pager.getRootView();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	//这里的序号是写死的，不易扩展
	public NewsCenterPager getNewsCenterPager(){
		return (NewsCenterPager) mPagerList.get(1);
	}
}
