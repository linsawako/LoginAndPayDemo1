package com.example.loginandpaytools.base.baserx;

import android.content.Context;
import android.util.Log;

import com.example.loginandpaytools.Exception.ServerException;
import com.example.loginandpaytools.R;
import com.example.loginandpaytools.base.util.NetWorkUtils;
import com.example.loginandpaytools.ui.CustomProgressDialog;

import rx.Subscriber;

import static android.content.ContentValues.TAG;


/**
 * Created by linsawako on 2017/2/22.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {
    public Context mContext;
    private static CustomProgressDialog mCustomProgressDialog;
    public boolean showDialog;

    /*mCustomProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
                        mCustomProgressDialog.show();*/

    public RxSubscriber(Context context, boolean showDialog) {
        mContext = context;
        this.showDialog = showDialog;
        mCustomProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
    }

    public RxSubscriber(Context context) {
        this(context, true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            mCustomProgressDialog.show();
        }
    }

    @Override
    public void onCompleted() {
        if (showDialog) {
            mCustomProgressDialog.dismiss();
            Log.d(TAG, "onCompleted: dismiss");
        }
        _onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        if (showDialog) {
            mCustomProgressDialog.dismiss();
        }

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

    public abstract void _onCompleted();

}

