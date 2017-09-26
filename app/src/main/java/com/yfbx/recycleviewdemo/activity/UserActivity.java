package com.yfbx.recycleviewdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfbx.recycleviewdemo.R;
import com.yfbx.recycleviewdemo.adapter.UserAdapter;
import com.yfbx.recycleviewdemo.base.BaseActivity;
import com.yfbx.recycleviewdemo.base.BaseRecyclerAdapter;
import com.yfbx.recycleviewdemo.bean.User;
import com.yfbx.recycleviewdemo.base.ItemTouchCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Edward
 * Date:2017/9/25
 * Description:
 */

public class UserActivity extends BaseActivity implements BaseRecyclerAdapter.OnRecyclerViewItemClickListener, ItemTouchCallback.OnItemDragListener {

    @BindView(R.id.main_list)
    RecyclerView recyclerView;

    private UserAdapter adapter;
    private List<User> data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();

        recyclerView.setHasFixedSize(true);//固定recyclerview大小
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemDragListener(recyclerView, true, this);
    }

    private void initData() {
        data = new ArrayList<>();
        data.add(new User("Edward", 26));
        data.add(new User("John", 27));
        data.add(new User("Jane", 23));
        data.add(new User("Steve", 25));
    }

    /**
     * Item 点击事件
     */
    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {

    }

    /**
     * Item 拖拽事件
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * Item 侧滑事件
     */
    @Override
    public void onItemSwipe(int position) {
        // TODO: 2017/9/25 显示提示信息
        data.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
