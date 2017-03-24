package com.example.loginandpaytools.ui.floatingbutton;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.loginandpaytools.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String WEBVIEW_URL = "webview_url";
    public static final String TOOLBAR_TITLE = "toolbar_title";
    WebView webView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra(WEBVIEW_URL);
        String toolBarTitle = intent.getStringExtra(TOOLBAR_TITLE);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title.setText(toolBarTitle);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.com_sawako_paylibrary_back);
        }


        webView = (WebView) findViewById(R.id.fab_intent_webview);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setAllowContentAccess(true);

        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
