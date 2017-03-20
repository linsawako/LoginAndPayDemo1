package com.example.loginandpaytools.bean;

/**
 * Created by linsawako on 2017/2/22.
 */


public class Order {


    /*用户ID*/
    private int uid;

    /*用户长期通行码*/
    private String token;

    /*应用ID*/
    private int game_id;

    /*包标识*/
    private String package_name;

    /*游戏方订单号*/
    private String order_num;

    /*订单金额，单位为分*/
    private int total_fee;

    /*订单名称*/
    private String order_name;

    /*支付方式：0：appstore，1：支付宝，2：微信*/
    private int pay_type;

    /*服务器ID*/
    private int server_id;

    /*角色ID（2.15添加）*/
    private int role_id;

    /*角色名*/
    private String role_name;

    /*角色当前等级*/
    private int role_level;

    /*签名*/
    private String Sign;

    //使用builder模式构建
    public Order(Builder builder) {
        this.game_id = builder.game_id;
        this.uid = builder.uid;
        this.token = builder.token;
        this.package_name = builder.package_name;
        this.order_name = builder.order_name;
        this.total_fee = builder.total_fee;
        this.order_num = builder.order_num;
        this.pay_type = builder.pay_type;
        this.server_id = builder.server_id;
        this.role_name = builder.role_name;
        this.role_level = builder.role_level;
        this.role_id = builder.role_id;
        this.Sign = builder.Sign;

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public void setRole_level(int role_level) {
        this.role_level = role_level;
    }

    public int getRole_level() {
        return role_level;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }


    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public static class Builder{
        /*用户ID*/
        private int uid;

        /*用户长期通行码*/
        private String token;

        /*应用ID*/
        private int game_id;

        /*包标识*/
        private String package_name;

        /*游戏方订单号*/
        private String order_num;

        /*订单金额，单位为分*/
        private int total_fee;

        /*订单名称*/
        private String order_name;

        /*支付方式：0：appstore，1：支付宝，2：微信*/
        private int pay_type;

        /*服务器ID*/
        private int server_id;

        /*角色ID（2.15添加）*/
        private int role_id;

        /*角色名*/
        private String role_name;

        /*角色当前等级*/
        private int role_level;

        /*签名*/
        private String Sign;


        public Builder setGame_id(int game_id) {
            this.game_id = game_id;
            return this;
        }

        public Builder setOrder_name(String order_name) {
            this.order_name = order_name;
            return this;
        }

        public Builder setOrder_num(String order_num) {
            this.order_num = order_num;
            return this;
        }

        public Builder setPackage_name(String package_name) {
            this.package_name = package_name;
            return this;
        }

        public Builder setPay_type(int pay_type) {
            this.pay_type = pay_type;
            return this;
        }

        public Builder setRole_level(int role_level) {
            this.role_level = role_level;
            return this;
        }

        public Builder setRole_name(String role_name) {
            this.role_name = role_name;
            return this;
        }

        public Builder setServer_id(int server_id) {
            this.server_id = server_id;
            return this;
        }

        public Builder setSign(String sign) {
            Sign = sign;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setTotal_fee(int total_fee) {
            this.total_fee = total_fee;
            return this;
        }

        public Builder setUid(int uid) {
            this.uid = uid;
            return this;
        }

        public Builder setRole_id(int role_id) {
            this.role_id = role_id;
            return this;
        }

        public Order create(){
            return new Order(this);
        }
    }
}
