package com.edu.feicui.newsclient.parser;

import com.edu.feicui.newsclient.entity.BaseEntity;
import com.edu.feicui.newsclient.entity.News;
import com.edu.feicui.newsclient.entity.NewsType;
import com.edu.feicui.newsclient.entity.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2016-11-28.
 */

public class NewsParser {

    //解析新闻分类的方法
    public static List<SubType> parseNewsType(String json){

        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntity<List<NewsType>>>(){}.getType();
        BaseEntity<List<NewsType>> baseEntity = gson.fromJson(json, type);

        if(baseEntity != null){
            NewsType newsType = baseEntity.getData().get(0);
            return newsType.getSubgrp();
        }

        return null;
    }
    //解析新闻列表数据
    public static  List<News> parseNews(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntity<List<News>>>(){}.getType();
        BaseEntity<List<News>> baseEntity = gson.fromJson(json,type);

        if(baseEntity != null){

            return baseEntity.getData();
        }
        return null;
    }

}
