package com.example.loginandpaytools.base.baserx;


/**
 * Created by linsawako on 2017/2/22.
 */

public class BaseResponse<T> {
    private int state;
    private T data;

    public boolean isStatusOK() {
        return state == 1;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}