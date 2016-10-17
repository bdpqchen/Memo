package com.example.chen.memo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.chen.memo.view.memo.AlarmActivity;

/**
 * Created by cdc on 16-10-16.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private Vibrator vibrator;

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        Intent intent1 = new Intent(context, AlarmActivity.class);
        intent1.putExtra("msg", msg);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


    }
}
