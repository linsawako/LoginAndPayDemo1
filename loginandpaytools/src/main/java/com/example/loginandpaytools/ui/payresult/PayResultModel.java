package com.example.loginandpaytools.ui.payresult;

import com.example.loginandpaytools.Api.Api;
import com.example.loginandpaytools.base.baserx.RxTransformer;
import com.example.loginandpaytools.base.util.SignUtil;
import com.example.loginandpaytools.bean.PayResultBean;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


/**
 * Created by linsawako on 2017/2/24.
 */

public class PayResultModel implements PayResultContract.Model {
    @Override
    public Observable<PayResultBean> checkPayResult(String platform_order_num) {
        Map<String, String> map = new HashMap<>();
        map.put("platform_order_num", platform_order_num);

        return Api.getDefault()
                .getPayResult(platform_order_num, SignUtil.sign(map))
                .compose(RxTransformer.handleResultFromString(PayResultBean.class))
                .compose(RxTransformer.<PayResultBean>schedules_io_main());
    }
}
