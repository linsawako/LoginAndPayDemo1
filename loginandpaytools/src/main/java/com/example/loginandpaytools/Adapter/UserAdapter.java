package com.example.loginandpaytools.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loginandpaytools.bean.User;
import com.example.loginandpaytools.Holder.UserHolder;
import com.example.loginandpaytools.R;

import java.util.List;


/**
 * Created by jerry on 17-2-22.
 */

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<User> mUserItems;
    private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public UserAdapter(Context context, List<User> userItems) {
        mContext = context;
        mUserItems = userItems;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserHolder userHolder = new UserHolder(mLayoutInflater.inflate(R.layout.drop_list_item_user, parent, false), mContext);
        return userHolder;
    }

    @Override
    public void onBindViewHolder(final UserHolder holder, int position) {
        User user = mUserItems.get(position);
        holder.bindUser(user);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mUserItems.size();
    }
}
