package com.example.loginandpaytools.ui.wechatPay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginandpaytools.R;
import com.example.loginandpaytools.base.basemvp.BaseActivity;
import com.example.loginandpaytools.common.Config;
import com.example.loginandpaytools.ui.LoginDialogActivity;
import com.example.loginandpaytools.ui.payresult.PayResultContract;
import com.example.loginandpaytools.ui.payresult.PayResultPresenter;
import com.example.loginandpaytools.ui.postorder.PostOrderActivity;
import com.example.loginandpaytools.ui.postorder.PostOrderContract;
import com.example.loginandpaytools.ui.postorder.PostOrderPresenter;
import com.example.loginandpaytools.ui.postorder.PostResultActivity;



public class WechatPayActivity extends BaseActivity<PostOrderPresenter>
        implements PostOrderContract.View, PayResultContract.View, View.OnClickListener {

    Button paid_btn;
    Button toPay_btn;
    PayResultContract.Presenter getResultPresenter;
    public final static String WECHATRESPONSE = "wechat_response";
    public String platform_order_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        paid_btn = (Button) findViewById(R.id.com_sawako_paylibrary_paid);
        toPay_btn = (Button) findViewById(R.id.com_sawako_paylibrary_topay);

        paid_btn.setOnClickListener(this);
        toPay_btn.setOnClickListener(this);

        platform_order_num = Config.platform_order_num;

        getResultPresenter = new PayResultPresenter(this);

        if (getResultPresenter != null) {
            getResultPresenter.setmContext(this);
        }

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
        return R.layout.activity_wechat_pay;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.com_sawako_paylibrary_paid) {
            if (platform_order_num == null) {
                Toast.makeText(mContext, getApplicationContext().getString(R.string.paylibrary_no_platform_order_num), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(WechatPayActivity.this, PostOrderActivity.class);
                startActivity(intent);
            } else {
                getResultPresenter.checkPayResult(platform_order_num);
            }

        } else if (view.getId() == R.id.com_sawako_paylibrary_topay) {
            if (platform_order_num != null) {
                mPresenter.getPayOrder(platform_order_num);
            }
        }
    }

    @Override
    public void returnPlatform_order_num(String platform_order_num) {

    }

    @Override
    public void errorReturnPlatForm(String errMsg) {

    }

    @Override
    public void returnPayOrder(String response) {
        Intent intent = new Intent(WechatPayActivity.this, WeChatResponseActivity.class);
        intent.putExtra(WECHATRESPONSE, response);
        startActivity(intent);
    }

    @Override
    public void errorReturnPayOrder(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startIntent() {

    }

    @Override
    public void returnPayResult(boolean ifPay) {
        if (ifPay) {
            Toast.makeText(mContext, "已支付", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "未支付", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(WechatPayActivity.this, PostOrderActivity.class);
            startActivity(intent);
        } 
    }

    @Override
    public void errorGetResult(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
