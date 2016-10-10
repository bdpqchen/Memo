package com.example.chen.memo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdc on 16-9-21.
 */
public class CustomApplication extends LitePalApplication {

    public static Context context;
    public static List<Activity> activities = new ArrayList<>();
    private static final int dbversion = 8;
    public static final int DIARY = 1;
    public static final int MEMO = 2;
    public static final int CIPHER = 3;

    /*数据库中 记录的状态(status)
    * @INVALID 已删除（伪删除delete）
    * @TRASHED 回收站(discard)
    * @VALID   正常
    * */
    public static final int RECORD_STATUS_INVALID = 0;
    public static final int RECORD_STATUS_TRASHED = 1;
    public static final int RECORD_STATUS_VALID   = 9;

    @Override
    public void onCreate(){
        context = getApplicationContext();
    }

    /*
   *sqlite 数据库版本号 升级数据库更改dbversion
   *getDbversion() 全局获取版本号
   */
    public static int getDbversion(){
        return dbversion;
    }

    /*
    * 活动管理器
    */
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

    /*
    * 获取全局Context*/
    public static Context getContext(){
        return context;
    }

}
