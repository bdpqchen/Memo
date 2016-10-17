package com.example.chen.memo.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.chen.memo.view.memo.MemoActivity;

/**
 * Created by cdc on 16-10-14.
 */
public interface IMemoPresenter {

    void addAlarm(MemoActivity memoActivity, Context context);

    void alterMemo(MemoActivity memoActivity, Bundle alterBundle);
}
