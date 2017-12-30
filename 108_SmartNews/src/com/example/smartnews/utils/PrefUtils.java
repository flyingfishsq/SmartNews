package com.example.smartnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.smartnews.config.KeyConfig;

public class PrefUtils {
	public static boolean getBoolean(Context ctx, String key,
			boolean defaultValue) {
		SharedPreferences msPref = ctx.getSharedPreferences(
				KeyConfig.MSPREF_CONFIG, Context.MODE_PRIVATE);
		return msPref.getBoolean(key, defaultValue);
	}

	public static void setBoolean(Context ctx, String key, boolean value) {
		SharedPreferences msPre = ctx.getSharedPreferences(
				KeyConfig.MSPREF_CONFIG, Context.MODE_PRIVATE);
		msPre.edit().putBoolean(key, value).commit();
	}
}
