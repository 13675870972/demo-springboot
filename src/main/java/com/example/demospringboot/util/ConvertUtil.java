package com.example.demospringboot.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: caoyc
 * @Date: 2018/12/10 上午9:09
 * @Description:
 */
public class ConvertUtil {

    /**
     * json -> map
     * @param objStr
     * @return
     */
    public static Map<String, String> json2Map(String objStr) {
        Map<String, String> data = new TreeMap<String, String>();
        JSONObject obj = JSONObject.parseObject(objStr);
        if (StringUtils.isEmpty(objStr)) {
            return data;
        }
        for (String key : obj.keySet()) {
            String value = obj.getString(key);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            data.put(key, value);
        }
        return data;
    }

    /**
     * map -> form
     * @param map
     * @return
     */
    public static String map2Form(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        if (map == null || map.size() == 0) {
            return stringBuilder.toString();
        } else {
            for (Map.Entry<String, String> entry :
                    map.entrySet()) {
                stringBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        }
    }

}
