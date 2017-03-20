package com.example.loginandpaytools.ui.postorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.loginandpaytools.R;
import com.example.loginandpaytools.common.ApiConfig;
import com.example.loginandpaytools.common.Config;

import static com.example.loginandpaytools.ui.postorder.PostOrderActivity.TOOLBAR_TITLE;


public class PostResultActivity extends AppCompatActivity {

    WebView webView;
    public String toolBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post_result);

        Intent intent = getIntent();
        String response = intent.getStringExtra(PostOrderActivity.INTENTRESPNSE);
        toolBarTitle = intent.getStringExtra(PostOrderActivity.TOOLBAR_TITLE);

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

        webView = (WebView) findViewById(R.id.alipay_webview);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setAllowContentAccess(true);

            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if( url.startsWith("http:") || url.startsWith("https:") ) {
                        return false;
                    }
                    try{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity( intent );
                    }catch(Exception e){}
                    return true;
                }
            });

        webView.loadDataWithBaseURL("about:blank", response, "text/html", "utf-8", null);

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
