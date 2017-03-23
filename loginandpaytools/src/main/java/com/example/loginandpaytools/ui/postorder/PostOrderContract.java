package com.example.loginandpaytools.ui.postorder;

import com.example.loginandpaytools.base.basemvp.BasePresenter;
import com.example.loginandpaytools.bean.Order;
import com.example.loginandpaytools.bean.PayOrderResult;

import rx.Observable;


/**
 * Created by linsawako on 2017/2/23.
 */

public interface PostOrderContract {

    interface View{
        void returnPlatform_order_num(String platform_order_num);

        void errorReturnPlatForm(String errMsg);

        void returnPayOrder(String response);

        void errorReturnPayOrder(String errMsg);

        void startIntent();
    }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        abstract void postOrder(Order order);

        abstract void getPayOrder(String platform_order_num);
    }

    interface Model{
        Observable<PayOrderResult> postOrder(Order order);

        Observable<String> getPayOrder(String platform_order_num);
    }

}
