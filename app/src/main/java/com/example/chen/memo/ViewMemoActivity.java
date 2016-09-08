package com.example.chen.memo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DCchen on 2016/4/18.
 */
public class ViewMemoActivity extends Activity {

    private MyApplication app ;
    private ListView listView;
    private Button addMemo;
    private SqlHelper dbHelper = new SqlHelper(ViewMemoActivity.this, "diary_db", null, app.dbversion);
    private String table = "memo";
    private TextView back;

    public void onCreate(Bundle savedInstanceStart) {
        super.onCreate(savedInstanceStart);
        setContentView(R.layout.view_memo_title_activity);
        app.addActivity(this);

        TextView title = (TextView) findViewById(R.id.title_user);
        title.setText(this.getString(R.string.view_memo));
        listView = (ListView) findViewById(R.id.list);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, getData(), R.layout.view_memo_activity,
              new String[]{"publish_time", "content", "_id"}, new int[]{R.id.time, R.id.text, R.id.textHidden});
        listView.setAdapter(simpleAdapter);
        /**点击查看全文
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("text", map.get("content"));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=map.get("publish_time")+":00";
                Date date = null;
                String datetime = "";
                try {
                    date = format.parse(time);
                    datetime = String.valueOf(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                System.out.println(date.getTime());
                intent.putExtra("publish_time",datetime);
                intent.putExtra("alarm_time", map.get("alarm_time"));
                intent.putExtra("pid", map.get("pid"));
                //intent.putExtra("table", "memo");
                intent.setClass(ViewMemoActivity.this, MemoActivity.class);
                finish();
                startActivity(intent);
            }
        });

        /**
         * 长按提示删除
         * */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                final String pid =map.get("pid");
                final int alarmTime = Integer.parseInt((map.get("alarm_time")));

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewMemoActivity.this);
                builder.setMessage("确定要删除吗？");
                //builder.setTitle("删除提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //ViewDiaryActivity.this.finish();
                        //取消闹钟
                        if(alarmTime !=0 ){
//                            MemoActivity memoAct = new MemoActivity();
//                            memoAct.cancelAlarm(alarmTime);
                            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
                            PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    alarmTime, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmMgr.cancel(pendIntent);  //取消闹钟
                        }

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

        addMemo = (Button) findViewById(R.id.add);
        addMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
                    Intent intent =new Intent();
                    intent.setClass(ViewMemoActivity.this,MemoActivity.class);
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
        Cursor cursor = db.query("memo", new String[]{"content", "publish_time", "_id","time"},
                                 "validity > ?", new String[]{"0"}, null, null, "publish_time desc");
        while (cursor.moveToNext()) {
            //用一次实例化一次
            HashMap<String, Object> map = new HashMap<String, Object>();
            long time = cursor.getLong(cursor.getColumnIndex("publish_time"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date time1 = new Date(time);
            String time2 = format.format(time1);
            String memo  = cursor.getString(cursor.getColumnIndex("content"));
            String pid   = cursor.getString(cursor.getColumnIndex("_id"));
            String alarmTime = String.valueOf(cursor.getInt(cursor.getColumnIndex("time")));
            map.put("publish_time", time2);
            map.put("content", memo);
            map.put("pid", pid);
            map.put("alarm_time",alarmTime);
            data.add(map);
        }
        db.close();
        return data;
    }


}


