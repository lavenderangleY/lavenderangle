package com.lavenderangle.util;

/**
 * Created by disanyun on 2015/7/13 0013.
 * �ַ���������
 */
public class LAStringUtil {

    /**
     * �ж��ַ����Ƿ�Ϊnull���߿�ֵ
     * @return
     */
    public  static  boolean isEmpty(String string){
        return string == null || string.equals("null") || string.trim().length() == 0;
    }
}
