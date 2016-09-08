package com.example.chen.memo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DCchen on 2016/4/12.
 */

public class SqlHelper extends SQLiteOpenHelper {


    //必须要有构造函数
    public SqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
        //创建日记表
        String diary = "create table diary(_id integer primary key autoincrement,diary text, validity integer,time integer)";
        db.execSQL(diary);
        //创建备忘录表
        String memo = "create table memo(_id integer primary key autoincrement,content text, validity integer,time integer,publish_time integer)";
        db.execSQL(memo);
        //密码表。存放保存的密码
        String cipher = "create table cipher(_id integer primary key autoincrement,name text, account text , value integer,validity integer,time integer)";
        db.execSQL(cipher);
        // 设置表，存放各种默认值，固定值，唯一密码。。。
        String setting = "create table setting(_id integer primary key autoincrement , name text, value text , validity integer)";
        db.execSQL(setting);
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //输出更新数据库的日志信息

        switch (newVersion) {

        }

    }
}

