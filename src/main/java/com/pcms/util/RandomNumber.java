package com.pcms.util;

/**
 * 随机数生成工具类
 *
 * @author Chen.Biao
 * @ClassName: RandomNumber
 * @date 2016-11-4 下午05:02:02
 */
public class RandomNumber {

    /**
     * 获取随机数
     *
     * @param sLen
     * @return String
     */
    public static String randomKey(int sLen) {
        String base;
        String temp;
        int i;
        int p;

        base = "0123456789";
        temp = "";
        for (i = 0; i < sLen; i++) {
            p = (int) (Math.random() * 10);
            temp += base.substring(p, p + 1);
        }
        return (temp);
    }

    /**
     * 数字字母混合随机数
     *
     * @param sLen
     * @return String
     * @Title: ConfirmId
     */
    public static String ConfirmId(int sLen) {
        String base;
        String temp;
        int i;
        int p;
        base = "1234567890abcdefghijklmnopqrstuvwxyz";
        temp = "";
        for (i = 0; i < sLen; i++) {
            p = (int) (Math.random() * 37);
            if (p > 35)
                p = 35;
            temp += base.substring(p, p + 1);
        }
        return (temp);
    }

    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + "0x" + s4;
        }
        return str;
    }

    public static String toHexString(int s) {
        String s4 = "0x" + Integer.toHexString(s);
        return s4;
    }


    public static void main(String[] args) {
        System.out.println(ConfirmId(20).toUpperCase());
    }
}
