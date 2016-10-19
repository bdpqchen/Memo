package com.example.chen.memo.bean;

import android.provider.ContactsContract;

import org.litepal.crud.DataSupport;

/**
 * Created by cdc on 16-10-18.
 */
public class CipherBean extends DataSupport{

    private String pwd;
    private String name;
    private String account;
    private long publishTime;
    private int status;
    private int id;


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
