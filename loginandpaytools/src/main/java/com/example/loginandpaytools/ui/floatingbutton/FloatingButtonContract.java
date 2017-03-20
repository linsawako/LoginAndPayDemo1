package com.example.loginandpaytools.ui.floatingbutton;

import com.example.loginandpaytools.base.basemvp.BasePresenter;
import com.example.loginandpaytools.bean.FabResponse;

import rx.Observable;

/**
 * Created by linsawako on 2017/3/17.
 */

public interface FloatingButtonContract {

    interface View{
        void ifOpenFab(boolean ifOpen);

        void errorReturn(String errMsg);
    }

    interface Model{
        Observable<FabResponse> getFabResponse();
    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void getFabResponse();
    }


}
