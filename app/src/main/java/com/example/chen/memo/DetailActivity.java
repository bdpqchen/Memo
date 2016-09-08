package com.example.chen.memo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by DCchen on 2016/4/16.
 */
public class DetailActivity extends Activity {
    private MyApplication app;
    private EditText textView;
    private TextView timeView;
    private String activity;
    private String table;
    private String content = table = activity = "diary";
    private TextView back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        app.addActivity(this);

        textView= (EditText) findViewById(R.id.text);
        timeView =(TextView)findViewById(R.id.time);
        Intent intent = getIntent();
        final String pid = intent.getStringExtra("pid");
        String time = intent.getStringExtra("time");
        String text = intent.getStringExtra("text");
        TextView title = (TextView) findViewById(R.id.title_user);
        title.setText(this.getString(R.string.view_diary));
        timeView.setText(time);
        textView.setText(text);
        Editable etext = textView.getText();
        Selection.setSelection(etext, etext.length());
        //intent.setClass(DetailActivity.this, ViewDiaryActivity.class);
        /**
        * 修改日记操作，同删除类似
        * */
        FloatingActionButton alter = (FloatingActionButton) findViewById(R.id.alter);
        alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前edittext内容，即要修改的内容
                String diaryA = String.valueOf(textView.getText());
                Integer pidLong = Integer.parseInt(pid);
                SqlHelper dbHelper = new SqlHelper(DetailActivity.this, "diary_db", null, app.dbversion);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(content, diaryA);
                db.update(table, values, "_id=?", new String[]{String.valueOf(pidLong)});
                db.close();
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//刷新
                intent.setClass(DetailActivity.this, ViewDiaryActivity.class);
                startActivity(intent);
                finish();

            }
        });

        //返回textview事件
        back = (TextView) findViewById(R.id.title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



}