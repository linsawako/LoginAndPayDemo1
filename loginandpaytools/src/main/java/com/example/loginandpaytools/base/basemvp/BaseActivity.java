package com.example.loginandpaytools.base.basemvp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.loginandpaytools.R;

import static com.example.loginandpaytools.ui.postorder.PostOrderActivity.TOOLBAR_TITLE;


/**
 * Created by linsawako on 2017/2/13.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;
    public Context mContext;
    public String toolBarTitle;
    protected Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getLayoutId());

        mIntent = getIntent();
        toolBarTitle = mIntent.getStringExtra(TOOLBAR_TITLE);

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

        mPresenter = createPresenter();
        mContext = getBaseContext();

        if (mPresenter != null) {
            mPresenter.mContext = this;
        }

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detechView();
        }
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

  /*  @Override
    protected void onResume() {

        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        super.onResume();
    }*/

    protected abstract T createPresenter();

    protected abstract void initView();

    protected abstract int getLayoutId();

}
