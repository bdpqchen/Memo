package com.example.chen.memo.application;

import android.app.Activity;
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

    public static final String DIARY_NAME = "日记";
    public static final String MEMO_NAME = "备忘";
    public static final String CIPHER_NAME = "密码";

    /*数据库中 记录的状态(status)
    * @INVALID 已删除（伪删除delete）
    * @TRASHED 回收站(discard)
    * @VALID   正常
    * */
    public static final int RECORD_STATUS_INVALID = 0;
    public static final int RECORD_STATUS_TRASHED = 1;
    public static final int RECORD_STATUS_VALID   = 9;

    /*
    * Intent Bundle 字符标识
    *
    * */
    public static final String KEY_UNIQUE_PASSWORD = "bdpq_unique_pwd";
    public static final String KEY_PWD = "bdpq_pwd";

    public static final String PWD_NAME = "pwd_name";
    public static final String PWD_ACCOUNT = "pwd_account";
    public static final String PWD_PWD = "pwd_pwd";
    public static final String NAME = "name";
    public static final String ACCOUNT = "account";
    public static final String PWD= "pwd";



    public static final String EDIT_TEXT_DIARY = "edit_text_diary";
    public static final String EDIT_TEXT_MEMO = "edit_text_memo";
    public static final String NOW_TIME = "now_time";
    public static final String ID = "id";
    public static final String DIARY_CONTENT = "diary_content";
    public static final String MEMO_CONTENT = "memo_content";
    public static final String MEMO_ALARM_TIME = "memo_alarm_time";
    public static final String ALARM_TIME_OLD = "alarm_time_old";

    public static final String PUBLISH_TIME = "publish_time";
    public static final String POSITION = "position";
    public static final String STATUS = "status";
    public static final String MSG = "msg";

    public static final int ALTER = 0;
    public static final int CREATE = 1;


    //datalist 条数
    public static final int RECORD_LIST_LIMIT = 20;


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
