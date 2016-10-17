package com.example.chen.memo.model;

import android.os.Bundle;

import com.example.chen.memo.view.diary.DiaryActivity;
import com.example.chen.memo.view.memo.MemoActivity;

/**
 * Created by cdc on 16-9-28.
 */
public interface ICreateDataModel {
    void createDiaryData(DiaryActivity diaryActivity, Bundle bundle);

    void createMemoData(MemoActivity memoActivity, Bundle bundle);
}
