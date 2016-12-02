package com.edu.feicui.newsclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.edu.feicui.newsclient.R;
import com.edu.feicui.newsclient.entity.MessageEvent;

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
}
