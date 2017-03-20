package com.example.loginandpaytools.bean;

import java.io.Serializable;

/**
 * Created by jerry on 17-2-21.
 */

public class SimpleResponse implements Serializable {

    public int state;

    public String msg;

    public JsonBean toJsonBean(){
        JsonBean jsonBean = new JsonBean();
        jsonBean.state = state;
        jsonBean.msg = msg;
        return jsonBean;
    }
}
