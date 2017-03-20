package com.example.loginandpaytools.Api;

import com.example.loginandpaytools.common.ApiConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;



/**
 * Created by linsawako on 2017/2/22.
 */

public class Api {

    private Retrofit retrofit;
    private static ApiService service;

    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 7676;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 7676;

    private Api() {

     OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .build();

     retrofit = new Retrofit.Builder()
            .client(okHttpClient)
             .addConverterFactory(ScalarsConverterFactory.create()) //添加字符串转换
          //  .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(ApiConfig.API_HOST)
     .build();
        service = retrofit.create(ApiService.class);
    }

    public static ApiService getDefault() {
        if (service == null) {
            synchronized (Api.class) {
                if (service == null) {
                    new Api();
                }
            }
        }
        return service;
    }


}
