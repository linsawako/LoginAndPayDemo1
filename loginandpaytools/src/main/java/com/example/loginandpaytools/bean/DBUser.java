package com.example.loginandpaytools.bean;

import android.provider.BaseColumns;

/**
 * Created by jerry on 17-2-22.
 */

public class DBUser  {


    public static final class User implements BaseColumns {
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String DATE = "date";
        public static final String ISSAVED = "issaved";
    }
}
