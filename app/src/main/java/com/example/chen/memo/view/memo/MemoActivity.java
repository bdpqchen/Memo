package com.example.chen.memo.view.memo;

import android.content.Context;
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
import com.example.chen.memo.presenter.MemoPresenterImpl;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.TimeStampUtils;
import com.example.chen.memo.view.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.chen.memo.application.CustomApplication.ALARM_TIME_OLD;
import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_MEMO;
import static com.example.chen.memo.application.CustomApplication.ID;
import static com.example.chen.memo.application.CustomApplication.MEMO_ALARM_TIME;
import static com.example.chen.memo.application.CustomApplication.MEMO_CONTENT;

/**
 * Created by cdc on 16-10-14.
 */

public class MemoActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.editTextMemo)
    EditText editTextMemo;
    @InjectView(R.id.switch_Alarm)
    SwitchCompat switchAlarm;
    private Context mContext;
    private MemoPresenterImpl memoPresenterImpl;
    private long alarmTimeStamp = 0;
    private int  existAlarmTimeStamp = 0;
    private Bundle bundle;
    private int id = -1;
    private boolean isFirstChecked = true;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        mContext = getApplicationContext();

        setContentView(R.layout.activity_memo);
        ButterKnife.inject(this);
        toolbar.setTitle(R.string.toolbar_title_create_memo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switchAlarm.setOnCheckedChangeListener(this);
        memoPresenterImpl = new MemoPresenterImpl();
        switchAlarm.setText(TimeStampUtils.getDatetimeString(new Date().getTime()));

        bundle = getIntent().getExtras();
        if(bundle != null){
            String s = bundle.getString(MEMO_CONTENT);
            editTextMemo.setText(s);
            editTextMemo.setSelection(s.length());
            id = bundle.getInt(ID);
            existAlarmTimeStamp = bundle.getInt(MEMO_ALARM_TIME);
            if(existAlarmTimeStamp > 0) {
                switchAlarm.setText(TimeStampUtils.getDatetimeString(Long.parseLong(existAlarmTimeStamp + "0000")));
                switchAlarm.setTextColor(Color.GREEN);
                switchAlarm.setChecked(true);
            }
        }
        //避免第一次打开时执行 addAlarm()
        isFirstChecked = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_create_data, menu);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            if(!isFirstChecked){
                memoPresenterImpl.addAlarm(this, mContext);
            }
        }else{

            switchAlarm.setTextColor(Color.GRAY);
            LogUtils.v("compoundButton is not checked");
        }

    }

    public boolean getCheckBoxStatus(){
        return switchAlarm.isChecked();
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
                    if(bundle == null){
                        //创建一条新记录
                        memoPresenterImpl.createMemo(this, String.valueOf(editTextMemo.getText()), alarmTimeStamp);
                    }else{
                        //更新一条记录
                        // 并删除该记录可能包含的闹钟
                        Bundle alterBundle = new Bundle();
                        alterBundle.putInt(ALARM_TIME_OLD, existAlarmTimeStamp);
                        alterBundle.putLong(MEMO_ALARM_TIME, alarmTimeStamp);
                        alterBundle.putString(EDIT_TEXT_MEMO, String.valueOf(editTextMemo.getText()));
                        alterBundle.putInt(ID, id);
                        memoPresenterImpl.alterMemoDialog(this, alterBundle);
                    }
                }else{
                    onToastMessage(getString(R.string.not_finish_memo));
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void onSetDatetimeSuccess(long timeStamp){
        switchAlarm.setTextColor(Color.GREEN);
        switchAlarm.setText(TimeStampUtils.getDatetimeString(timeStamp));
        alarmTimeStamp = timeStamp;

    }

    public void onSetDatetimeFailure(long timeStamp) {
        switchAlarm.setTextColor(Color.GRAY);
        switchAlarm.setText(TimeStampUtils.getDatetimeString(timeStamp));
        switchAlarm.setChecked(false);
        onToastMessage(getResources().getString(R.string.set_datetime_error));

    }

    public void onToastMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        memoPresenterImpl.onDateSet(view, year, monthOfYear, dayOfMonth);
    }
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        memoPresenterImpl.onTimeSet(view, hourOfDay, minute, second);
    }
    public void onCancel(){
        switchAlarm.setTextColor(Color.GRAY);
        switchAlarm.setChecked(false);
    }
}
