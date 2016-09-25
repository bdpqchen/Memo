package com.example.chen.memo.view.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.SettingsActivity;
import com.example.chen.memo.SimpleCrypto;
import com.example.chen.memo.ViewCipherActivity;
import com.example.chen.memo.ViewDiaryActivity;
import com.example.chen.memo.ViewMemoActivity;
import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.presenter.ValidatePresenter;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.common.NextActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cdc on 16-9-23.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainAcitvityImpl {

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
        ValidatePresenter validatePresenter = new ValidatePresenter();
        if (id == R.id.diary) {
            if (PrefUtils.isDiaryLock()) {
                validatePresenter.login(this, NextActivity.DiaryList);
            } else {
                startActivity(ViewDiaryActivity.class);
            }
        } else if (id == R.id.memo) {
            if (PrefUtils.isMemoLock()) {
                validatePresenter.login(this, NextActivity.MemoList);
            } else {
                startActivity(ViewMemoActivity.class);
            }
        } else if (id == R.id.cipher) {
            if (PrefUtils.isCipherLock()) {
                validatePresenter.login(this, NextActivity.CipherList);
            } else {
                startActivity(ViewCipherActivity.class);
            }
        } else if (id == R.id.settings) {
            validatePresenter.setup(this,NextActivity.Settings);
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
