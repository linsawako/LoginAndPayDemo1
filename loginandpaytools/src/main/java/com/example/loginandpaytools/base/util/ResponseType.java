package com.example.loginandpaytools.base.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by linsawako on 2017/2/26.
 */

public class ResponseType {

    public static String getType(String string) {
        if (isJson(string)){
            return "Json";
        }
        else{
            return "html";
        }
    }


    /**
     * 判断是否是json结构
     */
    public static boolean isJson(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }


}
