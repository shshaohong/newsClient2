package com.edu.feicui.newsclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends Activity{
	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		
		imageView = (ImageView) findViewById(R.id.iv_logo);
		AlphaAnimation an = new AlphaAnimation(0.9f, 1.0f);
		an.setDuration(3000);
		an.setFillAfter(true);
		imageView.setAnimation(an);
		an.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(LogoActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
