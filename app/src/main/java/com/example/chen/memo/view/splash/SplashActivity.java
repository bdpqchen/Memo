package com.example.chen.memo.view.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.chen.memo.model.SignModelImpl;
import com.example.chen.memo.view.main.MainActivity;

/**
 * Created by cdc on 16-9-21.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SignModelImpl signModel = new SignModelImpl();
        //今天的签到
        signModel.signInToday();
        //初始化签到数据
        signModel.initSignInData();

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 400);

    }

}
