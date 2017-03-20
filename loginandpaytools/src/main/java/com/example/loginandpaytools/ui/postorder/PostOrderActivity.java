package com.example.loginandpaytools.ui.postorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginandpaytools.R;
import com.example.loginandpaytools.base.basemvp.BaseActivity;
import com.example.loginandpaytools.common.Config;
import com.example.loginandpaytools.ui.wechatPay.WechatPayActivity;

import static com.example.loginandpaytools.common.ApiConfig.TYPE_ALIPAY;
import static com.example.loginandpaytools.common.ApiConfig.TYPE_WECHAT;



public class PostOrderActivity extends BaseActivity<PostOrderPresenter> implements PostOrderContract.View, View.OnClickListener {

    Button alipay_btn;
    Button wechat_btn;

    public final static String INTENTRESPNSE = "intent_response";

    public final static String TOOLBAR_TITLE = "toolbar_title";

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
        Intent intent = new Intent(PostOrderActivity.this, PostResultActivity.class);
        intent.putExtra(INTENTRESPNSE, response);
        intent.putExtra(TOOLBAR_TITLE, toolBarTitle);
        startActivity(intent);
    }

    @Override
    public void errorReturnPayOrder(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
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

            Intent intent = new Intent(PostOrderActivity.this, WechatPayActivity.class);
            intent.putExtra(TOOLBAR_TITLE, toolBarTitle);
            startActivity(intent);
        }

    }

}
