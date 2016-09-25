package com.example.chen.memo.presenter;

import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.model.ViewListModel;

/**
 * Created by cdc on 16-9-24.
 */

public class ViewListPresenter implements ViewListPresenterImpl{

    private static ViewListModel viewListModel = new ViewListModel();

    @Override
    public static void initData(int type){

        switch (type){
            case CustomApplication.DIARY:
                viewListModel.initDiaryData();
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
