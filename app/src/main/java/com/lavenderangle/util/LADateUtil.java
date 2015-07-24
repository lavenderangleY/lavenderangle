package com.lavenderangle.util;

import java.util.TimeZone;

/**
 * Created by disanyun on 2015/7/22 0022.
 */
public class LADateUtil {
    /**
     * &#x83b7;&#x53d6;&#x65f6;&#x533a;
     * @return
     */
    public static String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String s = tz.getDisplayName(false, TimeZone.SHORT);// +",Timezon id:"
        // +tz.getID();
        return createGmtOffsetString(true, true, tz.getRawOffset());
    }

    public static String createGmtOffsetString(boolean includeGmt,
                                               boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }
}
