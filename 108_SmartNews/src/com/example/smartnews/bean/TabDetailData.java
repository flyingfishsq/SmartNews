package com.example.smartnews.bean;

import java.util.ArrayList;

public class TabDetailData {

	public int retCode;

	public TabDetail data;

	public class TabDetail {
		public String title, more;
		public ArrayList<TabNewsData> news;
		public ArrayList<TopNewsData> topnews;

		@Override
		public String toString() {
			return "TabDetail [title=" + title + ", news=" + news
					+ ", topnews=" + topnews + "]";
		}

	}

	// 新闻列表
	public class TabNewsData {
		public String id, listimage, pubdate, title, type, url;

		@Override
		public String toString() {
			return "TabNewsData [title=" + title + "]";
		}

	}

	// 头条新闻
	public class TopNewsData {
		public String id, topimage, pubdate, title, type, url;

		@Override
		public String toString() {
			return "TopNewsData [title=" + title + "]";
		}

	}

	@Override
	public String toString() {
		return "TabDetailData [data=" + data + "]";
	}

}
