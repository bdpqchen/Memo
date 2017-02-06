package com.example.chen.memo.view.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.example.chen.memo.model.SignModelImpl;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.main.MainActivity;

/**
 * Created by cdc on 16-9-21.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApp();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jump();
            }
        }, 300);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void jump() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    public void initApp() {
        SignModelImpl signModel = new SignModelImpl();
        //今天的签到
        signModel.signInToday();
        if (PrefUtils.getIsFirstOpen()) {
            PrefUtils.setIsFirstOpen(false);
            //首次打开应用，初始化签到数据
            signModel.initSignInData();
        }


    }
}
