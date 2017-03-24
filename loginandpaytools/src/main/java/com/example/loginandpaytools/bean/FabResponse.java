package com.example.loginandpaytools.bean;

/**
 * Created by linsawako on 2017/3/17.
 */

public class FabResponse {


    /**
     * state : 1
     * data : {"open":"1","account_url":"http://api.900sy.com/?ct=safe&token=","bbs_url":"http://api.900sy.com/?ct=bbs&token=","chat_url":"http://api.900sy.com/?ct=chat&token="}
     * msg :
     */

    private int state;
    private DataBean data;
    private String msg;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * open : 1
         * account_url : http://api.900sy.com/?ct=safe&token=
         * bbs_url : http://api.900sy.com/?ct=bbs&token=
         * chat_url : http://api.900sy.com/?ct=chat&token=
         */

        private String open;
        private String account_url;
        private String bbs_url;
        private String chat_url;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getAccount_url() {
            return account_url;
        }

        public void setAccount_url(String account_url) {
            this.account_url = account_url;
        }

        public String getBbs_url() {
            return bbs_url;
        }

        public void setBbs_url(String bbs_url) {
            this.bbs_url = bbs_url;
        }

        public String getChat_url() {
            return chat_url;
        }

        public void setChat_url(String chat_url) {
            this.chat_url = chat_url;
        }
    }
}
