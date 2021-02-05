package com.chone.fightpet.common;

/**
 * Create 2021-02-05 10:07
 * CodeUtils
 *
 * @author chone
 */
public class CodeUtils {
    /**
     * unicode转字符串
     *
     * @param unicode uni
     * @return res
     */
    public static String unicodeToString(String unicode) {
        StringBuilder sb = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        System.out.println("hex = " + hex.length);
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }

    /**
     * 字符串转unicode
     *
     * @param str string
     * @return uni
     */
    public static String stringToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            sb.append("\\u").append(Integer.toHexString(c));
        }
        return sb.toString();
    }
}
