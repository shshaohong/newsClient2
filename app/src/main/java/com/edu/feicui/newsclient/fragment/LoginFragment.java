package com.edu.feicui.newsclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.activity.UserActivity;
import com.edu.feicui.newsclient.entity.BaseEntity;
import com.edu.feicui.newsclient.entity.MessageEvent;
import com.edu.feicui.newsclient.entity.UserResponse;
import com.edu.feicui.newsclient.listener.FailListener;
import com.edu.feicui.newsclient.listener.SuccessListener;
import com.edu.feicui.newsclient.utils.CommonUtils;
import com.edu.feicui.newsclient.utils.OkHttpUtils;
import com.edu.feicui.newsclient.utils.SharedPreferencesUtils;
import com.edu.feicui.newsclient.utils.Url;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016-12-2.
 */

public class LoginFragment extends Fragment {
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_re)
    Button btnRegister;
    @BindView(R.id.btn_forgot)
    Button btnForgotPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        ButterKnife.bind(this,view);

        return view;
    }
    @OnClick(R.id.btn_re)
    public void publishAddRegisterFragmentEvent(){
        MessageEvent event = new MessageEvent();
        event.setType(MessageEvent.TYPE_REGISTER_FRAGMENT);
        event.setFragmentFullName(RegisterFragment.class.getName());
        EventBus.getDefault().post(event);
    }
    @OnClick(R.id.btn_forgot)
    public void publishAddForgotPasswordFragmentEvent(){
        MessageEvent event = new MessageEvent();
        event.setType(MessageEvent.TYPE_FORGOT_PASSWORD);
        event.setFragmentFullName(ForgetPasswordFragment.class.getName());
        EventBus.getDefault().post(event);
    }

    //登陆
    @OnClick(R.id.btn_login) public void login(){
        String username = etNickname.getText().toString();
        String password = etPassword.getText().toString();

        if(!CommonUtils.verifyName(username)){
            CommonUtils.showShortToast(getActivity(),"请输入正确的用户名");
            return;
        }
        if(!CommonUtils.verifyPassword(password)){
            CommonUtils.showShortToast(getActivity(),"请输入正确的密码");
            return;
        }
        OkHttpUtils.doGet(Url.LOGIN + "?ver=0&device=0&uid=" + username + "&pwd=" + password,successListener,failListener);

    }
    private SuccessListener successListener = new SuccessListener() {
        @Override
        public void onSuccess(String json) {
            Gson gson = new Gson();
            BaseEntity<UserResponse> baseEntity = gson.fromJson(json,new TypeToken<BaseEntity<UserResponse>>(){}.getType());
            if(baseEntity.getStatus().equals("0")){
                if(baseEntity.getData().getResult() == 0){
                    SharedPreferencesUtils.saveBaseEntity(getActivity(),baseEntity);
                    String username = etNickname.getText().toString();
                    SharedPreferencesUtils.saveName(getActivity(),username);
                    //添加新闻至主界面
//                    MessageEvent messageEvent = new MessageEvent();
//                    messageEvent.setType(MessageEvent.TYPE_MAIN_FRAGMENT);
//                    EventBus.getDefault().post(messageEvent);

                    //更新右侧滑菜单状态



                    startActivity(new Intent(getActivity(), UserActivity.class));
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.right_in,R.anim.bottom_out);

                }else {
                    CommonUtils.showShortToast(getActivity(),baseEntity.getData().getExplain());
                }
            }else {
                CommonUtils.showShortToast(getActivity(),"登陆失败，请重试");
            }
        }
    };
    private FailListener failListener = new FailListener() {
        @Override
        public void onFail(String json) {

        }
    };

}
