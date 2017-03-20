package com.example.loginandpaytools.bean;

/**
 * Created by jerry on 17-2-22.
 */

public class User {
    private String mUserName;
    private String mData;
    private String mPassword;


    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
