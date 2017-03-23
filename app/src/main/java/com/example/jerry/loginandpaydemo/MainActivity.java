package com.example.jerry.loginandpaydemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginandpaytools.Support.LoginEvent;
import com.example.loginandpaytools.Support.RxBus;
import com.example.loginandpaytools.Utils.HttpCallbackListener;
import com.example.loginandpaytools.Utils.HttpUtil;
import com.example.loginandpaytools.bean.Order;
import com.example.loginandpaytools.common.Config;
import com.example.loginandpaytools.ui.LoginDialogActivity;
import com.example.loginandpaytools.ui.postorder.PostOrderActivity;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    Subscription rxSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 打开登陆界面
                 */
                Intent intent = LoginDialogActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 调用角色登陆接口,只需实现onFinish()以及onError()方法
                 */
                HttpUtil.roleLogin(MainActivity.this, new HttpCallbackListener() {
                    @Override
                    public void onFinish() {
                        /**
                         * 请自主改写以下逻辑
                         */
                        Toast.makeText(MainActivity.this, R.string.login_successfully, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish(String code) {

                    }

                    @Override
                    public void onFinish(String username, String password) {

                    }

                    @Override
                    public void onError(Exception e) {
                        /**
                         * 请自主改写以下逻辑
                         */
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /**
         *设置公共参数
         */
        setConfig();

        /**
         * 激活接口
         */
        HttpUtil.activeInterface(getApplicationContext(), new HttpCallbackListener() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onFinish(String code) {

            }

            @Override
            public void onFinish(String username, String password) {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        /**
         * 处理登陆成功之后的事件
         */
        rxSubscription = RxBus.getDefault().toObservable(LoginEvent.class)
                .subscribe(new Action1<LoginEvent>() {
                    @Override
                    public void call(LoginEvent loginEvent) {
                        String code = loginEvent.getLoginCode();
                        Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT).show();
                    }
                });


        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 打开支付界面
                 */
                Intent intent = new Intent(MainActivity.this, PostOrderActivity.class);
                intent.putExtra(PostOrderActivity.TOOLBAR_TITLE, "支付0.01");//传入显示的标题
                startActivity(intent);
            }
        });

        mButton4 = (Button) findViewById(R.id.button4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FabButtonActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置支付所需参数
     */
    private void setConfig() {
        //相关的值需要替换
        Config.channel_id = 1;
        Config.uid = 1;
        Config.token = "aaa";
        Config.game_id = 1;
        Config.package_name = "com.ios.xxqxz";
        Config.server_id = 1;
        Config.role_id = 1;
        Config.role_name = "test5";
        Config.role_level = 1;
        Config.ext = "2";

        Order.Builder orderBuilder = Config.getOrderBuilder();

        orderBuilder.setOrder_name("order_name")
                .setOrder_num("order_num")
                .setTotal_fee(1);

        Config.order = orderBuilder.create();
    }


    @Override
    protected void onDestroy() {
        /**
         * 必须添加以下代码
         */
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
