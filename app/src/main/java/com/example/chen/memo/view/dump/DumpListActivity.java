package com.example.chen.memo.view.dump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chen.memo.R;
import com.example.chen.memo.bean.Dump;
import com.example.chen.memo.presenter.DumpPresenterImpl;
import com.example.chen.memo.presenter.ViewListPresenterImpl;
import com.example.chen.memo.view.BaseActivity;
import com.example.chen.memo.view.adapter.DumpMenuAdapter;
import com.example.chen.memo.view.cipher.CipherActivity;
import com.example.chen.memo.view.common.DividerItemDecoration;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cdc on 16-10-19.
 */

public class DumpListActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recyclerview)
    SwipeMenuRecyclerView mRecyclerView;
    private ViewListPresenterImpl viewListPresenter;
    private Context mContext;
    private DumpMenuAdapter recyclerAdapter;
    private List<Dump> datalist = new ArrayList<>();
    private DumpPresenterImpl dumpPresenter;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_recycled_list);
        ButterKnife.inject(this);
        mContext = this;
        this.dumpPresenter = new DumpPresenterImpl();

        toolbar.setTitle(R.string.toolbar_title_recycled_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //加载数据
        dumpPresenter.initData((DumpListActivity) mContext);

        viewListPresenter = new ViewListPresenterImpl();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(16));

        // 设置菜单创建器
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        //设置菜单item点击监听
        mRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        recyclerAdapter = new DumpMenuAdapter(DumpListActivity.this, ((DumpListActivity) mContext).datalist);
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                startActivity(new Intent(this, CipherActivity.class));
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
//                        .setImage(R.mipmap.ic_action_delete)
//                        .setText(R.string.item_menu_delete) // 文字，还可以设置文字颜色，大小等。。
                        .setText("永久\n删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_purple)
//                        .setImage(R.mipmap.ic_action_close)
                        .setText(R.string.item_menu_revert)
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
            if (menuPosition == 1) {
                //还原
                viewListPresenter.revertRecord(DumpListActivity.this, datalist.get(adapterPosition).getType(), datalist.get(adapterPosition).getId());
                datalist.remove(adapterPosition);
                recyclerAdapter.notifyItemRemoved(adapterPosition);
            }else if (menuPosition == 0){
                //// 删除按钮被点击。
                viewListPresenter.deleteRecord(DumpListActivity.this, datalist.get(adapterPosition).getType(), datalist.get(adapterPosition).getId());
                datalist.remove(adapterPosition);
                recyclerAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };

    public void onDeleteSuccess() {
        this.onToastMessage(getString(R.string.delete_success));
    }

    private void onToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onInitSuccess(List<Dump> dumpList) {
        if (dumpList.size() > 0) {
            this.datalist = dumpList;
        }
    }

    public void onRevertSuccess() {
        onToastMessage(getString(R.string.revert_success));
    }

}
