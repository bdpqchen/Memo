package com.example.chen.memo.presenter;

import com.example.chen.memo.model.ViewListModelImpl;
import com.example.chen.memo.view.dump.DumpListActivity;

/**
 * Created by cdc on 16-10-19.
 */

public class DumpPresenterImpl  {



    public void initData(DumpListActivity dumpListActivity){

        ViewListModelImpl viewListModel = new ViewListModelImpl();

        viewListModel.initDumpData(dumpListActivity);

    }
}
