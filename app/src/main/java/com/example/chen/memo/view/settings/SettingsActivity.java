package com.example.chen.memo.view.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.chen.memo.application.CustomApplication.KEY_UNIQUE_PASSWORD;

/**
 * Created by cdc on 16-10-23.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.switch_diary_lock)
    SwitchCompat switchDiaryLock;
    @InjectView(R.id.switch_memo_lock)
    SwitchCompat switchMemoLock;
    @InjectView(R.id.switch_cipher_lock)
    SwitchCompat switchCipherLock;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.alter_pwd)
    RelativeLayout alterPwd;

    @Override
    protected int getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected Toolbar getToolbar() {
        toolbar.setTitle(R.string.toolbar_settings);
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alterPwd.setOnClickListener(this);
        switchDiaryLock.setChecked(PrefUtils.isDiaryLock());
        switchMemoLock.setChecked(PrefUtils.isMemoLock());
        switchCipherLock.setChecked(PrefUtils.isCipherLock());
        switchDiaryLock.setOnCheckedChangeListener(this);
        switchMemoLock.setOnCheckedChangeListener(this);
        switchCipherLock.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.switch_diary_lock:
                if (isChecked) {
                    PrefUtils.setDiaryLock(true);
                } else {
                    PrefUtils.setDiaryLock(false);
                }
                break;
            case R.id.switch_memo_lock:
                if (isChecked) {
                    PrefUtils.setMemoLock(true);
                } else {
                    PrefUtils.setMemoLock(false);
                }
                break;
            case R.id.switch_cipher_lock:
                if (isChecked) {
                    PrefUtils.setCipherLock(true);
                } else {
                    PrefUtils.setCipherLock(false);
                }
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.alter_pwd:
                //更改密码
                LayoutInflater inflater = LayoutInflater.from(this);
                View layout = inflater.inflate(R.layout.dialog_input, null);

                final EditText dialogPwd = (EditText) layout.findViewById(R.id.dialog_pwd);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.hint_input_pwd);
                builder.setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pwd = String.valueOf(dialogPwd.getText());
                        if (!pwd.equals("") && pwd != null) {
                            try {
                                String s = SimpleCrypto.enCrypto(pwd, KEY_UNIQUE_PASSWORD);
                                PrefUtils.setUniquePwd(s);
                                Toast.makeText(SettingsActivity.this, R.string.set_pwd_succeed, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                builder.setView(layout);
                builder.show();
                break;
        }
    }
}
