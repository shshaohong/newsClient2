package com.edu.feicui.newsclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.activity.ShowNewsActivity;
import com.edu.feicui.newsclient.adapter.NewsAdapter;
import com.edu.feicui.newsclient.adapter.NewsTypeAdapter;
import com.edu.feicui.newsclient.base.BaseActivity;
import com.edu.feicui.newsclient.biz.NewsDBManager;
import com.edu.feicui.newsclient.biz.NewsManager;
import com.edu.feicui.newsclient.entity.News;
import com.edu.feicui.newsclient.entity.SubType;
import com.edu.feicui.newsclient.parser.NewsParser;
import com.edu.feicui.newsclient.utils.CommonUtils;
import com.edu.feicui.newsclient.view.HorizontalListView;
import com.edu.feicui.newsclient.xlistview.XListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-11-28.
 */

public class MainFragment extends Fragment {
    @BindView(R.id.hl_type)
    HorizontalListView hlType;
    @BindView(R.id.type_move_right)
    ImageView ivTypeMove;
    @BindView(R.id.xl_listView)
    XListView listView;

    private NewsDBManager newsDBManager;
    private NewsTypeAdapter newsTypeAdapter;
    private NewsAdapter newsAdapter;


    //分类编号
    private int subid = 1;
    //加载数据模式
    private int refreshMode = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);

        newsTypeAdapter = new NewsTypeAdapter(getActivity());
        newsDBManager = new NewsDBManager(getActivity());
        hlType.setOnItemClickListener(typeItemClickListener);
        hlType.setAdapter(newsTypeAdapter);

        loadNewsType();
        newsAdapter = new NewsAdapter(getActivity());
        //启用上拉加载
        listView.setPullLoadEnable(true);

        //启动下拉刷新
        listView.setPullRefreshEnable(true);

        listView.setXListViewListener(listViewListener);
        listView.setAdapter(newsAdapter);
        refreshMode = NewsManager.MODE_PULL_REFRESH;

        listView.setOnItemClickListener(newsItemClickListener);

        loadNextNews(true);

        //显示加载进度对话框
        ((BaseActivity)getActivity()).showDialog(null,false);
        return view;
    }


    private AdapterView.OnItemClickListener typeItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
            SubType subType = (SubType) newsTypeAdapter.getItem(i);
            subid = subType.getSubid();

            newsTypeAdapter.setCurrentPosition(i);
            newsTypeAdapter.notifyDataSetChanged();
            ((BaseActivity)getActivity()).showDialog(null,false);
            refreshMode = NewsManager.MODE_PULL_REFRESH;
            loadNextNews(true);
        }
    };

    private AdapterView.OnItemClickListener newsItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            News news = (News) parent.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("news",news);
            BaseActivity activity = (BaseActivity) getActivity();
            activity.startActivity(ShowNewsActivity.class,bundle);
        }
    };

    private XListView.IXListViewListener listViewListener = new XListView.IXListViewListener(){
        //下拉刷新会回调该方法
        @Override
        public void onRefresh() {
            refreshMode = NewsManager.MODE_PULL_REFRESH;
            loadNextNews(false);
        }

        //上拉加载会回调该方法
        @Override
        public void onLoadMore() {
            refreshMode = NewsManager.MODE_LOAD_MORE;
            loadPrevNews();
        }
    };
    //加载新数据
    private void loadNextNews(boolean isNewType){
        int nid = 1;
        //如果isNewType为true，则是第一次加载数据，此时nid=1，如果不是第一次加载数据，则需要获取列表中第一天数据的新闻编号

        if(!isNewType){
            if(newsAdapter.getData().size() > 0){
                nid = newsAdapter.getData().get(0).getNid();
            }
        }
        if(CommonUtils.isNetConnect(getActivity())){
            NewsManager.getNewsList(getActivity(),subid,refreshMode,nid,newsListener,errorListener);

        }else {
            //从缓存中获取新闻数据
        }
    }
    //加载以前的数据
    private void loadPrevNews(){
        //没有数据，不需要上拉加载
        if(newsAdapter.getData().size() == 0){
            return;
        }
        int lastIndex = newsAdapter.getData().size() - 1;
        int nid = newsAdapter.getData().get(lastIndex).getNid();
        if(CommonUtils.isNetConnect(getActivity())){
            NewsManager.getNewsList(getActivity(),subid,refreshMode,nid,newsListener,errorListener);

        }else {
            //从缓存中获取新闻数据
        }

    }

    //加载新闻分类
    private void loadNewsType(){
        //先判断数据库中是否有缓存的数据，如果有，则使用缓存的数据，如果没有，则判断是否有网络，有网络，则去服务器端加载数据
        if(newsDBManager.getNewsSubType().size() == 0){
            if(CommonUtils.isNetConnect(getActivity())){
                NewsManager.getNewsType(getActivity(),newsTypeListener, errorListener);
            }
        }else{
            List<SubType> list = newsDBManager.getNewsSubType();
            newsTypeAdapter.appendDataToAdapter(list, true);
            newsTypeAdapter.notifyDataSetChanged();
        }
    }
    //获取新闻分类成功之后回调的接口
    private Response.Listener<String> newsTypeListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String json) {
            List<SubType> list = NewsParser.parseNewsType(json);

            Collections.sort(list, new Comparator<SubType>() {
                        @Override
                        public int compare(SubType subType, SubType t1) {
                            return subType.getSubid() - t1.getSubid();
                        }
                    });
            newsTypeAdapter.appendDataToAdapter(list, true);
            newsTypeAdapter.notifyDataSetChanged();
            //将新闻分类数据进行缓存
//            newsDBManager.saveNewsType(list);
        }
    };

    private Response.Listener<String> newsListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String json) {
            List<News> list = NewsParser.parseNews(json);
            boolean isClear = refreshMode == NewsManager.MODE_PULL_REFRESH ? true : false;
            newsAdapter.appendDataToAdapter(list,isClear);
            newsAdapter.notifyDataSetChanged();

            listView.stopLoadMore();
            listView.stopRefresh();
            listView.setRefreshTime(CommonUtils.getCurrentDate());

            ((BaseActivity)getActivity()).cancelDialog();

        }
    };

    //获取新闻分类失败之后回调的方法
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

            Toast.makeText(getActivity(),"加载数据失败",Toast.LENGTH_SHORT).show();
            //取消进度对话框
            listView.stopLoadMore();
            listView.stopRefresh();
        }
    };
}
