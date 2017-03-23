package com.example.loginandpaytools.Api;

import com.example.loginandpaytools.bean.FabResponse;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by linsawako on 2017/2/22.
 */

public interface ApiService {

    /*充值下单接口*/
    @FormUrlEncoded
    @POST("?ct=pay&ac=order")
    Observable<String> postOrder(@Field("uid") int uid,
                                 @Field("token") String token,
                                 @Field("game_id") int game_id,
                                 @Field("package_name") String package_name,
                                 @Field("order_num") String order_num,
                                 @Field("total_fee") int total_fee,
                                 @Field("order_name") String order_name,
                                 @Field("pay_type") int pay_type,
                                 @Field("server_id") int server_id,
                                 @Field("role_id") int role_id,
                                 @Field("role_name") String role_name,
                                 @Field("role_level") int role_level,
                                 @Field("ext") String ext,
                                 @Field("sign") String sign);

    /*支付接口*/
    @GET("?ct=pay&ac=payOrder")
    Observable<Response<String>> getAliPayOrder(@Query("platform_order_num") String platform_order_num,
                                                @Query("sign") String sign);

    /*检查订单是否已经支付*/
    @FormUrlEncoded
    @POST("?ct=pay&ac=payResult")
    Observable<String> getPayResult(@Field("platform_order_num") String platform_order_num,
                                    @Field("sign") String sign);

    @FormUrlEncoded
    @POST("?ct=user&ac=float")
    Observable<String> ifOpenFab(@Field("game_id") int game_id,
                                      @Field("package_name") String package_name,
                                      @Field("sign") String sign);

}
