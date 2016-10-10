package com.example.chen.memo.model;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;

import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by cdc on 16-9-24.
 */

public class ViewListModelImpl implements IViewListModel {

    private final String STATUS_WHERE = "status = ?";
    private final String VALID_DATA = String.valueOf(CustomApplication.RECORD_STATUS_VALID);

    @Override
    public void initDiaryData(DiaryListActivity view) {
        //初始化 diary 数据
        //20条
        List<Diary> diarylist = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(20).order("id desc").find(Diary.class);
        view.onInitSuccess(diarylist);
    }

    @Override
    public void initMemoData() {

    }

    @Override
    public void initCipherData() {
    }

    public void loadMoreDiaryData(DiaryListActivity view, int offset) {
        List<Diary> diarylistMore = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(20).offset(20 * offset).find(Diary.class);
        view.onInitSuccess(diarylistMore);
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
