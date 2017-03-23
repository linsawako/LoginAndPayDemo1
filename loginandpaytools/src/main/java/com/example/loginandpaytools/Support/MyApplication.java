package com.example.loginandpaytools.Support;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.MemoryCookieStore;

/**
 * Created by jerry on 17-2-23.
 */

public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.init(this);

        OkGo.getInstance()
                .setCookieStore(new MemoryCookieStore())
                .setCertificates()
                .setCacheMode(CacheMode.NO_CACHE);

        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
