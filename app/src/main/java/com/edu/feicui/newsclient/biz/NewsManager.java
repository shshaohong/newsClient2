package com.edu.feicui.newsclient.biz;

import android.content.Context;

import com.android.volley.Response;
import com.edu.feicui.newsclient.utils.CommonUtils;
import com.edu.feicui.newsclient.utils.Url;
import com.edu.feicui.newsclient.utils.VolleyHttp;

/**
 * Created by Administrator on 2016-11-28.
 */

public class NewsManager {
    //下拉刷新
    public static final int MODE_PULL_REFRESH = 1;
    //上拉加载
    public static final int MODE_LOAD_MORE = 2;

    //发送请求获取新闻分类
    public static void getNewsType(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String imei = CommonUtils.getIMEI(context);
        VolleyHttp http = new VolleyHttp(context);
        http.sendStringRequest(Url.GET_NEWS_TYPE + "?ver=0&imei=" + imei,listener,errorListener);
    }

    //发送请求获取新闻列表
    public static void getNewsList(Context context,int subid,int dir,int nid,Response.Listener<String> listener,
                                   Response.ErrorListener errorListener){
        String stamp = CommonUtils.getCurrentDate();
        VolleyHttp http = new VolleyHttp(context);
        http.sendStringRequest(Url.GET_NEWS + "?ver=0&subid=" + subid + "&dir=" + dir +
                "&nid=" + nid + "&stamp=" + stamp + "&cnt=20", listener, errorListener);
    }
    //获取评论列表
    public static void  getComment(Context context, int nid, int cid,int dir, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String stamp = CommonUtils.getCurrentDate();
        VolleyHttp http = new VolleyHttp(context);
        http.sendStringRequest(Url.COMMENTS + "?ver=0&nid="+nid+"&type=1&stamp="+stamp
                +"&cid="+cid+"&dir="+dir+"&cnt=20",listener,errorListener);
    }

}
