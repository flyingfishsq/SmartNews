package com.example.smartnews.utils;

import com.example.smartnews.config.KeyConfig;

public class UrlTransUtils {

	/**
	 * 主机地址转换方法
	 * 把接口返回的服务器地址"http://10.0.2.2:8080/zhbj"替换成本机服务器地址
	 * "http://192.168.99.200:8080/zhbj"
	 **/
	public static String hostUrlTrans(String oldString) {

		String newString = oldString.replace("http://10.0.2.2:8080/zhbj",
				KeyConfig.SERVER_URL);
		return newString;
	}
}
