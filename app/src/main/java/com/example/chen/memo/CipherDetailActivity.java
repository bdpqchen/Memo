package com.example.chen.memo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.memo.MyApplication;
import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.SqlHelper;

import java.util.Date;

/**
 * Created by dcchen on 16-6-6.
 */
public class CipherDetailActivity extends Activity {

    private MyApplication app;
    private String decryptingCode = "";
    private String encryptingCode = "";
    private EditText pwd1;
    private EditText pwd2;
    private EditText tittleView;
    private EditText accountView;
    private String seed = "chendingchen";
    private TextView back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        app.addActivity(this);

        TextView title = (TextView) findViewById(R.id.title_user);
        title.setText(this.getString(R.string.view_cipher));
        pwd1 = (EditText) findViewById(R.id.pwd1);
        tittleView = (EditText) findViewById(R.id.pwd_tittle);
        accountView = (EditText) findViewById(R.id.account);

        Intent intent = getIntent();
        if (intent != null) {
            final String pid = intent.getStringExtra("pid");
            String table = intent.getStringExtra("table");
            String name = intent.getStringExtra("name");
            String account = intent.getStringExtra("account");
            String value = intent.getStringExtra("value");
            //Log.i("------------",value);
            try {
                String pwd = SimpleCrypto.deCrypto(value,seed);
                pwd1.setText(pwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tittleView.setText(name);
            accountView.setText(account);

        }

        //定义保存按钮事件
        FloatingActionButton save = (FloatingActionButton) findViewById(R.id.finish);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String originalText = pwd1.getText().toString();
                final String pwd_tittle = tittleView.getText().toString();
                final String account = accountView.getText().toString();
                try {
                    encryptingCode = SimpleCrypto.enCrypto(originalText,seed);
                    //Log.i("原加密结果为 ", encryptingCode);
                    if (!account.equals("") || !pwd_tittle.equals("") || !originalText.equals("")) {
                        SqlHelper dbHelper = new SqlHelper(CipherDetailActivity.this, "diary_db", null, app.dbversion);
                        final SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor c = db.query(true, "cipher", new String[]{"name", "account", "validity"}, "name=? and account=? and validity>?", new String[]{pwd_tittle, account, "0"}, null, null, null, null);
                        if (c.getCount() != 0) {
                            //Log.i("", "找到相同记录操作");

                            AlertDialog.Builder builder = new AlertDialog.Builder(CipherDetailActivity.this);
                            builder.setMessage("已经有了相同的账户,要替换它吗？");
                            builder.setTitle("替换提示");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    CipherDetailActivity.this.finish();
                                    final Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                    long datetime = curDate.getTime();
                                    ContentValues cv = new ContentValues();
                                    cv.put("value",encryptingCode);
                                    cv.put("time", datetime);
                                    db.update("cipher",cv,"name=? and account=? and validity>?",new String[]{pwd_tittle, account, "0"});
                                    db.close();
                                    publicIntent();
                                }
                            });

                            builder.setNegativeButton("反悔", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }

                            });
                            builder.create().show();
                        }else {
                            ContentValues cv = new ContentValues();
                            final Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                            long datetime = curDate.getTime();
                            cv.put("name", pwd_tittle);
                            cv.put("account", account);
                            cv.put("value", encryptingCode);
                            cv.put("validity", 1);
                            cv.put("time", datetime);
                            //调用insert方法，将数据插入数据库
                            db.insert("cipher", null, cv);
                            //关闭数据库
                            db.close();
                            Toast.makeText(getApplicationContext() ,"保存成功" , Toast.LENGTH_SHORT).show();
                            publicIntent();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "请完善以上的内容", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
       // Log.i("解密结果", decryptingCode);
        //返回textview事件
        back = (TextView) findViewById(R.id.title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onBackPressed() {
        publicIntent();
    }

    public void publicIntent(){
        finish();
        Intent intent = new Intent();
        intent.setClass(CipherDetailActivity.this, ViewCipherActivity.class);
        startActivity(intent);
    }


}
