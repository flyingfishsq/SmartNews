package com.example.smartnews.config;

public interface KeyConfig {
	
	// SharedPreference用到的存储常量
	static final String MSPREF_CONFIG = "global_config";
	static final String MSPREF_IS_FIRST_LAUNCH = "is_first_launch";// 第一次启动

	public static final boolean isVitualDevice = true;//虚拟机调试
	// URL常量
//	static final String SERVER_URL = "http://192.168.99.200:8080/zhbj";
//	static final String SERVER_URL = "http://10.0.2.2:8080/zhbj";//虚拟机调试地址
	static final String SERVER_URL = "http://172.16.9.129:8080/zhbj";//虚拟机调试地址
		
	static final String CATEGORIES_URL = SERVER_URL + "/categories.json";//获取分类信息
}
