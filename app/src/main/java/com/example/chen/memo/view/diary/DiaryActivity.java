package com.example.chen.memo.view.diary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.presenter.DiaryPresenterImpl;
import com.example.chen.memo.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_DIARY;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.inject(this);
        toolbar.setTitle(R.string.toolbar_title_create_diary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fabSave.setOnClickListener(this);
        diaryPresenterImpl = new DiaryPresenterImpl(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public void onToastMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
