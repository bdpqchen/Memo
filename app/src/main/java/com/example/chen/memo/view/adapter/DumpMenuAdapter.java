/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.chen.memo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chen.memo.R;
import com.example.chen.memo.bean.Dump;
import com.example.chen.memo.presenter.DumpPresenterImpl;
import com.example.chen.memo.utils.LogUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.chen.memo.application.CustomApplication.CIPHER;
import static com.example.chen.memo.application.CustomApplication.CIPHER_NAME;
import static com.example.chen.memo.application.CustomApplication.DIARY;
import static com.example.chen.memo.application.CustomApplication.DIARY_NAME;
import static com.example.chen.memo.application.CustomApplication.MEMO;
import static com.example.chen.memo.application.CustomApplication.MEMO_NAME;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class DumpMenuAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {

    private List<Dump> datalist = new ArrayList<>();
    private final int TYPE_WITHOUT = 2;
    private final int TYPE_NORMAL = 1;
    private Context context;
    private DumpPresenterImpl dumpPresenterImpl;

    public DumpMenuAdapter(Context context, List<Dump> datalist) {
        LogUtils.i("dumpmenuAdapter----datalist.size", String.valueOf(datalist.size()));
        this.datalist = datalist;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder viewHolder = (NormalViewHolder) holder;
            final String titleType = getTitleType(datalist.get(position).getType());
            final String title = datalist.get(position).getTitle();
//            final int id = datalist.get(position).getId();
            viewHolder.tvTitle.setText(title);
            viewHolder.tvType.setText(titleType);
        }
    }

    private String getTitleType(int type){
        String s = "";
        switch (type){
            case DIARY:
                s = DIARY_NAME; break;
            case MEMO:
                s = MEMO_NAME; break;
            case CIPHER:
                s = CIPHER_NAME; break;
        }
        return s;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return LayoutInflater.from(context).inflate(R.layout.item_dump_normal, parent, false);
        }  else if (viewType == TYPE_WITHOUT) {
            return LayoutInflater.from(context).inflate(R.layout.item_without, parent, false);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalViewHolder(realContentView);
        }  else if (viewType == TYPE_WITHOUT) {
            return new WithoutViewHolder(realContentView);
        }
        return null;
    }

    public int getItemViewType(int position) {
        int itemCount = getItemCount();
        //没有数据item
        if (itemCount == 0) return TYPE_WITHOUT;
        //正常item
        if (position + 1 <= itemCount) {
            return TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvType;
        public NormalViewHolder(View inflate) {
            super(inflate);
            inflate.setOnClickListener(this);
            tvTitle = (TextView) inflate.findViewById(R.id.tv_part_content);
            tvType  = (TextView) inflate.findViewById(R.id.tv_publish_time);
        }
        @Override
        public void onClick(View v) {

        }
    }

    private class WithoutViewHolder extends RecyclerView.ViewHolder {
        WithoutViewHolder(View inflate) {
            super(inflate);
            LogUtils.v("WithoutViewHolder");
        }
    }
}
