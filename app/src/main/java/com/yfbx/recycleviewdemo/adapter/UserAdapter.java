package com.yfbx.recycleviewdemo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yfbx.recycleviewdemo.R;
import com.yfbx.recycleviewdemo.base.BaseHolder;
import com.yfbx.recycleviewdemo.base.BaseRecyclerAdapter;
import com.yfbx.recycleviewdemo.bean.User;

import java.util.List;

import butterknife.BindView;

/**
 * Author:Edward
 * Date:2017/9/20
 * Description:
 */

public class UserAdapter extends BaseRecyclerAdapter<User> {

    public UserAdapter(List<User> data) {
        super(data);
    }


    @Override
    public BaseHolder<User> getHolder(View v, int viewType) {
        return new UserHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_user;
    }


    class UserHolder extends BaseHolder<User> {

        @BindView(R.id.name)
        TextView nameTxt;
        @BindView(R.id.age)
        TextView ageTxt;
        @BindView(R.id.img_btn)
        ImageView btn;

        public UserHolder(View itemView) {
            super(itemView);
            setDragView(this,btn);//设置拖拽图标
        }

        @Override
        public void setData(User data, int position) {
            nameTxt.setText(data.getName());
            ageTxt.setText("" + data.getAge());
        }

    }
}
