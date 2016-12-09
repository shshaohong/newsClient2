package com.edu.feicui.newsclient.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.activity.UserActivity;
import com.edu.feicui.newsclient.base.BaseActivity;
import com.edu.feicui.newsclient.biz.VersionManager;
import com.edu.feicui.newsclient.entity.BaseEntity;
import com.edu.feicui.newsclient.entity.MessageEvent;
import com.edu.feicui.newsclient.entity.UserResponse;
import com.edu.feicui.newsclient.entity.Version;
import com.edu.feicui.newsclient.listener.FailListener;
import com.edu.feicui.newsclient.listener.SuccessListener;
import com.edu.feicui.newsclient.utils.LoadImage;
import com.edu.feicui.newsclient.utils.SharedPreferencesUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.edu.feicui.newsclient.MainActivity.slidingMenu;

/**
 * Created by Administr
 * ator on 2016-11-24.
 */

public class RightMenuFragment extends Fragment {
    //没登陆的
    @BindView(R.id.ll_unLogin)
    LinearLayout unLayout;
    @BindView(R.id.iv_right_unimg)
    ImageView univRightImg;
    @BindView(R.id.tv_unlijidenglu)
    TextView untvRightText;

    //登陆了的
    @BindView(R.id.ll_Login)
    LinearLayout layout;
    @BindView(R.id.iv_right_img)
    ImageView ivRightImg;
    @BindView(R.id.tv_lijidenglu)
    TextView tvRightText;

    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;
    @BindView(R.id.iv_qq)
    ImageView inqq;
    @BindView(R.id.iv_pengyouquan)
    ImageView ivPengyouquan;
    @BindView(R.id.iv_weibo)
    ImageView ivWeibo;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_menu,container,false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(SharedPreferencesUtils.isLogin(getActivity())){
            unLayout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            //设置头像与用户名
            SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            String username = sp.getString("username","");
            String headImage = sp.getString("headImage","");
            String localHeadImage = sp.getString("localHeadImage","");

            tvRightText.setText(username);
            File file = new File(localHeadImage);
            if(file.exists()){
                ivRightImg.setImageURI(Uri.fromFile(file));
            }else {
                LoadImage loadImage = new LoadImage(getActivity());
                loadImage.displayBitmap(headImage,ivRightImg);
            }

        }else {
            layout.setVisibility(View.GONE);
            unLayout.setVisibility(View.VISIBLE);
        }


    }



    @OnClick(R.id.iv_right_unimg)
    public void publishAddLoginFragmentEvent(){
        MessageEvent event = new MessageEvent();
        event.setType(MessageEvent.TYPE_LOGIN_FRAGMENT);
        event.setFragmentFullName(LoginFragment.class.getName());

        //发布事件
        EventBus.getDefault().post(event);
    }
    @OnClick(R.id.tv_unlijidenglu)
    public void publishAddLoginFragment_2(){

        publishAddLoginFragmentEvent();
    }

    @OnClick(R.id.iv_right_img)
    public void gotoUserCenter(){
        BaseActivity activity = (BaseActivity) getActivity();
        activity.startActivity(UserActivity.class);
    }

    @OnClick(R.id.tv_lijidenglu)
    public void gotoUserCenter2(){
        gotoUserCenter();
    }
    @OnClick(R.id.tv_gengxin)
    public void getUpdateVersion(){
        VersionManager.getUpdateVersion(getActivity(),successListener,failListener);
    }
    private SuccessListener successListener = new SuccessListener() {
        @Override
        public void onSuccess(String json) {
            System.out.println("+++++++++++++++++++++++" + json);
            Gson gson = new Gson();
            Version version = gson.fromJson(json, Version.class);
        }
//        getActivity().getPackageManager().equals()
    };
    private FailListener failListener = new FailListener() {
        @Override
        public void onFail(String json) {

        }
    };
}
