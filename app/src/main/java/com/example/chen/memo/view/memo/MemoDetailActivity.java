/*
package com.example.chen.memo.view.memo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.presenter.DiaryPresenterImpl;
import com.example.chen.memo.presenter.MemoPresenterImpl;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.view.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.chen.memo.application.CustomApplication.DIARY_CONTENT;
import static com.example.chen.memo.application.CustomApplication.ID;
import static com.example.chen.memo.application.CustomApplication.MEMO_CONTENT;

*/
/**
 * Created by cdc on 16-10-14.
 *//*


public class MemoDetailActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.editTextMemo)
    EditText editTextMemo;
    @InjectView(R.id.switch_Alarm)
    SwitchCompat switchAlarm;
    private Bundle bundle;
    private String memoContent;
    private int id;
    private MemoPresenterImpl memoPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_memo_detail);
        ButterKnife.inject(this);

        toolbar.setTitle(R.string.toolbar_title_view_diary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        memoPresenterImpl = new MemoPresenterImpl(this);
        bundle = getIntent().getExtras();
        if(bundle != null){
            memoContent = bundle.getString(MEMO_CONTENT);
            id = bundle.getInt(ID);
        }
        editTextMemo.setText(memoContent);
        switchAlarm.setOnCheckedChangeListener(this);

        //editTextMemo.setOnClickListener(this);
        //.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_create_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_done:
                String s = String.valueOf(editTextMemo.getText());
                if(!s.equals("")){
                    memoPresenterImpl.createMemo(this, String.valueOf(editTextMemo.getText()), alarmTimeStamp);
                }else{
                    onToastMessage(getString(R.string.not_finish_memo));
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onToastMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            memoPresenterImpl.addAlarm(this, mContext);

        }else{
            switchAlarm.setTextColor(Color.GRAY);
            LogUtils.v("compoundButton is not checked");
        }
    }
}

*/
