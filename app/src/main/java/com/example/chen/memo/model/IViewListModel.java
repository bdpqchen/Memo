package com.example.chen.memo.model;

import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.memo.MemoListActivity;

/**
 * Created by cdc on 16-9-24.
 */
public interface IViewListModel {


    void initDiaryData(DiaryListActivity view);


    void initMemoData(MemoListActivity view);

    void initCipherData();
}
