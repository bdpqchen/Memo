package com.example.chen.memo.presenter;

import android.content.Context;
import android.view.View;

import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.model.ViewListModelImpl;
import com.example.chen.memo.view.diary.DiaryListActivity;

/**
 * Created by cdc on 16-9-24.
 */

public class ViewListPresenterImpl implements IViewListPresenter {

    private static ViewListModelImpl viewListModel = new ViewListModelImpl();

    public static void initData(int type, Context context){

        switch (type){
            case CustomApplication.DIARY:
                viewListModel.initDiaryData((DiaryListActivity) context);
                break;
            case CustomApplication.MEMO:
                viewListModel.initMemoData();
                break;
            case CustomApplication.CIPHER:
                viewListModel.initCipherData();
                break;
        }



    }


}
