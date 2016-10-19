package com.example.chen.memo;

/**
 * Created by chen on 16-7-6.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/*自定义对话框
*
 *  */
public class CustomDialog extends Dialog {
    private EditText eText;
    private Button positiveButton, negativeButton;

    public CustomDialog(Context context) {
        super(context);
        setTitle("请输入密码");
        setCustomDialog();
    }

    public void setCustomDialog() {

        View mView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog, null);
        positiveButton = (Button) mView.findViewById(R.id.positive);
        negativeButton = (Button) mView.findViewById(R.id.negative);
        super.setContentView(mView);
        //对指定的输入框请求焦点。然后调用输入管理器弹出软键盘 使用timer管理器延迟弹出输入法
        eText = (EditText)findViewById(R.id.pwd);
        eText.setFocusable(true);
        eText.setFocusableInTouchMode(true);
        eText.requestFocus();
        //弹出键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager imm = (InputMethodManager) eText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                           }

                       },
                200);
    }

    public View getEditText() {
        eText = (EditText)findViewById(R.id.pwd);

        return eText;
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     *
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener) {
        positiveButton.setOnClickListener(listener);
    }

    /**
     * 取消键监听器
     *
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener) {
        negativeButton.setOnClickListener(listener);

    }
}