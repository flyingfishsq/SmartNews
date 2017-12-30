package com.example.smartnews.baseclass.tabpager;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.Toast;

import com.example.smartnews.activity.MainActivity;
import com.example.smartnews.baseclass.BaseMenuDetailPager;
import com.example.smartnews.baseclass.BaseTabPager;
import com.example.smartnews.baseclass.menudetailpager.InteractMenuDetailPager;
import com.example.smartnews.baseclass.menudetailpager.NewsMenuDetailPager;
import com.example.smartnews.baseclass.menudetailpager.PhotoMenuDetailPager;
import com.example.smartnews.baseclass.menudetailpager.TopicMenuDetailPager;
import com.example.smartnews.bean.NewsData;
import com.example.smartnews.bean.NewsData.NewsMenuData;
import com.example.smartnews.config.KeyConfig;
import com.example.smartnews.fragment.LeftMenuFragment;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NewsCenterPager extends BaseTabPager {

	private ArrayList<BaseMenuDetailPager> mPagers; // 四个菜单详情页的集合
	private NewsData mNewsData;

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initView() {
		super.initView();
	}

	@Override
	public void initData() {
		tv_title.setText("新闻");
		setSlidingMenuEnable(true);

		getDataFromServer();
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();

		// 获取数据通常用get请求方法，RequestCallBack<String>表示回传的json，实质上是个String
		utils.send(HttpMethod.GET, KeyConfig.CATEGORIES_URL,
				new RequestCallBack<String>() {

					// 这两个回调方法都是在主线程里执行
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = (String) responseInfo.result;
						// System.out.println("返回结果：" + result);

						parseData(result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
						error.printStackTrace();
					}
				});
	}

	// 解析网络数据，为了将数据传给左边栏，则通过其依附的MainActivity
	protected void parseData(String result) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);

		// System.out.println("解析结果：" + data);

		// 设置左边栏
		MainActivity mainUI = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUI.getLeftMenuFragment();
		leftMenuFragment.setMenuData(mNewsData);

		// 准备四个菜单详情页
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,
				mNewsData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new PhotoMenuDetailPager(mActivity));
		mPagers.add(new InteractMenuDetailPager(mActivity));

		setCurrentMenuDetailPager(0);// 强制设置左边栏选中的是第一项
	}

	// 设置当前菜单详情页
	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDetailPager pager = mPagers.get(position);
		fl_content.removeAllViews();
		fl_content.addView(pager.getRootView());

		// 设置当前页的标题
		NewsMenuData newsMenuData = mNewsData.data.get(position);
		tv_title.setText(newsMenuData.title);

		pager.initData();
	}
}
