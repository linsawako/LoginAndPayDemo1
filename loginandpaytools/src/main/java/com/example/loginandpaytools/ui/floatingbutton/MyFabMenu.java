package com.example.loginandpaytools.ui.floatingbutton;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.loginandpaytools.R;
import com.example.loginandpaytools.Utils.AnimationUtil;
import com.example.loginandpaytools.Utils.ScreenUtil;
import com.example.loginandpaytools.common.Config;
import com.example.loginandpaytools.ui.LoginDialogActivity;

import static android.content.ContentValues.TAG;
import static com.example.loginandpaytools.common.Config.account_url;

/**
 * Created by linsawako on 2017/3/20.
 */

public class MyFabMenu extends RelativeLayout {

    private FabButtonInterface fabButtonInterface;

    private View viewFabMenu;
    private MyViewHolder viewHolder;
    private Context mContext;


    public MyFabMenu(Context context) {
        super(context);
        mContext = context;
        setFabButtonInterface(new mFabButtonInterface());
        init();
    }

    public MyFabMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setFabButtonInterface(new mFabButtonInterface());
        init();
    }

    public MyFabMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setFabButtonInterface(new mFabButtonInterface());
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        viewFabMenu = inflater.inflate(R.layout.layout_floatingmenu, null);

        this.addView(viewFabMenu, layoutParams);

        viewHolder = new MyViewHolder(this);
        viewHolder.floatingDraftButton.setLayoutView(viewHolder.btnLayout);
        viewHolder.account_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabButtonInterface != null) {
                    fabButtonInterface.accountEvent();
                }
            }
        });
        viewHolder.info_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabButtonInterface != null) {
                    fabButtonInterface.infoEvent();
                }
            }
        });
        viewHolder.forum_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabButtonInterface != null) {
                    fabButtonInterface.forumEvent();
                }
            }
        });
        viewHolder.floatingDraftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.slideButtons(v.getContext(), viewHolder.floatingDraftButton);
            }
        });
        viewHolder.back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.closeButtonList(v.getContext(), viewHolder.floatingDraftButton);
            }
        });
    }

    public void initViewsVisible(boolean isFabVisile, boolean isBtnListVisile)
    {

        viewHolder.floatingDraftButton.setVisibility(isFabVisile ? View.VISIBLE : View.INVISIBLE);

        viewHolder.btnLayout.setVisibility(isBtnListVisile ? View.VISIBLE : View.INVISIBLE);

    }

    public void setFabButtonInterface(FabButtonInterface fabButtonInterface) {
        this.fabButtonInterface = fabButtonInterface;
    }


    class mFabButtonInterface implements FabButtonInterface {

        Intent intent = new Intent(mContext, WebViewActivity.class);

        @Override
        public void accountEvent() {
            Log.d(TAG, "accountEvent: " + Config.account_url);
            intent.putExtra(WebViewActivity.WEBVIEW_URL, Config.account_url);
            intent.putExtra(WebViewActivity.TOOLBAR_TITLE, "账户");
            mContext.startActivity(intent);
        }

        @Override
        public void infoEvent() {
            Log.d(TAG, "accountEvent: " + Config.chat_url);
            intent.putExtra(WebViewActivity.WEBVIEW_URL, Config.chat_url);
            intent.putExtra(WebViewActivity.TOOLBAR_TITLE, "消息");
            mContext.startActivity(intent);
        }

        @Override
        public void forumEvent() {
            Log.d(TAG, "accountEvent: " + Config.bbs_url);
            intent.putExtra(WebViewActivity.WEBVIEW_URL, Config.bbs_url);
            intent.putExtra(WebViewActivity.TOOLBAR_TITLE, "论坛");
            mContext.startActivity(intent);
        }
    }

    static class MyViewHolder {

        FloatingDraftButton floatingDraftButton;
        RelativeLayout fab_layout;
        RelativeLayout btnLayout;
        Button info_btn;
        Button account_btn;
        Button forum_btn;
        Button back_btn;
        private MyViewHolder(View v) {
            fab_layout = (RelativeLayout) v.findViewById(R.id.fab_layout);
            floatingDraftButton = (FloatingDraftButton) v.findViewById(R.id.library_floatingActionButton);
            btnLayout = (RelativeLayout) v.findViewById(R.id.button_list_layout);
            info_btn = (Button) v.findViewById(R.id.fab_info_btn);
            account_btn = (Button) v.findViewById(R.id.fab_account_btn);
            forum_btn = (Button) v.findViewById(R.id.fab_forum_btn);
            back_btn = (Button) v.findViewById(R.id.fab_back_btn);

        }
    }

}
