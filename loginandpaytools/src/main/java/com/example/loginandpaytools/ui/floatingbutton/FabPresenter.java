package com.example.loginandpaytools.ui.floatingbutton;

import com.example.loginandpaytools.base.baserx.RxSubscriber;
import com.example.loginandpaytools.bean.FabResponse;
import com.example.loginandpaytools.common.Config;

/**
 * Created by linsawako on 2017/3/17.
 */

public class FabPresenter extends FloatingButtonContract.Presenter {

    FloatingButtonContract.Model model;

    public FabPresenter(FloatingButtonContract.View view) {
        super(view);
        model = new FabModel();
    }

    @Override
    public void getFabResponse() {
        model.getFabResponse()
                .subscribe(new RxSubscriber<FabResponse>(mContext, false) {
                    @Override
                    public void _onNext(FabResponse fabResponse) {
                        if (fabResponse.getData().getOpen().equals("0")) {
                            getView().ifOpenFab(false);
                        } else {
                            getView().ifOpenFab(true);
                        }

                        Config.account_url = fabResponse.getData().getAccount_url() + Config.fab_token;

                        Config.bbs_url = fabResponse.getData().getBbs_url() + Config.fab_token;

                        Config.chat_url = fabResponse.getData().getChat_url() + Config.fab_token;
                    }

                    @Override
                    public void _onError(String errMsg) {
                        getView().errorReturn(errMsg);
                    }

                    @Override
                    public void _onCompleted() {

                    }
                });
    }
}
