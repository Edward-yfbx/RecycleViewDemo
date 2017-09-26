package com.yfbx.recycleviewdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfbx.recycleviewdemo.R;
import com.yfbx.recycleviewdemo.adapter.GridAdapter;
import com.yfbx.recycleviewdemo.base.BaseActivity;
import com.yfbx.recycleviewdemo.base.BaseRecyclerAdapter;
import com.yfbx.recycleviewdemo.base.ItemTouchCallback;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Edward
 * Date:2017/9/25
 * Description:
 */

public class GridActivity extends BaseActivity implements BaseRecyclerAdapter.OnRecyclerViewItemClickListener, ItemTouchCallback.OnItemDragListener {

    @BindView(R.id.main_list)
    RecyclerView recyclerView;

    private GridAdapter adapter;
    private List<String> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);//固定recyclerview大小
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        data = Arrays.asList(getResources().getStringArray(R.array.dummy_items));
        adapter = new GridAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        adapter.setOnItemDragListener(recyclerView, false, this);

    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {

    }
}
