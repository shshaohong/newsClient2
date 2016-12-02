package com.edu.feicui.newsclient.biz;

import android.content.Context;

import com.android.volley.Response;
import com.edu.feicui.newsclient.utils.Url;
import com.edu.feicui.newsclient.utils.VolleyHttp;

/**
 * Created by Administrator on 2016-12-1.
 */

public class CommentManager {

    //发送请求评论数量
    public static void getCommentNum(Context context, int nid, Response.Listener listener,Response.ErrorListener errorListener){
        VolleyHttp volleyHttp = new VolleyHttp(context);
        volleyHttp.sendStringRequest(Url.GET_COMMENT_NUM + "?ver=0&nid=" + nid,listener,errorListener);
    }
}
