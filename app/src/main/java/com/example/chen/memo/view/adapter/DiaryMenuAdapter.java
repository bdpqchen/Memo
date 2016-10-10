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
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chen.memo.R;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.listener.OnItemClickListener;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.TimeStampUtils;
import com.example.chen.memo.view.diary.DiaryDetailActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class DiaryMenuAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {

    private List<Diary> datalist;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 0;
    private Context context;
//    private OnItemClickListener mOnItemClickListener;

    public DiaryMenuAdapter(Context context, List<Diary>datalist) {
        this.datalist = datalist;
        this.context  = context;
    }

    /*public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.mOnItemClickListener = onItemClickListener;
    }*/

    @Override
    public int getItemCount() {
        return datalist == null ? 0 : datalist.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder viewHolder = (NormalViewHolder) holder;
            final String  publishTime = TimeStampUtils.getDatetimeString(datalist.get(position).getPublishTime());
            final String diary = datalist.get(position).getDiary();
            final int id = datalist.get(position).getId();
            viewHolder.tvPartContent.setText(diary);
            viewHolder.tvPublishTime.setText(publishTime);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DiaryDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("diary", diary);
                    bundle.putString("publishTime", publishTime);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    //context.finish();

                    /*
                    * 构建diaryDetailActivity类
                    * 修改操作
                    * eventBus 返回更新条数
                    *
                    * */


                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LogUtils.v("longClick");
                    return true;
                }
            });
        }
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return LayoutInflater.from(context).inflate(R.layout.item_diary_normal, parent, false);
        } else if (viewType == TYPE_FOOTER) {
            return LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalViewHolder(realContentView);
        }else if(viewType == TYPE_FOOTER) {
            return new FooterViewHolder(realContentView);
        }
        return null;
    }

    public int getItemViewType(int position){
        if(position + 1 < datalist.size()){
            return TYPE_NORMAL;
        }else{
            return TYPE_FOOTER;
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvPartContent,tvPublishTime;
        public NormalViewHolder(View inflate) {
            super(inflate);
            inflate.setOnClickListener(this);
            tvPartContent = (TextView) inflate.findViewById(R.id.tv_part_content);
            tvPublishTime = (TextView) inflate.findViewById(R.id.tv_publish_time);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View inflate) {
            super(inflate);
            LogUtils.v("footerViewHolder");
        }
    }

}
