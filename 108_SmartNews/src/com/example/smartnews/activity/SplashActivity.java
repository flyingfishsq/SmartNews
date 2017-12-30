package com.example.smartnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.smartnews.R;
import com.example.smartnews.config.KeyConfig;
import com.example.smartnews.utils.PrefUtils;

public class SplashActivity extends Activity {

	private final int animDuration = 1000;
	private RelativeLayout rl_root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		rl_root = (RelativeLayout) findViewById(R.id.rl_root);

		startUpAnim();
	}

	// 启动动画
	// Animation.RELATIVE_TO_SELF表示相对于自身旋转，即旋转中心在图片自身内
	private void startUpAnim() {
		// 对图片设置多个同时展现的动画效果，要用AnimationSet
		AnimationSet set = new AnimationSet(false);

		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(animDuration);
		rotate.setFillAfter(true);
		set.addAnimation(rotate);

		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(animDuration);
		scale.setFillAfter(true);
		set.addAnimation(scale);

		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(animDuration * 2);//整个动画集的播放时间以最长的动画为准
		alpha.setFillAfter(true);
		set.addAnimation(alpha);

		MyAnimationListener myAnimationListener = new MyAnimationListener();
		set.setAnimationListener(myAnimationListener);// 设置动画的监听

		rl_root.startAnimation(set);
	}

	class MyAnimationListener implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			jumpToNextPage();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

	}

	private void jumpToNextPage() {
		boolean isFirstLaunch = PrefUtils.getBoolean(this,
				KeyConfig.MSPREF_IS_FIRST_LAUNCH, true);
		if (isFirstLaunch) {
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
		} else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		finish();
	}
}
