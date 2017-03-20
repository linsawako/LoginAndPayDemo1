package com.example.jerry.loginandpaydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.loginandpaytools.Utils.AnimationUtil;
import com.example.loginandpaytools.ui.floatingbutton.FabPresenter;
import com.example.loginandpaytools.ui.floatingbutton.FloatingButtonContract;
import com.example.loginandpaytools.ui.floatingbutton.FloatingDraftButton;

public class FabButtonActivity extends AppCompatActivity
        implements View.OnClickListener, FloatingButtonContract.View {

    FloatingDraftButton floatingDraftButton;
    FloatingButtonContract.Presenter presenter;
    Button ifOpenFab_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fab_button);

        presenter = new FabPresenter(this);

        if (presenter != null) {
            presenter.setmContext(getBaseContext());
        }

        ifOpenFab_btn = (Button) findViewById(R.id.ifopenfab_btn);
        ifOpenFab_btn.setOnClickListener(this);


        floatingDraftButton = (FloatingDraftButton) findViewById(R.id.library_floatingActionButton);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.button_list_layout);
        floatingDraftButton.setLayoutView(relativeLayout);
        floatingDraftButton.setOnClickListener(this);

        Button info_btn = (Button) findViewById(R.id.fab_info_btn);
        info_btn.setOnClickListener(this);

        Button account_btn = (Button) findViewById(R.id.fab_account_btn);
        account_btn.setOnClickListener(this);

        Button forum_btn = (Button) findViewById(R.id.fab_forum_btn);
        forum_btn.setOnClickListener(this);

        Button back_btn = (Button) findViewById(R.id.fab_back_btn);
        back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.library_floatingActionButton:
                AnimationUtil.slideButtons(getBaseContext(), floatingDraftButton);
                break;
            case R.id.fab_info_btn:
                Toast.makeText(this, "info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_account_btn:
                Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_forum_btn:
                Toast.makeText(this, "forum", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_back_btn:
                AnimationUtil.closeButtonList(getBaseContext(), floatingDraftButton);
                break;
            case R.id.ifopenfab_btn:
                presenter.getFabResponse();
                break;
            default:
                break;
        }
    }

    @Override
    public void ifOpenFab(boolean ifOpen) {
        Toast.makeText(this, "" + ifOpen, Toast.LENGTH_SHORT).show();
        floatingDraftButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorReturn(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }
}
