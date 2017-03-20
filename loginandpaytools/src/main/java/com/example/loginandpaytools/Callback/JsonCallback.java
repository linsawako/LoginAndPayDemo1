package com.example.loginandpaytools.Callback;

import android.content.Context;

import com.lzy.okgo.callback.AbsCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jerry on 17-2-20.
 */

public class JsonCallback<T> extends AbsCallback<T> {

    public JsonCallback(Context context) {

    }


    @Override
    public T convertSuccess(Response response) throws Exception {
        return null;
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {

    }
}
