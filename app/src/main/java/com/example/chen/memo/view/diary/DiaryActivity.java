package com.example.chen.memo.view.diary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.event.DiaryEvent;
import com.example.chen.memo.presenter.DiaryPresenterImpl;
import com.example.chen.memo.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cdc on 16-9-27.
 */

public class DiaryActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.editTextDiary)
    EditText editTextDiary;
    @InjectView(R.id.nowTime)
    TextView nowTime;
    @InjectView(R.id.fabSave)
    FloatingActionButton fabSave;

    private Bundle bundle = new Bundle();
    private DiaryPresenterImpl diaryPresenterImpl;
    public static String EDIT_TEXT_DIARY = "exit_text_diary";
    public static String NOW_TIME = "now_time";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_activity);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);


        toolbar.setTitle(R.string.create_diary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabSave.setOnClickListener(this);

        diaryPresenterImpl = new DiaryPresenterImpl(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabSave:
                bundle.putString(EDIT_TEXT_DIARY, String.valueOf(editTextDiary.getText()));
                //bundle.putString(NOW_TIME, String.valueOf(nowTime.getText()));
                diaryPresenterImpl.createDiary(bundle);
                break;
        }


    }

    @Subscribe
    public void onEventMainThread(DiaryEvent event){
        String msg = event.getMsg();
        //onToastMessage(msg);
    }

    public void onToastMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
