package com.pcms.util;

import org.apache.commons.lang3.StringUtils;

public class UnicodeUtil {

    public static String string2Unicode(String string) {

        if (StringUtils.isBlank(string)) {
            return null;
        }

        char[] bytes = string.toCharArray();
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            char c = bytes[i];

            // 标准ASCII范围内的字符，直接输出
            if (c >= 0 && c <= 127) {
                unicode.append(c);
                continue;
            }
            String hexString = Integer.toHexString(bytes[i]);

            unicode.append("\\u");

            // 不够四位进行补0操作
            if (hexString.length() < 4) {
                unicode.append("0000".substring(hexString.length(), 4));
            }
            unicode.append(hexString);
        }
        return unicode.toString();
    }

    public static String unicode2String(String unicode) {
        if (StringUtils.isBlank(unicode)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = unicode.indexOf("\\u", pos)) != -1) {
            sb.append(unicode.substring(pos, i));
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }
        //如果pos位置后，有非中文字符，直接添加
        sb.append(unicode.substring(pos));

        return sb.toString();
    }

    public static void main(String[] args) {
        String name ="你好";
        String ename=UnicodeUtil.string2Unicode(name);
        System.out.println(ename);

        String dname ="\\u4f60\\u597d";
        System.out.println(UnicodeUtil.unicode2String(dname));
    }


}
