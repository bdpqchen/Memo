package com.example.chen.memo.view.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cdc on 16-9-27.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int offsetSize;

    public DividerItemDecoration(int size){
        this.offsetSize = size;
    }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildAdapterPosition(view) != 0)
                outRect.top = offsetSize;
        }

}
