package com.example.chen.memo.view.memo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.chen.memo.R;
import com.example.chen.memo.view.BaseActivity;

/**
 * Created by cdc on 16-10-16.
 */

public class AlarmActivity extends AppCompatActivity {
    MediaPlayer alarmMusic;
    private Vibrator vibrator;
    private PowerManager.WakeLock mWakelock;
    private Button positive;
    private TextView message;

    @Override
    public void onCreate(Bundle savedInstanceStart) {
        super.onCreate(savedInstanceStart);
        //setContentView(R.layout.alarm_music_activity);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        alarmMusic = MediaPlayer.create(this, (Uri) getSystemDefultRingtoneUri());
        alarmMusic.setLooping(true);
        alarmMusic.start();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {4000, 1000, 4000, 1000};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, 2);           //重复两次上面的pattern 如果只想震动一次，index设为-1

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alarm_title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.alarm_btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                alarmMusic.stop();
                vibrator.cancel();
            }
        });
        builder.show();

    }

    private Uri getSystemDefultRingtoneUri() {
        //获取系统默认的闹钟铃声
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_ALARM);
    }
}