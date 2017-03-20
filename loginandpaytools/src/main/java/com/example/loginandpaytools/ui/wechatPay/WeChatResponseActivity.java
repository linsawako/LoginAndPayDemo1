package com.example.loginandpaytools.ui.wechatPay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.loginandpaytools.R;
import com.example.loginandpaytools.common.ApiConfig;
import com.example.loginandpaytools.common.Config;
import com.example.loginandpaytools.ui.postorder.PostOrderActivity;

public class WeChatResponseActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_we_chat_response);

        Intent intent = getIntent();
        String response = intent.getStringExtra(WechatPayActivity.WECHATRESPONSE);

        webView = (WebView) findViewById(R.id.wechat_webview);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setAllowContentAccess(true);

        class JsObject {
            @JavascriptInterface
            public String toString() { return "injectedObject"; }
        }
        webView.addJavascriptInterface(new JsObject(), "injectedObject");

        webView.loadDataWithBaseURL("about:blank", response, "text/html", "utf-8", null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
