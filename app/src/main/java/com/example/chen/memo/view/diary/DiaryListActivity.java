package com.example.chen.memo.view.diary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.application.CustomApplication;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.event.DiaryEvent;
import com.example.chen.memo.presenter.ViewListPresenterImpl;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.adapter.DiaryMenuAdapter;
import com.example.chen.memo.view.common.DividerItemDecoration;
import com.example.chen.memo.view.common.NextActivity;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static com.example.chen.memo.application.CustomApplication.RECORD_LIST_LIMIT;

/**
 * Created by cdc on 16-9-24.
 */

public class DiaryListActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.recyclerview)
    SwipeMenuRecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Diary> datalist = new ArrayList<>();
    private DiaryMenuAdapter recyclerAdapter;
    private boolean loading = false;
    private ViewListPresenterImpl viewListPresenter;
    private int offset;
    private int dataCount;
    private Activity mContext;

    @Override
    protected int getLayout() {
        return R.layout.activity_diary_list;
    }

    @Override
    protected Toolbar getToolbar() {
        toolbar.setTitle(R.string.toolbar_title_diary_list);
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        EventBus.getDefault().register(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewListPresenter = new ViewListPresenterImpl();
        viewListPresenter.initData(CustomApplication.DIARY, this);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(16));

        // 设置菜单创建器。
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        //设置菜单item点击监听
        mRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        recyclerAdapter = new DiaryMenuAdapter(this, datalist);
        mRecyclerView.setAdapter(recyclerAdapter);

        fab.setOnClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalCount = linearLayoutManager.getItemCount();
                int lastItem = linearLayoutManager.findLastVisibleItemPosition();
                final ProgressBar progressBar;
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                final TextView noMoreData = (TextView) findViewById(R.id.no_more_data);

                if(totalCount < RECORD_LIST_LIMIT){
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }

                if (lastItem + 1 >= totalCount && !loading && dy > 0 && totalCount >= RECORD_LIST_LIMIT) {
                    //加载数据
                    loading = true;
                    offset++;
                    LogUtils.i("offset", String.valueOf(offset));
                    LogUtils.i("dataCount", String.valueOf(dataCount));
                    LogUtils.i("datalistSize", String.valueOf(datalist.size()));
                    viewListPresenter.loadMoreData(CustomApplication.DIARY, DiaryListActivity.this, offset);
                    viewListPresenter.getDataCount(CustomApplication.DIARY, DiaryListActivity.this);
                    fab.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LogUtils.i("datalist.size()", String.valueOf(datalist.size()));
                            LogUtils.i("dataCount", String.valueOf(dataCount));
                            if (datalist.size() < dataCount) {
                                LogUtils.v("recyclerAdapter.notifyDataSetChanged();");
                                recyclerAdapter.notifyDataSetChanged();
                            } else {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                    noMoreData.setText(getString(R.string.no_more_data));
                                    noMoreData.setVisibility(View.VISIBLE);
                                }
                            }
                            loading = false;
                        }
                    }, 400);
                }else if(lastItem +2 <totalCount && !loading){
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.item_height);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText(R.string.item_menu_discard) // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。
            }
        }
    };
    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                viewListPresenter.discardRecord(DiaryListActivity.this, NextActivity.DiaryList, datalist.get(adapterPosition).getId());
                datalist.remove(adapterPosition);
                recyclerAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };
    /*
    * 长按删除
    * */
    public void onDeleteItem(int position){
        datalist.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
        this.onToastMessage(getString(R.string.already_delete_diary));
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

    public void onToastMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void onInitSuccess(List<Diary> datalist) {
        if(datalist.size() > 0){
            this.datalist.addAll(datalist);
        }
    }

    public void onGetDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivity(new Intent(this, DiaryActivity.class));
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(DiaryEvent event) {
        String msg = event.getMsg();
        offset = 0;
        datalist.removeAll(datalist);
        viewListPresenter.initData(CustomApplication.DIARY, this);
        //startActivity 执行后会销毁 recycleradapter 对象
        recyclerAdapter = new DiaryMenuAdapter(this, datalist);
        mRecyclerView.setAdapter(recyclerAdapter);
        //更新列表
        recyclerAdapter.notifyDataSetChanged();
    }

}
