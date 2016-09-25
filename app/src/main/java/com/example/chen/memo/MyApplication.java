package com.example.chen.memo;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DCchen on 2016/5/14.
 */
public class MyApplication extends Application {

    /*
    *sqlite 数据库版本号 升级数据库更改dbversion
    *getDbversion() 全局获取版本号
    */
    private static final int dbversion = 8;
    public static int getDbversion(){
        return dbversion;
    }

    /*
    * 活动管理器
    */
    public static List<Activity> activities = new ArrayList<Activity>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
