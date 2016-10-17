package com.example.chen.memo.event;

/**
 * Created by cdc on 16-10-16.
 */

public class MemoEvent {
    private int type;

    public MemoEvent(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
