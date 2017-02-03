package com.example.chen.memo.event;

/**
 * Created by cdc on 16-9-28.
 */

public class DiaryEvent{

    private String msg;

    public DiaryEvent(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

}
