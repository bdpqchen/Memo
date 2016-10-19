package com.example.chen.memo.event;

/**
 * Created by cdc on 16-10-18.
 */
public class CipherEvent {
    private String msg;

    public CipherEvent(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

}
