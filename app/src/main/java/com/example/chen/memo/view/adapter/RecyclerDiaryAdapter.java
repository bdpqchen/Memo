/*
package com.example.chen.memo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chen.memo.R;
import com.example.chen.memo.bean.Diary;
import com.example.chen.memo.utils.LogUtils;
import com.example.chen.memo.utils.TimeStampUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;


*/
/**
 * Created by cdc on 16-9-24.
 *//*


public class RecyclerDiaryAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {

    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 0;
    private final List<Diary> datalist;
    private Context context;

    public RecyclerDiaryAdapter(Context context, List<Diary> datalist) {
        this.context = context;
        this.datalist = datalist;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalViewHolder(LayoutInflater.from(context).inflate(R.layout.item_diary_normal, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NormalViewHolder) {
            LogUtils.i("onBindViewHolder->", "NormalViewHolder");

            NormalViewHolder viewHolder = (NormalViewHolder) holder;

            String  publishTime = TimeStampUtils.getDatetimeString(datalist.get(position).getPublishTime());

            viewHolder.tvPartContent.setText(datalist.get(position).getDiary());
            viewHolder.tvPublishTime.setText(publishTime);

            LogUtils.i("diary_id",String.valueOf(datalist.get(position).getId()));

        } */
/*else if (holder instanceof FooterViewHolder) {

            LogUtils.i("onBindViewHolder->","FooterViewHolder");
        }*//*

        else if (datalist.size() == 0) {
            //空页面提示

        }
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public int getItemViewType(int position) {
        LogUtils.d("datalist.size()", String.valueOf(datalist.size()));
        LogUtils.d("position", String.valueOf(position));

        if (position + 1 < datalist.size()) {
            return TYPE_NORMAL;
        } else {
            return TYPE_FOOTER;
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView tvPartContent,tvPublishTime;

        public NormalViewHolder(View inflate) {
            super(inflate);
            tvPartContent = (TextView) inflate.findViewById(R.id.tv_part_content);
            tvPublishTime = (TextView) inflate.findViewById(R.id.tv_publish_time);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View inflate) {
            super(inflate);
            LogUtils.v("footerViewHolder");
        }
    }
}
*/
