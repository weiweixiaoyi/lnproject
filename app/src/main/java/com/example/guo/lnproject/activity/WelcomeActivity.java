package com.example.guo.lnproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.guo.lnproject.R;


public class WelcomeActivity extends Activity
{
	
	private LinearLayout launch;
	private Animation fadeIn;
	private Animation fadeInScale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 取消标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//必须放在中间
		setContentView(R.layout.activity_welcome);
		// 取消状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		init();
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		fadeIn.setAnimationListener(new AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				launch.setAnimation(fadeInScale);
			}
		});
		fadeInScale.setAnimationListener(new AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				//动画完成后跳转到主界面
				Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		launch = (LinearLayout) findViewById(R.id.launch);
		fadeIn = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in);
		fadeIn.setDuration(500);
		fadeIn.setFillAfter(true);
		fadeInScale = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in_scale);
		fadeInScale.setDuration(1000);//3000
		fadeInScale.setFillAfter(true);
		launch.startAnimation(fadeIn);
	}

	//屏蔽返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
