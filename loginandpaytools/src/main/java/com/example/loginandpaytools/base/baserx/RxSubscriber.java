package com.example.loginandpaytools.base.baserx;

import android.content.Context;

import com.example.loginandpaytools.Exception.ServerException;
import com.example.loginandpaytools.R;
import com.example.loginandpaytools.base.util.NetWorkUtils;

import rx.Subscriber;


/**
 * Created by linsawako on 2017/2/22.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {
    public Context mContext;

    public RxSubscriber(Context context) {
        mContext = context;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        Context applicationContext = mContext.getApplicationContext();

        if (!NetWorkUtils.isNetConnected(applicationContext)) {
            _onError(applicationContext.getString(R.string.paylibrary_no_net));
        } else if (e instanceof ServerException) {
            _onError(e.getMessage());
        } else {
            _onError(applicationContext.getString(R.string.paylibrary_net_error));
        }

    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String errMsg);

}

