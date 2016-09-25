package com.example.chen.memo.view.diary;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen.memo.R;
import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.presenter.ViewListPresenter;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.adapter.RecyclerAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cdc on 16-9-24.
 */

public class DiaryListActivity extends BaseActivity {

    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Object> dataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        ButterKnife.inject(this);

        ViewListPresenter.initData(CustomApplication.DIARY);

        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter();


    }



}
