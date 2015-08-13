package com.lavenderangle.util;

/**
 * Created by disanyun on 2015/7/13 0013.
 * 字符串工具类
 */
public class LAStringUtil {

    /**
     * 判断字符串是否为null或者空值
     * @return
     */
    public  static  boolean isEmpty(String string){
        return string == null || string.equals("null") || string.trim().length() == 0;
    }
}
