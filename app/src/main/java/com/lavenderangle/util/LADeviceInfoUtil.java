package com.lavenderangle.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by disanyun on 2015/7/13 0013.
 * �豸��Ϣ��ȡ������
 */
public class LADeviceInfoUtil {

    /**
     * ��ȡMac��ַ
     * @param context
     * @return
     */
    public static String getMac(Context context) {
        try {
            // ��ȡwifi����
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            // �ж�wifi�Ƿ���
            if (!wifiManager.isWifiEnabled()) {
                return "aa:22:33:44:%5";
                // wifiManager.setWifiEnabled(true);
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String macAddress = wifiInfo.getMacAddress();
            return macAddress;
        } catch (Exception e) {
            e.printStackTrace();
            return "aa:22:33:44:%5";
        }
    }

    /**
     * ��ȡiP��ַ
     * @param context
     * @return
     */
    public static String getIp(Context context) {
        // // ��ȡwifi����
        try {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            // �ж�wifi�Ƿ���
            if (!wifiManager.isWifiEnabled()) {
                // wifiManager.setWifiEnabled(true);
                return "10.0.0.1";
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF)
                    + "." + ((ipAddress >> 16) & 0xFF) + "."
                    + (ipAddress >> 24 & 0xFF);
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
            return "10.0.0.1";
        }
    }

    /**
     * ��ȡ�豸imei��
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String imei = "imei";
        imei = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return imei;
    }

    /**
     * ��ȡ�豸Ʒ��
     * @param context
     * @return
     */
    public static String getDeviceBrand(Context context) {
        return Build.BRAND;
    }

    /**
     * ��ȡ�豸����ϵͳ
     * @return
     */
    public static String getOs() {
        return Build.VERSION.RELEASE;
    }

    /**
     * ��ȡ�豸�ֱ���
     * @param context
     * @return
     */
    public static String getResolution(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWMgrmWMgr = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mWMgrmWMgr.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels + "*" + metric.heightPixels;
    }

    /**
     * ��ȡ�豸��Ļ�ܶ�
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWMgrmWMgr = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mWMgrmWMgr.getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }

    /**
     * ��ȡ�豸��Ļ���
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWMgrmWMgr = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mWMgrmWMgr.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * ��ȡ�豸��Ļ�߶�
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWMgrmWMgr = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mWMgrmWMgr.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * ��ȡ��Ӫ��
     * @param context
     * @return
     */
    public static String getOperator(Context context) {
        TelephonyManager tel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String simOperator = tel.getSimOperatorName();
        return simOperator;
    }

    /**
     * ��ȡ�豸�������� ��ͨ��3GΪUMTS��HSDPA���ƶ�����ͨ��2GΪGPRS��EDGE�����ŵ�2GΪCDMA�����ŵ�3GΪEVDO
     * @param context
     * @return  "WIFI","2G","3G","4G"
     */
    public static String getNetworkType(Context context) {
        String type = "NONE";
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                type = "WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA) {
                    type = getOperator(context) + "2G";// "CDMA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                    type = getOperator(context) + "2G";// "EDGE";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_0) {
                    type = getOperator(context) + "3G";// "EVDO0";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                    type = getOperator(context) + "3G";// "EVDOA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
                    type = getOperator(context) + "GPRS";// "GPRS";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSDPA) {
                    type = getOperator(context) + "3G";// "HSDPA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSPA) {
                    type = getOperator(context) + "3G";// "HSPA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSUPA) {
                    type = getOperator(context) + "3G";// "HSUPA";
                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_UMTS) {
                    type = getOperator(context) + "3G";// "UMTS";
                }
            }
        }
        return type;
    }

}
