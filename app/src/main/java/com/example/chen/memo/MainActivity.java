package com.example.chen.memo;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.xml.transform.stream.StreamSource;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //数据库版本号
    private MyApplication app;
    private static boolean isExit = false;
    SharedPreferences settings ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app.addActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
//        MyApplication.getDbversion();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
                return false;
            } else {
                exit();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            app.finishAll();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up btn_fulfil, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.diary) {
            boolean locked = isLocked("diary_lock");
            if(locked) {
                checkLogin(ViewDiaryActivity.class);
            }else{
                startActivity(ViewDiaryActivity.class);
            }
        } else if (id == R.id.memo) {
            boolean locked = isLocked("memo_lock");
            if(locked) {
                checkLogin(ViewMemoActivity.class);
            }else{
                startActivity(ViewMemoActivity.class);
            }
        } else if (id == R.id.cipher) {
            boolean locked = isLocked("cipher_lock");
            if(locked) {
                checkLogin(ViewCipherActivity.class);
            }else{
                startActivity(ViewCipherActivity.class);
            }
        } else if (id == R.id.settings) {
            settings = getSharedPreferences("setting", 0);
            int first = settings.getInt("first", 1);
            if(first==1) {
                //第一次点击设置，提示设置唯一密码方可进入
                final PasswordDialog dialog = new PasswordDialog(MainActivity.this);
                dialog.setTitle("首次进入，请设置唯一密码");
                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText1 = (EditText) dialog.getEditText1();
                        EditText editText2 = (EditText) dialog.getEditText2();
                        String pwd1 = String.valueOf(editText1.getText());
                        String pwd2 = String.valueOf(editText2.getText());

                        if (pwd1.equals(pwd2) && !pwd1.equals("")) {
                            try {
                                String pwd = SimpleCrypto.enCrypto(pwd1, "unique_pwd");
                                //设置sharedpreference为可写状态
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putInt("first", 0);
                                editor.putString("unique_pwd",pwd);
                                editor.commit();
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this,"初始密码设置成功",Toast.LENGTH_SHORT).show();
                                startActivity(SettingsActivity.class);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            //密码输入不一致
                            Toast.makeText(MainActivity.this,"两次密码输入不一致，请重新输入",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setOnNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else {
                checkLogin(SettingsActivity.class);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private  void startActivity(Class<?> ClassName){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ClassName);
        startActivity(intent);
    }

    //判断功能密码开启状态
    private boolean isLocked(String type){
        settings = getSharedPreferences("setting", 0);
        int locked = settings.getInt(type,0);
        return locked == 1;
    }

    //进入密码验证
    private void checkLogin(final Class<?> ClassName){
        final CustomDialog dialog = new CustomDialog(MainActivity.this);
        final EditText editText = (EditText) dialog.getEditText();//方法在CustomDialog中实现
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something yourself
                String unique_pwd = settings.getString("unique_pwd","");
                String input_pwd = String.valueOf(editText.getText());
                try{
                    String pwd = SimpleCrypto.enCrypto(input_pwd, "unique_pwd");
                    if(pwd.equals(unique_pwd) && !input_pwd.equals("")){
                        dialog.dismiss();
                        //密码验证成功
                        startActivity(ClassName);
                    }else{
                        Toast.makeText(MainActivity.this,"密码输入错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        dialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
