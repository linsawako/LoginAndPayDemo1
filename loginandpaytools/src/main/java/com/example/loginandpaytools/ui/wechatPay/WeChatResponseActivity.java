package com.example.loginandpaytools.ui.wechatPay;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.loginandpaytools.R;
import com.example.loginandpaytools.common.ApiConfig;
import com.example.loginandpaytools.common.Config;
import com.example.loginandpaytools.ui.floatingbutton.WebViewActivity;
import com.example.loginandpaytools.ui.postorder.PostOrderActivity;

import java.util.ArrayList;
import java.util.logging.Logger;

import static android.R.attr.description;
import static android.view.View.GONE;

public class WeChatResponseActivity extends AppCompatActivity {

    WebView mWebView;
    private static final String TAG = "WeChatResponseActivity";
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_we_chat_response);

        Log.d(TAG, "onCreate: ");
        flag = 0;

        Intent intent = getIntent();
        String response = intent.getStringExtra(WechatPayActivity.WECHATRESPONSE);

        mWebView = (WebView) findViewById(R.id.wechat_webview);
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.loadDataWithBaseURL("about:blank", response, "text/html", "utf-8", null);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                   // finish();
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 如下方案可在非微信内部WebView的H5页面中调出微信支付
                if(url.startsWith("weixin://wap/pay?")) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);

                    return true;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }



            @Override
            public void onReceivedError(WebView view, int errorCode,
                    String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
       flag = 1;
        Log.d(TAG, "onPause: " + flag);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + flag);
        if (flag == 1) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }
}



