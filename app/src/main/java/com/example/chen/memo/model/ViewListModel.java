package com.example.chen.memo.model;

import com.example.chen.memo.bean.DiaryBean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdc on 16-9-24.
 */

public class ViewListModel implements ViewListModelImpl {

    @Override
    public void initDiaryData() {
        ArrayList<DiaryBean> datalist = new ArrayList<>();
        //datalist.add();
        List<DiaryBean> diaryList = DataSupport.select("title",)
    }

    @Override
    public void initMemoData() {

    }

    @Override
    public void initCipherData() {
    }
}
