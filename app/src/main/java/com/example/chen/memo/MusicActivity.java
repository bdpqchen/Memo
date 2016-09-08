package com.example.chen.memo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DCchen on 2016/4/20.
 */


public class MusicActivity extends Activity {
    MediaPlayer alarmMusic;
    private MyApplication app;
    private Vibrator vibrator;
    private PowerManager.WakeLock mWakelock;
    private TextView message;
    private Button positive;
    private AnalogClock analogClock;


    public void onCreate(Bundle savedInstanceStart) {
        super.onCreate(savedInstanceStart);
        app.addActivity(this);
        setContentView(R.layout.alarm_music_activity);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
//        String time = intent.getStringExtra("time");

        //强制点亮屏幕
        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);// init powerManager
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|
                PowerManager.SCREEN_DIM_WAKE_LOCK,"target"); // this target for tell OS which app control screen
        mWakelock.acquire();

        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        alarmMusic = MediaPlayer.create(MusicActivity.this, (Uri) getSystemDefultRingtoneUri());
        alarmMusic.setLooping(true);
        alarmMusic.start();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {2000, 2000, 2000, 2000};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, 2);           //重复两次上面的pattern 如果只想震动一次，index设为-1

        message = (TextView) findViewById(R.id.msg);
        message.setText(msg);

        positive = (Button) findViewById(R.id.positive);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmMusic.stop();
                vibrator.cancel();
                finish();
            }
        });
    }

    private Uri getSystemDefultRingtoneUri() {
        //获取系统默认的闹钟铃声
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_ALARM);
    }
}
