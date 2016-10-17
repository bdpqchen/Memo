package com.example.chen.memo.presenter;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.chen.memo.R;
import com.example.chen.memo.model.AlterDataModelImpl;
import com.example.chen.memo.model.CreateDataModelImpl;
import com.example.chen.memo.view.dialog.SimpleDialog;
import com.example.chen.memo.view.diary.DiaryActivity;
import com.example.chen.memo.view.diary.DiaryDetailActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;

import static com.example.chen.memo.application.CustomApplication.EDIT_TEXT_DIARY;
import static com.example.chen.memo.application.CustomApplication.ID;
import static com.example.chen.memo.application.CustomApplication.POSITION;


/**
 * Created by cdc on 16-9-27.
 */

public class DiaryPresenterImpl implements IDiaryPresenter {

    private DiaryDetailActivity diaryDetailActivity;
    private CreateDataModelImpl createDataModel;
    private DiaryActivity diaryActivity;
    private String diaryContent;
    private String nowTime;
    private Bundle diaryBundle;
    private AlterDataModelImpl alterDataModel = new AlterDataModelImpl();
    private Bundle deleteBundle;
    private DiaryListActivity diaryListActivity;


    public DiaryPresenterImpl(DiaryActivity diaryActivity) {
        this.diaryActivity = diaryActivity;
    }

    public DiaryPresenterImpl(DiaryDetailActivity diaryDetailActivity){
        this.diaryDetailActivity = diaryDetailActivity;
    }

    public DiaryPresenterImpl(){
    }

    public void createDiary(Bundle bundle) {
        diaryContent = bundle.getString(EDIT_TEXT_DIARY, "");
        if (!diaryContent.equals("")){
            createDataModel = new CreateDataModelImpl();
            createDataModel.createDiaryData(diaryActivity, bundle);
        }else{
            this.diaryActivity.onToastMessage(diaryActivity.getString(R.string.not_finish_diary));
        }

    }

    public void alterDiary(Bundle diaryBundle){
        this.diaryBundle = diaryBundle;
        SimpleDialog simpleDialog = new SimpleDialog(diaryDetailActivity);
        simpleDialog.createDialog(diaryDetailActivity.getString(R.string.dialog_title_alter_tips),
                diaryDetailActivity.getString(R.string.alter_diary_message),
                diaryDetailActivity.getString(R.string.btn_negative),negativeListener,
                diaryDetailActivity.getString(R.string.btn_positive),alterPositiveListener
        );
    }

    private DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    private DialogInterface.OnClickListener alterPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            alterDataModel.alterDiary(diaryDetailActivity, diaryBundle);
        }
    };

    public void deleteDiary(DiaryListActivity diaryListActivity, Bundle bundle){
        this.diaryListActivity = diaryListActivity;
        SimpleDialog simpleDialog = new SimpleDialog(diaryListActivity);
        this.deleteBundle = bundle;

        simpleDialog.createDialog(diaryListActivity.getString(R.string.dialog_title_delete_tips),
                diaryListActivity.getString(R.string.delete_diary_message),
                diaryListActivity.getString(R.string.btn_negative),negativeListener,
                diaryListActivity.getString(R.string.btn_positive),deletePositiveListener
                );
    }

    private DialogInterface.OnClickListener deletePositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //删除该item记录
            alterDataModel.deleteDiary(deleteBundle.getInt(ID,0));
            //在列表中移除
            diaryListActivity.onDeleteItem(deleteBundle.getInt(POSITION,0));
        }
    };

}
