package com.example.chen.memo.model;

import android.app.usage.UsageEvents;
import android.content.ContentValues;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.bean.CipherBean;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.bean.Memo;
import com.example.chen.memo.event.CipherEvent;
import com.example.chen.memo.event.DiaryEvent;
import com.example.chen.memo.event.MemoEvent;
import com.example.chen.memo.view.cipher.CipherActivity;
import com.example.chen.memo.view.cipher.CipherActivity$$ViewInjector;
import com.example.chen.memo.view.diary.DiaryDetailActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.memo.MemoActivity;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.Date;

import static com.example.chen.memo.application.CustomApplication.ACCOUNT;
import static com.example.chen.memo.application.CustomApplication.ALTER;
import static com.example.chen.memo.application.CustomApplication.DIARY_CONTENT;
import static com.example.chen.memo.application.CustomApplication.KEY_PWD;
import static com.example.chen.memo.application.CustomApplication.MEMO_ALARM_TIME;
import static com.example.chen.memo.application.CustomApplication.MEMO_CONTENT;
import static com.example.chen.memo.application.CustomApplication.NAME;
import static com.example.chen.memo.application.CustomApplication.PUBLISH_TIME;
import static com.example.chen.memo.application.CustomApplication.PWD;
import static com.example.chen.memo.application.CustomApplication.PWD_ACCOUNT;
import static com.example.chen.memo.application.CustomApplication.PWD_NAME;
import static com.example.chen.memo.application.CustomApplication.PWD_PWD;
import static com.example.chen.memo.application.CustomApplication.RECORD_STATUS_INVALID;
import static com.example.chen.memo.application.CustomApplication.STATUS;

/**
 * Created by cdc on 16-10-11.
 */

public class AlterDataModelImpl implements IAlterDataModel {

    private static final String ID = "id";
    private static final String DIARY = "diary";
    private static final String MEMO = "memo";
    private static final String ALARM_TIME = "alarmtime";
    private static final String PUBLISH_TIME = "publishtime";



    public void alterDiary(DiaryDetailActivity diaryDetailActivity, Bundle diaryBundle){
        long timeGetTime = new Date().getTime();
        ContentValues values = new ContentValues();
        values.put(DIARY,diaryBundle.getString(DIARY_CONTENT));
        values.put(PUBLISH_TIME,timeGetTime);
        DataSupport.update(Diary.class, values, diaryBundle.getInt(ID));

        EventBus.getDefault().post(new DiaryEvent("refresh diary list"));
        diaryDetailActivity.onToastMessage(diaryDetailActivity.getString(R.string.save_data_succeed));
        diaryDetailActivity.finish();
        //更新列表
    }

    public void deleteDiary(int id){
        ContentValues values = new ContentValues();
        values.put(STATUS,RECORD_STATUS_INVALID);
        DataSupport.update(Diary.class, values, id);
    }


    public void alterMemo(MemoActivity memoActivity, Bundle bundle) {
        long timeGetTime = new Date().getTime();
        ContentValues values = new ContentValues();
        values.put(MEMO, bundle.getString(MEMO_CONTENT));
        values.put(PUBLISH_TIME, timeGetTime);
        values.put(ALARM_TIME, bundle.getInt(MEMO_ALARM_TIME));
        DataSupport.update(Memo.class, values, bundle.getInt(ID));

        EventBus.getDefault().post(new MemoEvent(ALTER));
        memoActivity.onToastMessage(memoActivity.getString(R.string.alter_success));
        memoActivity.finish();

    }

    /*deleteMemo()
    * */

    public void alterCipher(CipherActivity cipherActivity, Bundle bundle){
        boolean encrypt = true;
        String msg =cipherActivity.getString(R.string.alter_success);
        ContentValues values = new ContentValues();
        values.put(NAME, bundle.getString(PWD_NAME));
        values.put(ACCOUNT, bundle.getString(PWD_ACCOUNT));
        try {
            values.put(PWD, SimpleCrypto.enCrypto(bundle.getString(PWD_PWD), KEY_PWD));
        } catch (Exception e) {
            encrypt = false;
            e.printStackTrace();
        }

        if(encrypt){
            msg = "加密失败.建议查杀病毒后重试";
            DataSupport.update(CipherBean.class, values, bundle.getInt(ID));
        }
        EventBus.getDefault().post(new CipherEvent("flash cipher list"));
        cipherActivity.onToastMessage(msg);
        cipherActivity.finish();
    }

}
