package com.example.chen.memo;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chen on 16-7-7.
 */
public class PasswordDialog extends Dialog{
    private EditText editText1,editText2;
    private Button positiveButton, negativeButton;

    public PasswordDialog(Context context) {
        super(context);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setTitle("ddddd");
        setPasswordDialog();
    }

    private void setPasswordDialog() {

        View mView = LayoutInflater.from(getContext()).inflate(R.layout.password_dialog, null);
        //title = (TextView) mView.findViewById(R.id.title);
        editText1 = (EditText) mView.findViewById(R.id.pwd1);
        editText2 = (EditText) mView.findViewById(R.id.pwd2);
        positiveButton = (Button) mView.findViewById(R.id.positive);
        negativeButton = (Button) mView.findViewById(R.id.negative);
        super.setContentView(mView);
        //弹出键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager imm = (InputMethodManager) editText1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                           }
                       },
                200);
    }

    public View getEditText1(){
        return editText1;
    }

    public View getEditText2(){
        return editText2;
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    public void setTitle1(String titleText){
        setTitle(titleText);
        //View mView = LayoutInflater.from(getContext()).inflate(R.layout.password_dialog, null);
        //title.setText(titleText);
        //super.setTitle(titleText);
        //setTitle(titleText);
        //super.setContentView(mView);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        positiveButton.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener){
        negativeButton.setOnClickListener(listener);

    }}
