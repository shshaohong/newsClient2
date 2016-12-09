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

    //注册接口地址
    public static final String REGISTER = BASE_URL + "/user_register";
    //忘记密码
    public static final String FORGET_PASSWORD = BASE_URL + "/user_forgetpass";
    //登陆
    public static final String LOGIN = BASE_URL + "/user_login";
    //获取评论列表
    public static final String COMMENTS = BASE_URL + "/cmt_list";
    //发布评论
    public static final String GET_COMMENT = BASE_URL + "/cmt_commit";
    //用户中心接口地址
    public static final String USER_HOME = BASE_URL + "/user_home";

    //上传头像
    public static final String USER_IMAGE = BASE_URL + "/user_image";

    //版本更新接口
    public static final String UPDATE_VERSION = BASE_URL + "/update";
}
