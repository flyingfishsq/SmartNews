package com.example.smartnews.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	// 获得Fragment将要依附的activity
	protected Activity activity; // 利于子类使用

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 此时activity已经有了，但是逻辑还没有执行完，onActivityCreated时候表示所依附的activity已经构建完成
		activity = getActivity();
	}

	// 处理fragment的布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	// 子类必须实现初始化界面的方法
	public abstract View initView();

	public abstract void initData();
}
