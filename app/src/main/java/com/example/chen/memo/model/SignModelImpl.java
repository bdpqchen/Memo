package com.example.chen.memo.model;

import com.example.chen.memo.bean.Sign;
import com.example.chen.memo.mydatepicker.DPCManager;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.PrefUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * Created by cdc on 16-10-23.
 */

public class SignModelImpl {
    private final String ORDERBY_ID_DESC = "id desc";

    public void signInToday(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        //当前的日期时间
        int startYear = calendar.get(Calendar.YEAR);
        int startMonth = (calendar.get(Calendar.MONTH))+1 ;
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);

        String date = startYear + "-" + startMonth + "-" + startDay;
        LogUtils.i("today", date);
        List<Sign> signList = DataSupport.where("date=?", date).limit(1).order(ORDERBY_ID_DESC).find(Sign.class);
        if(signList.size() == 0){
            Sign sign = new Sign();
            long timeGetTime = new Date().getTime();
            sign.setDatetime(timeGetTime);
            sign.setDate(date);
            sign.save();
        }
    }

    public void initSignInData(){

        List<Sign> signList = DataSupport.select("date").order(ORDERBY_ID_DESC).find(Sign.class);
        if(signList.size() > 0){
            PrefUtils.setSignInCount(signList.size());
            List<String> tmp = new ArrayList<>();
            for(int i = 0; i < signList.size(); i++){
                tmp.add(signList.get(i).getDate());
            }
            DPCManager.getInstance().setDecorBG(tmp);
        }

    }




}
