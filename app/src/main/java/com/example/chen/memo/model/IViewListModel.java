package com.example.chen.memo.model;

import com.example.chen.memo.view.diary.DiaryListActivity;

/**
 * Created by cdc on 16-9-24.
 */
public interface IViewListModel {


    void initDiaryData(DiaryListActivity view);

    void initMemoData();

    void initCipherData();
}
