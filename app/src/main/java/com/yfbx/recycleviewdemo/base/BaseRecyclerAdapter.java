/*
  * Copyright 2017 JessYan
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
package com.yfbx.recycleviewdemo.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author:Edward
 * Date:2017/9/21
 * Description:
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

    protected List<T> data;
    protected OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private BaseHolder<T> mHolder;
    protected ItemTouchHelper helper;

    public BaseRecyclerAdapter(List<T> data) {
        super();
        this.data = data;
    }

    /**
     * 创建Hodler
     */
    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        mHolder = getHolder(view, viewType);
        mHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {//设置Item点击事件
            @Override
            public void onViewClick(View view, int position) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, viewType, data.get(position), position);
                }
            }
        });
        return mHolder;
    }

    /**
     * 绑定数据
     */
    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position) {
        holder.setData(data.get(position), position);
    }


    /**
     * 数据的个数
     */
    @Override
    public int getItemCount() {
        return data.size();
    }


    public List<T> getData() {
        return data;
    }


    /**
     * 获得item的数据
     */
    public T getItem(int position) {
        return data == null ? null : data.get(position);
    }

    /**
     * 子类实现提供holder
     */
    public abstract BaseHolder<T> getHolder(View view, int viewType);

    /**
     * 提供Item的布局
     */
    public abstract int getLayoutId(int viewType);


    /**
     * 遍历所有hodler,释放他们需要释放的资源
     */
    public static void releaseAllHolder(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
            final View view = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder != null && viewHolder instanceof BaseHolder) {
                ((BaseHolder) viewHolder).onRelease();
            }
        }
    }


    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view, int viewType, T data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 拖拽事件
     */
    public void setOnItemDragListener(RecyclerView view, boolean isSwipeEnabled, ItemTouchCallback.OnItemDragListener listener) {
        ItemTouchCallback itemTouchCallback = new ItemTouchCallback(isSwipeEnabled, listener);
        helper = new ItemTouchHelper(itemTouchCallback);
        helper.attachToRecyclerView(view);
    }


    public void setDragView(final RecyclerView.ViewHolder holder, View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (helper != null) {
                        helper.startDrag(holder);
                    }
                }
                return false;
            }
        });
    }
}
