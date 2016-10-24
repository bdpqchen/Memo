package com.example.chen.memo.presenter;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.chen.memo.R;
import com.example.chen.memo.model.AlterDataModelImpl;
import com.example.chen.memo.model.CreateDataModelImpl;
import com.example.chen.memo.view.cipher.CipherActivity;
import com.example.chen.memo.view.cipher.CipherListActivity;
import com.example.chen.memo.view.dialog.SimpleDialog;

import static com.example.chen.memo.application.CustomApplication.ID;
import static com.example.chen.memo.application.CustomApplication.POSITION;
import static com.example.chen.memo.application.CustomApplication.PWD_ACCOUNT;
import static com.example.chen.memo.application.CustomApplication.PWD_NAME;
import static com.example.chen.memo.application.CustomApplication.PWD_PWD;

/**
 * Created by cdc on 16-10-18.
 */
public class CipherPresenterImpl {

    private CipherActivity cipherActivity;
    private CipherListActivity cipherListActivity;
    private CreateDataModelImpl createDataModel;
    private Bundle deleteBundle;
    private AlterDataModelImpl alterDataModel = new AlterDataModelImpl();
    private Bundle alterBundle;


    public void createCipher(CipherActivity cipherActivity, Bundle bundle) {
        this.cipherActivity = cipherActivity;
        this.createDataModel = new CreateDataModelImpl();

        if(bundle.getString(PWD_NAME).equals("")){
            cipherActivity.nameGetError(cipherActivity.getString(R.string.not_is_null));
        }else if (bundle.getString(PWD_ACCOUNT).equals("")){
            cipherActivity.accountGetError(cipherActivity.getString(R.string.not_is_null));
        }else if (bundle.getString(PWD_PWD).equals("")){
            cipherActivity.pwdGetError(cipherActivity.getString(R.string.not_is_null));
        }else{
            createDataModel.createCipherData(cipherActivity, bundle);
        }
    }

    public void alterCipher(CipherActivity cipherActivity, Bundle bundle) {
        this.cipherActivity = cipherActivity;
        if(bundle.getString(PWD_NAME).equals("")){
            cipherActivity.nameGetError(cipherActivity.getString(R.string.not_is_null));
        }else if (bundle.getString(PWD_ACCOUNT).equals("")){
            cipherActivity.accountGetError(cipherActivity.getString(R.string.not_is_null));
        }else if (bundle.getString(PWD_PWD).equals("")){
            cipherActivity.pwdGetError(cipherActivity.getString(R.string.not_is_null));
        }else{
            alterCipherDialog(cipherActivity, bundle);
        }
    }

    private void alterCipherDialog(CipherActivity cipherActivity, Bundle cipherBundle){
        this.cipherActivity = cipherActivity;
        this.alterBundle = cipherBundle;
        SimpleDialog simpleDialog = new SimpleDialog(cipherActivity);
        simpleDialog.createDialog(cipherActivity.getString(R.string.dialog_title_alter_tips),
                cipherActivity.getString(R.string.alter_diary_message),
                cipherActivity.getString(R.string.btn_negative),negativeListener,
                cipherActivity.getString(R.string.btn_positive),alterPositiveListener
        );
    }

    private DialogInterface.OnClickListener alterPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            alterDataModel.alterCipher(cipherActivity, alterBundle);
        }
    };

    public void deleteCipher(CipherListActivity cipherListActivity, Bundle bundle) {

        this.cipherListActivity = cipherListActivity;
        SimpleDialog simpleDialog = new SimpleDialog(cipherListActivity);
        this.deleteBundle = bundle;

        simpleDialog.createDialog(cipherListActivity.getString(R.string.dialog_title_delete_tips),
                cipherListActivity.getString(R.string.delete_diary_message),
                cipherListActivity.getString(R.string.btn_negative),negativeListener,
                cipherListActivity.getString(R.string.btn_positive),deletePositiveListener
        );
    }

    private DialogInterface.OnClickListener deletePositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //删除该item记录
            alterDataModel.deleteDiary(deleteBundle.getInt(ID,0));
            //在列表中移除
            cipherListActivity.onDeleteItem(deleteBundle.getInt(POSITION,0));
        }

    };
    private DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
}


