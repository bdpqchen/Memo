package com.example.chen.memo.model;

import android.os.Bundle;

import com.example.chen.memo.R;
import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.event.DiaryEvent;
import com.example.chen.memo.view.diary.DiaryActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

/**
 * Created by cdc on 16-9-28.
 */

public class CreateDataModelImpl implements ICreateDataModel {

    private Diary diary;

    @Override
    public void createDiaryData(DiaryActivity diaryActivity, Bundle bundle){
        diary = new Diary();
        int timeGetTime = (int) new Date().getTime();
        diary.setDiary(bundle.getString(DiaryActivity.EDIT_TEXT_DIARY));
        diary.setPublishTime(timeGetTime);
        diary.setStatus(CustomApplication.RECORD_STATUS_VALID);

        if(diary.save()){
//            EventBus.getDefault().post(new DiaryEvent(""));

            EventBus.getDefault().post(new DiaryEvent("refresh diary list"));
            diaryActivity.onToastMessage(String.valueOf(diaryActivity.getString(R.string.save_data_succeed)));
        }else{
            diaryActivity.onToastMessage(diaryActivity.getString(R.string.save_data_fail));
        }

    }


}
