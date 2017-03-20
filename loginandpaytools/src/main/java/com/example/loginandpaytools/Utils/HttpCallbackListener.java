package com.example.loginandpaytools.Utils;

/**
 * Created by jerry on 17-3-3.
 */

public interface HttpCallbackListener {

    void onFinish();
    void onFinish(String code);
    void onFinish(String username, String password);
    void onError(Exception e);
}
