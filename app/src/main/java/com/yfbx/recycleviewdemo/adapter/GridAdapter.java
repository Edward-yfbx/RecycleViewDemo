package com.yfbx.recycleviewdemo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yfbx.recycleviewdemo.R;
import com.yfbx.recycleviewdemo.base.BaseHolder;
import com.yfbx.recycleviewdemo.base.BaseRecyclerAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Author:Edward
 * Date:2017/9/25
 * Description:
 */

public class GridAdapter extends BaseRecyclerAdapter<String> {

    public GridAdapter(List<String> data) {
        super(data);
    }

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new ViewHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_list;
    }


    class ViewHolder extends BaseHolder<String> {

        @BindView(R.id.item_text)
        TextView textView;
        @BindView(R.id.item_btn)
        ImageView btn;

        public ViewHolder(View itemView) {
            super(itemView);
            setDragView(this,btn);
        }

        @Override
        public void setData(String data, int position) {
            textView.setText(data);
        }

    }
}
