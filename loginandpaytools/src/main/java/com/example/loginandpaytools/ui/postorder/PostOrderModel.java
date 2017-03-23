package com.example.loginandpaytools.ui.postorder;

import com.example.loginandpaytools.Api.Api;
import com.example.loginandpaytools.base.baserx.RxTransformer;
import com.example.loginandpaytools.base.util.SignUtil;
import com.example.loginandpaytools.bean.Order;
import com.example.loginandpaytools.bean.PayOrderResult;
import com.example.loginandpaytools.common.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by linsawako on 2017/2/23.
 */

public class PostOrderModel implements PostOrderContract.Model {


    @Override
    public Observable<PayOrderResult> postOrder(Order order) {
        Map<String, String> map = new HashMap<>();

        map.put("uid", String.valueOf(order.getUid()));
        map.put("token", order.getToken());
        map.put("game_id", String.valueOf(order.getGame_id()));
        map.put("package_name", order.getPackage_name());
        map.put("order_num", order.getOrder_num());
        map.put("total_fee", String.valueOf(order.getTotal_fee()));
        map.put("order_name", order.getOrder_name());
        map.put("pay_type", String.valueOf(order.getPay_type()));
        map.put("server_id", String.valueOf(order.getServer_id()));
        map.put("role_id", String.valueOf(order.getRole_id()));
        map.put("role_name", order.getRole_name());
        map.put("role_level", String.valueOf(order.getRole_level()));
        map.put("ext", Config.ext);

        String sign = SignUtil.sign(map);

        return Api.getDefault()
                .postOrder(order.getUid(),
                        order.getToken(),
                        order.getGame_id(),
                        order.getPackage_name(),
                        order.getOrder_num(),
                        order.getTotal_fee(),
                        order.getOrder_name(),
                        order.getPay_type(),
                        order.getServer_id(),
                        order.getRole_id(),
                        order.getRole_name(),
                        order.getRole_level(),
                        Config.ext,
                        sign)
                .compose(RxTransformer.<String>schedules_io_main())
                .compose(RxTransformer.handleResultFromString(PayOrderResult.class));
    }

    @Override
    public Observable<String> getPayOrder(String platform_order_num) {
        Map<String, String> map = new HashMap<>();
        map.put("platform_order_num", platform_order_num);

        return Api.getDefault()
                .getAliPayOrder(platform_order_num, SignUtil.sign(map))
                .compose(RxTransformer.<Response<String>>schedules_io_main())
                .compose(RxTransformer.handlerPayFromString());
    }

}
