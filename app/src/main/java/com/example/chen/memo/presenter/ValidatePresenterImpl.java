package com.example.chen.memo.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.model.ValidateModelImpl;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.cipher.CipherListActivity;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.dump.DumpListActivity;
import com.example.chen.memo.view.main.MainActivity;
import com.example.chen.memo.view.memo.MemoListActivity;
import com.example.chen.memo.view.settings.SettingsActivity;

import java.util.ArrayList;

import static com.example.chen.memo.application.CustomApplication.KEY_UNIQUE_PASSWORD;

/**
 * Created by cdc on 16-9-23.
 */
public class ValidatePresenterImpl implements IValidatePresenter {


    private NextActivity nextActivity;
    private MainActivity view;
    private ValidateModelImpl validateModel = new ValidateModelImpl();
    private EditText dialogPwd;
    public static final int maxErrorPwdTime = 7;

    private boolean check_first = false;
    private boolean check_second = false;
    private int position_first;
    private int position_second;

    @Override
    public void login(MainActivity view, NextActivity nextActivity) {
        this.view = view;
        this.nextActivity = nextActivity;
        //判断密码错误次数  >=7 提示找回密码
        if (PrefUtils.getErrorPwdCount() < maxErrorPwdTime) {
            LogUtils.i("getErrorCount ", String.valueOf(PrefUtils.getErrorPwdCount()));
            validateModel.checkLogin(view, this);
        } else {
            /*找回密码*/
            findBackPwdDialog();
        }
    }

    //找回密码提示框
    public void findBackPwdDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        builder.setTitle(R.string.warn);
        builder.setMessage(R.string.tips_find_back_pwd);
        builder.setPositiveButton(R.string.btn_positive, positiveListener);
        builder.show();
    }

    private DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            onPositiveFirst();
        }
    };

    public void onPositiveFirst(){
        String realData = "";
        if(validateModel.getMemoDataCount() > 0){
            realData = validateModel.getOneRealDataMemo();
        }else{
            realData = validateModel.getOneRealDataDiary();
        }
        position_first = validateModel.getRandom(0, 9);
        ArrayList<String> realList = validateModel.initImitateData(realData, position_first);
        String[] array = realList.toArray(new String[10]);
        new AlertDialog.Builder(view)
                .setTitle(R.string.choose_you_wrote_one)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(array, 0, choiceListenerFirst)
                .setPositiveButton(R.string.btn_positive, firstPositiveListener)
                .show();
    }

    private DialogInterface.OnClickListener choiceListenerFirst = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == position_first){
                check_first = true;
            }

            //LogUtils.i("点击了", String.valueOf(which));
        }
    };

    private DialogInterface.OnClickListener firstPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String realData = "";
            if(validateModel.getMemoDataCount() > 0){
                realData = validateModel.getOneRealDataDiary();
            }else{
                realData = validateModel.getOneRealDataMemo();
            }
            position_second = validateModel.getRandom(0, 9);
            ArrayList<String> realList = validateModel.initImitateData(realData, position_second);
            String[] array = realList.toArray(new String[10]);
            new AlertDialog.Builder(view)
                    .setTitle(R.string.choose_you_wrote_one)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setSingleChoiceItems(array, 0, choiceListenerSecond)
                    .setPositiveButton(R.string.btn_positive, secondPositiveListener)
                    .show();

        }
    };

    private DialogInterface.OnClickListener choiceListenerSecond = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == position_second){
                check_second = true;
            }
        }
    };

    private DialogInterface.OnClickListener secondPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (check_second && check_first){
                findBackPwdSuccess();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(view);
                builder.setTitle(R.string.warn);
                builder.setMessage(R.string.find_back_pwd_success);
                builder.setPositiveButton(R.string.btn_positive, null);
                builder.show();
            }
        }
    };

    private void findBackPwdSuccess(){
        LayoutInflater inflater = LayoutInflater.from(view);
        View layout = inflater.inflate(R.layout.dialog_input, null);
        dialogPwd = (EditText) layout.findViewById(R.id.dialog_pwd);
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        builder.setTitle(R.string.verify_success);
        builder.setPositiveButton(R.string.btn_positive, resetPwdListener);
        builder.setView(layout);
        builder.show();

    }

    private DialogInterface.OnClickListener resetPwdListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            try {
                String pwd = SimpleCrypto.enCrypto(String.valueOf(dialogPwd.getText()), KEY_UNIQUE_PASSWORD);
                PrefUtils.setUniquePwd(pwd);
                PrefUtils.setErrorPwdCount(0);
                dialog.dismiss();
                Toast.makeText(view, R.string.set_pwd_succeed, Toast.LENGTH_LONG).show();

                setupSuccess();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };


    @Override
    public void loginSuccess() {
        Intent intent = new Intent();
        switch (nextActivity){
            case DiaryList:
                intent.setClass(view, DiaryListActivity.class);
                break;
            case MemoList:
                intent.setClass(view, MemoListActivity.class);
                break;
            case CipherList:
                intent.setClass(view, CipherListActivity.class);
                break;
            case DumpList:
                intent.setClass(view, DumpListActivity.class);
                break;
            case Settings:
                intent.setClass(view, SettingsActivity.class);
                break;
        }

        view.startViewActivity(intent);
    }

    @Override
    public void setup(final MainActivity view, NextActivity nextActivity) {

        if (!PrefUtils.isFirstOpen()) {
            login(view, nextActivity);
        } else {
            //当第一次点击设置，提示设置唯一密码方可进入
            validateModel.setupPassword(view, this);
        }
    }

    @Override
    public void setupSuccess() {
        loginSuccess();
    }

    public void startSettingsActivity() {
        Intent intent = new Intent(view, SettingsActivity.class);
        view.startActivity(intent);
    }
}
