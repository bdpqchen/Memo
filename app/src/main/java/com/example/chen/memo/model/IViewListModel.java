package com.example.chen.memo.model;

import android.content.Context;

import com.example.chen.memo.view.cipher.CipherListActivity;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.memo.MemoListActivity;

/**
 * Created by cdc on 16-9-24.
 */
public interface IViewListModel {

    void initDiaryData(DiaryListActivity view);

    void initMemoData(MemoListActivity view);

    void initCipherData(CipherListActivity view);

    void loadMoreDiaryData(DiaryListActivity view, int offset);

    void loadMoreMemoData(MemoListActivity view, int offset);

    void loadMoreCipherData(CipherListActivity context, int offset);

    void getDiaryDataCount(DiaryListActivity view);

    void getMemoDataCount(MemoListActivity view);

    void getCipherDataCount(CipherListActivity view);

    void discardRecord(Context context, NextActivity viewType, int id);
}
