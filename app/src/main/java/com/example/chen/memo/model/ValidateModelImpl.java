package com.example.chen.memo.model;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.CustomDialog;
import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.presenter.ValidatePresenterImpl;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.dialog.SetupPasswordDialog;
import com.example.chen.memo.view.main.MainActivity;

/**
 * Created by cdc on 16-9-23.
 */
public class ValidateModelImpl implements IValidateModel{

    private MainActivity view;

    @Override
    public void checkLogin(final MainActivity view, final ValidatePresenterImpl validatePresenterImpl) {

        final CustomDialog dialog = new CustomDialog(view);
        final EditText editText = (EditText) dialog.getEditText();//方法在CustomDialog中实现
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unique_pwd = PrefUtils.uniquePwd();
                String input_pwd = String.valueOf(editText.getText());
                //隐藏输入法
                view.hideKeyboard();
                try{
                    String pwd = SimpleCrypto.enCrypto(input_pwd, String.valueOf(R.string.key_password));
                    //密码验证成功
                    if(pwd.equals(unique_pwd) && !input_pwd.equals("")){
                        dialog.dismiss();
                        //清空错误密码统计
                        PrefUtils.setErrorPwdCount(0);
                        validatePresenterImpl.loginSuccess();
                    }else{
                        //密码错误次数累加器
                        int i = PrefUtils.getErrorPwdCount();
                        PrefUtils.setErrorPwdCount(i++);
                        if(i < ValidatePresenterImpl.maxErrorPwdTime){
                            Toast.makeText(view, R.string.error_pwd_text, Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.dismiss();
                            /*找回密码提示框*/
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        dialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void setupPassword(final MainActivity view, final ValidatePresenterImpl validatePresenterImpl){
        final SetupPasswordDialog dialog = new SetupPasswordDialog(view);
        dialog.setTitle(R.string.first_enter);
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = (EditText) dialog.getEditText1();
                EditText editText2 = (EditText) dialog.getEditText2();
                String pwd1 = String.valueOf(editText1.getText());
                String pwd2 = String.valueOf(editText2.getText());
                view.hideKeyboard();
                if (pwd1.equals(pwd2) && !pwd1.equals("")) {
                    try {
                        String pwd = SimpleCrypto.enCrypto(pwd1, String.valueOf(R.string.key_password));
                        PrefUtils.setUniquePwd(pwd);
                        PrefUtils.setFirstOpen(false);
                        dialog.dismiss();
                        Toast.makeText(view,R.string.set_pwd_succeed,Toast.LENGTH_LONG).show();
                        validatePresenterImpl.setupSuccess();
                        //startActivity(SettingsActivity.class);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    //密码输入不一致
                    Toast.makeText(view,R.string.not_same_pwd,Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
