package com.edu.feicui.newsclient;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

import com.edu.feicui.newsclient.adapter.LeadPagerAdapter;

public class LeadActivity extends Activity {
	private ViewPager viewPager;
	private ImageView[] imageViews = new ImageView[4];
	
	private int[] array = {
			R.mipmap.welcome,R.mipmap.wy,R.mipmap.bd,R.mipmap.bd
	};
	private List<View> listView = new ArrayList<View>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        SharedPreferences sp = getSharedPreferences("lead", Context.MODE_PRIVATE);
        boolean isFristRun = sp.getBoolean("isFristRun", true);
        if(isFristRun){
        	serct();
        	initView();
            initViewPager();
        }else{
        	toLogo();
        }
    }
    
	private void serct() {
		SharedPreferences sp = getSharedPreferences("lead", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("isFristRun", false);
		editor.commit();
	}

	private void initView() {
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
        imageViews[0] = (ImageView) findViewById(R.id.imageView1);
        imageViews[1] = (ImageView) findViewById(R.id.imageView2);
        imageViews[2] = (ImageView) findViewById(R.id.imageView3);
        imageViews[3] = (ImageView) findViewById(R.id.imageView4);
        
//        imageViews[0].setAlpha(255);
	}


	private void toLogo() {
		Intent intent = new Intent(LeadActivity.this,LogoActivity.class);
		startActivity(intent);
		finish();
	}


	private void initViewPager() {
		for(int i = 0;i < imageViews.length;i++){
			ImageView imageView = (ImageView) getLayoutInflater().inflate(
					R.layout.activity_lead_image, null);
			imageView.setImageResource(array[i]);
			listView.add(imageView);
			
		}
		LeadPagerAdapter leadAdapter = new LeadPagerAdapter(listView);
		//LeadPagerAdapter leadAdapter = new LeadPagerAdapter(listView);
		viewPager.setAdapter(leadAdapter);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				for(int i = 0;i < array.length;i++){
					imageViews[i].setAlpha(128);
					
				}
				imageViews[arg0].setAlpha(255);
				if(viewPager.getCurrentItem() == 3){
					toLogo();
					finish();
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}





    
}
