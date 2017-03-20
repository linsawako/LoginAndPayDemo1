package com.example.loginandpaytools.base.baserx;

import android.util.Log;

import com.example.loginandpaytools.Exception.ServerException;
import com.example.loginandpaytools.base.util.ResponseType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;


/**
 * Created by linsawako on 2017/2/22.
 */

public class RxTransformer {

    /**
     * 线程调度
     * @param <T>
     * @return Transformer
     */
    public static <T> Observable.Transformer<T, T> schedules_io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 结果预处理，此方法用于处理不规范的JSON格式，代码需根据不规范的形式进行修改
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<String, T> handleResultFromString(final Class<T> cls) {
        if (cls == null) {
            throw new NullPointerException("cls cannot be null!");
        }
        return new Observable.Transformer<String, T>() {
            @Override
            public Observable<T> call(Observable<String> sObservable) {
                return sObservable.flatMap(new Func1<String, Observable<T>>() {
                    @Override
                    public Observable<T> call(String s) {
                        Log.d(TAG, "call: " + s);
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseResponse<T>>(){}.getType();

                        BaseResponse<T> response = gson.fromJson(s, type);

                        if (response.isStatusOK()) {
                            if (response.getData() == null) {
                                return createData(null);
                            } else {
                                Log.d(TAG, "call: " + response.getData().toString());
                                T t = gson.fromJson(response.getData().toString(), cls);
                                return createData(t);
                            }

                        } else {
                            BaseError error = gson.fromJson(s, BaseError.class);
                            Log.d(TAG, "call: " + error.getMsg());
                            return Observable.error(new ServerException(error.getMsg()));
                        }
                    }
                });
            }
        };
    }


    public static Observable.Transformer<Response<String>, String> handlerPayFromString() {

        return new Observable.Transformer<Response<String>, String>() {
            @Override
            public Observable<String> call(Observable<Response<String>> responseObservable) {
                return responseObservable.flatMap(new Func1<Response<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response<String> response) {
                        if (response.code() == 200) {
                            String s = response.body();

                            if (ResponseType.isJson(s)) {
                                Gson gson = new Gson();

                                BaseError error = gson.fromJson(s, BaseError.class);
                                return Observable.error(new ServerException(error.getMsg()));

                            } else {

                                return createData(response.body().toString());

                            }
                        }

                        return createData(null);
                    }
                });
            }
        };
    }
}

