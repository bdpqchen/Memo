/*
package com.example.chen.memo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.memo.view.dialog.PasswordDialog;

*/
/**
 * Created by chen on 16-7-7.
 *//*

public class SettingsActivity extends Activity {

    private CheckBox diary_cbox,memo_cbox,cipher_cbox;
    private SharedPreferences settings ;
    private RelativeLayout rtl_alter_pwd;
    private PasswordDialog dialog;
    private TextView back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings =getSharedPreferences("setting",MODE_PRIVATE);
        int diaryLocked = settings.getInt("diary_lock",0);
        int memoLocked = settings.getInt("memo_lock",0);
        int cipherLocked = settings.getInt("cipher_lock",0);

        diary_cbox  = (CheckBox) findViewById(R.id.lock_diary);
        memo_cbox   = (CheckBox) findViewById(R.id.lock_memo);
        cipher_cbox = (CheckBox) findViewById(R.id.lock_cipher);
        //设置checkbox当前状态
        setCboxState(diary_cbox,diaryLocked);
        setCboxState(memo_cbox,memoLocked);
        setCboxState(cipher_cbox,cipherLocked);

        //checkbox选中事件
        diary_cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeSetting("diary",isChecked);
                System.out.println(isChecked);
            }
        });
        memo_cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeSetting("memo",isChecked);
            }
        });
        cipher_cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeSetting("cipher",isChecked);
            }
        });

        //修改密码
        rtl_alter_pwd = (RelativeLayout) findViewById(R.id.alter_pwd);
        rtl_alter_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new PasswordDialog(SettingsActivity.this);
                dialog.setTitle("修改密码");
                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText1 = (EditText) dialog.getEditText1();
                        EditText editText2 = (EditText) dialog.getEditText2();
                        String pwd1 = String.valueOf(editText1.getText());
                        String pwd2 = String.valueOf(editText2.getText());

                        if (pwd1.equals(pwd2) && !pwd1.equals("")) {
                            try {
                                String pwd = SimpleCrypto.enCrypto(pwd1, "unique_pwd");
                                //设置sharedpreference为可写状态
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putInt("first", 0);
                                editor.putString("unique_pwd",pwd);
                                editor.commit();
                                dialog.dismiss();
                                Toast.makeText(SettingsActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
*/
/*
                                Intent intent = new Intent();
                                intent.setClass(SettingsActivity.this, );
                                startActivity(intent);
*//*

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            //密码输入不一致
                            Toast.makeText(SettingsActivity.this,"两次密码输入不一致，请重新输入",Toast.LENGTH_SHORT).show();
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
        });

        //返回textview事件
        back = (TextView) findViewById(R.id.title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
    }

    public void setCboxState(CheckBox cobx , int isChecked){
        //设置checkbox的当前状态
        boolean value;
        value = isChecked == 1;
        cobx.setChecked(value);
    }

    public void changeSetting(String cboxName,boolean checked){
        SharedPreferences.Editor editor = settings.edit();
        int value;
        if(checked){
            value = 1;
        }else{
            value = 0;
        }
        switch (cboxName){
            case "diary" :
                editor.putInt("diary_lock",value);
                editor.apply();
                break;
            case "memo":
                editor.putInt("memo_lock",value);
                editor.apply();
                break;
            case "cipher" :
                editor.putInt("cipher_lock",value);
                editor.apply();
                break;
            default:
                break;

        }



    }


}
*/
