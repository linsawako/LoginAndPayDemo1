package com.example.loginandpaytools.Utils;

/**
 * Created by linsawako on 2017/3/16.
 */

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.example.loginandpaytools.ui.LoginDialogActivity;
import com.example.loginandpaytools.ui.floatingbutton.FloatingDraftButton;
import com.example.loginandpaytools.ui.payresult.PayResultContract;

import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.radius;
import static android.content.ContentValues.TAG;


public class AnimationUtil {

    static View view;

    public static void closeButtonList(Context context, final FloatingDraftButton button) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void slideButtons(Context context, final FloatingDraftButton button) {

        Log.d(TAG, "slideButtons: ");
        view = button.getLayoutView();
        if (view == null) {
            return;
        }
        int buttonLeft = button.getLeft();
        int screenWidth = ScreenUtil.getScreenWidth(context);
        int buttonRight = button.getRight();
        int buttonTop = button.getTop();
        int buttonBottom = button.getBottom();
        int buttonWidth = button.getWidth();
        int gap = 5;

        int viewWidth = view.getWidth();
        int viewHight = view.getHeight();


        if (!button.ifLayoutVisible()) {//可拖拽 展开Buttons

            if ((screenWidth - buttonRight) < viewWidth) {

                //view.layout(buttonLeft - gap - viewWidth, buttonTop, buttonLeft - gap, buttonTop + viewHight);
                buttonLeft = screenWidth - buttonWidth - viewWidth - gap;
                buttonRight = screenWidth - viewWidth - gap;
                button.layout(buttonLeft, buttonTop, buttonRight, buttonBottom);
                view.layout(buttonRight + gap, buttonTop, buttonRight + gap + viewWidth, buttonTop + viewHight);
                view.setVisibility(View.VISIBLE);


            } else {
                view.layout(buttonRight + gap, buttonTop, buttonRight + gap + viewWidth, buttonTop + viewHight);
                view.setVisibility(View.VISIBLE);
            }

        } else { //关闭Buttons
            view.setVisibility(View.INVISIBLE);
        }

    }


}