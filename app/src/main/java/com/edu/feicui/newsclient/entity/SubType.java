package com.edu.feicui.newsclient.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2016-11-28.
 */


public class SubType {
    //子分类编码

    private int subid;

    private String subgroup;

    public SubType(int subid, String subgroup) {
        this.subid = subid;
        this.subgroup = subgroup;
    }

    public SubType() {
    }

    public String getSubgroup() {

        return subgroup;
    }

    public void setSubgroup(String subgroup) {

        this.subgroup = subgroup;
    }

    public int getSubid() {

        return subid;
    }

    public void setSubid(int subid) {

        this.subid = subid;
    }
}
