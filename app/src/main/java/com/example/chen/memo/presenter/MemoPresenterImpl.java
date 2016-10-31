package com.example.chen.memo.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.example.chen.memo.R;
import com.example.chen.memo.model.AlterDataModelImpl;
import com.example.chen.memo.model.CreateDataModelImpl;
import com.example.chen.memo.service.AlarmReceiver;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.TimeStampUtils;
import com.example.chen.memo.view.dialog.SimpleDialog;
import com.example.chen.memo.view.memo.MemoActivity;
import com.example.chen.memo.view.memo.MemoListActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.chen.memo.application.CustomApplication.ALARM_TIME_OLD;
import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_MEMO;
import static com.example.chen.memo.application.CustomApplication.ID;
import static com.example.chen.memo.application.CustomApplication.MEMO_ALARM_TIME;
import static com.example.chen.memo.application.CustomApplication.MEMO_CONTENT;
import static com.example.chen.memo.application.CustomApplication.MSG;
import static com.example.chen.memo.application.CustomApplication.POSITION;


/**
 * Created by cdc on 16-10-14.
 */

public class MemoPresenterImpl  implements IMemoPresenter,
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    //当前的日期时间
    private int startYear = calendar.get(Calendar.YEAR);
    private int startMonth = (calendar.get(Calendar.MONTH)) ;
    private int startDay = calendar.get(Calendar.DAY_OF_MONTH);
    private int startHour = calendar.get(Calendar.HOUR_OF_DAY);
    private int startMinute = calendar.get(Calendar.MINUTE);
    //private int startSecond = calendar.get(Calendar.SECOND);

    //设定的日期时间
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second = 0;

    private CreateDataModelImpl createDataModel;
    private MemoActivity memoActivity;
    private Context context;
    private Bundle alterBundle;
    private MemoListActivity memoListActivity;
    private Bundle deleteBundle;

    private AlterDataModelImpl alterDataModel = new AlterDataModelImpl();

    @Override
    public void addAlarm(final MemoActivity memoActivity, Context context){

        this.memoActivity = memoActivity;
        this.context = context;
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(memoActivity, startYear, startMonth, startDay);
        datePickerDialog.show(memoActivity.getFragmentManager(), "Datepickerdialog");
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                memoActivity.onCancel();
            }
        });
    }

    public void createMemo(MemoActivity memoActivity, String memoContent, long alarmTimeStamp) {
        createDataModel = new CreateDataModelImpl();
        Bundle bundle = new Bundle();
        bundle.putString(MEMO_CONTENT, memoContent);
        if(alarmTimeStamp > 0){
            int intAlarmStamp = Integer.parseInt(((alarmTimeStamp + "").substring(0, 9)));
            createAlarm(alarmTimeStamp, intAlarmStamp, memoContent);
            bundle.putInt(MEMO_ALARM_TIME, intAlarmStamp);
        }else{
            bundle.putInt(MEMO_ALARM_TIME, 0);
        }
        createDataModel.createMemoData(memoActivity, bundle);
    }

    @Override
    public void alterMemo(MemoActivity memoActivity, Bundle alterBundle){
        long alarmStamp = alterBundle.getLong(MEMO_ALARM_TIME);
        //闹钟标识
        int id = alterBundle.getInt(ID);
        String memoContent = alterBundle.getString(EDIT_TEXT_MEMO);
        LogUtils.v(memoContent);
        int oldAlarmStamp = alterBundle.getInt(ALARM_TIME_OLD);
        Bundle bundle = new Bundle();

        //删除旧闹钟
        if(oldAlarmStamp > 0 && alarmStamp > 0 || !memoActivity.getCheckBoxStatus()){
            AlarmManager alarmMgr = (AlarmManager) memoActivity.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(memoActivity.getApplicationContext(), AlarmReceiver.class);
            PendingIntent pendIntent = PendingIntent.getBroadcast(memoActivity.getApplicationContext(),
                    oldAlarmStamp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.cancel(pendIntent);  //取消闹钟
            oldAlarmStamp = 0;
        }

        //更新记录数据
        if(alarmStamp > 0){
            int intAlarmStamp = Integer.parseInt((alarmStamp + "").substring(0, 9));
            createAlarm(alarmStamp, intAlarmStamp, memoContent);
            bundle.putInt(MEMO_ALARM_TIME, intAlarmStamp);
        }else{

            bundle.putInt(MEMO_ALARM_TIME, oldAlarmStamp);
        }

        bundle.putInt(ID, id);
        bundle.putString(MEMO_CONTENT, memoContent);

        alterDataModel.alterMemo(memoActivity, bundle);
    }

    public void alterMemoDialog(MemoActivity memoActivity, Bundle alterBundle) {
        this.memoActivity = memoActivity;
        this.alterBundle = alterBundle;
        SimpleDialog simpleDialog = new SimpleDialog(memoActivity);
        simpleDialog.createDialog(memoActivity.getString(R.string.dialog_title_alter_tips),
                memoActivity.getString(R.string.alter_diary_message),
                memoActivity.getString(R.string.btn_negative),negativeListener,
                memoActivity.getString(R.string.btn_positive),alterPositiveListener
        );
    }

    private DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    private DialogInterface.OnClickListener alterPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            alterMemo(memoActivity, alterBundle);
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createAlarm(long alarmTimeStamp, int intAlarmStamp, String memoContent){
        AlarmManager alarmManager = (AlarmManager) memoActivity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra(MSG, memoContent);
        PendingIntent pi = PendingIntent.getBroadcast(context.getApplicationContext(), intAlarmStamp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimeStamp, pi);
            }else{
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeStamp, pi);
            }
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeStamp, pi);
        }
    }

    private void setDate(int y, int m, int d) {
        this.year = y;
        this.month = m + 1;
        this.day = d;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(memoActivity, startHour,startMinute, true);
        setDate(year, monthOfYear, dayOfMonth);
        timePickerDialog.show(memoActivity.getFragmentManager(), "Timepickerdialog");
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                memoActivity.onCancel();
            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        long timeStamp = TimeStampUtils.setDatetimeString(year, month, day, hourOfDay, minute, second);
        long nowTimeStamp = new Date().getTime();
        if (timeStamp != 0 && nowTimeStamp < timeStamp) {
            memoActivity.onSetDatetimeSuccess(timeStamp);
        }else{
            memoActivity.onSetDatetimeFailure(timeStamp);
        }
    }

    public void deleteMemo(MemoListActivity memoListActivity, Bundle bundle){
        this.memoListActivity = memoListActivity;
        SimpleDialog simpleDialog = new SimpleDialog(memoListActivity);
        this.deleteBundle = bundle;

        simpleDialog.createDialog(memoListActivity.getString(R.string.dialog_title_delete_tips),
                memoListActivity.getString(R.string.delete_diary_message),
                memoListActivity.getString(R.string.btn_negative),negativeListener,
                memoListActivity.getString(R.string.btn_positive),deletePositiveListener
        );
    }

    private DialogInterface.OnClickListener deletePositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //删除该item记录
            alterDataModel.deleteDiary(deleteBundle.getInt(ID,0));
            //在列表中移除
            memoListActivity.onDeleteItem(deleteBundle.getInt(POSITION,0));
        }
    };
}
