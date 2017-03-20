package com.example.loginandpaytools.base.basemvp;

import android.content.Context;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by linsawako on 2017/2/13.
 */

public class BasePresenter<V>  {

    protected Reference<V> mViewRefer;
    protected Context mContext;

    public BasePresenter(V view) {
        mViewRefer = new WeakReference<>(view);
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public V getView() {
        return mViewRefer.get();
    }

    public void attachView(V view) {
        mViewRefer = new WeakReference<V>(view);
    }

    public void detechView() {
        if (mViewRefer != null) {
            mViewRefer.clear();
            mViewRefer = null;
        }
    }

}
