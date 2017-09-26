package com.yfbx.recycleviewdemo.base;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Author:Edward
 * Date:2017/9/21
 * Description:
 */

public class ItemTouchCallback extends ItemTouchHelper.Callback {

    private OnItemDragListener listener;
    private boolean isSwipeEnabled;

    public ItemTouchCallback(boolean isSwipeEnabled, OnItemDragListener listener) {
        this.isSwipeEnabled = isSwipeEnabled;
        this.listener = listener;
    }

    /**
     * 是否支持侧滑
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnabled;
    }

    /**
     * 设置拖动的方向以及侧滑的方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = isSwipeEnabled ? ItemTouchHelper.UP | ItemTouchHelper.DOWN ://支持侧滑，List样式，只能上下拖拽
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//不支持侧滑，Grid样式，可四个方向拖拽
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;//侧滑方向
        return makeMovementFlags(dragFlags, swipeFlags);

    }

    /**
     * 拖动item时会回调此方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //如果两个item不是一个类型的，我们让他不可以拖拽
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        //回调adapter中的onItemMove方法
        listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 侧滑item时会回调此方法
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onItemSwipe(viewHolder.getAdapterPosition());
    }


    /**
     * 状态改变事件
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        //当前状态不是idel（空闲）状态时，说明当前正在拖拽或者侧滑
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //看看这个viewHolder是否实现了onStateChangedListener接口
            if (viewHolder instanceof OnStateChangedListener) {
                OnStateChangedListener listener = (OnStateChangedListener) viewHolder;
                //回调ItemViewHolder中的onItemSelected方法来改变item的背景颜色
                listener.onItemSelected();
            }
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof OnStateChangedListener) {
            OnStateChangedListener listener = (OnStateChangedListener) viewHolder;
            listener.onItemClear();
        }
    }

    /**
     * 这个方法可以判断当前是拖拽还是侧滑
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        switch (actionState) {
            case ItemTouchHelper.ACTION_STATE_SWIPE:
                break;
            case ItemTouchHelper.ACTION_STATE_DRAG:
                break;
            case ItemTouchHelper.ACTION_STATE_IDLE:
        }
    }

    /**
     * 拖拽事件回调接口
     */
    public interface OnItemDragListener {

        boolean onItemMove(int fromPosition, int toPosition);

        void onItemSwipe(int position);
    }

    /**
     * 状态改变事件回调接口
     */
    public interface OnStateChangedListener {

        void onItemSelected();

        void onItemClear();
    }

}
