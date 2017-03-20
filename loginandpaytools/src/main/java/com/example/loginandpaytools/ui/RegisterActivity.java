package com.example.loginandpaytools.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginandpaytools.R;
import com.example.loginandpaytools.Utils.HttpCallbackListener;
import com.example.loginandpaytools.Utils.HttpUtil;
import com.example.loginandpaytools.Utils.NetworkUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mRegister_button;
    private EditText mInputAccount_Editview;
    private EditText mInputPassword_Editview;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.com_sawako_paylibrary_back);
        }
        initView();
    }

    private void initView() {
        mRegister_button = (Button) findViewById(R.id.register_button);
        mInputAccount_Editview = (EditText) findViewById(R.id.input_account_edittext);
        mInputPassword_Editview = (EditText) findViewById(R.id.input_password_edittext);
        mRegister_button.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.register_button){
            if (!NetworkUtil.isNetworkConnected(RegisterActivity.this)) {
                Toast.makeText(RegisterActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                return;
            }
            if (mInputAccount_Editview.getText()==null||mInputAccount_Editview.getText().toString().equals("")) {
                Toast.makeText(this, R.string.non_user_name, Toast.LENGTH_SHORT).show();
                return;
            } else if (mInputPassword_Editview.getText()==null||mInputPassword_Editview.getText().toString().equals("")) {
                Toast.makeText(this, R.string.non_password, Toast.LENGTH_SHORT).show();
                return;
            }
            String s1 = mInputAccount_Editview.getText().toString();
            String s2 = mInputPassword_Editview.getText().toString();
            HttpUtil.Register(s1, s2, getApplicationContext(), new HttpCallbackListener() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onFinish(String code) {

                }

                @Override
                public void onFinish(String s1, String s2) {
                    Toast.makeText(getApplicationContext(), R.string.register_successfully, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
