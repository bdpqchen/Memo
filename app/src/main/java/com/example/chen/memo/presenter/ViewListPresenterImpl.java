package com.example.chen.memo.presenter;

import android.content.Context;

import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.model.ViewListModelImpl;
import com.example.chen.memo.view.cipher.CipherListActivity;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.dump.DumpListActivity;
import com.example.chen.memo.view.memo.MemoListActivity;

import static com.example.chen.memo.application.CustomApplication.CIPHER;
import static com.example.chen.memo.application.CustomApplication.DIARY;
import static com.example.chen.memo.application.CustomApplication.MEMO;

/**
 * Created by cdc on 16-9-24.
 */

public class ViewListPresenterImpl implements IViewListPresenter {

    private static ViewListModelImpl viewListModel = new ViewListModelImpl();

    public void initData(int type, Context context) {

        switch (type) {
            case DIARY:
                viewListModel.initDiaryData((DiaryListActivity) context);
                break;
            case CustomApplication.MEMO:
                viewListModel.initMemoData((MemoListActivity) context);
                break;
            case CustomApplication.CIPHER:
                viewListModel.initCipherData((CipherListActivity) context);
                break;
        }

    }

    public void loadMoreData(int type, Context context, int offset) {
        switch (type) {
            case DIARY:
                viewListModel.loadMoreDiaryData((DiaryListActivity) context, offset);
                break;
            case CustomApplication.MEMO:
                viewListModel.loadMoreMemoData((MemoListActivity) context, offset);
                break;
            case CustomApplication.CIPHER:
                viewListModel.loadMoreCipherData((CipherListActivity) context, offset);
                break;
        }

    }

    public void getDataCount(int type, Context context) {
        switch (type) {
            case DIARY:
                viewListModel.getDiaryDataCount((DiaryListActivity) context);
                break;
            case CustomApplication.MEMO:
                viewListModel.getMemoDataCount((MemoListActivity) context);
                break;
            case CustomApplication.CIPHER:
                viewListModel.getCipherDataCount((CipherListActivity) context);
                break;
        }
    }


    public void discardRecord(Context context, NextActivity viewType, int id) {
        viewListModel.discardRecord(context, viewType, id);
    }

    public void revertRecord(DumpListActivity dumpListActivity, int type, int id) {
        switch (type){
            case DIARY:
                viewListModel.revertDiary(dumpListActivity, id);
                break;
            case MEMO:
                viewListModel.revertMemo(dumpListActivity, id);
                break;
            case CIPHER:
                viewListModel.revertCipher(dumpListActivity, id);
                break;
        }
    }

    public void deleteRecord(DumpListActivity dumpListActivity, int type, int id) {
        switch (type){
            case DIARY:
                viewListModel.deleteDiary(dumpListActivity, id);
                break;
            case MEMO:
                viewListModel.deleteMemo(dumpListActivity, id);
                break;
            case CIPHER:
                viewListModel.deleteCipher(dumpListActivity, id);
                break;
        }
    }
}
