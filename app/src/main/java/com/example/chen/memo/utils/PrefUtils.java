package com.example.chen.memo.utils;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.chen.memo.application.CustomApplication;

import static com.example.chen.memo.application.CustomApplication.KEY_UNIQUE_PASSWORD;

/**
 * Created by cdc on 16-9-21.
 */
public class PrefUtils {
    private static final String FIRST_OPEN = "first_open";
    private static final String UNIQUE_PWD = KEY_UNIQUE_PASSWORD;
    private static final String DIARY_LOCK = "diary_lock";
    private static final String MEMO_LOCK = "memo_lock";
    private static final String CIPHER_LOCK = "cipher_lock";
    private static final String ERROR_PWD_COUNT = "error_pwd_count";
    private static final String IMITATE_DATA = "imitation_data";
    private static final String SIGN_COUNT = "sing_count";
    private static final String INIT_FIRST_OPEN = "init_first_open";
    private static final String INIT_VERSION_CODE = "init_version_code";


    /*
        *sharedPreferences 存储APP状态信息
        * getSettingsSharedPreferences() 获取配置文件
        *
        * */
    private static SharedPreferences getDefaultSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(CustomApplication.getContext());
    }

    public static void setFirstOpen(boolean firstOpen){
        getDefaultSharedPreferences().edit().putBoolean(FIRST_OPEN, firstOpen).apply();
    }
    public static boolean isFirstOpen(){
        return getDefaultSharedPreferences().getBoolean(FIRST_OPEN, true);
    }

    public static void setUniquePwd(String uniquePwd){
        getDefaultSharedPreferences().edit().putString(UNIQUE_PWD, uniquePwd).apply();
    }
    public static String uniquePwd(){
        return getDefaultSharedPreferences().getString(UNIQUE_PWD, " ");
    }

    public static void setDiaryLock(boolean diaryLock){
        getDefaultSharedPreferences().edit().putBoolean(DIARY_LOCK, diaryLock).apply();
    }
    public static boolean isDiaryLock(){
        return getDefaultSharedPreferences().getBoolean(DIARY_LOCK, false);
    }

    public static void setMemoLock(boolean memoLock){
        getDefaultSharedPreferences().edit().putBoolean(MEMO_LOCK, memoLock).apply();
    }
    public static boolean isMemoLock(){
        return getDefaultSharedPreferences().getBoolean(MEMO_LOCK, false);
    }

    public static void setCipherLock(boolean cipherLock){
        getDefaultSharedPreferences().edit().putBoolean(CIPHER_LOCK, cipherLock).apply();
    }
    public static boolean isCipherLock(){
        return getDefaultSharedPreferences().getBoolean(CIPHER_LOCK, false);
    }

    public static void setErrorPwdCount(int errorPwdCount){
        getDefaultSharedPreferences().edit().putInt(ERROR_PWD_COUNT, errorPwdCount).apply();
    }
    public static int getErrorPwdCount(){
        return getDefaultSharedPreferences().getInt(ERROR_PWD_COUNT, 0);
    }

    public static void setSignInCount(int count){
        getDefaultSharedPreferences().edit().putInt(SIGN_COUNT, count).apply();
    }

    public static int getSignInCount(){
        return getDefaultSharedPreferences().getInt(SIGN_COUNT, 0);
    }

    public static boolean getIsFirstOpen(){
        return getDefaultSharedPreferences().getBoolean(INIT_FIRST_OPEN, true);
    }
    public static void setIsFirstOpen(boolean b){
        getDefaultSharedPreferences().edit().putBoolean(INIT_FIRST_OPEN, b).apply();
    }

    public static int getVersionCode(){
        return getDefaultSharedPreferences().getInt(INIT_VERSION_CODE, 3);
    }
    public static void setVersionCode(int v){
        getDefaultSharedPreferences().edit().putInt(INIT_VERSION_CODE, v).apply();
    }


}


