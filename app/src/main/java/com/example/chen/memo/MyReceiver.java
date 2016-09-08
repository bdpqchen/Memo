package com.example.chen.memo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by DCchen on 2016/4/20.
 */
public class MyReceiver extends BroadcastReceiver {

    private Vibrator vibrator;
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg=intent.getStringExtra("msg");
        String time=intent.getStringExtra("time");
        Intent intent1=new Intent(context,MusicActivity.class);
        intent1.putExtra("msg",msg);
        intent1.putExtra("time", time);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        //System.out.println("66666666666666666666666666666");
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


    }
}
