package com.example.loginandpaytools.Support;

/**
 * Created by linsawako on 2017/3/4.
 */

public class LoginEvent {

    String loginCode;

    public LoginEvent(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }
}