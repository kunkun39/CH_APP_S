package com.changhong.common.utils;


import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午11:54
 */
public class CHStringUtils {

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String convertListToSQLIn(List<Integer> ids) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            if (i == ids.size() - 1) {
                buffer.append(id + "");
            } else {
                buffer.append(id + ",");
            }
        }
        return buffer.toString();
    }

    public static String convertListToSQLIn(String[] ids) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            if (i == ids.length - 1) {
                buffer.append(id + "");
            } else {
                buffer.append(id + ",");
            }
        }
        String convert = buffer.toString();
        return convert.lastIndexOf(",") == convert.length() - 1 ? convert.substring(0, convert.length() - 1) : convert;
    }

    public static String toFixNumberString(String value, int length) {
        if (value == null) {
            value = "";
        }
        while (value.length() < length) {
            value = "0" + value;
        }
        return value;
    }

    public static String toFixNumberStringAfterDot(String value, int length) {
        String[] tokens = StringUtils.delimitedListToStringArray(value, ".");
        if (tokens.length < 2) {
            return "0.0000";
        }
        while (tokens[1].length() < length) {
            tokens[1] = tokens[1] + "0";
        }
        return tokens[0] + "." + tokens[1];
    }
}
