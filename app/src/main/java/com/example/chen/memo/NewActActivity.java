/*
package com.example.chen.memo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

*/
/**
 * Created by DCchen on 2016/4/18.
 *//*

public class NewActActivity extends Activity {


    DatePicker datePicker = null;
    private Intent intent = new Intent();
    private MyApplication app;
    @Override
    public void onCreate(Bundle savedInstanceStart) {
        super.onCreate(savedInstanceStart);
        //setContentView(R.layout.new_act_activity);
        app.addActivity(this);
        final Calendar ca = Calendar.getInstance();
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(ca.get(Calendar.HOUR_OF_DAY));
        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        //监听日期控件，获取更新的数据 交给intent

        intent.putExtra("year", ca.get(Calendar.YEAR));
        intent.putExtra("month", ca.get(Calendar.MONTH) +1);
        intent.putExtra("day", ca.get(Calendar.DAY_OF_MONTH));
        datePicker.init(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        intent.putExtra("year", year);
                        intent.putExtra("month", monthOfYear + 1);
                        intent.putExtra("day", dayOfMonth);
                        //System.out.println(year);
                    }
                });

        //未触发时间控件的默认为当前时间
        intent.putExtra("hour", ca.get(Calendar.HOUR_OF_DAY));
        intent.putExtra("minute", ca.get(Calendar.MINUTE));
        //监听时间控件，获取更新的数据 交给intent
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                intent.putExtra("hour", hourOfDay);
                intent.putExtra("minute", minute);
            }
        });

        Button finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2,intent);
                finish();
            }
        });

        new DatePickerDialog(NewActActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		        */
/*月份从0计数*//*

                String theDate = String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth);
                System.out.println(theDate);

                //btnChooseDate.setText(theDate);
            }
        },2016,2,30).show();

        new TimePickerDialog(NewActActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format("%d:%d",hourOfDay,minute);
                System.out.println(time);
            }
        },0,0,true).show();


    }


}
*/
