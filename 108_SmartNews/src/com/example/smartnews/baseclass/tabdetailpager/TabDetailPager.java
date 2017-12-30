package com.example.smartnews.baseclass.tabdetailpager;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartnews.R;
import com.example.smartnews.baseclass.BaseMenuDetailPager;
import com.example.smartnews.bean.NewsData.NewsTabData;
import com.example.smartnews.bean.TabDetailData;
import com.example.smartnews.bean.TabDetailData.TabNewsData;
import com.example.smartnews.bean.TabDetailData.TopNewsData;
import com.example.smartnews.config.KeyConfig;
import com.example.smartnews.utils.UrlTransUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

//页签详情页
public class TabDetailPager extends BaseMenuDetailPager {

	private NewsTabData mNewsTabData;
	private String mUrl;
	private TabDetailData mTabDetailData;

	@ViewInject(R.id.vp_news)
	private ViewPager vp_news;

	@ViewInject(R.id.lv_news)
	private ListView lv_news;

	@ViewInject(R.id.tv_title)
	private TextView tv_title;

	@ViewInject(R.id.cpi_indicator)
	private CirclePageIndicator cpi_indicator;

	private ArrayList<TopNewsData> mTopNewsList;// 头条新闻列表
	private ArrayList<TabNewsData> mNewsList;
	private NewsAdapter mNewsAdapter;

	public TabDetailPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		mNewsTabData = newsTabData;

		mUrl = KeyConfig.SERVER_URL + mNewsTabData.url;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.layout_tab_detail_pager,
				null);
		ViewUtils.inject(this, view);

		return view;
	}

	public void initData() {
		super.initData();
		getDataFromServer();
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = (String) responseInfo.result;
				// System.out.println("页签详情页返回结果：" + result);

				parseData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
			}
		});
	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		mTabDetailData = gson.fromJson(result, TabDetailData.class);
		// System.out.println("页签详情页解析结果：" + mTabDetailData.toString());

		mTopNewsList = mTabDetailData.data.topnews;
		tv_title.setText(mTopNewsList.get(0).title);

		TopNewsAdapter topNewsAdapter = new TopNewsAdapter();
		vp_news.setAdapter(topNewsAdapter);

		mNewsList = mTabDetailData.data.news;
		if (mNewsList != null) {
			mNewsAdapter = new NewsAdapter();
			lv_news.setAdapter(mNewsAdapter);
		}

		cpi_indicator.setViewPager(vp_news);
		cpi_indicator.onPageSelected(0);// 默认选中第一页，去除indicator的页面指示错误（指示位置记录）
		cpi_indicator.setSnap(true);
		// 加入了indicator，就把页面切换回调的逻辑设置给indicator而不再是viewPager
		cpi_indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				TopNewsData topNewsData = mTopNewsList.get(position);
				tv_title.setText(topNewsData.title);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}

		});
	}

	class TopNewsAdapter extends PagerAdapter {

		// 利用xUtils的BitmapUtils填充图片
		private BitmapUtils bitmapUtils;

		public TopNewsAdapter() {
			bitmapUtils = new BitmapUtils(mActivity);
			// 设置网络延时时的默认图片
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.topnews_item_default);
		}

		@Override
		public int getCount() {
			return mTabDetailData.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(mActivity);
			imageView.setImageResource(R.drawable.topnews_item_default);
			// 设置imageView拉伸填充控件
			imageView.setScaleType(ScaleType.FIT_XY);

			TopNewsData topNewsData = mTopNewsList.get(position);

			String topimageUrl = "";
			if (KeyConfig.isVitualDevice) {
				topimageUrl = topNewsData.topimage;
			} else {
				topimageUrl = UrlTransUtils.hostUrlTrans(topNewsData.topimage);
			}

			bitmapUtils.display(imageView, topimageUrl);

			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	class NewsAdapter extends BaseAdapter {

		private BitmapUtils utils;

		public NewsAdapter() {
			utils = new BitmapUtils(mActivity);
			//设置默认图片
			utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		}

		@Override
		public int getCount() {
			return mNewsList.size();
		}

		@Override
		public TabNewsData getItem(int position) {
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.item_news_list,
						null);
				holder = new ViewHolder();
				holder.iv_pic = (ImageView) convertView
						.findViewById(R.id.iv_pic);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_date = (TextView) convertView
						.findViewById(R.id.tv_date);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			TabNewsData item = getItem(position);
			holder.tv_title.setText(item.title);
			holder.tv_date.setText(item.pubdate);
			
			utils.display(holder.iv_pic, item.listimage);

			return convertView;
		}

		class ViewHolder {
			public ImageView iv_pic;
			public TextView tv_title;
			public TextView tv_date;
		}

	}
}
