




package com.diary.activities;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import app.diary.R;


public class SplashActivity extends Activity {
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private LinearLayout animLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		init();
	}


	private void init() {
		
		animLayout = (LinearLayout) this.findViewById(R.id.animLayout);
		leftLayout = (LinearLayout) this.findViewById(R.id.leftLayout);
		rightLayout = (LinearLayout) this.findViewById(R.id.rightLayout);

		animLayout.setBackgroundResource(R.drawable.diary_view_bg);
		Animation leftOutAnimation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.translate_left);
		Animation rightOutAnimation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.translate_right);
		leftLayout.setAnimation(leftOutAnimation);
		rightLayout.setAnimation(rightOutAnimation);
		
		
//		AnimationSet animationSet = new AnimationSet(true);
//		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//		alphaAnimation.setDuration(3000);
//		animationSet.addAnimation(alphaAnimation);
//		animLayout = (LinearLayout) this.findViewById(R.id.animLayout);
//		animLayout.startAnimation(animationSet);
		
		
		leftOutAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				leftLayout.setVisibility(View.GONE);
				rightLayout.setVisibility(View.GONE);
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
				SplashActivity.this.finish();
			}
		});
	}
}
