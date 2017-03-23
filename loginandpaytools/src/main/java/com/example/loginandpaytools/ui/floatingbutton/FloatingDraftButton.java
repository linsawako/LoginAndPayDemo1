package com.example.loginandpaytools.ui.floatingbutton;

/**
 * Created by linsawako on 2017/3/16.
 */

import android.content.Context;

import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


import com.example.loginandpaytools.R;
import com.example.loginandpaytools.Utils.ScreenUtil;


public class FloatingDraftButton extends Button
        implements View.OnTouchListener {

    int lastX, lastY;
    int originX, originY;
    final int screenWidth ;
    final int screenHeight ;
    View layoutView;
    int gap = 5;

    public FloatingDraftButton(Context context) {
        this(context, null);
    }

    public FloatingDraftButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingDraftButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = ScreenUtil.getScreenWidth(context);
        screenHeight = ScreenUtil.getContentHeight(context);
        setOnTouchListener(this);
    }

    public void setLayoutView(View view) {
        this.layoutView= view;
        if (layoutView != null) {
            layoutView.setOnTouchListener(this);
        }
    }

    public View getLayoutView() {
        return layoutView;
    }

    public boolean ifLayoutVisible() {
        if (layoutView.getVisibility() == VISIBLE) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int fabWidth = getWidth();
        int fabHeight = getHeight();
        int layoutWidth = layoutView.getWidth();
        int layoutHeight = layoutView.getHeight();

        if (v.getId() == R.id.library_floatingActionButton) {
            int ea = event.getAction();
            switch (ea) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                    lastY = (int) event.getRawY();
                    originX = lastX;
                    originY = lastY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    int l = v.getLeft() + dx;
                    int b = v.getBottom() + dy;
                    int r = v.getRight() + dx;
                    int t = v.getTop() + dy;
                    // 下面判断移动是否超出屏幕
                    if (l < 0) {
                        l = 0;
                        r = l + v.getWidth();
                    }
                    if (t < 0) {
                        t = 0;
                        b = t + v.getHeight();
                    }
                    if (r > screenWidth || (ifLayoutVisible() && layoutView.getRight() > screenWidth)) {
                        if (ifLayoutVisible()) {
                            r = screenWidth - layoutWidth - gap;
                        } else {
                            r = screenWidth;
                        }
                        l = r - v.getWidth();
                    }
                    if (b > screenHeight) {
                        b = screenHeight;
                        t = b - fabWidth;
                    }
                    v.layout(l, t, r, b);

                    int buttonRight = v.getRight();
                    int gap = 5;
                    int buttonTop = v.getTop();

                    layoutView.layout(buttonRight + gap, buttonTop, buttonRight + gap + layoutWidth, buttonTop + layoutHeight);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    v.postInvalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    int distance = (int) event.getRawX() - originX + (int)event.getRawY() - originY;
                    if (Math.abs(distance)<20) {
                        //当变化太小的时候什么都不做 OnClick执行
                    }else {
                        return true;
                    }
                    break;
            }
            return false;

        } else if (v.getId() == R.id.button_list_layout){

            if (!ifLayoutVisible()) {
                return false;
            }
            int ea = event.getAction();
            switch (ea) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                    lastY = (int) event.getRawY();
                    originX = lastX;
                    originY = lastY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    int l = v.getLeft() + dx;
                    int b = v.getBottom() + dy;
                    int r = v.getRight() + dx;
                    int t = v.getTop() + dy;
                    // 下面判断移动是否超出屏幕
                    if (getLeft() < 0) {
                        l = 0 + fabWidth + gap;
                        r = l + v.getWidth();
                    }
                    if (t < 0) {
                        t = 0;
                        b = t + v.getHeight();
                    }
                    if (r > screenWidth) {
                        r = screenWidth;
                        l = r - v.getWidth();
                    }
                    if (b > screenHeight) {
                        b = screenHeight;
                        t = b - v.getHeight();
                    }
                    v.layout(l, t, r, b);

                    int right = v.getRight();
                    int top = v.getTop();

                    layout(right - gap - fabWidth - v.getWidth(), top, right - gap - v.getWidth(), top + layoutHeight);

                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    v.postInvalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    int distance = (int) event.getRawX() - originX + (int)event.getRawY() - originY;

                    if (Math.abs(distance) < 50) {

                    } else {
                        return true;
                    }
            }
            return true;
        }
        return true;
    }

}


