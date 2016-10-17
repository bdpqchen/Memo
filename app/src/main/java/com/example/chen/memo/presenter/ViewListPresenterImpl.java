package com.example.chen.memo.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.model.ViewListModelImpl;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.memo.MemoListActivity;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;

/**
 * Created by cdc on 16-9-24.
 */

public class ViewListPresenterImpl implements IViewListPresenter {

    private static ViewListModelImpl viewListModel = new ViewListModelImpl();

    public void initData(int type, Context context){

        switch (type){
            case CustomApplication.DIARY:
                viewListModel.initDiaryData((DiaryListActivity) context);
                break;
            case CustomApplication.MEMO:
                viewListModel.initMemoData((MemoListActivity) context);
                break;
            case CustomApplication.CIPHER:
                viewListModel.initCipherData();
                break;
        }

    }

    public void loadMoreData(int type, Context context, int offset){
        switch (type){
            case CustomApplication.DIARY:
                viewListModel.loadMoreDiaryData((DiaryListActivity) context, offset);
                break;

        }

    }

    public void getDataCount(int type, Context context) {
        switch (type){
            case CustomApplication.DIARY:
                viewListModel.getDiaryDataCount((DiaryListActivity) context);
                break;
            case CustomApplication.MEMO:
//                viewListModel.getMemoDataCount();
                break;
            case CustomApplication.CIPHER:
//                viewListModel.getCipherDataCount();
                break;
        }
    }


    public void discardRecord(Context context, NextActivity viewType, int id){
        viewListModel.discardRecord(context, viewType, id);

    }

}
