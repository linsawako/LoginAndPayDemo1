package com.example.loginandpaytools.bean;

import java.io.Serializable;

/**
 * Created by jerry on 17-2-20.
 */

public class JsonBean<T> {
    public int state;
    public String msg;
    public T data;


    /**
     * 激活接口返回的data类型
     */
    public class Active implements Serializable{

    }

    /**
     * 获取随机用户名和密码返回的data类型
     */
    public class RandomNameAndPassword implements Serializable{
       public String username;
        public String password;
    }

    /**
     * 登陆返回的data类型
     */
    public class Login implements Serializable{
        public String code;
        public String token;
    }

    /**
     * 注册返回的data类型
     */
    public class Register implements Serializable{

    }

    /**
     * 角色登陆返回的data类型
     */
    public class RoleLogin implements Serializable{

    }
}

