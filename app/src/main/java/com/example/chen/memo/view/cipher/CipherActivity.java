package com.example.chen.memo.view.cipher;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.presenter.CipherPresenterImpl;
import com.example.chen.memo.view.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.chen.memo.application.CustomApplication.ID;
import static com.example.chen.memo.application.CustomApplication.KEY_PWD;
import static com.example.chen.memo.application.CustomApplication.PWD_ACCOUNT;
import static com.example.chen.memo.application.CustomApplication.PWD_NAME;
import static com.example.chen.memo.application.CustomApplication.PWD_PWD;

/**
 * Created by cdc on 16-10-18.
 */

public class CipherActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.pwd_name)
    EditText name;
    @InjectView(R.id.account)
    EditText account;
    @InjectView(R.id.pwd)
    EditText pwd;
    @InjectView(R.id.fab_finish)
    FloatingActionButton fabFinish;
    private Context mContext;
    private CipherPresenterImpl cipherPresenterImpl;
    private Bundle oldBundle;


    @Override
    protected int getLayout() {
        return R.layout.activity_cipher;
    }

    @Override
    protected Toolbar getToolbar() {
        toolbar.setTitle(R.string.toolbar_title_create_cipher);
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        mContext = getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fabFinish.setOnClickListener(this);
        cipherPresenterImpl = new CipherPresenterImpl();

        oldBundle = getIntent().getExtras();
        if (oldBundle != null) {
            name.setText(oldBundle.getString(PWD_NAME));
            account.setText(oldBundle.getString(PWD_ACCOUNT));
            try {
                pwd.setText(SimpleCrypto.deCrypto(oldBundle.getString(PWD_PWD), KEY_PWD));
            } catch (Exception e) {
                pwd.setText("解密失败.建议查杀病毒后重试");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_finish:
                Bundle bundle = new Bundle();
                bundle.putString(PWD_NAME, String.valueOf(name.getText()));
                bundle.putString(PWD_ACCOUNT, String.valueOf(account.getText()));
                bundle.putString(PWD_PWD, String.valueOf(pwd.getText()));
                if (oldBundle == null) {
                    //添加记录
                    cipherPresenterImpl.createCipher(this, bundle);
                } else {
                    //修改记录 where -->id
                    bundle.putInt(ID, oldBundle.getInt(ID));
                    cipherPresenterImpl.alterCipher(this, bundle);
                }

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

    public void nameGetError(String msg) {
        name.setError(msg);
    }

    public void accountGetError(String msg) {
        account.setError(msg);
    }

    public void pwdGetError(String msg) {
        pwd.setError(msg);
    }

    public void onToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
