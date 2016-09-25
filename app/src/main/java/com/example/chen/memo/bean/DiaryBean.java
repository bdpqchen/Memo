package com.example.chen.memo.bean;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by cdc on 16-9-24.
 */

public class DiaryBean extends DataSupport{

    private int id;
    private String diary;
    private int validity;
    private Date time;


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

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
