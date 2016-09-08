package com.example.chen.memo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by DCchen on 2016/4/12.
 */
public class ViewDiaryActivity extends Activity {

    private MyApplication app;
    private ListView listView;
    private Button add;
    private TextView back;
    private SqlHelper dbHelper = new SqlHelper(ViewDiaryActivity.this, "diary_db", null, app.dbversion);
    private String table = "diary";
    @Override
    public void onCreate(Bundle savedInstanceStart) {
        super.onCreate(savedInstanceStart);
        //setContentView view_diary_title 显示列表框架，view_diary数据列表
        setContentView(R.layout.view_diary_title_activity);
        app.addActivity(this);
        TextView title = (TextView) findViewById(R.id.title_user);
        title.setText(this.getString(R.string.view_diary));
        listView = (ListView) findViewById(R.id.list);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, getData(), R.layout.view_diary_activity, new String[]{"time", "text", "_id"}, new int[]{R.id.time, R.id.text, R.id.textHidden});
        listView.setAdapter(simpleAdapter);

        /**点击查看日记全文*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String text = map.get("text");
                String time = map.get("time");
                String pid = map.get("pid");
                Intent intent = new Intent();
                intent.putExtra("text", text);
                intent.putExtra("time", time);
                intent.putExtra("pid", pid);
                //intent.putExtra("table", "diary");
                intent.setClass(ViewDiaryActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 长按提示删除日记
         * */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                final String pid =map.get("pid");

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewDiaryActivity.this);
                builder.setMessage("确定要删除吗？");
                //builder.setTitle("删除提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //ViewDiaryActivity.this.finish();
                        String strSQL = "UPDATE " + table + " SET validity=0 WHERE _id=" + pid;
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        db.execSQL(strSQL);
                        db.close();
                        onCreate(null);
                        Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("反悔", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                builder.create().show();
                return true;
            }
        });

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent();
                intent.setClass(ViewDiaryActivity.this, DiaryActivity.class);
                startActivity(intent);
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

    private List<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("diary", new String[]{"diary", "time", "_id"}, "validity > ?", new String[]{"0"}, null, null, "time desc");
        while (cursor.moveToNext()) {
            //用一次实例化一次
            HashMap<String, Object> map = new HashMap<String, Object>();
            long time = cursor.getLong(cursor.getColumnIndex("time"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date time1 = new Date(time);
            String time2 = format.format(time1);
            String diary = cursor.getString(cursor.getColumnIndex("diary"));
            String pid = cursor.getString(cursor.getColumnIndex("_id"));

            if (diary.length() >= 1) {
                map.put("time", time2);
                map.put("text", diary);
                map.put("pid", pid);
                data.add(map);
            }
        }
        db.close();
        return data;
    }

    public void onBackPressed() {
        finish();
    }

}
