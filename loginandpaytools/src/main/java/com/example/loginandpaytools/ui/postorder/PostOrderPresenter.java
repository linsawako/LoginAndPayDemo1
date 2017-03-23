package com.example.loginandpaytools.ui.postorder;

import android.util.Log;

import com.example.loginandpaytools.base.baserx.RxSubscriber;
import com.example.loginandpaytools.bean.Order;
import com.example.loginandpaytools.bean.PayOrderResult;

import static android.content.ContentValues.TAG;



/**
 * Created by linsawako on 2017/2/23.
 */

public class PostOrderPresenter extends PostOrderContract.Presenter {

    PostOrderContract.Model model;

    public PostOrderPresenter(PostOrderContract.View view) {
        super(view);
        model = new PostOrderModel();
    }

    @Override
    public void postOrder(Order order) {
        model.postOrder(order)
                .subscribe(new RxSubscriber<PayOrderResult>(mContext, false) {
                    @Override
                    public void _onNext(PayOrderResult dataBean) {
                        getView().returnPlatform_order_num(dataBean.getPlatform_order_num());
                    }

                    @Override
                    public void _onError(String errMsg) {
                        Log.d(TAG, "_onError: " + errMsg);
                        getView().errorReturnPlatForm(errMsg);
                    }

                    @Override
                    public void _onCompleted() {

                    }
                });
    }

    @Override
    public void getPayOrder(String platform_order_num) {
        model.getPayOrder(platform_order_num)
                .subscribe(new RxSubscriber<String>(mContext) {
                    @Override
                    public void _onNext(String s) {
                        getView().returnPayOrder(s);
                    }

                    @Override
                    public void _onError(String errMsg) {
                        getView().errorReturnPayOrder(errMsg);
                    }

                    @Override
                    public void _onCompleted() {
                        getView().startIntent();
                    }
                });
    }
}
