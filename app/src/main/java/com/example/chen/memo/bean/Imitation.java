package com.example.chen.memo.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by cdc on 16-10-19.
 */

public class Imitation extends DataSupport{

    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
