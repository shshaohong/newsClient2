package com.edu.feicui.newsclient.entity;

import java.util.List;

/**
 * Created by Administrator on 2016-11-28.
 */

public class NewsType {
    //子分类
    List<SubType> subgrp;
    //分类编码
    private int gid;
    //分类名
    private String group;

    public int getGid() {

        return gid;
    }

    public void setGid(int gid) {

        this.gid = gid;
    }

    public String getGroup() {

        return group;
    }

    public void setGroup(String group) {

        this.group = group;
    }

    public List<SubType> getSubgrp() {

        return subgrp;
    }

    public void setSubgrp(List<SubType> subgrp) {

        this.subgrp = subgrp;
    }

}
