package com.example.loginandpaytools.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by linsawako on 2017/2/23.
 */

public class SignUtil {

    private static final String TAG = "SignUtil";


    /**
     * sign参数签名
     * @param map
     * @return
     */
    public static String sign(Map<String, String> map) {

        //参数名按照升序排列
        List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HSF&$G*BHJ#KGEF(#@");

        for(int i = 0; i < list.size(); i++){
            Map.Entry<String, String> mapping = list.get(i);
            if (i == 0) {
                stringBuilder.append(mapping.getKey() + "=" + mapping.getValue());
            }else{
                stringBuilder.append("&" + mapping.getKey() + "=" + mapping.getValue());
            }
        }

        return getMd5(stringBuilder.toString());

    }


    /**
     * md5加密
     * @param plainText
     * @return
     */
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

}
