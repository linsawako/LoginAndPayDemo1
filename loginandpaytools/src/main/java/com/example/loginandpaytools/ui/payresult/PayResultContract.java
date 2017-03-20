package com.example.loginandpaytools.ui.payresult;

import com.example.loginandpaytools.base.basemvp.BasePresenter;
import com.example.loginandpaytools.bean.PayResultBean;

import rx.Observable;


/**
 * Created by linsawako on 2017/2/24.
 */

public interface PayResultContract {

    interface View{
        void returnPayResult(boolean ifPay);

        void errorGetResult(String errMsg);
    }

    interface Model{
        Observable<PayResultBean> checkPayResult(String platform_order_num);
    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void checkPayResult(String platform_order_num);
    }

}
