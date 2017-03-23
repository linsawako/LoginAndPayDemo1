package com.example.loginandpaytools.ui.payresult;

import com.example.loginandpaytools.base.baserx.RxSubscriber;
import com.example.loginandpaytools.bean.PayResultBean;


/**
 * Created by linsawako on 2017/2/24.
 */

public class PayResultPresenter extends PayResultContract.Presenter {

    PayResultContract.Model model;

    public PayResultPresenter(PayResultContract.View view) {
        super(view);
        model = new PayResultModel();
    }

    @Override
    public void checkPayResult(String platform_order_num) {
        model.checkPayResult(platform_order_num)
                .subscribe(new RxSubscriber<PayResultBean>(mContext) {
                    @Override
                    public void _onNext(PayResultBean payResultBean) {
                        getView().returnPayResult(payResultBean.getIs_pay().equals("1"));
                    }

                    @Override
                    public void _onError(String errMsg) {
                        getView().errorGetResult(errMsg);
                    }

                    @Override
                    public void _onCompleted() {

                    }
                });
    }
}
