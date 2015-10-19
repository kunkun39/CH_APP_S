package com.changhong.common.utils;

import java.util.Random;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午5:05
 */
public class NumberUtils {

    public static int generateRandomNumber(int range) {
        Random rand = new Random();
        return rand.nextInt(range);
    }

    public static int hexStr2Number(String hexStr) {
        int num = Integer.parseInt(hexStr, 16);
        return num;
    }

    public static String numberToHexStr(int number) {
        return Integer.toHexString(number);
    }

    public static void main(String[] arges) {
        int i = NumberUtils.hexStr2Number("12dfdfdf");
        System.out.println(i);

        i = i + 1;
        String str = NumberUtils.numberToHexStr(i);
        System.out.println(str);
    }
}
