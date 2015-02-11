




package com.diary.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import app.diary.R;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		init();
	}

	private void init() {
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, AccessActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
		        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				
			}
		}, 2000);
	}
}
