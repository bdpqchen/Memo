package com.example.chen.memo.view.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.chen.memo.mydatepicker.DPCManager;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdc on 16-9-21.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        List<String> tmp = new ArrayList<>();
        tmp.add("2016-10-1");
        tmp.add("2016-10-8");
        tmp.add("2016-10-16");
        tmp.add("2016-10-17");
        tmp.add("2016-10-18");
        DPCManager.getInstance().setDecorBG(tmp);





        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 200);

    }

}
