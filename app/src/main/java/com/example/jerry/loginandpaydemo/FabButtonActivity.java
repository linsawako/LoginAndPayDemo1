package com.example.jerry.loginandpaydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.loginandpaytools.Utils.AnimationUtil;
import com.example.loginandpaytools.Utils.ScreenUtil;
import com.example.loginandpaytools.ui.floatingbutton.FabButtonInterface;
import com.example.loginandpaytools.ui.floatingbutton.FabPresenter;
import com.example.loginandpaytools.ui.floatingbutton.FloatingButtonContract;
import com.example.loginandpaytools.ui.floatingbutton.FloatingDraftButton;
import com.example.loginandpaytools.ui.floatingbutton.MyFabMenu;
import com.example.loginandpaytools.ui.floatingbutton.WebViewActivity;

import static android.R.attr.x;

public class FabButtonActivity extends AppCompatActivity
        implements FloatingButtonContract.View {

    FloatingButtonContract.Presenter presenter;
    MyFabMenu mFabMenu;
    Button ifOpenFab_btn;

    String account_url = "http://www.baidu.com";
    String info_url = "http://www.csdn.net";
    String forum_url = "http://www.zhihu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fab_button);

        setFabMeun();

        presenter = new FabPresenter(this);

        if (presenter != null) {
            presenter.setmContext(getBaseContext());
        }

        ifOpenFab_btn = (Button) findViewById(R.id.ifopenfab_btn);

        ifOpenFab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getFabResponse();
            }
        });

    }

    public void setFabMeun() {
        mFabMenu = (MyFabMenu) findViewById(R.id.my_fab_menu);

        mFabMenu.initViewsVisible(false, false);//设置是否显示悬浮按钮和菜单

        mFabMenu.setFabButtonInterface(new FabButtonInterface() {

            Intent intent = new Intent(FabButtonActivity.this, WebViewActivity.class);

            @Override
            public void accountEvent() {
                intent.putExtra(WebViewActivity.WEBVIEW_URL, account_url);
                intent.putExtra(WebViewActivity.TOOLBAR_TITLE, "账户");
                startActivity(intent);
            }

            @Override
            public void infoEvent() {
                intent.putExtra(WebViewActivity.WEBVIEW_URL, info_url);
                intent.putExtra(WebViewActivity.TOOLBAR_TITLE, "消息");
                startActivity(intent);
            }

            @Override
            public void forumEvent() {
                intent.putExtra(WebViewActivity.WEBVIEW_URL, forum_url);
                intent.putExtra(WebViewActivity.TOOLBAR_TITLE, "论坛");
                startActivity(intent);
            }
        });
    }

    @Override
    public void ifOpenFab(boolean ifOpen) {
        Toast.makeText(this, "" + ifOpen, Toast.LENGTH_SHORT).show();
        if (ifOpen) {
            mFabMenu.initViewsVisible(true, false);
        }
    }

    @Override
    public void errorReturn(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }
}
