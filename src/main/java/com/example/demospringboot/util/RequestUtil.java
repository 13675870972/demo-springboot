package com.example.demospringboot.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Author: caoyc
 * @Date: 2018/12/10 上午10:56
 * @Description:
 */
public class RequestUtil {
    public static String getParameterMapString(HttpServletRequest request) {
        Map map=request.getParameterMap();
        Set keSet=map.entrySet();
        StringBuilder result = new StringBuilder();

        for(Iterator itr = keSet.iterator(); itr.hasNext();){
            Map.Entry me=(Map.Entry)itr.next();
            Object ok=me.getKey();
            Object ov=me.getValue();
            String[] value=new String[1];
            if(ov instanceof String[]){
                value=(String[])ov;
            }else{
                value[0]=ov.toString();
            }
            for(int k=0;k<value.length;k++){
                result = result.append(String.valueOf(ok)).append("=").append(value[k]).append("&");
            }
        }
        String resultStr = result.toString();
        if (StringUtils.isEmpty(resultStr)) {
            return resultStr;
        }
        return resultStr.substring(0,resultStr.length()-1);
    }

    public static Map getParameterAllMap(HttpServletRequest request) {
        Map result = new HashMap();
        Map map=request.getParameterMap();
        Set keSet=map.entrySet();

        for(Iterator itr = keSet.iterator(); itr.hasNext();){
            Map.Entry me=(Map.Entry)itr.next();
            Object ok=me.getKey();
            Object ov=me.getValue();
            String[] value=new String[1];
            if(ov instanceof String[]){
                value=(String[])ov;
            }else{
                value[0]=ov.toString();
            }
            for(int k=0;k<value.length;k++){
                result.put(String.valueOf(ok), value[k]);
            }
        }
        return result;
    }
}
