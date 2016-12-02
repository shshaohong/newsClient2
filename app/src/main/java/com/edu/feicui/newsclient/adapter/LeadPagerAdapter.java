package com.edu.feicui.newsclient.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016-11-23.
 */

public class LeadPagerAdapter extends PagerAdapter {
    private List<View> list;

    public LeadPagerAdapter(List<View> list) {
        super();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }



    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = list.get(position);
        container.removeView(view);//����ȥʱ����ͼƬ
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position);
        container.addView(view);//������ʱ����ͼƬ
        return view;
    }


}
