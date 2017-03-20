package com.example.loginandpaytools.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandpaytools.Adapter.UserAdapter;
import com.example.loginandpaytools.R;
import com.example.loginandpaytools.Support.LoginEvent;
import com.example.loginandpaytools.Support.RxBus;
import com.example.loginandpaytools.Utils.DataBaseHelper;
import com.example.loginandpaytools.Utils.HttpCallbackListener;
import com.example.loginandpaytools.Utils.HttpUtil;
import com.example.loginandpaytools.Utils.NetworkUtil;
import com.example.loginandpaytools.bean.User;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LoginDialogActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mAccountLogin_Textview;
    private TextView mRegister_Textview;
    private EditText mInputAccount_Editview;
    private EditText mInputPassword_Editview;
    private Button mLogin_Button;
    private DataBaseHelper mDataBaseHelper;
    private PopupWindow mPopupWindow;
    private UserAdapter mUserAdapter;
    private ImageView mDropDownView;
    private RecyclerView mUserRecycleView;
    private ArrayList<User> mUsers;
    private boolean mArrowDown = true;



    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginDialogActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);
        mDataBaseHelper = new DataBaseHelper(this);
        initView();
    }

    private void initView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.drop_list_view, null);
        mUserRecycleView = (RecyclerView) view.findViewById(R.id.user_drop_list_recycleview);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this);
        mUserRecycleView.setLayoutManager(linearlayoutmanager);
        mAccountLogin_Textview = (TextView) findViewById(R.id.account_login_textview);
        mAccountLogin_Textview.setTextColor(ContextCompat.getColor(this, R.color.text_color));
        mAccountLogin_Textview.setOnClickListener(this);
        mRegister_Textview = (TextView) findViewById(R.id.register_textview);
        mRegister_Textview.setOnClickListener(this);
        mInputAccount_Editview = (EditText) findViewById(R.id.input_account_edittext);
        mInputPassword_Editview = (EditText) findViewById(R.id.input_password_edittext);
        mLogin_Button = (Button) findViewById(R.id.login_button);
        mLogin_Button.setOnClickListener(this);
        mDropDownView = (ImageView) findViewById(R.id.dropdown_button);
        mDropDownView.setOnClickListener(this);
        mDropDownView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!mArrowDown){
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(mPopupWindow==null){
                        initPopView();
                    }
                    if (mArrowDown&&!mPopupWindow.isShowing()) {

                        /*if (!mPopupWindow.isShowing()) {
                            setDropDownViewUp();
                            View view = findViewById(R.id.input_and_dropdown_linearlayout);
                            mPopupWindow.showAsDropDown(view);
                        } else {

                            mPopupWindow.dismiss();
                        }*/
                        View view = findViewById(R.id.input_and_dropdown_linearlayout);
                        mPopupWindow.showAsDropDown(view);
                        setDropDownViewUp();
                    }

                }
                return true;
            }

        });


        checkLoginBefore();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        /**
         * 点击账号登陆文本
         */
        if (i == R.id.account_login_textview) {
            if (!mArrowDown) {
                return;
            }
            //checkLoginBefore();
            mInputAccount_Editview.requestFocus();
            mInputAccount_Editview.setText(null);
            mInputPassword_Editview.setText(null);
            setDropDownViewDown();
            //mAccountLogin_Textview.setTextColor(ContextCompat.getColor(this, R.color.text_color));
            //mRegister_Textview.setTextColor(ContextCompat.getColor(this, R.color.text_color_original));

            //mLogin_Button.setText(R.string.login_game);

        }
        /**
         * 点击注册账号文本
         */
        else if (i == R.id.register_textview) {
            //setDropDownViewDown();
            Intent intent = RegisterActivity.newIntent(LoginDialogActivity.this);
            startActivity(intent);
        }
        /**
         * 点击登陆游戏按钮
         */
        else if (i == R.id.login_button) {
            if (!NetworkUtil.isNetworkConnected(LoginDialogActivity.this)) {
                Toast.makeText(LoginDialogActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                return;
            }
            if (mInputAccount_Editview.getText() == null || mInputAccount_Editview.getText().toString().equals("")) {
                Toast.makeText(this, R.string.non_user_name, Toast.LENGTH_SHORT).show();
                return;
            } else if (mInputPassword_Editview.getText() == null || mInputPassword_Editview.getText().toString().equals("")) {
                Toast.makeText(this, R.string.non_password, Toast.LENGTH_SHORT).show();
                return;
            }
            String s1 = mInputAccount_Editview.getText().toString();
            String s2 = mInputPassword_Editview.getText().toString();
            HttpUtil.Login(s1, s2, LoginDialogActivity.this, new HttpCallbackListener() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onFinish(String code) {
                    RxBus.getDefault().post(new LoginEvent(code));
                    finish();
                }

                @Override
                public void onFinish(String s1, String s2) {
                    String date = createDate();
                    mDataBaseHelper.insertOrUpdate(s1, s2, date, 1);
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(LoginDialogActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


    public void setNameAndPassword(String userName, String passWord) {
        mInputAccount_Editview.setText(userName);
        mInputPassword_Editview.setText(passWord);
        mInputAccount_Editview.requestFocus();
        mInputAccount_Editview.setSelection(mInputAccount_Editview.getText().length());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void initPopView() {
        View view = findViewById(R.id.input_and_dropdown_linearlayout);
        mPopupWindow = new PopupWindow(mUserRecycleView, view.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.popupwindow_background)));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setDropDownViewDown();
            }
        });
    }

    private void checkLoginBefore() {

        mUsers = (ArrayList<User>) mDataBaseHelper.queryAllUserName();
        if (mUsers.size() == 0) {
            mDropDownView.setVisibility(View.GONE);
            HttpUtil.getRandomNameAndPassword(this, new HttpCallbackListener() {

                @Override
                public void onFinish() {

                }

                @Override
                public void onFinish(String code) {

                }

                @Override
                public void onFinish(String s1, String s2) {
                    setNameAndPassword(s1, s2);
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mDropDownView.setVisibility(View.VISIBLE);
            setDropDownViewDown();
            setNameAndPassword(mUsers.get(mUsers.size()-1).getUserName(),mUsers.get(mUsers.size()-1).getPassword());
            mUserAdapter = new UserAdapter(LoginDialogActivity.this, mUsers);
            mUserAdapter.setOnItemClickListener(new UserAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    setDropDownViewDown();
                    setNameAndPassword(mUsers.get(position).getUserName(), mUsers.get(position).getPassword());
                    mPopupWindow.dismiss();
                }
            });
            mUserRecycleView.setAdapter(mUserAdapter);

            //initPopView();
        }
    }

    private void setDropDownViewDown() {
        mArrowDown = true;
        //mDropDownView.setImageDrawable(ContextCompat.getDrawable(LoginDialogActivity.this, R.drawable.arrow_down));
        mDropDownView.setImageResource(R.drawable.arrow_down);
    }

    private void setDropDownViewUp() {
        mArrowDown = false;
        //mDropDownView.setImageDrawable(ContextCompat.getDrawable(LoginDialogActivity.this, R.drawable.arrow_up));
        mDropDownView.setImageResource(R.drawable.arrow_up);
    }

    private String createDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return month + "." + day;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataBaseHelper.cleanUp();
        OkGo.getInstance().cancelAll();
        this.finish();

    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        checkLoginBefore();
        super.onResume();
    }


}
