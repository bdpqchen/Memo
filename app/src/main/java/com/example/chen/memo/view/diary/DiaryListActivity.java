package com.example.chen.memo.view.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.chen.memo.R;
import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.presenter.ViewListPresenterImpl;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.adapter.RecyclerDiaryAdapter;
import com.example.chen.memo.view.common.DividerItemDecoration;
import com.example.chen.memo.view.common.NextActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cdc on 16-9-24.
 */

public class DiaryListActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.recyclerview) RecyclerView mRecyclerView;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.fab) FloatingActionButton fab;
    private LinearLayoutManager linearLayoutManager;
    private List<Diary> datalist;
    private RecyclerDiaryAdapter recyclerAdapter;
    private NextActivity nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        ButterKnife.inject(this);

        toolbar.setTitle(R.string.diary_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewListPresenterImpl.initData(CustomApplication.DIARY, this);

        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerDiaryAdapter(this, datalist);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(16));

        fab.setOnClickListener(this);

    }

    public void onInitSuccess(List<Diary> datalist) {
        this.datalist = datalist;
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(this,DiaryActivity.class));
                break;
        }

    }


}
