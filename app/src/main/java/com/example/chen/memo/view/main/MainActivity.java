package com.example.chen.memo.view.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.chen.memo.R;
import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.mydatepicker.DPDecor;
import com.example.chen.memo.mydatepicker.DatePicker2;
import com.example.chen.memo.presenter.ValidatePresenterImpl;
import com.example.chen.memo.utils.PrefUtils;
import com.example.chen.memo.utils.ToastUtils;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.cipher.CipherActivity;
import com.example.chen.memo.view.cipher.CipherListActivity;
import com.example.chen.memo.view.common.NextActivity;
import com.example.chen.memo.view.diary.DiaryActivity;
import com.example.chen.memo.view.diary.DiaryListActivity;
import com.example.chen.memo.view.dump.DumpListActivity;
import com.example.chen.memo.view.memo.MemoActivity;
import com.example.chen.memo.view.memo.MemoListActivity;
import com.example.chen.memo.view.update.SearchUpdate;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.InjectView;
import butterknife.OnClick;

/*
 * Created by cdc on 16-9-23.
*/
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.fam)
    FloatingActionsMenu actionsMenu;
    @InjectView(R.id.action_diary)
    FloatingActionButton actionButtonDiary;
    @InjectView(R.id.action_memo)
    FloatingActionButton actionButtonMemo;
    @InjectView(R.id.action_cipher)
    FloatingActionButton actionButtonCipher;
    @InjectView(R.id.nav_view)
    NavigationView navView;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.white_view)
    View whiteView;


    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolbar() {
        mToolbar.setTitle(R.string.app_name);
        return mToolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //对于DrawerLayout的沉浸式状态栏,5.0以下问题明显
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            //将侧边栏顶部延伸至status bar
            drawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
            drawerLayout.setClipToPadding(false);
        }

//        ToastUtils.showMessage(this, "This is a new version for you.");

        actionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                whiteView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                whiteView.setVisibility(View.INVISIBLE);
            }
        });


        DatePicker2 picker = (DatePicker2) findViewById(R.id.date_picker);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        //当前的日期时间
        int startYear = calendar.get(Calendar.YEAR);
        int startMonth = (calendar.get(Calendar.MONTH)) ;

        picker.setDate(startYear, startMonth + 1);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(0x88F37B70);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
            }
        });

        TextView tvSignInCount = (TextView) findViewById(R.id.tv_sign_in_count);
        tvSignInCount.setText(PrefUtils.getSignInCount() + "");

        //将Activity加入activity管理类
        CustomApplication.addActivity(this);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navView.setNavigationItemSelectedListener(this);

        checkUpdate(2000);
    }

    public void checkUpdate(int time){
        SearchUpdate searchUpdate = new SearchUpdate(this);
        //第二个参数是延迟执行时间
        searchUpdate.checkUpdate(time);
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

        } else if (id == R.id.settings) {
            validatePresenterImpl.setup(this, NextActivity.Settings);
        } else if (id == R.id.action_check_update){
            SearchUpdate searchUpdate = new SearchUpdate(this);
            searchUpdate.checkUpdate(0);
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

    public void startViewActivity(Intent intent) {
        startActivity(intent);
    }

    @OnClick({R.id.action_diary, R.id.action_memo, R.id.action_cipher, R.id.white_view})
    void onMenuClick(View v){
        Intent intent;
        switch(v.getId()){

            case R.id.action_diary:
                intent = new Intent(this, DiaryActivity.class);
                startActivity(intent);
                break;
            case R.id.action_memo:
                intent = new Intent(this, MemoActivity.class);
                startActivity(intent);
                break;
            case R.id.action_cipher:
                intent = new Intent(this, CipherActivity.class);
                startActivity(intent);
                break;
            case R.id.white_view:
                whiteView.setVisibility(View.INVISIBLE);
                break;
        }
        actionsMenu.collapse();


    }

}
