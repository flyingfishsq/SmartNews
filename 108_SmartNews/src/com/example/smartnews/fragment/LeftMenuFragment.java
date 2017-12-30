package com.example.smartnews.fragment;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartnews.R;
import com.example.smartnews.activity.MainActivity;
import com.example.smartnews.baseclass.tabpager.NewsCenterPager;
import com.example.smartnews.bean.NewsData;
import com.example.smartnews.bean.NewsData.NewsMenuData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LeftMenuFragment extends BaseFragment {

	@ViewInject(R.id.lv_list)
	private ListView lv_list;
	private ArrayList<NewsMenuData> mMenuList;
	private int mCurrentMenu;// 当前被点击的左边栏菜单项
	private MenuAdapter mMenuAdapter;

	@Override
	public View initView() {
		View view = View.inflate(activity, R.layout.fragment_left_menu, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		lv_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentMenu = position;
				mMenuAdapter.notifyDataSetChanged();// 数据刷新就会再调getView方法来更新UI

				setCurrentMenuDetailPager(position);

				toggleSlidingMenu();
			}
		});
	}

	// 切换（开关）侧边栏
	protected void toggleSlidingMenu() {
		MainActivity mainUI = (MainActivity) activity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();// 切换状态，显示时隐藏
	}

	protected void setCurrentMenuDetailPager(int position) {
		MainActivity mainUI = (MainActivity) activity;
		ContentFragment fragment = mainUI.getContentFragment();
		NewsCenterPager pager = fragment.getNewsCenterPager();
		pager.setCurrentMenuDetailPager(position);
	}

	class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mMenuList.size();
		}

		@Override
		public NewsMenuData getItem(int position) {
			return mMenuList.get(position);
		}// 注意这里getItem的返回值以及下面代码对这个方法的使用

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder viewHolder;
			if (convertView == null) {
				view = View.inflate(activity, R.layout.item_menu_list, null);
				viewHolder = new ViewHolder();
				viewHolder.mTv_title = (TextView) view
						.findViewById(R.id.tv_title);
				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}

			NewsMenuData newsMenuData = getItem(position);
			viewHolder.mTv_title.setText(newsMenuData.title);

			// notifyDataSetChanged后刷新UI
			if (mCurrentMenu == position) {
				viewHolder.mTv_title.setEnabled(true);
			} else {
				viewHolder.mTv_title.setEnabled(false);
			}

			return view;
		}

		class ViewHolder {
			TextView mTv_title;
		}

	}

	// 将网络数据设置到左边栏中
	public void setMenuData(NewsData data) {
		mMenuList = data.data;
		mMenuAdapter = new MenuAdapter();
		lv_list.setAdapter(mMenuAdapter);
	}

}
