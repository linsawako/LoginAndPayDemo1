package com.example.loginandpaytools.ui.floatingbutton;

import com.example.loginandpaytools.Api.Api;
import com.example.loginandpaytools.base.baserx.RxTransformer;
import com.example.loginandpaytools.base.util.SignUtil;
import com.example.loginandpaytools.bean.FabResponse;
import com.example.loginandpaytools.bean.PayResultBean;
import com.example.loginandpaytools.common.Config;
import com.example.loginandpaytools.ui.payresult.PayResultContract;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by linsawako on 2017/3/17.
 */

public class FabModel implements FloatingButtonContract.Model {
    @Override
    public Observable<FabResponse> getFabResponse() {
        Map<String, String> map = new HashMap<>();
        map.put("game_id", String.valueOf(Config.game_id));
        map.put("package_name", Config.package_name);

        return Api.getDefault()
                .ifOpenFab(Config.game_id,
                        Config.package_name,
                        SignUtil.sign(map))
                .compose(RxTransformer.handleResultFromString(FabResponse.class))
                .compose(RxTransformer.<FabResponse>schedules_io_main());
    }
}
