package com.example.chen.memo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cdc on 16-9-30.
 */

public class TimeStampUtils {

    private static String NO_SECOND = "yyyy-MM-dd HH:mm";
    private static String NORMAL = "yyyy-MM-dd HH:mm:ss";

    public static String getDatetimeString(long timeStamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat(NO_SECOND);
        return dateFormat.format(timeStamp);
    }

    public static long setDatetimeString(int y, int M, int d, int h, int m, int s){
        long l = 0;
        String time = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
        SimpleDateFormat dateFormat = new SimpleDateFormat(NORMAL);
        try {
            Date date = dateFormat.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return l;
    }


}
