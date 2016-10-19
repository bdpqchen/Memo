package com.example.chen.memo.utils;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.chen.memo.application.CustomApplication;

import java.net.UnknownHostException;

/**
 * Created by cdc on 16-9-21.
 */
public class PrefUtils {
    private static final String FIRST_OPEN = "first_open";
    private static final String UNIQUE_PWD = "unique_pwd";
    private static final String DIARY_LOCK = "diary_lock";
    private static final String MEMO_LOCK = "memo_lock";
    private static final String CIPHER_LOCK = "cipher_lock";
    private static final String ERROR_PWD_COUNT = "error_pwd_count";
    private static final String IMITATE_DATA = "imitation_data";


    /*
        *sharedPreferences 存储APP状态信息
        * getSettingsSharedPreferences() 获取配置文件
        *
        * */
    private static SharedPreferences getDefaultsharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(CustomApplication.getContext());
    }

    public static void setFirstOpen(boolean firstOpen){
        getDefaultsharedPreferences().edit().putBoolean(FIRST_OPEN, firstOpen).apply();
    }
    public static boolean isFirstOpen(){
        return getDefaultsharedPreferences().getBoolean(FIRST_OPEN, true);
    }

    public static void setUniquePwd(String uniquePwd){
        getDefaultsharedPreferences().edit().putString(UNIQUE_PWD, uniquePwd).apply();
    }
    public static String uniquePwd(){
        return getDefaultsharedPreferences().getString(UNIQUE_PWD, " ");
    }

    public static void setDiaryLock(boolean diaryLock){
        getDefaultsharedPreferences().edit().putBoolean(DIARY_LOCK, diaryLock).apply();
    }
    public static boolean isDiaryLock(){
        return getDefaultsharedPreferences().getBoolean(DIARY_LOCK, false);
    }

    public static void setMemoLock(boolean memoLock){
        getDefaultsharedPreferences().edit().putBoolean(MEMO_LOCK, memoLock).apply();
    }
    public static boolean isMemoLock(){
        return getDefaultsharedPreferences().getBoolean(MEMO_LOCK, false);
    }

    public static void setCipherLock(boolean cipherLock){
        getDefaultsharedPreferences().edit().putBoolean(CIPHER_LOCK, cipherLock).apply();
    }
    public static boolean isCipherLock(){
        return getDefaultsharedPreferences().getBoolean(CIPHER_LOCK, false);
    }

    public static void setErrorPwdCount(int errorPwdCount){
        getDefaultsharedPreferences().edit().putInt(ERROR_PWD_COUNT, errorPwdCount).apply();
    }
    public static int getErrorPwdCount(){
        return getDefaultsharedPreferences().getInt(ERROR_PWD_COUNT, 0);
    }

    public static void setImitateData(boolean b){
        getDefaultsharedPreferences().edit().putBoolean(IMITATE_DATA, b).apply();
    }
    public static boolean isImitateData(){
        return getDefaultsharedPreferences().getBoolean(IMITATE_DATA, false);
    }


}
