package com.edu.feicui.newsclient.utils;

/**
 * Created by Administrator on 2016-11-28.
 */

public class Url {
    public static  final String BASE_URL = "http://118.244.212.82:9092/newsClient";
    //获取新闻分类接口地址
    public static final String GET_NEWS_TYPE = BASE_URL + "/news_sort";
    //获取新闻接口地址
    public static final String GET_NEWS = BASE_URL + "/news_list";


    //获取新闻评论数量
    public static final String GET_COMMENT_NUM = BASE_URL + "/cmt_num";
}
