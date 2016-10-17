package com.example.chen.memo.model;

import android.os.Bundle;

import com.example.chen.memo.R;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.bean.Memo;
import com.example.chen.memo.event.DiaryEvent;
import com.example.chen.memo.event.MemoEvent;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.view.diary.DiaryActivity;
import com.example.chen.memo.view.memo.MemoActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import static com.example.chen.memo.application.CustomApplication.CREATE;
import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_DIARY;
import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_MEMO;
import static com.example.chen.memo.application.CustomApplication.MEMO_ALARM_TIME;
import static com.example.chen.memo.application.CustomApplication.MEMO_CONTENT;
import static com.example.chen.memo.application.CustomApplication.RECORD_STATUS_VALID;

/**
 * Created by cdc on 16-9-28.
 */

public class CreateDataModelImpl implements ICreateDataModel {

    private Diary diary;

    @Override
    public void createDiaryData(DiaryActivity diaryActivity, Bundle bundle){
        diary = new Diary();
        long timeGetTime = new Date().getTime();
//        LogUtils.i("timeGetTime date", String.valueOf(new Date().getTime()));
        diary.setDiary(bundle.getString(EDIT_TEXT_DIARY));
        diary.setPublishTime(timeGetTime);
        diary.setStatus(RECORD_STATUS_VALID);

        if(diary.save()){
            EventBus.getDefault().post(new DiaryEvent("refresh diary list"));
            diaryActivity.onToastMessage(diaryActivity.getString(R.string.save_data_succeed));
            diaryActivity.finish();
        }else{
            diaryActivity.onToastMessage(diaryActivity.getString(R.string.save_data_fail));
        }
    }

    @Override
    public void createMemoData(MemoActivity memoActivity, Bundle bundle){
        Memo memo = new Memo();
        long timeGetTime = new Date().getTime();

        LogUtils.i("timeGetTime date", String.valueOf(new Date().getTime()));
        memo.setAlarmTime(bundle.getInt(MEMO_ALARM_TIME));
        memo.setMemo(bundle.getString(MEMO_CONTENT));
        memo.setPublishTime(timeGetTime);
        memo.setStatus(RECORD_STATUS_VALID);

        if(memo.save()){
            EventBus.getDefault().post(new MemoEvent(CREATE));
            memoActivity.onToastMessage(memoActivity.getString(R.string.save_data_succeed));
            memoActivity.finish();
        }else{
            memoActivity.onToastMessage(memoActivity.getString(R.string.save_data_fail));
        }

    }


}
