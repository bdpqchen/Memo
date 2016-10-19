package com.example.chen.memo.model;

import android.os.Bundle;

import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.bean.CipherBean;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.bean.Memo;
import com.example.chen.memo.event.CipherEvent;
import com.example.chen.memo.event.DiaryEvent;
import com.example.chen.memo.event.MemoEvent;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.view.cipher.CipherActivity;
import com.example.chen.memo.view.diary.DiaryActivity;
import com.example.chen.memo.view.memo.MemoActivity;

import org.greenrobot.eventbus.EventBus;

import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.example.chen.memo.application.CustomApplication.CREATE;
import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_DIARY;
import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_MEMO;
import static com.example.chen.memo.application.CustomApplication.KEY_PWD;
import static com.example.chen.memo.application.CustomApplication.MEMO_ALARM_TIME;
import static com.example.chen.memo.application.CustomApplication.MEMO_CONTENT;
import static com.example.chen.memo.application.CustomApplication.PWD_ACCOUNT;
import static com.example.chen.memo.application.CustomApplication.PWD_NAME;
import static com.example.chen.memo.application.CustomApplication.PWD_PWD;
import static com.example.chen.memo.application.CustomApplication.RECORD_STATUS_VALID;

/**
 * Created by cdc on 16-9-28.
 */

public class CreateDataModelImpl implements ICreateDataModel {


    @Override
    public void createDiaryData(DiaryActivity diaryActivity, Bundle bundle){
        Diary diary = new Diary();
        long timeGetTime = new Date().getTime();
        diary.setDiary(bundle.getString(EDIT_TEXT_DIARY));
        diary.setPublishTime(timeGetTime);
        diary.setStatus(RECORD_STATUS_VALID);

        if(diary.save()){
            EventBus.getDefault().post(new DiaryEvent("refresh diary list"));
            diaryActivity.onToastMessage(diaryActivity.getString(R.string.save_data_succeed));
            diaryActivity.finish();
        }else{
            diaryActivity.onToastMessage(diaryActivity.getString(R.string.save_data_fail));
        }
    }

    @Override
    public void createMemoData(MemoActivity memoActivity, Bundle bundle){
        Memo memo = new Memo();
        long timeGetTime = new Date().getTime();

        memo.setAlarmTime(bundle.getInt(MEMO_ALARM_TIME));
        memo.setMemo(bundle.getString(MEMO_CONTENT));
        memo.setPublishTime(timeGetTime);
        memo.setStatus(RECORD_STATUS_VALID);

        if(memo.save()){
            EventBus.getDefault().post(new MemoEvent(CREATE));
            memoActivity.onToastMessage(memoActivity.getString(R.string.save_data_succeed));
            memoActivity.finish();
        }else{
            memoActivity.onToastMessage(memoActivity.getString(R.string.save_data_fail));
        }
    }

    @Override
    public void createCipherData(CipherActivity cipherActivity, Bundle bundle){
        CipherBean cipher = new CipherBean();
        long timeGetTime = new Date().getTime();
        cipher.setAccount(bundle.getString(PWD_ACCOUNT));
        try {
            cipher.setPwd(SimpleCrypto.enCrypto(bundle.getString(PWD_PWD), KEY_PWD));
        } catch (Exception e) {
            timeGetTime = 0;
            cipherActivity.onToastMessage("加密失败.建议查杀病毒后重试");
            e.printStackTrace();
        }
        cipher.setName(bundle.getString(PWD_NAME));
        cipher.setPublishTime(timeGetTime);
        cipher.setStatus(RECORD_STATUS_VALID);

        if(cipher.save() && timeGetTime != 0){
            EventBus.getDefault().post(new CipherEvent("refresh cipher list"));
            cipherActivity.onToastMessage(cipherActivity.getString(R.string.save_data_succeed));
            cipherActivity.finish();
        }else{
            cipherActivity.onToastMessage(cipherActivity.getString(R.string.save_data_fail));
        }

    }


}
