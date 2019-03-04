package com.example.demospringboot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
//import java.util.function.BiConsumer;
//import java.util.function.Consumer;

/**
 * Created by haoxz11 on 2017/12/6.
 * 安全工具类
 */
@Slf4j
public class ParamSecurityUtil {

    private static StringBuilder forEachObject(final JSONObject object) {
        StringBuilder value = new StringBuilder();
//        TreeMap<String, String> treeMap = new TreeMap<>();
//        object.forEach(new BiConsumer<String, Object>() {
//            @Override
//            public void accept(String s, Object o) {
//                if (o instanceof JSONObject) {
//                    treeMap.put(s, forEachObject((JSONObject) o).toString());
//                } else if (o instanceof JSONArray) {
//                    treeMap.put(s, new StringBuilder().append("[").append(forEachArray((JSONArray) o)).append("]").toString());
//                } else {
//                    if (o.toString().length() > 0) {
//                        treeMap.put(s, o.toString());
//                    }
//                }
//            }
//        });
//        treeMap.forEach(new BiConsumer<String, String>() {
//            @Override
//            public void accept(String s, String s2) {
//                if (value.length() > 0) {
//                    value.append(",");
//                }
//                value.append(s).append(":").append(s2);
//            }
//        });
        return value;
    }

    /**
     * 对数组循环
     *
     * @param object
     * @return
     */
    private static StringBuilder forEachArray(final JSONArray object) {
        StringBuilder value = new StringBuilder();
//        object.forEach(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) {
//                if (value.length() > 0) {
//                    value.append(",");
//                }
//                if (o instanceof JSONObject) {
//                    value.append(forEachObject((JSONObject) o));
//                } else if (o instanceof JSONArray) {
//                    value.append(new StringBuilder().append("[").append(forEachArray((JSONArray) o)).append("]"));
//                } else {
//                    value.append(o.toString());
//                }
//            }
//        });
        return value;
    }

    /**
     * 取MD5
     *
     * @param value
     * @return
     */
    private static String getMD5(String value) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes("utf-8"));
            byte[] hash = md.digest();

            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        }
        return hexString.toString().toUpperCase();
    }

    /**
     * 得到参数的安全码
     *
     * @param param
     * @param publicKey
     * @param secretKey
     * @return
     */
    public static String getParamSecure(final Object param, final String publicKey, final String secretKey) {

        JSONObject object = JSON.parseObject(JSON.toJSONString(param));
        if (object.containsKey("accessToken")) {
            object.fluentRemove("accessToken");
        }
        StringBuilder value = forEachObject(object).append(":").append(publicKey).append(":").append(secretKey);

        return getMD5(value.toString());
    }

}
