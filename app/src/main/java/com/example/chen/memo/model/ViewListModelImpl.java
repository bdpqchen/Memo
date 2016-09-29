package com.example.chen.memo.model;

import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.view.diary.DiaryListActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by cdc on 16-9-24.
 */

public class ViewListModelImpl implements IViewListModel {

    @Override
    public void initDiaryData(DiaryListActivity view) {
        //初始化 diary 数据
        //20条
        List<Diary> diarylist = DataSupport.where("status >= ?","1").order("id desc").find(Diary.class);
        view.onInitSuccess(diarylist);
    }

    @Override
    public void initMemoData() {

    }

    @Override
    public void initCipherData() {
    }

}
