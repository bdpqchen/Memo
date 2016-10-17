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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen.memo.R;
import com.example.chen.memo.bean.Memo;
import com.example.chen.memo.presenter.MemoPresenterImpl;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.TimeStampUtils;
import com.example.chen.memo.view.memo.MemoActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.Date;
import java.util.List;

import static com.example.chen.memo.application.CustomApplication.ID;
import static com.example.chen.memo.application.CustomApplication.MEMO_ALARM_TIME;
import static com.example.chen.memo.application.CustomApplication.MEMO_CONTENT;
import static com.example.chen.memo.application.CustomApplication.POSITION;
import static com.example.chen.memo.application.CustomApplication.PUBLISH_TIME;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MemoMenuAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {

    private List<Memo> datalist;
    private final int TYPE_WITHOUT = 2;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 0;
    private Context context;
    private MemoPresenterImpl memoPresenterImpl;

    public MemoMenuAdapter(Context context, List<Memo> datalist) {
        this.datalist = datalist;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder viewHolder = (NormalViewHolder) holder;
            final String publishTime = TimeStampUtils.getDatetimeString(datalist.get(position).getPublishTime());
            final String memo = datalist.get(position).getMemo();
            final int id = datalist.get(position).getId();
            viewHolder.tvPartContent.setText(memo);
            viewHolder.tvPublishTime.setText(publishTime);
            int alarmTimeStamp = datalist.get(position).getAlarmTime();
            if(alarmTimeStamp > 0 ){
                int nowTimeStamp = Integer.parseInt(((new Date().getTime() + "").substring(0, 9)));
                if(alarmTimeStamp > nowTimeStamp){
                    viewHolder.imageAlarm.setImageResource(R.drawable.ic_alarm_black_24dp);
                }
                viewHolder.imageAlarm.setVisibility(View.VISIBLE);
            }else{
                viewHolder.imageAlarm.setVisibility(View.GONE);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MemoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID, id);
                    bundle.putString(MEMO_CONTENT, memo);
                    bundle.putLong(PUBLISH_TIME, datalist.get(position).getPublishTime());
                    bundle.putInt(MEMO_ALARM_TIME, datalist.get(position).getAlarmTime());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    memoPresenterImpl = new MemoPresenterImpl();
                    Bundle bundle = new Bundle();
                    bundle.putInt(ID, datalist.get(position).getId());
                    bundle.putInt(POSITION, position);
                    //memoPresenterImpl.deleteMemo((MemoListActivity) context, bundle);
                    LogUtils.v("longClick");
                    return true;
                }
            });
        }
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return LayoutInflater.from(context).inflate(R.layout.item_memo_normal, parent, false);
        } else if (viewType == TYPE_FOOTER) {
            return LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
        } else if (viewType == TYPE_WITHOUT) {
            return LayoutInflater.from(context).inflate(R.layout.item_without, parent, false);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalViewHolder(realContentView);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(realContentView);
        } else if (viewType == TYPE_WITHOUT) {
            return new WithoutViewHolder(realContentView);
        }
        return null;
    }

    public int getItemViewType(int position) {
        int itemCount = getItemCount();
        //没有数据item
        if (itemCount == 1) return TYPE_WITHOUT;
        //正常item
        if (position + 1 < itemCount) {
            return TYPE_NORMAL;
        }else{
            return TYPE_FOOTER;
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size() + 1;
    }


    private class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPartContent, tvPublishTime;
        ImageView imageAlarm;
        NormalViewHolder(View inflate) {
            super(inflate);
            inflate.setOnClickListener(this);
            tvPartContent = (TextView) inflate.findViewById(R.id.tv_part_content);
            tvPublishTime = (TextView) inflate.findViewById(R.id.tv_publish_time);
            imageAlarm = (ImageView) inflate.findViewById(R.id.image_alarm);
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

    private class WithoutViewHolder extends RecyclerView.ViewHolder {
        WithoutViewHolder(View inflate) {
            super(inflate);
            LogUtils.v("WithoutViewHolder");
        }
    }
}
