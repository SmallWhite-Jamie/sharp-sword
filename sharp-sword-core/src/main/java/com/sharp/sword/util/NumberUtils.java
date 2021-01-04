package com.sharp.sword.util;

/**
 * NumberUtils
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/4/28 14:55
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils  {
    /**
     * Integer 转 int
     * @param source
     * @return
     */
    public static int toInt(final Integer source) {
        return toInt(source, 0);
    }

    /**
     * Integer 转 int
     * @param source
     * @param defaultValue
     * @return
     */
    public static int toInt(final Integer source, final int defaultValue) {
        return source == null ? 0 : source;
    }

    /**
     * 转字符串
     * @param source
     * @param defaultValue
     * @return
     */
    public static String toStr(Number source, final String defaultValue) {
        if (source == null) {
            return defaultValue;
        } else {
            return source.toString();
        }
    }

    /**
     * 转字符串
     * @param source
     * @return
     */
    public static String toStr(Number source) {
        return toStr(source, "");
    }

}
