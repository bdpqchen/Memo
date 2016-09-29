package com.example.chen.memo.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.CustomDialog;
import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.model.ValidateModelImpl;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.main.MainActivity;

/**
 * Created by cdc on 16-9-23.
 */
public class ValidatePresenterImpl implements IValidatePresenter {


    private NextActivity nextActivity;
    private MainActivity view;
    private ValidateModelImpl validateModel = new ValidateModelImpl();
    public static final int maxErrorPwdTime = 7;

    @Override
    public void login(final MainActivity view, NextActivity nextActivity){
        //判断密码错误次数  >=7 提示找回密码
        if(PrefUtils.getErrorPwdCount() < maxErrorPwdTime){
            this.nextActivity = nextActivity;
            this.view = view;
            validateModel.checkLogin(view, this);
        }else{
            /*找回密码*/
        }
    }
    @Override
    public void loginSuccess() {
        Intent intent = new Intent();
        intent.setClass(view, nextActivity.getClass());
        view.startActivity(intent);
    }

    @Override
    public void setup(final MainActivity view, NextActivity nextActivity){
        if (!PrefUtils.isFirstOpen()) {
            login(view, NextActivity.Settings);
        } else {
            //当第一次点击设置，提示设置唯一密码方可进入
            validateModel.setupPassword(view, this);
        }
    }
    @Override
    public void setupSuccess(){
        loginSuccess();
    }


}
