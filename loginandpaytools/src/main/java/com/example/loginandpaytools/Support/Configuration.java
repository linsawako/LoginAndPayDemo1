package com.example.loginandpaytools.Support;

import com.example.loginandpaytools.common.Config;

/**
 * Created by jerry on 17-3-1.
 */

public class Configuration {

    public static final String API_HOST = "http://api.900sy.com/";

    //设备唯一标识码
    public static String DEVICE_ID = "imei";

    //应用ID
    public static int GAME_ID = Config.game_id;

    //渠道ID
    public static int CHANNEL_ID = Config.channel_id;

    //包标示
    public static String PACKAGE_NAME = Config.package_name;

    //临时通行码
    public static String CODE = "";

    //用户ID
    public static int UID = Config.uid;

    //用户长期通行码
    public static String TOKEN = Config.token;

    //服务器ID
    public static int SERVER_ID = Config.server_id;

    //角色ID
    public static int ROLE_ID = Config.role_id;

    //角色名
    public static String ROLE_NAME = Config.role_name;

    //角色当前等级
    public static int ROLE_LEVEL = Config.role_level;

    //签名所需KEY
    public static String SIGN_KEY = "HSF&$G*BHJ#KGEF(#@";
}
