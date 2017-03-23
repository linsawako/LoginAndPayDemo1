package com.example.loginandpaytools.common;

import com.example.loginandpaytools.bean.Order;


/**
 * Created by linsawako on 2017/2/22.
 */

public class Config {

    /*需要post的参数*/

    public static int channel_id;

    public static int uid;

    public static String token;

    public static int game_id;

    public static String package_name;

    public static int server_id;

    public static int role_id;

    public static String role_name;

    public static int role_level;

    public static Order.Builder getOrderBuilder() {
        return new Order.Builder()
                .setGame_id(game_id)
                .setPackage_name(package_name)
                .setToken(token)
                .setUid(uid)
                .setServer_id(server_id)
                .setRole_id(role_id)
                .setRole_name(role_name)
                .setRole_level(role_level);
    }

    public static Order order;

    public static int payType;

    public static String ext;

    public static String platform_order_num;

    public final static String SIGN_KEY = "HSF&$G*BHJ#KGEF(#@";

}
