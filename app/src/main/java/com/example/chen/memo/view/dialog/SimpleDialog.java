package com.example.chen.memo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.chen.memo.R;

/**
 * Created by cdc on 16-10-11.
 */

public class SimpleDialog extends Dialog {
    private final Context context;

    public SimpleDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void createDialog(String title, String message,
                             String negative, OnClickListener negativeListener,
                             String positive, OnClickListener positiveListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(negative, negativeListener);
        builder.setPositiveButton(positive, positiveListener);
        builder.show();


    }
}
