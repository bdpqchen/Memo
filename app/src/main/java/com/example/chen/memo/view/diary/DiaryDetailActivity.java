package com.example.chen.memo.view.diary;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.presenter.DiaryPresenterImpl;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.dialog.SimpleDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.chen.memo.application.CustomApplication.DIARY_CONTENT;
import static com.example.chen.memo.application.CustomApplication.ID;

/**
 * Created by cdc on 16-10-10.
 */
public class DiaryDetailActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.diary_content)
    EditText etDiaryContent;

    private String diaryContent;
    private int id;
    private boolean isFirstClick = true;
    private static boolean isDoubleClick = false;
    private DiaryPresenterImpl diaryPresenter;
    private Bundle bundle;
    private boolean isEditMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);
        ButterKnife.inject(this);

        toolbar.setTitle(R.string.toolbar_title_view_diary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        diaryPresenter = new DiaryPresenterImpl(this);

        bundle = getIntent().getExtras();
        if(bundle != null){
            diaryContent = bundle.getString(DIARY_CONTENT);
            id = bundle.getInt(ID);
        }
        etDiaryContent.setText(diaryContent);
        etDiaryContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.diary_content:
                doubleClick();
                break;
        }
    }

    private void doubleClick(){
        if(!isDoubleClick){
            isDoubleClick = true;
            mHandler.sendEmptyMessageDelayed(0,300);
            if(isFirstClick){
                isFirstClick = false;
                onToastMessage(getString(R.string.enter_write_mode));
            }
        }else{
            //弹出键盘
            etDiaryContent.setFocusable(true);
            etDiaryContent.setFocusableInTouchMode(true);
            etDiaryContent.requestFocus();
            InputMethodManager inputManager = (InputMethodManager)etDiaryContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(etDiaryContent, 0);
            isEditMode = true;
            isDoubleClick = false;
        }
    }

    public void onToastMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isDoubleClick = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_done:
                bundle.putString(DIARY_CONTENT, String.valueOf(etDiaryContent.getText()));
                diaryPresenter.alterDiary(bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_create_data, menu);
        return true;
    }


    @Override
    public void onBackPressed(){
        if(etDiaryContent.getText().length() > 0 && isEditMode){
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
