package com.example.chen.memo.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.chen.memo.R;
import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.presenter.ValidatePresenterImpl;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.cipher.CipherListActivity;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.dump.DumpListActivity;
import com.example.chen.memo.view.memo.MemoListActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
 * Created by cdc on 16-9-23.
*/
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.nav_view)
    NavigationView navView;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        //将Activity加入activity管理类
        CustomApplication.addActivity(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        ValidatePresenterImpl validatePresenterImpl = new ValidatePresenterImpl();
        if (id == R.id.diary) {
            if (PrefUtils.isDiaryLock()) {
                validatePresenterImpl.login(this, NextActivity.DiaryList);
            } else {
                startActivity(DiaryListActivity.class);
            }
        } else if (id == R.id.memo) {
            if (PrefUtils.isMemoLock()) {
                validatePresenterImpl.login(this, NextActivity.MemoList);
            } else {
                startActivity(MemoListActivity.class);
            }
        } else if (id == R.id.cipher) {

            if (PrefUtils.isCipherLock()) {
                validatePresenterImpl.login(this, NextActivity.CipherList);
            } else {
                startActivity(CipherListActivity.class);
            }
        } else if(id == R.id.dump){
            /* if (PrefUtils.isDumpLock()) {
                validatePresenterImpl.login(this, NextActivity.CipherList);
            } else {
                startActivity(CipherListActivity.class);
            }*/

            startActivity(DumpListActivity.class);

        }

        else if (id == R.id.settings) {
            validatePresenterImpl.setup(this, NextActivity.Settings);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startActivity(Class<?> nextActivity) {
        Intent intent = new Intent();
        intent.setClass(this, nextActivity);
        startActivity(intent);

    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(getWindow().getDecorView().getWindowToken(), 0);
    }

    public void startActivity() {

    }

}
