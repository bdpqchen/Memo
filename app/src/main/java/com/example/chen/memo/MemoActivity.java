/*

package com.example.chen.memo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.chen.memo.application.CustomApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;



public class MemoActivity extends Activity {

    private MyApplication app;
    private CheckBox cbox;
    private EditText text;
    private TextView hiddenId;
    private int year, month, day, minute, hour, i;
    private int intDate ;
    private long publishTime = 0;

    private TextView textTime;
    private TextView success;
    private TextView title ,title_back;
    private String table = "memo";
    private int pid = 0;
    private SqlHelper dbHelper = new SqlHelper(MemoActivity.this, "diary_db", null, CustomApplication.getDbversion());
    private int intDateMore;


    @Override
    public void onCreate(Bundle savedInstanceStart) {
        super.onCreate(savedInstanceStart);
        setContentView(R.layout.memo_activity);
        app.addActivity(this);

        textTime = (TextView) findViewById(R.id.timeText);
        success  = (TextView) findViewById(R.id.success);
        title= (TextView) findViewById(R.id.title_user);
        cbox = (CheckBox) findViewById(R.id.cbox);
        text = (EditText) findViewById(R.id.text);
        title.setText(this.getString(R.string.view_memo));
        hiddenId = (TextView) findViewById(R.id.hiddenId);
        hiddenId.setVisibility(View.GONE);

        final Intent intent = getIntent();
        if(intent != null && intent.getStringExtra("pid") != null) {
            text.setText(intent.getStringExtra("text"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String alarmTimeStr = intent.getStringExtra("alarm_time");
            if (alarmTimeStr != null && !alarmTimeStr.equals("0")) {
                long intDatetime = Long.parseLong(alarmTimeStr.concat("0000"));
                String alarmDatetime = format.format(intDatetime);
                textTime.setText(String.valueOf(alarmDatetime));
                cbox.setChecked(true);
            }else{
                cbox.setChecked(false);
            }
            intDate = Integer.parseInt(intent.getStringExtra("alarm_time"));
            intDateMore = intDate;
            publishTime = Long.parseLong(intent.getStringExtra("publish_time"));
            pid = Integer.parseInt(intent.getStringExtra("pid"));
        }

        //checkbox监听
        cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                text = (EditText) findViewById(R.id.text);
                String undealtText = String.valueOf(text.getText());
                if (undealtText.equals("")) {
                    Toast.makeText(MemoActivity.this, "备忘内容还没有填写.....", Toast.LENGTH_SHORT).show();
                    cbox.setChecked(false);
                } else {
                    if (cbox.isChecked()) {
                        pickDate();
                    }else{
                        //取消闹钟,
                        //Log.i("intData", String.valueOf(intDate));
                        cancelAlarm(intDate);
                        //清除闹钟标识
                        clearAlarmTime(intDate);

                    }
                }
            }
        });

        //完成按钮监听
        FloatingActionButton finish = (FloatingActionButton) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenId = (TextView) findViewById(R.id.hiddenId);
                String datetime = (String) hiddenId.getText();
                String s = String.valueOf(text.getText());
                if (s.equals("")) {
                    //若备忘录为空则直接返回
                    Toast.makeText(MemoActivity.this, "没有备忘信息", Toast.LENGTH_SHORT).show();
                    startActivity();
                } else {
                    if (datetime.equals("")) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        if(pid == 0) {
                            //判断是创建一条新记录还是修改原记录
                            long timeGetTime = new Date().getTime();
                            //若未找到隐藏的唯一标识则认为未定义闹钟提醒，有备忘数据
                            ContentValues cv = new ContentValues();
                            cv.put("content", s);
                            cv.put("validity", 1);
                            cv.put("time", 0);
                            cv.put("publish_time", timeGetTime);
                            db.insert("memo", null, cv);
                            db.close();
                            writeFinish();
                        }else{
                            ContentValues cv = new ContentValues();
                            cv.put("content", s);
                            if (cbox.isChecked()) {
                                cv.put("time",intDate);
                            }
                            cv.put("publish_time",publishTime);
                            db.update(table,cv,"_id=" + pid ,null);
                            db.close();
                            writeFinish();
                        }
                    } else {
                        //找到标识则已添加到数据库，直接返回
                        writeFinish();
                    }
                }
            }
        });

        title_back = (TextView) findViewById(R.id.title_back);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity();
            }
        });
    }

    //
    public void writeFinish() {
        Toast.makeText(MemoActivity.this, "备忘保存成功", Toast.LENGTH_SHORT).show();
        finish();
        startActivity();
    }
    //清除闹钟标识
    public void clearAlarmTime(int intDate){
        String strSQL = "UPDATE " + table + " SET time=0 WHERE time=" + intDate;
        SQLiteOpenHelper dbHelper = new SqlHelper(MemoActivity.this, "diary_db", null, CustomApplication.getDbversion());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(strSQL);
        db.close();
    }

    //取消闹钟
    public void cancelAlarm(int intDate) {
        //intDate = Integer.parseInt(datetime);
        if(intDate!=0) {
            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
            PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    intDate, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.cancel(pendIntent);  //取消闹钟
        }
        textTime.setText("");
        success.setText("");
    }

    public void pickerTime() {
        //初始化timepicker也会执行onTimeSet()代码
        i=0;
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        new TimePickerDialog(MemoActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                if (i == 1) {
                    hour = hourOfDay;
                    minute = minutes;
                    int y = year;
                    int m = month;
                    int d = day;
                    String datetime = y + "-" + m + "-" + d + " " + hour + ":" + minute + ":00";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //Log.i("the time", String.valueOf(format));
                    long time = 0;
                    long dateUnix = 0;
                    //将intent 过来的时间转换为Unix时间戳，与当前时间比较、计算
                    try {
                        Date q = format.parse(datetime);
                        //未来时间
                        dateUnix = q.getTime();
                        final Date CurTime = new Date(System.currentTimeMillis());//获取当前时间
                        long datetime1 = CurTime.getTime();
                        if (dateUnix > datetime1) {
                            time = dateUnix - datetime1;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (time > 0) {
                        Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
                        //备忘录数据
                        EditText msg = (EditText) findViewById(R.id.text);
                        String value = msg.getText().toString();
                        //将intData转换为int类型 截取前9位数字 作为该闹钟的唯一标识  并作为数据库中的唯一备忘录标识
                        String IntDateUnix = dateUnix + "";
                        intDate = Integer.parseInt(IntDateUnix.substring(0, 9));
                        //数据库保存备忘录
                        long timeGetTime = new Date().getTime();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        //判断是创建一条新记录还是修改原记录
                        if(pid == 0) {
                            //若未找到隐藏的唯一标识则认为未定义闹钟提醒，有备忘数据
                            SqlHelper dbHelper = new SqlHelper(MemoActivity.this, "diary_db", null, CustomApplication.getDbversion());
                            ContentValues cv = new ContentValues();
                            cv.put("content", value);
                            cv.put("validity", 1);
                            cv.put("time", intDate);
                            cv.put("publish_time", timeGetTime);
                            db.insert("memo", null, cv);
                            db.close();
                            //writeFinish();
                        }else{
                            ContentValues cv = new ContentValues();
                            cv.put("content", String.valueOf(text.getText()));
                            cv.put("time",intDateMore);
                            cv.put("publish_time",publishTime);
                            db.update(table,cv,"_id=" + pid ,null);
                            db.close();
                            //writeFinish();
                        }
                        //传递给广播的Intent数据
                        intent.putExtra("msg", value);
                        //闹钟时间，作为数据库中标识
                        intent.putExtra("time", dateUnix);

                        PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                intDate, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        // 秒后发送广播，只发送一次
                        int triggerAtTime = (int) (SystemClock.elapsedRealtime() + time);
                        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendIntent);
                        //设置时间文本
                        textTime.setText(datetime);
                        success.setText("√");
                        textTime.setTextColor(Color.parseColor("#00ff00"));
                        success.setTextColor(Color.parseColor("#00ff00"));
                        //设置隐藏textview 给返回键提供索引
                        String strDate = intDate + "";
                        hiddenId = (TextView) findViewById(R.id.hiddenId);
                        hiddenId.setText(strDate);
                    } else {
                        textTime.setText(datetime);
                        success.setText("×");
                        textTime.setTextColor(Color.parseColor("#ff0000"));
                        success.setTextColor(Color.parseColor("#ff0000"));
                        cbox.setChecked(false);
                        Toast.makeText(MemoActivity.this, "时间设定错误", Toast.LENGTH_SHORT).show();
                    }
                }
                //初始化timepicker也会执行onTimeSet()代码
                i++;
            }
        }, cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE), true ).show();
    }

    public void pickDate() {
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog mDialog = new DatePickerDialog(this, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                year = datePicker.getYear();
                month = datePicker.getMonth() + 1;
                day = datePicker.getDayOfMonth();
                pickerTime();
            }
        });

        mDialog.show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            hiddenId = (TextView) findViewById(R.id.hiddenId);
            String datetime = (String) hiddenId.getText();
            if (datetime.equals("") || datetime.equals("")) {
                //没有获取到设置的闹钟时间，直接返回
                //finish();
                startActivity();
            } else {
                //获取到隐藏的时间数据，则删除数据库的记录 、 取消闹钟
                SqlHelper dbHelper = new SqlHelper(MemoActivity.this , "diary_db" , null,CustomApplication.getDbversion());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("validity", 0);
                db.update("memo", values, "time=?", new String[]{datetime});
                db.close();
                //取消闹钟
                cancelAlarm(intDate);
                //finish();
                startActivity();
            }
        }
        return false;
    }


    public void onBackPressed() {
        startActivity();
    }

    public void startActivity() {
        finish();
        Intent intent = new Intent();
        intent.setClass(MemoActivity.this, ViewMemoActivity.class);
        startActivity(intent);

    }

}
*/
