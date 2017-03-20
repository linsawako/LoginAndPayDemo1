package com.example.loginandpaytools.Utils;

import com.example.loginandpaytools.Support.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by jerry on 17-2-20.
 */

public class SignUtil {


    public static String getSign(SortedMap<String, Object> sortedMap) {
        StringBuffer sb = new StringBuffer();
        sb.append(Configuration.SIGN_KEY);
        //所有参与传参的参数按照升序排序
        Set ex = sortedMap.entrySet();
        Iterator it = ex.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        //删去最后一个&
        sb.deleteCharAt(sb.length() - 1);
        //String sign = SignUtil.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        //String sign = SignUtil.MD5Encode(sb.toString(), "UTF-8");
        String sign = SignUtil.MD5Encode(sb.toString());

        return sign;
    }

    public static String MD5Encode(String origin) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(origin.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

}
