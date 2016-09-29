/*
package com.example.chen.memo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.memo.application.CustomApplication;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

*/
/**
 * Created by dcchen on 16-6-4.
 *//*

public class ViewCipherActivity extends Activity{

    private MyApplication app;
    private ListView listView;
    private Button addCipher;
    private static boolean isExit = false;
    private SqlHelper dbHelper = new SqlHelper(ViewCipherActivity.this, "diary_db", null, CustomApplication.getDbversion());
    private String table = "cipher";
    private TextView back;

    //@Override
    public void onCreate(Bundle savedInstanceStart){
        super.onCreate(savedInstanceStart);
        //setContentView view_diary_title 显示列表框架，view_diary数据列表
        setContentView(R.layout.view_cipher_title_activity);
        app.addActivity(this);

        TextView title = (TextView) findViewById(R.id.title_user);
        title.setText(this.getString(R.string.view_cipher));
        listView = (ListView) findViewById(R.id.list);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, getData(), R.layout.view_cipher_activity, new String[]{"name","account","value","_id"}, new int[]{R.id.name,R.id.account,R.id.value,R.id.textHidden});
        listView.setAdapter(simpleAdapter);

        //查看详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //ArrayMap<String, String> map = (ArrayMap<String, String>) listView.getItemAtPosition(position);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("name",map.get("name"));
                intent.putExtra("account", map.get("account"));
                intent.putExtra("value", map.get("value"));
                intent.putExtra("pid", map.get("pid"));
                intent.putExtra("table", "cipher");
                intent.setClass(ViewCipherActivity.this, CipherDetailActivity.class);
                startActivity(intent);
            }
        });

        */
/**
         * 长按提示删除
         * *//*

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                final String pid =map.get("pid");

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewCipherActivity.this);
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

        addCipher = (Button) findViewById(R.id.add);
        addCipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent();
                intent.setClass(ViewCipherActivity.this,CipherDetailActivity.class);
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

    private List<HashMap<String, Object>> getData(){
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("cipher",new String[]{"name","account","value","_id"},"validity = ?",new String[]{"1"},null,null,"_id desc");
        while(cursor.moveToNext()){
            HashMap<String, Object> map = new HashMap<>();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String account = cursor.getString(cursor.getColumnIndex("account"));
            String value = cursor.getString(cursor.getColumnIndex("value"));
            String pid = cursor.getString(cursor.getColumnIndex("_id"));
            ArrayList arrayList = new ArrayList();

                map.put("name", name);
                map.put("account",account);
                map.put("pid",pid);
                map.put("value",value);
                data.add(map);
        }
        db.close();
        return data;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //System.out.println("------------------");
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            //System.out.println("------------------");
            finish();
            Intent intent = new Intent();
            intent.setClass(ViewCipherActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return false;
    }

}
*/
