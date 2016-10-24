package com.example.chen.memo.model;

import android.content.ContentValues;
import android.content.Context;

import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.bean.CipherBean;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.bean.Dump;
import com.example.chen.memo.bean.Memo;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.view.cipher.CipherListActivity;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.dump.DumpListActivity;
import com.example.chen.memo.view.memo.MemoListActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.chen.memo.application.CustomApplication.CIPHER;
import static com.example.chen.memo.application.CustomApplication.DIARY;
import static com.example.chen.memo.application.CustomApplication.MEMO;
import static com.example.chen.memo.application.CustomApplication.RECORD_LIST_LIMIT;
import static com.example.chen.memo.application.CustomApplication.RECORD_STATUS_TRASHED;
import static com.example.chen.memo.application.CustomApplication.STATUS;

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
    public void initCipherData(CipherListActivity view) {
        List<CipherBean> cipherList = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(RECORD_LIST_LIMIT).order(ORDERBY_ID_DESC).find(CipherBean.class);
        view.onInitSuccess(cipherList);
    }

    @Override
    public void loadMoreDiaryData(DiaryListActivity view, int offset) {
        List<Diary> diaryListMore = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(RECORD_LIST_LIMIT).offset(RECORD_LIST_LIMIT * offset).order(ORDERBY_ID_DESC).find(Diary.class);
        view.onInitSuccess(diaryListMore);
    }

    @Override
    public void loadMoreMemoData(MemoListActivity view, int offset) {
        List<Memo> memoListMore = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(RECORD_LIST_LIMIT).offset(RECORD_LIST_LIMIT * offset)
                .order(ORDERBY_ID_DESC).find(Memo.class);
        view.onInitSuccess(memoListMore);
    }

    @Override
    public void loadMoreCipherData(CipherListActivity view, int offset) {
        List<CipherBean> cipherListMore = DataSupport.where(STATUS_WHERE, VALID_DATA).limit(RECORD_LIST_LIMIT).offset(RECORD_LIST_LIMIT * offset)
                .order(ORDERBY_ID_DESC).find(CipherBean.class);
        view.onInitSuccess(cipherListMore);
    }

    @Override
    public void getDiaryDataCount(DiaryListActivity view) {
        int count = DataSupport.where(STATUS_WHERE, VALID_DATA).count(Diary.class);
        view.onGetDataCount(count);
    }

    @Override
    public void getMemoDataCount(MemoListActivity view) {
        int count = DataSupport.where(STATUS_WHERE, VALID_DATA).count(Memo.class);
        view.onGetDataCount(count);
    }

    @Override
    public void getCipherDataCount(CipherListActivity view) {
        int count = DataSupport.where(STATUS_WHERE, VALID_DATA).count(CipherBean.class);
        view.onGetDataCount(count);
    }

    @Override
    public void discardRecord(Context context, NextActivity viewType, int id) {
        switch (viewType) {
            case DiaryList:
                ContentValues valuesDiary = new ContentValues();
                valuesDiary.put(STATUS, CustomApplication.RECORD_STATUS_TRASHED);
                DataSupport.update(Diary.class, valuesDiary, id);
                break;
            case MemoList:
                ContentValues valuesMemo = new ContentValues();
                valuesMemo.put(STATUS, CustomApplication.RECORD_STATUS_TRASHED);
                DataSupport.update(Memo.class, valuesMemo, id);
                break;
            case CipherList:
                ContentValues valuesCipher = new ContentValues();
                valuesCipher.put(STATUS, CustomApplication.RECORD_STATUS_TRASHED);
                DataSupport.update(CipherBean.class, valuesCipher, id);
                break;
        }

    }

    @Override
    public void initDumpData(DumpListActivity dumpListActivity) {
        List<Diary> diaryList = DataSupport.where(STATUS_WHERE, RECORD_STATUS_TRASHED + "").order(ORDERBY_ID_DESC).find(Diary.class);
        List<Memo> memoList = DataSupport.where(STATUS_WHERE, RECORD_STATUS_TRASHED + "").order(ORDERBY_ID_DESC).find(Memo.class);
        List<CipherBean> cipherBeanList = DataSupport.where(STATUS_WHERE, RECORD_STATUS_TRASHED + "").order(ORDERBY_ID_DESC).find(CipherBean.class);
        int count = memoList.size() + diaryList.size() + cipherBeanList.size();
        LogUtils.i("count", String.valueOf(count));
        List<Dump> dumpList = new ArrayList<>(count);

        if (diaryList.size() > 0) {
            for (int i = 0; i < diaryList.size(); i++) {
                Dump dump = new Dump();
                dump.setType(DIARY);
                dump.setId(diaryList.get(i).getId());
                dump.setTitle(diaryList.get(i).getDiary());
                dumpList.add(dump);
            }
        }

        if (memoList.size() > 0) {
            for (int i = 0; i < memoList.size(); i++) {
                Dump dump = new Dump();
                dump.setType(MEMO);
                dump.setId(memoList.get(i).getId());
                dump.setTitle(memoList.get(i).getMemo());
                dumpList.add(dump);
            }
        }
        if (cipherBeanList.size() > 0) {
            for (int i = 0; i < cipherBeanList.size(); i++) {
                Dump dump = new Dump();
                dump.setType(CIPHER);
                dump.setId(cipherBeanList.get(i).getId());
                dump.setTitle(cipherBeanList.get(i).getName());
                dumpList.add(dump);
            }
        }

        dumpListActivity.onInitSuccess(dumpList);

    }

    public void revertDiary(DumpListActivity dumpListActivity, int id) {
        ContentValues valuesDiary = new ContentValues();
        valuesDiary.put(STATUS, CustomApplication.RECORD_STATUS_VALID);
        DataSupport.update(Diary.class, valuesDiary, id);
        dumpListActivity.onRevertSuccess();
    }

    public void revertMemo(DumpListActivity dumpListActivity, int id) {
        ContentValues valuesMemo = new ContentValues();
        valuesMemo.put(STATUS, CustomApplication.RECORD_STATUS_VALID);
        DataSupport.update(Memo.class, valuesMemo, id);
        dumpListActivity.onRevertSuccess();

    }

    public void revertCipher(DumpListActivity dumpListActivity, int id) {
        ContentValues valuesCipher = new ContentValues();
        valuesCipher.put(STATUS, CustomApplication.RECORD_STATUS_VALID);
        DataSupport.update(CipherBean.class, valuesCipher, id);
        dumpListActivity.onRevertSuccess();
    }

    public void deleteDiary(DumpListActivity dumpListActivity, int id) {
        ContentValues valuesDiary = new ContentValues();
        valuesDiary.put(STATUS, CustomApplication.RECORD_STATUS_INVALID);
        DataSupport.update(Diary.class, valuesDiary, id);
        dumpListActivity.onDeleteSuccess();
    }

    public void deleteMemo(DumpListActivity dumpListActivity, int id) {
        ContentValues valuesMemo = new ContentValues();
        valuesMemo.put(STATUS, CustomApplication.RECORD_STATUS_INVALID);
        DataSupport.update(Memo.class, valuesMemo, id);
        dumpListActivity.onDeleteSuccess();
    }

    public void deleteCipher(DumpListActivity dumpListActivity, int id) {
        ContentValues valuesCipher = new ContentValues();
        valuesCipher.put(STATUS, CustomApplication.RECORD_STATUS_INVALID);
        DataSupport.update(CipherBean.class, valuesCipher, id);
        dumpListActivity.onDeleteSuccess();
    }



}
