package com.edu.feicui.newsclient.entity;

/**
 * Created by Administrator on 2016-12-2.
 */

//EventBus派发的事件实体类
public class MessageEvent {
    //将要添加LoginFragment
    public static final int TYPE_LOGIN_FRAGMENT = 1;
    //将要添加RegisterFragment
    public static final int TYPE_REGISTER_FRAGMENT = 2;
    //ForgotPasswordFragment
    public static final int TYPE_FORGOT_PASSWORD = 3;
    //MainFragment
    public  static final int TYPE_MAIN_FRAGMENT = 4;
    //
    public static final int TYPE_RIGHT_MENU_UNLOGIN = 5;

    public static final int TYPE_RIGHT_MENU_LOGIN = 6;



    private int type;
    private String fragmentFullName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFragmentFullName() {
        return fragmentFullName;
    }

    public void setFragmentFullName(String fragmentFullName) {
        this.fragmentFullName = fragmentFullName;
    }

    public MessageEvent() {
    }

    public MessageEvent(int type, String fragmentFullName) {
        this.type = type;
        this.fragmentFullName = fragmentFullName;
    }
}
