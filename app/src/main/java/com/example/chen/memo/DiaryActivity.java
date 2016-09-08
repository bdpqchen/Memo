package com.example.chen.memo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DCchen on 2016/4/10.
 */
public class DiaryActivity extends MainActivity {

    private MyApplication app;
    private TextView time , back;
    private EditText diary;

    @Override
    public void onCreate(Bundle savedInstanceStart) {
        super.onCreate(savedInstanceStart);
        setContentView(R.layout.diary_activity);
        app.addActivity(this);
        TextView title = (TextView) findViewById(R.id.title_user);
        title.setText(this.getString(R.string.write_diary));
        //获取当前时间
        time = (TextView) findViewById(R.id.time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        final Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        time.setText(str);

        //定义保存按钮事件
        FloatingActionButton save = (FloatingActionButton)findViewById(R.id.save);
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                diary = (EditText) findViewById(R.id.diary);
                String value = diary.getText().toString();
                if(value.length() >= 1) {
                    SqlHelper dbHelper = new SqlHelper(DiaryActivity.this, "diary_db", null, app.dbversion);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    long datetime = curDate.getTime();
                    cv.put("diary", value);
                    cv.put("validity", 1);
                    cv.put("time", datetime);
                    //调用insert方法，将数据插入数据库
                    db.insert("diary", null, cv);
                    //关闭数据库
                    db.close();
                    Toast.makeText(getApplicationContext(), "成功保存日记", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent =new Intent();
                    intent.setClass(DiaryActivity.this,ViewDiaryActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "日记好像没有写完哦~*~~", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //返回textview事件
        back = (TextView) findViewById(R.id.title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent();
        intent.setClass(DiaryActivity.this,ViewDiaryActivity.class);
        startActivity(intent);
    }
}

