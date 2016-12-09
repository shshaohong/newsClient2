package com.edu.feicui.newsclient.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2016-12-2.
 */

@Entity(nameInDb = "tb_user")
public class User {
    @Id(autoincrement = true)
    private long id;
    @Property(nameInDb = "tb_email")
    private String email;
    @Property(nameInDb = "tb_name")
    private String name;
    @Property(nameInDb = "tb_password")
    private String password;

    @Generated(hash = 160809878)
    public User(long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
