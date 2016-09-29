package com.example.chen.memo.bean;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by cdc on 16-9-24.
 */

public class Diary extends DataSupport{

    private int id;
    private String diary;
    private int status;
    private int publishTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public int getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(int publishTime) {
        this.publishTime = publishTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
