package com.example.chen.memo.view.diary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.presenter.DiaryPresenterImpl;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.dialog.SimpleDialog;

import org.greenrobot.eventbus.EventBus;
import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_DIARY;

/**
 * Created by cdc on 16-9-27.
 */

public class DiaryActivity extends BaseActivity{

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.editTextDiary) EditText editTextDiary;

    private Bundle bundle = new Bundle();
    private DiaryPresenterImpl diaryPresenterImpl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.inject(this);
        toolbar.setTitle(R.string.toolbar_title_create_diary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        diaryPresenterImpl = new DiaryPresenterImpl(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_done:
                bundle.putString(EDIT_TEXT_DIARY, String.valueOf(editTextDiary.getText()));
                //bundle.putString(NOW_TIME, String.valueOf(nowTime.getText()));
                diaryPresenterImpl.createDiary(bundle);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_create_data, menu);
        return true;
    }

    public void onToastMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed(){
        if(editTextDiary.getText().length() > 0){
            SimpleDialog simpleDialog = new SimpleDialog(this);
            simpleDialog.createDialog(getString(R.string.warn), getString(R.string.sure_do_not_save_data), getString(R.string.btn_negative), null, getString(R.string.btn_positive), backPositiveListener);
        }else{
            finish();
        }
    }

    private DialogInterface.OnClickListener backPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    };

}
