package com.example.chen.memo.model;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;

import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.bean.Memo;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.memo.MemoListActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.example.chen.memo.application.CustomApplication.RECORD_LIST_LIMIT;

/**
 * Created by cdc on 16-9-24.
 */

public class ViewListModelImpl implements IViewListModel {

    private final String STATUS_WHERE = "status = ?";
    private final String ORDERBY_ID_DESC = "id desc";
    private final String VALID_DATA = String.valueOf(CustomApplication.RECORD_STATUS_VALID);

    @Override
    public void initDiaryData(DiaryListActivity view) {
        //初始化 diary 数据
        //20条
        List<Diary> diarylist = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(RECORD_LIST_LIMIT).order(ORDERBY_ID_DESC).find(Diary.class);
        view.onInitSuccess(diarylist);
    }

    @Override
    public void initMemoData(MemoListActivity view) {
        List<Memo> memoList = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(RECORD_LIST_LIMIT).order(ORDERBY_ID_DESC).find(Memo.class);
        view.onInitSuccess(memoList);
    }

    @Override
    public void initCipherData() {
    }

    public void loadMoreDiaryData(DiaryListActivity view, int offset) {
        List<Diary> diaryListMore = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(RECORD_LIST_LIMIT).offset(RECORD_LIST_LIMIT * offset).order(ORDERBY_ID_DESC).find(Diary.class);
        view.onInitSuccess(diaryListMore);
    }

    public void getDiaryDataCount(DiaryListActivity view) {
        int count = DataSupport.where(STATUS_WHERE, VALID_DATA).count(Diary.class);
        view.onGetDataCount(count);
    }

    public void discardRecord(Context context, NextActivity viewType, int id){
        switch (viewType){
            case DiaryList:
                ContentValues values = new ContentValues();
                values.put("status",CustomApplication.RECORD_STATUS_TRASHED);
                DataSupport.update(Diary.class,values,id);
                break;

        }

    }
}
