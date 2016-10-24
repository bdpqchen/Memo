package com.example.chen.memo.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by cdc on 16-10-23.
 */

public class Sign extends DataSupport{

    private int id;
    private String date;
    private long datetime;


    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
