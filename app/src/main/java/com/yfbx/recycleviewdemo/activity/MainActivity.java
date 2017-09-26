package com.yfbx.recycleviewdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfbx.recycleviewdemo.R;
import com.yfbx.recycleviewdemo.adapter.MainAdapter;
import com.yfbx.recycleviewdemo.base.BaseActivity;
import com.yfbx.recycleviewdemo.base.BaseRecyclerAdapter;
import com.yfbx.recycleviewdemo.base.ItemTouchCallback;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BaseRecyclerAdapter.OnRecyclerViewItemClickListener, ItemTouchCallback.OnItemDragListener {

    @BindView(R.id.main_list)
    RecyclerView recyclerView;

    private List<String> data;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        data = Arrays.asList(getResources().getStringArray(R.array.main_items));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MainAdapter(data);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemDragListener(recyclerView,true,this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {

        if (data.equals("User Activity")) {
            startActivity(new Intent(this, UserActivity.class));
        }

        if (data.equals("Grid Activity")) {
            startActivity(new Intent(this, GridActivity.class));
        }

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        data.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
