package com.edu.feicui.newsclient.entity;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2016-12-7.
 */

public class Users {
    private String uid;
    private String portrait;
    private int intergration;
    private  int comnum;
    private List<LoginLog> loginlog;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getIntergration() {
        return intergration;
    }

    public void setIntergration(int intergration) {
        this.intergration = intergration;
    }

    public int getComnum() {
        return comnum;
    }

    public void setComnum(int comnum) {
        this.comnum = comnum;
    }

    public List<LoginLog> getLoginLog() {
        return loginlog;
    }

    public void setLoginlog(List<LoginLog> loginlog) {
        this.loginlog = loginlog;
    }
}
