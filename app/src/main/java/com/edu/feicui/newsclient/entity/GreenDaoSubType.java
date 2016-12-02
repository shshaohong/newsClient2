package com.edu.feicui.newsclient.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2016-12-2.
 */
@Entity(nameInDb = "news_type")
public class GreenDaoSubType {

    //子分类编码
    @Id(autoincrement = true)
    private int subid;
    @Property(nameInDb = "shbgroup")
    @NotNull
    private String subgroup;

    public GreenDaoSubType(int subid, String subgroup) {
        this.subid = subid;
        this.subgroup = subgroup;
    }

    public GreenDaoSubType() {
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
