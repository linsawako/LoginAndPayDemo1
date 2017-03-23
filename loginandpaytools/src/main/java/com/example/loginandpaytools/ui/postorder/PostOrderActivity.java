package com.example.loginandpaytools.ui.postorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginandpaytools.Api.Api;
import com.example.loginandpaytools.R;
import com.example.loginandpaytools.base.basemvp.BaseActivity;
import com.example.loginandpaytools.base.util.SignUtil;
import com.example.loginandpaytools.common.Config;
import com.example.loginandpaytools.ui.floatingbutton.WebViewActivity;
import com.example.loginandpaytools.ui.wechatPay.WeChatResponseActivity;
import com.example.loginandpaytools.ui.wechatPay.WechatPayActivity;

import java.util.HashMap;
import java.util.Map;

import static com.example.loginandpaytools.common.ApiConfig.TYPE_ALIPAY;
import static com.example.loginandpaytools.common.ApiConfig.TYPE_WECHAT;
import static com.example.loginandpaytools.ui.wechatPay.WechatPayActivity.WECHATRESPONSE;


public class PostOrderActivity extends BaseActivity<PostOrderPresenter> implements PostOrderContract.View, View.OnClickListener {

    Button alipay_btn;
    Button wechat_btn;
    Intent pay_intent;
    Intent wechat_intent;

    public final static String INTENTRESPNSE = "intent_response";

    public final static String TOOLBAR_TITLE = "toolbar_title";

    private static final String TAG = "PostOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alipay_btn = (Button) findViewById(R.id.pay_alipay);
        wechat_btn = (Button) findViewById(R.id.pay_wechat);

        alipay_btn.setOnClickListener(this);
        wechat_btn.setOnClickListener(this);
    }

    @Override
    protected PostOrderPresenter createPresenter() {
        return new PostOrderPresenter(this);
    }


    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_order;
    }

    @Override
    public void returnPlatform_order_num(String platform_order_num) {
        if (platform_order_num != null) {
            Config.platform_order_num = platform_order_num;
            mPresenter.getPayOrder(platform_order_num);
        }

    }

    @Override
    public void errorReturnPlatForm(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnPayOrder(String response) {

        if (Config.payType == TYPE_WECHAT) {
            pay_intent = new Intent(PostOrderActivity.this, WeChatResponseActivity.class);
            pay_intent.putExtra(WECHATRESPONSE, response);
        } else {
            pay_intent = new Intent(PostOrderActivity.this, PostResultActivity.class);
            pay_intent.putExtra(INTENTRESPNSE, response);
            pay_intent.putExtra(TOOLBAR_TITLE, toolBarTitle);
        }
    }

    @Override
    public void errorReturnPayOrder(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startIntent() {
        if (wechat_intent != null && Config.payType == TYPE_WECHAT) {
            startActivity(wechat_intent);
        }
        if (pay_intent != null) {
            startActivity(pay_intent);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.pay_alipay) {
            Config.payType = TYPE_ALIPAY;
            Config.order.setPay_type(Config.payType);

            mPresenter.postOrder(Config.order);
        } else if (i == R.id.pay_wechat) {
            Config.payType = TYPE_WECHAT;
            Config.order.setPay_type(Config.payType);

            wechat_intent = new Intent(PostOrderActivity.this, WechatPayActivity.class);
            wechat_intent.putExtra(TOOLBAR_TITLE, toolBarTitle);

            mPresenter.postOrder(Config.order);
        }

    }

}
