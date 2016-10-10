package com.example.chen.memo.utils;

import java.text.SimpleDateFormat;

/**
 * Created by cdc on 16-9-30.
 */

public class TimeStampUtils {

    public static String getDatetimeString(long timeStamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(timeStamp);
    }



}
