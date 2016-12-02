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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-12-2.
 */

public class ForgetPasswordFragment extends Fragment {
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}