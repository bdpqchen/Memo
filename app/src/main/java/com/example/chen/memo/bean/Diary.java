package com.example.chen.memo.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by cdc on 16-9-24.
 */

public class Diary extends DataSupport{

    private int id;
    private String diary;
    private int status;
    private long publishTime;

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
}
