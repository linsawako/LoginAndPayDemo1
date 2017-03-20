package com.example.loginandpaytools.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.loginandpaytools.bean.User;
import com.example.loginandpaytools.R;


/**
 * Created by jerry on 17-2-22.
 */

public class UserHolder extends RecyclerView.ViewHolder{

    private User mUser;
    private TextView mUserNameTextView;
    private TextView mDateTextView;
    private Context mContext;

    public UserHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        initItemView(itemView);
    }

    private void initItemView(View itemView) {
        mUserNameTextView = (TextView) itemView.findViewById(R.id.user_name_textview);
        mDateTextView = (TextView) itemView.findViewById(R.id.date_textview);
    }

    public void bindUser(User user){
            mUser = user;
        mUserNameTextView.setText(mUser.getUserName());
        mDateTextView.setText(mUser.getData());
    }

}
