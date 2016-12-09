package com.edu.feicui.newsclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.adapter.CommentsAdapter;
import com.edu.feicui.newsclient.base.BaseActivity;
import com.edu.feicui.newsclient.biz.NewsManager;
import com.edu.feicui.newsclient.entity.BaseEntity;
import com.edu.feicui.newsclient.entity.Comments;
import com.edu.feicui.newsclient.entity.UserResponse;
import com.edu.feicui.newsclient.listener.FailListener;
import com.edu.feicui.newsclient.listener.SuccessListener;
import com.edu.feicui.newsclient.parser.NewsParser;
import com.edu.feicui.newsclient.utils.CommonUtils;
import com.edu.feicui.newsclient.utils.OkHttpUtils;
import com.edu.feicui.newsclient.utils.SharedPreferencesUtils;
import com.edu.feicui.newsclient.utils.Url;
import com.edu.feicui.newsclient.xlistview.XListView;

import org.w3c.dom.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016-12-1.
 */

public class CommentActivity extends BaseActivity {

    private int nid = 0;
    private int dir = 1;

    @BindView(R.id.lv_comment)
    XListView listView;
    @BindView(R.id.et_comments_in)
    EditText etCommentsIn;

    private List<Comments> list;
    private CommentsAdapter adapter;
    private int refreshMode = 1;

    private String stamp = CommonUtils.getCurrentDate();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        adapter = new CommentsAdapter(CommentActivity.this, list);
        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        nid = bundle.getInt("nid",0);
        refreshMode = NewsManager.MODE_PULL_REFRESH;

        int cid = 10;
        NewsManager.getComment(this,nid,dir,cid,listener,errorListener);
        listView.setAdapter(adapter);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(listViewListener);

        refreshMode = NewsManager.MODE_PULL_REFRESH;
        loadNewX(true);


    }
    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            System.out.println(response);
            List<Comments> list = NewsParser.parseComments(response);
            boolean isClear = refreshMode == NewsManager.MODE_PULL_REFRESH ? true : false;
            adapter.appendDataToAdapter(list,isClear);
            adapter.notifyDataSetChanged();

            listView.stopLoadMore();//停止
            listView.stopRefresh();
            listView.setRefreshTime(CommonUtils.getCurrentDate());
            cancelDialog();
        }
    };
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            CommonUtils.showShortToast(CommentActivity.this,"访问失败");
        }
    };

    private void loadNewX(boolean isNewType){
        int cid = 1;
        if(!isNewType){
            if(adapter.getData().size() > 0 ){
                cid = adapter.getData().get(0).getCid();
            }
        }

        if(CommonUtils.isNetConnect(this)){
            NewsManager.getComment(this,nid,cid,dir,listener,errorListener);
        }
    }
    private void loadPrevNews(){
        if(adapter.getData().size() == 0){return;}
        int lastIndex = adapter.getData().size() - 1;
        int cid = adapter.getData().get(lastIndex).getCid();
        int dir = 2;
        if(CommonUtils.isNetConnect(this)){
            NewsManager.getComment(this,nid,cid,dir,listener,errorListener);
        }else{

        }
    }

    private XListView.IXListViewListener listViewListener = new XListView.IXListViewListener() {
        @Override
        public void onRefresh() {
            refreshMode = NewsManager.MODE_PULL_REFRESH;
            loadNewX(false);
        }

        @Override
        public void onLoadMore() {
            refreshMode = NewsManager.MODE_LOAD_MORE;
            loadPrevNews();
        }
    };

    @OnClick(R.id.iv_comments_in) public void commentsIn(){
        String comments = etCommentsIn.getText().toString();
        BaseEntity<UserResponse> entity = SharedPreferencesUtils.readBaseEntity(this);
        String token = entity.getData().getToken();
        String imei = CommonUtils.getIMEI(this);
        String name = SharedPreferencesUtils.readName(this);
        if(name.equals("")){
            CommonUtils.showShortToast(this,"请登录后再评论!");
            return;
        }else {
            OkHttpUtils.doGet(Url.GET_COMMENT + "?ver=0&nid=" + nid + "&token=" + token + "&imei=" + imei + "&ctx=" + comments,successListener,failListener);

        }
    }
    private SuccessListener successListener = new SuccessListener() {
        @Override
        public void onSuccess(String json) {

            String stmap = CommonUtils.getCurrentDate();
            int cid = 1;
            refreshMode = NewsManager.MODE_PULL_REFRESH;
            loadNewX(true);
            CommonUtils.showShortToast(CommentActivity.this,"发表成功");
        }
    };

    private FailListener failListener = new FailListener() {
        @Override
        public void onFail(String json) {
            CommonUtils.showShortToast(CommentActivity.this,"发表失败");
        }
    };


    @OnClick(R.id.iv_comments_back)
    public void back() {
        finish();
    }
}
