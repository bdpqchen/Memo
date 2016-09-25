package com.example.chen.memo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.chen.memo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cdc on 16-9-23.
 */
public class PasswordDialog extends Dialog {

    private EditText eText;
    private Button positiveButton, negativeButton;

    public PasswordDialog(Context context) {
        super(context);
        setTitle("");
        setCustomDialog();
    }

    private void setCustomDialog() {

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
