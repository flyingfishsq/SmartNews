package com.example.smartnews.bean;

import java.util.ArrayList;

/**
 * { "data": [ { "children": [ { "id": 10007, "title": "北京", "type": 1, "url":
 * "/10007/list_1.json" }, { "id": 10006, "title": "中国", "type": 1, "url":
 * "/10006/list_1.json" }, { "id": 10008, "title": "国际", "type": 1, "url":
 * "/10008/list_1.json" }, { "id": 10010, "title": "体育", "type": 1, "url":
 * "/10010/list_1.json" }, { "id": 10091, "title": "生活", "type": 1, "url":
 * "/10091/list_1.json" }, { "id": 10012, "title": "旅游", "type": 1, "url":
 * "/10012/list_1.json" }, { "id": 10095, "title": "科技", "type": 1, "url":
 * "/10095/list_1.json" }, { "id": 10009, "title": "军事", "type": 1, "url":
 * "/10009/list_1.json" }, { "id": 10093, "title": "时尚", "type": 1, "url":
 * "/10093/list_1.json" }, { "id": 10011, "title": "财经", "type": 1, "url":
 * "/10011/list_1.json" }, { "id": 10094, "title": "育儿", "type": 1, "url":
 * "/10094/list_1.json" }, { "id": 10105, "title": "汽车", "type": 1, "url":
 * "/10105/list_1.json" } ], "id": 10000, "title": "新闻", "type": 1 }, { "id":
 * 10002, "title": "专题", "type": 10, "url": "/10006/list_1.json", "url1":
 * "/10007/list1_1.json" }, { "id": 10003, "title": "组图", "type": 2, "url":
 * "/10008/list_1.json" }, { "dayurl": "", "excurl": "", "id": 10004, "title":
 * "互动", "type": 3, "weekurl": "" } ], "extend": [ 10007, 10006, 10008, 10014,
 * 10012, 10091, 10009, 10010, 10095 ], "retcode": 200 }
 * 
 **/
// 分类信息的封装，字段名字必须与服务器返回的一致，便于Gson解析
// extend暂时不用，则不对它做解析
public class NewsData {

	public int retcode;

	public ArrayList<NewsMenuData> data;

	// 侧边栏数据对象
	public class NewsMenuData {
		public String id;// id过长，可以用String来接收int
		public String title;
		public int type;
		public String url;

		public ArrayList<NewsTabData> children;

		@Override
		public String toString() {
			return "NewsMenuData [title=" + title + ", children=" + children
					+ "]";
		}

	}

	// 新闻页面下子页签数据对象
	public class NewsTabData {
		public String id;
		public String title;
		public int type;
		public String url;

		@Override
		public String toString() {
			return "NewsTabData [title=" + title + "]";
		}

	}

	@Override
	public String toString() {
		return "NewsData [data=" + data + "]";
	}

}
